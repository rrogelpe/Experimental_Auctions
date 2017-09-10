/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experimental_auctions;

import static experimental_auctions.Player.randInt;

/**
 *
 * @author Rodrigo
 */
public class Auction {
    private final Player[] arr_player;
    private final Archive G1Archive;
    private final Archive RSOP1Archive;
    private final Archive G3Archive;
    private final Archive RSOP3Archive;
    private final Archive PPArchive;
    private final Archive CPArchive;
    private final Archive optArchive;
    private final Directory directory;  
    // O(n*log(n))
    public static void sort(Player[] bundle, int low, int high, int greedy){
        int N = high - low;
        if (N <= 1)
            return;
        int mid = low + N/2;
        // recursively sort
        sort(bundle, low, mid, greedy);
        sort(bundle, mid, high, greedy);
        // merge two sorted sub-arrays
        Player[] temp = new Player[N];
        int i = low, j = mid;
        if (greedy == 1) {
            for (int k = 0; k < N; k++) {
                if (i == mid)
                    temp[k] = bundle[j++];
                else if (j == high)
                    temp[k] = bundle[i++];
                else if (bundle[j].getBid()>bundle[i].getBid())
                    temp[k] = bundle[j++];
                else
                    temp[k] = bundle[i++];
            }
        }
        else if (greedy == 2) {
            for (int k = 0; k < N; k++) {
                if (i == mid)
                    temp[k] = bundle[j++];
                else if (j == high)
                    temp[k] = bundle[i++];
                else if (bundle[j].getBundle().length<bundle[i].getBundle().length)
                    temp[k] = bundle[j++];
                else
                 temp[k] = bundle[i++];
            }
        }
        else {
            for (int k = 0; k < N; k++) {
                if (i == mid)
                    temp[k] = bundle[j++];
                else if (j == high)
                    temp[k] = bundle[i++];
                else if (bundle[j].getPPU()>bundle[i].getPPU())
                    temp[k] = bundle[j++];
                else
                    temp[k] = bundle[i++];
            }
        }
        System.arraycopy(temp, 0, bundle, low, N);
    }
    //Constructor O(n^4)-W/O OPT 
    public Auction(Market market,int number_players,int distribution) {
        Player[] player_arr;  
        Player[] arr;
        directory = new Directory();
        
        G1Archive = new Archive(number_players);
        G1Archive.setGoods(market.size());
        G3Archive = new Archive(number_players);
        G3Archive.setGoods(market.size());
        RSOP1Archive = new Archive(number_players);
        RSOP1Archive.setGoods(market.size());
        RSOP3Archive = new Archive(number_players);
        RSOP3Archive.setGoods(market.size());
        PPArchive = new Archive(number_players);
        PPArchive.setGoods(market.size());
        CPArchive = new Archive(number_players);
        CPArchive.setGoods(market.size());
        arr_player = getPlayerList(number_players,distribution,market); 
        //O(c^n)
        maxWelfare();
        optArchive = directory.getOptimalArchive();
        G1Archive.setOptimal(optArchive.getSocialWelfare());
        G3Archive.setOptimal(optArchive.getSocialWelfare());
        RSOP1Archive.setOptimal(optArchive.getSocialWelfare());
        RSOP3Archive.setOptimal(optArchive.getSocialWelfare());
        PPArchive.setOptimal(optArchive.getSocialWelfare());
        CPArchive.setOptimal(optArchive.getSocialWelfare());
        
        player_arr = clone(arr_player); 
        //O(n^2)
        getWinnerList(player_arr,1,market);
        //O(n^4)
        calcGreedyPrices(player_arr,1,true);
        
        player_arr = clone(arr_player);
        //O(n^2)
        getWinnerList(player_arr,3,market);
        //O(n^4)
        calcGreedyPrices(player_arr,3,true);
        
        player_arr = clone(arr_player); 
        //O(n^2)
        Player[] arr_list = getWinnerList(player_arr,3,market);
        calcRSOPrices(arr_list);
        
        player_arr = clone(arr_player);
        arr = getWinnerList(player_arr,3,market);
        calcProPrice(arr);
        
        player_arr = clone(arr_player);
        arr = getWinnerList(player_arr,3,market);
        calcConsPrice(arr);
    }    
    // O(n)-LINEAR
    private Player[] getPlayerList(int number_Players,int distribution,Market market) {
        Player[] arr = new Player[number_Players];
        Player player;
        // O(n)
        for (int i=0;i<arr.length;i++) {
            player = new Player((i+1),distribution,market);
            arr[i] = player;
        }
        return arr;
    }
    // O(n)-LINEAR
    private Player[] clone(Player[] arr) {
        Player[] arr_clone = new Player[arr.length];
        Player player_clone;
        // O(n)
        for (int i=0;i<arr.length;i++) {
            player_clone = new Player(arr[i].getID(),arr[i].getBundle(),(int) arr[i].getBid(),arr[i].getPrice());
            arr_clone[i] = player_clone;
        }
        return arr_clone;
    }
    // O(n^2)+O(2n)+O(n*log(n))-QUADRATIC
    private Player[] getWinnerList(Player[] arr,int greedy ,Market market){
        Player[] arr_winner;
        // O(n*log(n))
        sort(arr,0,arr.length,greedy);
        int number_winners = 0;
        // O(n)
        for (Player player : arr) {
            // O(n)
            insideloop:
            for (int j = 0; j < player.getBundle().length; j++) {
                // O(1)
                if (j == player.getBundle().length - 1) {
                    if (player.getBundle()[j].isAvailable() == true) {
                        player.win();
                        number_winners++;
                    }
                }
                // O(1)
                else if (player.getBundle()[j].isAvailable() == false) {
                    break;
                }
            }
        }
        arr_winner = new Player[number_winners];
        // O(n)
        for (int i=arr.length-1;i>-1;i--) {
            // O(1)
            if (arr[i].didWin() == true) {
                arr_winner[--number_winners] = arr[i];
            }
        }
        // O(n)
        market.restore();
        return arr_winner;
    }    
    // O(n^4)-POLYNOMIAL
    private void calcGreedyPrices(Player[] arr,int greedy,boolean flag) { 
        Player player;
        int x = 0;
        // O(n)
        for (int i=0;i<arr.length;i++) {
            if (arr[i].didWin() == true) {
                Player winner = arr[i];
                // O(n)
                searchloop:
                for (int j=i;j<arr.length;j++) {
                    if (arr[j].didWin() == false) {
                        Player loser = arr[j];
                        // O(n)
                        for (Good good_A : winner.getBundle()) {
                            // O(n)
                            for (Good good_B : loser.getBundle()) {
                                if (good_A == good_B) {
                                    player = new Player();
                                    if (greedy == 1) {
                                        winner.setPrice(loser.getBid(),1);
                                        if (flag==true){
                                            winner.copy(player);
                                            G1Archive.addPlayer(player);
                                        }                      
                                    }
                                    else if (greedy == 3){
                                        winner.setPrice(loser.getPPU(),3);
                                        if (flag==true){
                                            winner.copy(player);
                                            G3Archive.addPlayer(player);
                                        }                                       
                                    }                                                                     
                                    break searchloop;
                                }
                            }
                        }
                    }
                    if (j == arr.length-1){
                        player = new Player();
                        if (greedy == 1){
                            winner.copy(player);
                            G1Archive.addPlayer(player);
                        }                      
                        else if (greedy == 3){
                            winner.copy(player);
                            G3Archive.addPlayer(player);
                        }                           
                    }
                }               
            }
        }
    } 
    //O(n)-LINEAR
    private int getDemand(Player[] arr){
        int demand = 0;
        //O(n)
        for (Player player : arr){
            demand+=player.getBundle().length;
        }
        return demand;
    }
    //O(n^2)-QUADRATIC
    private void calcRSOPrices(Player[] arr){
        int remaining;
        int rand;
        Archive partitionA;
        Archive partitionB;
        Player[] arr_partA;
        Player[] arr_partB;
        int demand;
        float bundle_price;
        float unit_price;
        if (arr.length>=2){
            remaining = arr.length;
            partitionA = new Archive(arr.length-1);
            partitionB = new Archive(arr.length-1);
            //O(n)
            while(remaining>0){
                rand=randInt(0,arr.length-1);
                //O(n)
                while(arr[rand]==null){
                    rand=randInt(0,arr.length-1);
                }
                Player player = arr[rand];
                arr[rand] = null;
                rand = randInt(0,1);
                if(rand==0){
                    if (partitionA.isFull()==false){
                        partitionA.addPlayer(player);
                    }
                    else{
                        partitionB.addPlayer(player);
                    }                   
                }
                else{
                    if (partitionB.isFull()==false){
                        partitionB.addPlayer(player);
                    }
                    else{
                        partitionA.addPlayer(player);
                    }
                }
                remaining--;
            } 
            arr_partA = new Player[partitionA.size()];
            arr_partB = new Player[partitionB.size()];
            //O(n)
            partitionA.export(arr_partA);
            //O(n)
            partitionB.export(arr_partB);
            //O(n^2)
            bundle_price = findBundlePrice(arr_partA);
            //O(n)
            setPrices2(bundle_price,arr_partB,1,RSOP1Archive);
            //O(n)
            demand = getDemand(arr_partA);
            //O(n^2)
            unit_price = findUnitPrice(arr_partA,demand);
            //O(n)
            setPrices2(unit_price,arr_partB,3,RSOP3Archive);
            //O(n^2)
            bundle_price = findBundlePrice(arr_partB);
            //O(n)
            setPrices2(bundle_price,arr_partA,1,RSOP1Archive);
            //O(n)
            demand = getDemand(arr_partB);
            //O(n^2)
            unit_price = findUnitPrice(arr_partB,demand);
            //O(n)
            setPrices2(unit_price,arr_partA,3,RSOP3Archive);
        }
    }
    //O(n^2)-QUADRATIC
    private float findBundlePrice(Player[] arr){
        //O(n*log(n))
        sort(arr,0,arr.length,1);
        float bundle_price = 0;
        int losers = 0;
        //O(n)
        for (int i=arr.length-1;i>-1;i--){           
            float temp = arr[i].getBid();
            if (i==arr.length-1) {
                bundle_price = temp;
            }
            else {
                if ((bundle_price*(arr.length-losers))<(temp*(i))) {
                    bundle_price = temp;
                    losers = i;
                }
            }
        }
        return bundle_price;
    }
    //O(n^2)-QUADRATIC
    private float findUnitPrice(Player[] arr,int demand){
        //O(n*log(n))
        sort(arr,0,arr.length,3);
        float unit_price = 0;
        int surplus = 0;
        int goods_left = 0;
        //O(n)
        for (int i=arr.length-1;i>-1;i--) {
            float temp = arr[i].getPPU();
            if (i==arr.length-1) {
                unit_price = temp;
            }
            else {
                if ((unit_price*(demand-surplus))<(temp*(demand-goods_left))) {
                    unit_price = temp;
                    surplus = goods_left;
                }
            }
            goods_left+=arr[i].getBundle().length;
        }
        return unit_price;
    }
    //O(n)-LINEAR
    private void setPrices2(float price,Player[] arr,int mechanism,Archive arch) {
        Player copiedPlayer;
        if (mechanism==1) {
            //O(n)
            for (Player player : arr) {
                copiedPlayer = new Player();
                if (player.getBid() >= price){                  
                    player.setPrice(price,1);
                    player.copy(copiedPlayer);
                    arch.addPlayer(copiedPlayer);
                }
            }
        }
        else {
            //O(n)
            for (Player player : arr) {
                copiedPlayer = new Player();
                if (player.getPPU() >= price){
                    player.setPrice(price,3);
                    player.copy(copiedPlayer);
                    arch.addPlayer(copiedPlayer);
                }
            }
        }
    }   
    
    private void calcProPrice(Player[] arr){
        int demand;
        float unit_price;
        demand = getDemand(arr);
        unit_price = findUnitPrice(arr,demand);
        setPrices2(unit_price,arr,3,PPArchive);  
    }
    
    private void calcConsPrice(Player[] arr){
        float constant_price;
        constant_price = findBundlePrice(arr);
        setPrices2(constant_price,arr,1,CPArchive);
    }
    // O(1)-CONSTANT
    public Archive getArchive(int code) {
        if (code == 1){
            return G1Archive;
        }
        else if (code == 2){
            return RSOP1Archive;
        }
        else if (code == 3){
            return G3Archive;
        }
        else if (code == 4){
            return RSOP3Archive;
        }
        else if (code == 5){
            return PPArchive;
        }
        else if (code == 6){
            return CPArchive;
        }
        else {
            return optArchive;
        }
    }
    //O(c^n)-EXPONENTIAL
    private void maxWelfare() {
        for (int i=0;i<arr_player.length;i++) {
            Player current = arr_player[i];
            current.win(); // set player i's goods to taken
            Archive archiveA = new Archive(arr_player.length); // create a new queue for winners
            archiveA.addPlayer(current); // add player i to that queue of winners
            directory.addArchive(true,archiveA); // add the queue of winners to the array of winner's queues (the array of winners combinations)
            findCompatible(archiveA,i); //start recursions assuming i as first winner            
            current.Default();
        }
    }
    //O(c^n)-EXPONENTIAL
    private void findCompatible(Archive archive,int j) {
        for (int i=j+1; i<arr_player.length; i++) { //Start from top of array going down
            Player current = arr_player[i];
            if(current.isCompatible()) { // if player is compatible (method in player class)
                Archive archiveB = new Archive(arr_player.length); // create new queue
                archive.copy(archiveB); // populate new queue with elements of the queue that was passed in
                current.win(); // set player's goods to taken
                archiveB.addPlayer(current); // place player in new winners' queue
                directory.addArchive(true,archiveB); // player winners' queue into collection of winning combinations
                findCompatible(archiveB,i); //Run method again with copied queue
                current.Default(); // after recursion set players goods to not taken
            }
        }
    }
    //O(1)
    public Player[] getList() {
        return arr_player;
    }
    //O(n) : Type 2 to display price
    public void printList(Player[] arr,int choose) {
        for (Player player : arr) {
            player.printSet(choose);
            System.out.println();
        }
    }
}

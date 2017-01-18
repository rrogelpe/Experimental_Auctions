/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experimental_auctions;

/**
 *
 * @author Rodrigo
 */
public class Archive {
    
    private final int players;
    private int goods;
    private int size;
    private int i;
    private float profit;
    private float social_welfare;
    private final Player[] arr_player;
    private float optimal;
    //Constructor
    public Archive(int players){
        this.players = players;
        this.profit = 0;
        this.social_welfare = 0;
        this.arr_player = new Player[players];
        this.optimal = 0;
        this.goods = 0;
        i = 0;
    }
    //O(1)-CONSTANT
    public void addPlayer(Player player){
        arr_player[i++] = player;
        social_welfare += player.getBid();
        profit += player.getPrice();
        size++;
    }
    //O(n)-LINEAR
    public void copy(Archive archive) {
        for(int j = 0; j < size; j++) {  
            archive.addPlayer(arr_player[j]);
        }
    }
    //O(n)-LINEAR
    public void export(Player[] arr_player2){
        System.arraycopy(arr_player, 0, arr_player2, 0, size);
    }
    //O(n)-LINEAR
    public void printArchive(int choice){
        for (int j=0;j<size;j++){
            arr_player[j].printSet(choice);
            System.out.println();
        }
    }
    public boolean isFull(){
        return size==arr_player.length;
    }
    public void setGoods(int goods){
        this.goods = goods;
    }
    public void setOptimal(float optimal){
        this.optimal = optimal;
    }
    public float getOPT(){
        return optimal;
    }
    public float getProfit(){
        return profit;
    }
    public float getSocialWelfare(){
        return social_welfare;
    }
    public int getPlayers(){
        return players;
    }
    public int getGoods(){
        return goods;
    }
    public int size(){
        return size;
    } 
    public float getPercent(){
        float ratio;
        ratio = (profit/optimal)*100;
        return ratio;
    }
}

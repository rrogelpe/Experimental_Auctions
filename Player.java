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
import static java.lang.Math.sqrt;
import java.util.Random;
//import jsc.distributions.Poisson;
//import jsc.distributions.Uniform;
//import jsc.distributions.Normal;

public class Player {
    private int ID;
    private int bid;
    private float price;
    private Good[] bundle;
    private boolean winner;
    
    //Random number generator
    public static int randInt(int min, int max) {
        // Usually this can be a field rather than a method variable
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    // Constructor A
    public Player(int id,int distribution,Market market) {
        this.ID = id;  
        winner = false;
        price = 0;
        genBundle(distribution,market);
        
        market.restore();
    }
    // Constructor B
    public Player(int id,Good[] bundle,int bid,float price) {
        this.ID = id;
        this.bundle = bundle;
        this.bid = bid;
        this.price = price;      
        this.winner = false;    
    } 
    public Player(){
        this.ID = 0;
        this.bundle = null;
        this.bid = 0;
        this.price = 0;      
    }
    // O(n)-LINEAR
    private void genBundle(int distribution, Market market) {
        int size=0;
        /* NOTE :  The smaller the distribution of bids the better RSOP 1 performs*/
        // O(1) 
        if (distribution == 1) {
            size = randInt(1,market.size());
            bid = randInt(75,100);
        }
        //O(1)
        else if (distribution == 2) {
            size = randInt(1,market.size());
            bid = randInt(50,100);
        }
        // O(1)
        else if (distribution == 3) {
            size = randInt(1,market.size());
            bid = randInt(25,100);
        }
        // O(1)
        else if (distribution == 4) {
            size = randInt(1,market.size());
            bid = randInt(1,100);
        }
        // O(1)
        else if (distribution == 5) {
            size = randInt(1,market.size());
            bid = randInt(1,25);
        }
        // O(1)
        else if (distribution == 6) {
            size = randInt(1,market.size());
            bid = randInt(1,50);
        }
        // O(1)
        else if (distribution == 7) {
            size = randInt(1,market.size());
            bid = randInt(1,75);
        }
        // O(1)
        else if (distribution == 8) {
            size = randInt(1,market.size());
            bid = randInt(95,100);
        }
        // O(1)
        else if (distribution == 9) {
            size = randInt(1,market.size());
            bid = randInt(50,55);
        }
        // O(1)
        else if (distribution == 10) {
            size = (int) (market.size()*.2);
//            size = randInt(1,market.size());
            bid = randInt(1,100);
        }
        /*NOTE : If bid is held constant RSOP1 always outperforms*/
        /*NOTE : If size is held constant the Greedy Algorithms perform the same and out perform */  
        bundle = new Good[size];
        genBundle2(market);
    }
    // O(n^2)-QUADRATIC
    private void genBundle2(Market market) {
        // O(n)
        for(int i=0;i<bundle.length;i++) {
            int random = randInt(1,market.size());
            Good good = market.selectGood(random);
            // O(n)
            while(good.isAvailable() == false) {
                random = randInt(1,market.size());
                good = market.selectGood(random);
            }
            bundle[i] = good;
            // O(1)
            good.taken();
        }
    }
    // O(n)-LINEAR : Type 2 to display price
    public void printSet(int choose) {
        if (choose == 1) {
            if (ID > 9) {
                System.out.print("| Bid "+ID+":($"+bid+",{");
            }
            else {
                System.out.print("| Bid "+ID+" :($"+bid+",{");
            }
        }
        else {
            if (ID > 9) {
                System.out.print("| Bid "+ID+":($"+price+",{");
            }
            else {
                System.out.print("| Bid "+ID+" :($"+price+",{");
            }
        }      
        for (int i=0;i<bundle.length;i++) {
            if (i==bundle.length-1) {
                System.out.print(bundle[i].getID());
            }
            else {
                System.out.print(bundle[i].getID()+",");
            }
        }
        System.out.print("})");
    }
    // O(1)-CONSTANT
    public void setPrice(float payment,int mechanism) {
        if (mechanism == 1) {
            this.price = payment;
        }
        else {
            this.price = (float) (payment*sqrt(bundle.length));
        }
    }
    // O(n)-LINEAR
    public void win() {
        for (Good good : bundle) {
            good.taken();
        }
        winner = true;
    }
    
    public void Default() {
        for (Good good : bundle) {
            good.available();
        }
        winner = false;
    }
    public boolean isCompatible() {
        for (Good good : bundle) {
            if (good.isAvailable() == false) {
                return false;
            }
        }
        return true;
    }
    public void copy(Player player){
        player.setID(ID);
        player.setPrice(price);
        player.setBid(bid);
        player.setBundle(bundle);
    }
    //All getter and return methods are O(1)-CONSTANT
    public int getID() {
        return ID;
    }
    public float getBid() {
        return bid;
    }
    public float getPrice() {
        return price;
    } 
    public float getPPU() {
        float ppu = (float) (bid/sqrt(bundle.length));
        return ppu;
    }   
    public Good[] getBundle() {
        return bundle;
    }  
    public boolean didWin() {
        return winner;
    } 
    public void setID(int id){
        this.ID = id;
    }
    public void setPrice(float price){
        this.price = price;
    }
    public void setBid(int bid){
        this.bid = bid;
    }
    public void setBundle(Good[] bundle){
        this.bundle = bundle;
    }
}
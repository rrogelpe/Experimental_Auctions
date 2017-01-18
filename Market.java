/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experimental_auctions;

import java.util.Random;

/**
 *
 * @author Rodrigo
 */
public class Market {
    private final Good[] arr;
    
    //Constructor : O(n)-LINEAR
    public Market(int number_Goods) {
        arr = new Good[number_Goods];
        addGoods();
    }
    //O(n)-LINEAR
    private void addGoods() {
        Good good;
        for(int i=0;i<arr.length;i++) {
            good=new Good(i+1);
            arr[i]=good;
        }
    }
    //O(1)-CONSTANT
    public Good selectGood(int id) {
        Good selected_Good=arr[id-1];
        return selected_Good;
    }  
    //O(n)-LINEAR
    public void restore() {
        for (Good good : arr) {
            if (good.isAvailable() == false) {
                good.available();
            }
        }  
    }
    //O(1)-CONSTANT
    public int size() {
        return arr.length;
    } 
    //O(n)-LINEAR
    public void printAvailableGoods() {
        for (Good good : arr) {
            if (good.isAvailable() == true) {
                System.out.print(good.getID() + " ");
            }
        }
    }
}

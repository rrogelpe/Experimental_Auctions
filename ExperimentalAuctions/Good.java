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
// O(1)-CONSTANT
public class Good {
    private int ID;
    private boolean available;
    private float price;
    
    public Good (int ID) {
        this.ID = ID;
        this.available = true;
        this.price = 0;
    }
    
    public void setGoodPrice(float price){
        this.price = price;
    }
    
    public void taken() {
        available = false;
    }

    public void available() {
        available = true;
    }
    
    public void setID (int id) {
	ID =id;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public int getID() {
        return ID;
    }
    
    public float getGoodPrice(){
        return price;
    }
}

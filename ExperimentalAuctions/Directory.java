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
public class Directory {
    
    private Archive[] arr_archive;
    private float[] arr_avg;
    private int i;
    private int j;
    private int size;
    private float sum_percent;
    private Archive optimal;
    //Constructor
    public Directory(){
        arr_archive = new Archive[2037483645/6];
        i = 0;
        size = 0; 
    }
    public Directory(int n){
        arr_archive = new Archive[n];
        arr_avg = new float[100];
        sum_percent = 0;
        i = 0;
        j = 0;
        size = 0; 
    }
    //O(1)-CONSTANT
    public void addArchive(boolean opt,Archive archive){
        arr_archive[i++] = archive;
        size++;
        if (opt == true){
            if (size == 1){
                optimal = archive;
            }
            else {
                if (optimal.getSocialWelfare() < archive.getSocialWelfare()) {
                    optimal = archive;
                }
            }
        }
        else{
            sum_percent += archive.getPercent();
            if ((size+1)%1000==0){
                arr_avg[j++] = sum_percent/1000;
                sum_percent = 0;
            }
        }   
    }
    //O(n)-LINEAR
    public void export(Archive[] arr_archive2){
        System.arraycopy(arr_archive, 0, arr_archive2, 0, size);
    }
    //O(1)-CONSTANT
    public float[] getAvgArr(){
        return arr_avg;
    }
    //O(1)-CONSTANT
    public Archive getOptimalArchive(){
        return optimal;
    } 
    //O(1)-CONSTANT
    public int size(){
        return size;
    }
}

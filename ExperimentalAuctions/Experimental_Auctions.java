/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experimental_auctions;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.write.WriteException;

/**
 *
 * @author Rodrigo
 */
public class Experimental_Auctions {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here  
        
        System.out.println("Auction Algorithms Simulator 1.2\n");
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);
        int choose;
        do {
            System.out.println("--------------------------------------------------------------");
            System.out.println("\n	    Main Menu\n");
            System.out.println("1. Generate Instance");
            System.out.println("2. Greedy Auction Algorithms");
            System.out.println("3. Random Sampling Optimal Pricing Auction Algorithms");
            System.out.println("4. Exit");
            
            System.out.println("\nSelect a Command :");
            do {
                while (!scan.hasNextInt()) {
                    System.out.println("Error : InputMismatchException\n Select a Command :");
                    scan.next();
                }
            } while (!scan.hasNextInt());
            choose = scan.nextInt();                      
            Market market;
            Auction auction;
            Archive G1Archive;
            Archive G3Archive;
            Archive RSOP1Archive;
            Archive RSOP3Archive;
            Archive PPArchive;
            Archive CPArchive;
            Directory G1Directory;
            Directory G3Directory;
            Directory RSOP1Directory;
            Directory RSOP3Directory;
            Directory PPDirectory;
            Directory CPDirectory;
            Writer writer;
            String fileName;
            int distribution = 0; 
            int supply;
            int bids;
            int sqrt;
            int size = 100;
            sqrt = (int) Math.sqrt(size);
            switch (choose){
                case 1:
                    System.out.println("Enter number of Goods");
                    do {
                        while (!scan.hasNextInt()) {
                            System.out.println("Error : InputMismatchException");
                            scan.next();
                        }
                    } while (!scan.hasNextInt());
                    supply = scan.nextInt();

                    System.out.println("Enter number of Players");
                    do {
                        while (!scan.hasNextInt()) {
                            System.out.println("Error : InputMismatchException");
                            scan.next();
                        }
                    } while (!scan.hasNextInt());
                    bids = scan.nextInt();                  
                    
  
                    System.out.println("-------------------------------------------------");
                    System.out.println("\n	    Distribution Selection \n");
                    System.out.println("1.  Random_1");
                    System.out.println("2.  Random_2");
                    System.out.println("3.  Random_3");
                    System.out.println("4.  Random_4");
                    System.out.println("5.  Random_5");
                    System.out.println("6.  Random_6");
                    System.out.println("7.  Random_7");
                    System.out.println("8.  Random_8");
                    System.out.println("9.  Random_9");
                    System.out.println("10. Random_10");
                    System.out.println("11. Back");
                    System.out.println("Choose distribution");
                    do {                          
                        if (!scan.hasNextInt()) {
                            System.out.println("Error : Invalid Input");
                            scan.next(); 
                        }
                        else {
                            distribution = scan.nextInt();
                            if (distribution > 11){
                                System.out.println("Error : Invalid Input");
                            }                                
                        }
                    } while (distribution < 1 || distribution > 11);
                    if (distribution != 11) {
                        market = new Market(supply);
                        auction = new Auction(market,bids,distribution);
                        Player[] arr_player = auction.getList();
                        Archive optArchive = auction.getArchive(7);
                        G1Archive = auction.getArchive(1);
                        G3Archive = auction.getArchive(3);
                        RSOP1Archive = auction.getArchive(2);
                        RSOP3Archive = auction.getArchive(4);
                        PPArchive = auction.getArchive(5);
                        CPArchive = auction.getArchive(6);

                        System.out.println("            ---Bids---");
                        auction.printList(arr_player,1); 
                        System.out.println("            ---Optimal Solution---");
                        optArchive.printArchive(1);
                        System.out.println("|    OPT     = $"+optArchive.getSocialWelfare());
                        System.out.println("            ---G1 Winners---");
                        G1Archive.printArchive(2);
                        System.out.println("|    P       = $"+G1Archive.getProfit());
                        System.out.println("|    P/OPT   = "+(int)G1Archive.getPercent()+"%");
                        System.out.println("            ---G3 Winners---");
                        G3Archive.printArchive(2);
                        System.out.println("|    P       = $"+G3Archive.getProfit());
                        System.out.println("|    P/OPT   = "+(int)G3Archive.getPercent()+"%");
                        System.out.println("            ---RSOP1 Winners---");
                        RSOP1Archive.printArchive(2);
                        System.out.println("|    P       = $"+RSOP1Archive.getProfit());
                        System.out.println("|    P/OPT   = "+(int)RSOP1Archive.getPercent()+"%");
                        System.out.println("            ---RSOP3 Winners---");
                        RSOP3Archive.printArchive(2);
                        System.out.println("|    P       = $"+RSOP3Archive.getProfit());
                        System.out.println("|    P/OPT   = "+(int)RSOP3Archive.getPercent()+"%");                   
                        System.out.println("            ---Proportional Pricing Winners---");
                        PPArchive.printArchive(2);
                        System.out.println("|    P       = $"+PPArchive.getProfit());
                        System.out.println("|    P/OPT   = "+(int)PPArchive.getPercent()+"%");
                        System.out.println("            ---Constant Pricing Winners---");
                        CPArchive.printArchive(2);
                        System.out.println("|    P       = $"+CPArchive.getProfit());
                        System.out.println("|    P/OPT   = "+(int)CPArchive.getPercent()+"%");
                    }                   
                    break;
                case 2:                                    
                    G1Directory = new Directory(size*1000);
                    G3Directory = new Directory(size*1000); 
                    do {
                        System.out.println("-------------------------------------------------");
                        System.out.println("\n	    Distribution Selection \n");
                        System.out.println("1.  Random_1");
                        System.out.println("2.  Random_2");
                        System.out.println("3.  Random_3");
                        System.out.println("4.  Random_4");
                        System.out.println("5.  Random_5");
                        System.out.println("6.  Random_6");
                        System.out.println("7.  Random_7");
                        System.out.println("8.  Random_8");
                        System.out.println("9.  Random_9");
                        System.out.println("10. Random_10");
                        System.out.println("11. Back");
                        System.out.println("Choose distribution");
                        do {                          
                            if (!scan.hasNextInt()) {
                                System.out.println("Error : Invalid Input");
                                scan.next(); 
                            }
                            else {
                                distribution = scan.nextInt();
                                if (distribution > 11){
                                    System.out.println("Error : Invalid Input");
                                }                                
                            }
                        } while (distribution < 1 || distribution > 11);
                        if (distribution != 11){
                            System.out.println("Generating Data...");                       
                            int iteration = 0;
                            for (bids = sqrt; bids<= size; bids += sqrt) {
                                for (supply = sqrt; supply <= size; supply += sqrt) {
                                    while (iteration != 1000) {
                                        market = new Market(supply);
                                        auction = new Auction(market,bids,distribution);
                                        G1Archive = auction.getArchive(1);
                                        G1Directory.addArchive(false,G1Archive);
                                        G3Archive = auction.getArchive(3);
                                        G3Directory.addArchive(false,G3Archive);
                                        iteration++;                                   
                                    }
                                    iteration = 0;
                                }
                            }
                            System.out.println("Save Data As :\n(format: .txt)");
                            while (scan.hasNextInt()) {
                                System.out.println("Error : Create file to save results:");
                                scan.next();
                            }
                            fileName = scan.next();
                            writer = new Writer(fileName);
                            try {
                                writer.writeExcel(G1Directory,G3Directory);
                            } catch (IOException | WriteException ex) {
                                Logger.getLogger(Experimental_Auctions.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }                       
                    } while (distribution != 11);
                    break;
                case 3:
                    G1Directory = new Directory(size*1000);
                    G3Directory = new Directory(size*1000);    
                    RSOP1Directory = new Directory(size*1000);
                    RSOP3Directory = new Directory(size*1000);
                    PPDirectory = new Directory(size*1000);
                    CPDirectory = new Directory(size*1000);
                    do {
                        System.out.println("-------------------------------------------------");
                        System.out.println("\n	    Distribution Selection \n");
                        System.out.println("1.  Random_1");
                        System.out.println("2.  Random_2");
                        System.out.println("3.  Random_3");
                        System.out.println("4.  Random_4");
                        System.out.println("5.  Random_5");
                        System.out.println("6.  Random_6");
                        System.out.println("7.  Random_7");
                        System.out.println("8.  Random_8");
                        System.out.println("9.  Random_9");
                        System.out.println("10. Random_10");
                        System.out.println("11. Back");
                        System.out.println("Choose distribution");
                        do {                          
                            if (!scan.hasNextInt()) {
                                System.out.println("Error : Invalid Input");
                                scan.next(); 
                            }
                            else {
                                distribution = scan.nextInt();
                                if (distribution > 11){
                                    System.out.println("Error : Invalid Input");
                                }                                
                            }
                        } while (distribution < 1 || distribution > 11);    
                        if (distribution != 11){
                            System.out.println("Generating Data...");                       
                            int iteration = 0;
                            for (bids = sqrt; bids<= size; bids += sqrt) {
                                for (supply = sqrt; supply <= size; supply += sqrt) {
                                    while (iteration != 1000) {
                                        market = new Market(supply);
                                        auction = new Auction(market,bids,distribution);
                                        G1Archive = auction.getArchive(1);
                                        G1Directory.addArchive(false,G1Archive);
                                        G3Archive = auction.getArchive(3);
                                        G3Directory.addArchive(false,G3Archive);
                                        RSOP1Archive = auction.getArchive(2);
                                        RSOP1Directory.addArchive(false,RSOP1Archive);
                                        RSOP3Archive = auction.getArchive(4);
                                        RSOP3Directory.addArchive(false,RSOP3Archive);
                                        PPArchive = auction.getArchive(5);
                                        PPDirectory.addArchive(false, PPArchive);
                                        CPArchive = auction.getArchive(6);
                                        CPDirectory.addArchive(false, CPArchive);                                      
                                        iteration++;                                   
                                    }
                                    iteration = 0;
                                }
                            }
                            System.out.println("Save Data As :\n(format: .txt)");
                            while (scan.hasNextInt()) {
                                System.out.println("Error : Create file to save results:");
                                scan.next();
                            }
                            fileName = scan.next();
                            writer = new Writer(fileName);
                            try {
                                writer.writeExcel(G1Directory,G3Directory);
                                writer.writeExcel2(RSOP1Directory,RSOP3Directory);
                                writer.writeExcel3(PPDirectory,CPDirectory);
                            } catch (IOException | WriteException ex) {
                                Logger.getLogger(Experimental_Auctions.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }         
                    }while (distribution != 11);
                    break;
                case 4 :
                    System.out.println("Exiting...");
                    System.out.println("Program is closed");
                    break;
                default:
                    System.out.println("Error : Command not recognized");
                    break;                  
            }           
        } while (choose != 4);
    }                      
}

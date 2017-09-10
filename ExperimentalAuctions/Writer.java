/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experimental_auctions;

import java.io.File;
import java.io.IOException;
import jxl.Workbook;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Rodrigo
 */
public class Writer {
    
    private final String fileName;

    public Writer(String fileName){
        this.fileName = fileName;
    }
    public void writeExcel(Directory G1Directory,Directory G3Directory)throws IOException, WriteException{
        Archive[] G1 = new Archive[G1Directory.size()];
        G1Directory.export(G1);
        Archive[] G3 = new Archive[G3Directory.size()];
        G3Directory.export(G3);
        
        float[] avgG1 = G1Directory.getAvgArr();
        float[] avgG3 = G3Directory.getAvgArr();
        WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName + "Greedy.xls"));
        WritableSheet sheet;
        WritableSheet sheet2; 
        WritableSheet sheet3;
        boolean copy = true;
        int x_pos;
        int y_pos; 
        int marker;
        
        sheet = workbook.createSheet("Average G1%OPT", 0);
        sheet2 = workbook.createSheet("Average G3%OPT", 0);
        x_pos = -1;
        y_pos = 0;
        for (int i=0;i<avgG1.length;i++){
            x_pos++;
            Number number1 = new Number(x_pos, (y_pos), avgG1[i]);
            Number number2 = new Number(x_pos, (y_pos), avgG3[i]);
            sheet.addCell(number1);
            sheet2.addCell(number2);
            if (x_pos == 9) {
                y_pos++;
                x_pos = -1;
            }           
        }
        
        sheet3 = workbook.createSheet("STATA",0);
        y_pos = -1;
        marker = 0;
        for (int i=0;i<G1.length;i++){
            if(copy==true){
                if(i<(marker+30)){
                    y_pos++;
                    Number number1 = new jxl.write.Number(0,y_pos,G1[i].getPlayers());
                    Number number2 = new jxl.write.Number(1,y_pos,G3[i].getGoods());
                    Number number3 = new jxl.write.Number(2,y_pos,G1[i].getProfit());
                    Number number4 = new jxl.write.Number(3,y_pos,G3[i].getProfit());
                    sheet3.addCell(number1);
                    sheet3.addCell(number2);
                    sheet3.addCell(number3);
                    sheet3.addCell(number4);
                }
                else{
                    copy=false;
                }
            }
            else if((i+1)%1000==0){
                copy=true;
                marker=i+1;
            }
        }
        workbook.write();
        workbook.close();
        System.out.println("File 1 complete");
    }
    public void writeExcel2(Directory RSOP1Directory,Directory RSOP3Directory)throws IOException, WriteException{
        Archive[] RSOP1 = new Archive[RSOP1Directory.size()];
        RSOP1Directory.export(RSOP1);
        Archive[] RSOP3 = new Archive[RSOP3Directory.size()];
        RSOP3Directory.export(RSOP3);
        
        float[] avgG1 = RSOP1Directory.getAvgArr();
        float[] avgG3 = RSOP3Directory.getAvgArr();
        WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName + "RSOP.xls"));
        WritableSheet sheet;
        WritableSheet sheet2; 
        WritableSheet sheet3;
        boolean copy = true;
        int x_pos;
        int y_pos; 
        int marker;
        
        sheet = workbook.createSheet("Average RSOP1%OPT", 0);
        sheet2 = workbook.createSheet("Average RSOP3%OPT", 0);
        x_pos = -1;
        y_pos = 0;
        for (int i=0;i<avgG1.length;i++){
            x_pos++;
            Number number1 = new Number(x_pos, (y_pos), avgG1[i]);
            Number number2 = new Number(x_pos, (y_pos), avgG3[i]);
            sheet.addCell(number1);
            sheet2.addCell(number2);
            if (x_pos == 9) {
                y_pos++;
                x_pos = -1;
            }           
        }
        
        sheet3 = workbook.createSheet("STATA",0);
        y_pos = -1;
        marker = 0;
        for (int i=0;i<RSOP1.length;i++){
            if(copy==true){
                if(i<(marker+30)){
                    y_pos++;
                    Number number1 = new jxl.write.Number(0,y_pos,RSOP1[i].getPlayers());
                    Number number2 = new jxl.write.Number(1,y_pos,RSOP3[i].getGoods());
                    Number number3 = new jxl.write.Number(2,y_pos,RSOP1[i].getProfit());
                    Number number4 = new jxl.write.Number(3,y_pos,RSOP3[i].getProfit());
                    sheet3.addCell(number1);
                    sheet3.addCell(number2);
                    sheet3.addCell(number3);
                    sheet3.addCell(number4);
                }
                else{
                    copy=false;
                }
            }
            else if((i+1)%1000==0){
                copy=true;
                marker=i+1;
            }
        }
        workbook.write();
        workbook.close();
        System.out.println("File 2 complete");
    }
    public void writeExcel3(Directory PPDirectory,Directory CPDirectory)throws IOException, WriteException{
        Archive[] PP = new Archive[PPDirectory.size()];
        PPDirectory.export(PP);
        Archive[] CP = new Archive[CPDirectory.size()];
        CPDirectory.export(CP);
        
        float[] avgPP = PPDirectory.getAvgArr();
        float[] avgCP = CPDirectory.getAvgArr();
        
        WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName + "PCP.xls"));
        WritableSheet sheet;
        WritableSheet sheet2; 
        WritableSheet sheet3;
        boolean copy = true;
        int x_pos;
        int y_pos; 
        int marker;
        
        sheet = workbook.createSheet("Average PP%OPT", 0);
        sheet2 = workbook.createSheet("Average CP%OPT", 0);
        x_pos = -1;
        y_pos = 0;
        for (int i=0;i<avgPP.length;i++){
            x_pos++;
            Number number1 = new Number(x_pos, (y_pos), avgPP[i]);
            Number number2 = new Number(x_pos, (y_pos), avgCP[i]);
            sheet.addCell(number1);
            sheet2.addCell(number2);
            if (x_pos == 9) {
                y_pos++;
                x_pos = -1;
            }           
        }
        sheet3 = workbook.createSheet("STATA",0);
        y_pos = -1;
        marker = 0;
        for (int i=0;i<PP.length;i++){
            if(copy==true){
                if(i<(marker+30)){
                    y_pos++;
                    Number number1 = new jxl.write.Number(0,y_pos,PP[i].getPlayers());
                    Number number2 = new jxl.write.Number(1,y_pos,CP[i].getGoods());
                    Number number3 = new jxl.write.Number(2,y_pos,PP[i].getProfit());
                    Number number4 = new jxl.write.Number(3,y_pos,CP[i].getProfit());
                    sheet3.addCell(number1);
                    sheet3.addCell(number2);
                    sheet3.addCell(number3);
                    sheet3.addCell(number4);
                }
                else{
                    copy=false;
                }
            }
            else if((i+1)%1000==0){
                copy=true;
                marker=i+1;
            }
        }
        workbook.write();
        workbook.close();
        System.out.println("File 3 complete");
    }
}

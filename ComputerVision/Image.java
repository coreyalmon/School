import java.io.*;
import java.util.Scanner;

public class Image {
    
    private int numRows = 0;
    private int numCols = 0;
    private int minVal = 0;
    private int maxVal = 0;
    private int histAry[]; 
    private int thresholdValue = 0; // Reffered to as thrVal and thresholdValue in doc

    public Image() {}

    // Most of the Compute Histogram algorithm (shown from the lecture notes) is already done in main (due to following the given main instructions).
    void computeHist(Scanner inFile) throws IOException {

        while(inFile.hasNextInt()) {

            histAry[inFile.nextInt()]++;
        }
    } 
    // (to be used in the future project)
    void printHist (BufferedWriter outFile1) throws IOException {  

        outFile1.write(String.valueOf(this.numRows) + " ");
        outFile1.write(String.valueOf(this.numCols) + " ");
        outFile1.write(String.valueOf(this.minVal) + " ");
        outFile1.write(String.valueOf(this.maxVal) + '\n');

        for(int i = 0; i < this.histAry.length; i++) 
            outFile1.write(i + " " + this.histAry[i] + '\n');
    }

    void dispHist (BufferedWriter outFile2) throws IOException { 

        outFile2.write(String.valueOf(this.numRows) + " ");
        outFile2.write(String.valueOf(this.numCols) + " ");
        outFile2.write(String.valueOf(this.minVal) + " ");
        outFile2.write(String.valueOf(this.maxVal) + '\n');

        for(int i = 0; i < this.histAry.length; i++) 
            outFile2.write(i + " " + "(" + this.histAry[i] + "):" 
                    + new String(new char[this.histAry[i] > 70 ?  80 : this.histAry[i]]).replace('\0', '+') + '\n');       
    }
    // (to be used in the future project)
    void threshold(Scanner inFile, BufferedWriter outFile3, BufferedWriter outFile4, int thrVal) throws IOException { 

        this.minVal = 0;
        this.maxVal = 1;


        outFile3.write(String.valueOf(this.numRows) + " ");
        outFile3.write(String.valueOf(this.numCols) + " ");
        outFile3.write(String.valueOf(this.minVal) + " ");
        outFile3.write(String.valueOf(this.maxVal) + '\n');

        outFile4.write(String.valueOf(this.numRows) + " ");
        outFile4.write(String.valueOf(this.numCols) + " ");
        outFile4.write(String.valueOf(this.minVal) + " ");
        outFile4.write(String.valueOf(this.maxVal) + '\n');

        inFile.nextLine();
        int countCol = 0;
        int pixelVal = 0;
        while(inFile.hasNextInt()) {

            pixelVal = inFile.nextInt();
            if(pixelVal >= thrVal)  { outFile3.write(Integer.toString(1) + " "); outFile4.write(Integer.toString(1) + " "); } 
            else { outFile3.write(Integer.toString(0) + " "); outFile4.write("." + " "); }               
            countCol++;

            if(countCol < this.numCols) { continue; }
            else if(countCol == this.numCols) {  outFile3.write('\n'); outFile4.write('\n'); countCol = 0; }
        }
    } 


    public static void main(String[] args) throws IOException { 

        String inFileName = args[0];
        FileReader fileReader = null;
        BufferedReader buffInReader = null;
        Scanner inFile = null;

        String outFile1Name = args[2];
        FileWriter fileWriter1 = null; 
        BufferedWriter outFile1 = null; 

        String outFile2Name = args[3];
        FileWriter fileWriter2 = null; 
        BufferedWriter outFile2 = null; 

        String outFile3Name = args[4];
        FileWriter fileWriter3 = null; 
        BufferedWriter outFile3 = null; 

        String outFile4Name = args[5];
        FileWriter fileWriter4 = null; 
        BufferedWriter outFile4 = null;


        try {

            fileReader = new FileReader(inFileName);
            buffInReader = new BufferedReader(fileReader);
            inFile = new Scanner(buffInReader);

            fileWriter1 = new FileWriter(outFile1Name);
            outFile1 = new BufferedWriter(fileWriter1);

            fileWriter2 = new FileWriter(outFile2Name);
            outFile2 = new BufferedWriter(fileWriter2);

            fileWriter3 = new FileWriter(outFile3Name);
            outFile3 = new BufferedWriter(fileWriter3);

            fileWriter4 = new FileWriter(outFile4Name);
            outFile4 = new BufferedWriter(fileWriter4);

            Image image = new Image();    
            image.thresholdValue = Integer.parseInt(args[1]);


            if(inFile.hasNext()) image.numRows = inFile.nextInt();
            if(inFile.hasNext()) image.numCols = inFile.nextInt();
            if(inFile.hasNext()) image.minVal = inFile.nextInt();
            if(inFile.hasNext()) image.maxVal = inFile.nextInt();


            image.histAry = new int[image.maxVal+1];
            image.computeHist(inFile);
            image.printHist(outFile1);
            image.dispHist(outFile2);

            if(inFile != null) inFile.close();

            fileReader = new FileReader(inFileName);
            buffInReader = new BufferedReader(fileReader);
            inFile = new Scanner(buffInReader);

            outFile3.write("The threshold value uses is " + String.valueOf(image.thresholdValue) + '\n');
            outFile4.write("The threshold value uses is " + String.valueOf(image.thresholdValue) + '\n');

            image.threshold(inFile, outFile3, outFile4, image.thresholdValue);            
        } finally {

            if(inFile   != null) inFile.close();
            if(outFile1 != null) outFile1.close();
            if(outFile2 != null) outFile2.close();
            if(outFile3 != null) outFile3.close();
            if(outFile4 != null) outFile4.close();
        }
    }
}
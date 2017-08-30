package comp102x.project.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import comp102x.project.model.GameRecord;

public class SaveLoader {
	
	public void saveGameRecords(GameRecord[] records, String filename) throws FileNotFoundException {
		
		// Please write your code after this line
		PrintWriter input = new PrintWriter(new File(filename));
       for(int i=0; i<records.length ; i++)
       {
           int level = records[i].getLevel();
           String name =records[i].getName();
           int score = records[i].getScore();
           input.write(name+"\t"+level+"\t"+score);
       }
        input.close();
	}
	
	public GameRecord[] loadGameRecords(String filename) throws FileNotFoundException {
		
		// Please write your code after this line
        // assume that there are at most 30 game records in the text file.
        int numOfRecords = 30;
        GameRecord[] records = new GameRecord[numOfRecords];
        // 1. Create a File and Scanner objects
        Scanner input = new Scanner(new File(filename));
        // A scanner object for each line of input
        Scanner line;
        
        int i = 0;
        for (i = 0; input.hasNextLine() && i < 30; i++) {
            String inputGameRecords = input.nextLine();
            line = new Scanner(inputGameRecords);
            records[i] = new GameRecord(line.next(), line.nextInt(), line.nextInt());
        }
        // 3. Close the file
        input.close();

        //return null; // this line should be modified/removed after finishing the implementation of this method.
        return java.util.Arrays.copyOfRange(records, 0, i);		
	}

}

package comp102x.project.task;

import comp102x.project.model.GameRecord;

public class Util {
    
    public static void sort(GameRecord[] records) {
    
        int minIndex;
        int remaining = records.length;

        while (remaining > 1) {

            minIndex = minPos(records, remaining);
            swap(records, minIndex, --remaining);
        }
    }

    private static int minPos(GameRecord[] records, int size) {

       // Please write your code after this line
      int k=0;
        
        for(int i=0;i<size;i++)
        {
            if(records[i].getScore()<=records[0].getScore())
            k=i;
        }
        return k; // this line should be removed or modified after the implementation of this method is completed.
    }

    private static void swap(GameRecord[] array, int index1, int index2) {

        GameRecord temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}

package comp102x.project.task;

import comp102x.project.model.GameRecord;

public class RecordManager {

    public GameRecord[] updateGameRecords(GameRecord[] oldRecords, GameRecord newRecord) {
        
        // Please write your code after this line
        int level = newRecord.getLevel();int count=0,find=0;boolean firstUpdate=true,found=false;
       // int[] find= new find[];
        for(int i=0;i<oldRecords.length;i++)
        {
            if((oldRecords[i].getName().equals(newRecord.getName())) && (oldRecords[i].getLevel()==newRecord.getLevel()))
            {
               if(oldRecords[i].getScore()<newRecord.getScore())
               oldRecords[i].setScore(newRecord.getScore());
                found=true;
            }
            
            if(oldRecords[i].getLevel()==level) count++;
        }
        
        if(!found)
        {
        if(count<10)
        {
            GameRecord[] oldRecords1 = new GameRecord[oldRecords.length+1];
            for(int i=0;i<oldRecords.length;i++)
            oldRecords1[i]=oldRecords[i];
            oldRecords1[oldRecords.length]=newRecord;
            Util.sort(oldRecords1);
            return oldRecords1;
        }
        
        else
        {
         for(int i=0;i<oldRecords.length;i++)
         {
             if(oldRecords[i].getLevel()==newRecord.getLevel())
             {
                 if(oldRecords[i].getScore()<newRecord.getScore())
                 {
                     if(firstUpdate)
                     {
                         find=i;
                         firstUpdate=false;
                     }
                     else
                     {
                        if(oldRecords[i].getScore()<oldRecords[find].getScore()) 
                        find=i;
                    }
                }
            }
        }
        oldRecords[find]=newRecord;
        }
    }
    Util.sort(oldRecords);
    return oldRecords; // this line should be removed or modified when the implementation of this method is completed.
       
    }
}

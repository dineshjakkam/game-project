package comp102x.project.task;

import java.util.Random;

import comp102x.project.model.Target;

public class TargetUpdater {
    
    public void updateTarget(Target[] targets, int level) {
        
        // Please write your code after this line
	int numberOfTimes,i=0,random1,random2;
	int len = targets.length;
	Random random = new Random();
	if(level == 2 || level == 3)
	{
	if(level == 2) { numberOfTimes = 4;}
	else numberOfTimes = 10;
	for(i=0;i<numberOfTimes;i++)
	{
	random1 = random.nextInt(len);
	do
	{
	random2 = random.nextInt(len);} while(random1==random2);
	if(((targets[random1].isHit() && targets[random2].isHit()) || (!targets[random1].isHit() && !targets[random2].isHit())) ){ continue;}
	int x = targets[random1].getX();
	int y = targets[random1].getY();
	int z = targets[random1].getZ();
	targets[random1].setX(targets[random2].getX());
	targets[random1].setY(targets[random2].getY());
	targets[random1].setZ(targets[random2].getZ());
	targets[random2].setX(x);
	targets[random2].setY(y);
	targets[random2].setZ(z);
    }
}
}
}

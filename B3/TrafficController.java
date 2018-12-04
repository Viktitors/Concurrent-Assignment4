package B3;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Remember to move the 'ass4_images' folder to the root directory
 * of your project,
 * or write the absolute path to the folder in lines 27,29,31
 * in CarWorld.java. 
 * 
 * Use Semaphores to create a safe bridge (only 1 car on the bridge at 
 * the same time)
 * */

public class TrafficController {
    ReentrantLock lock = new ReentrantLock();
    final Condition pedestrianQueue = lock.newCondition();
    final Condition carQueue = lock.newCondition();
    
    private int pedestrian=0;
    private int car=0;
    
    private boolean turn;
    
    private int carcrossed=0;
    private int pedcrossed=0;
    
    final int maxcars=5;
    final int maxpeds=10;
    
    public void carEnters() throws InterruptedException {
	lock.lock();
        try{
        while((lock.hasWaiters(pedestrianQueue)&& turn==false) ||pedestrian!=0){
                       
            carQueue.await();       
        
        }    
        car++;        
        carcrossed++;
        
        //turn=carcrossed!=maxcars;
        
        if(carcrossed==maxcars){
            turn=false;
            carcrossed=0;
            
        }
        }finally{
        lock.unlock();
        }
    }
     public void carExits() {
	 lock.lock();
         try{
         car--;
         if(car==0){
             pedestrianQueue.signalAll();
         }
         }finally{
             lock.unlock();
         }
    }

    public void pedestrianEnters() throws InterruptedException {
	lock.lock(); 
        try {
        while(car!=0||(turn && lock.hasWaiters(carQueue))){           
            pedestrianQueue.await();       
        }
        pedestrian++;
        pedcrossed++;
        //turn = maxpeds!=pedcrossed;
        
        if(pedcrossed==maxpeds){
            turn=true;
            pedcrossed=0;  
         }
        }finally{
        lock.unlock();
        }
    }

     public void pedestrianExits() {
	 lock.lock(); 
         try{
         pedestrian--;
         if(pedestrian==0)
            carQueue.signalAll();
         }finally{
	 lock.unlock();
         }
    }
   
}

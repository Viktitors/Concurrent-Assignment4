package B2;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private int carGoing=0;
    
    
    public void carEnters() {
	lock.lock();
        
        if(lock.hasWaiters(pedestrianQueue) ||pedestrian!=0){
            try {
            carQueue.await();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(TrafficController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }    
        carGoing++;
        lock.unlock();
    }
     public void carExits() {
	 lock.lock();
         carGoing--;
         if(carGoing==0)
         pedestrianQueue.signal();
         lock.unlock();
    }

    public void pedestrianEnters() {
	lock.lock(); 
        if(carGoing>0){
            try {
                pedestrianQueue.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(TrafficController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        pedestrian++;
               
        lock.unlock();
    }

     public void pedestrianExits() {
	 lock.lock();
         
         pedestrian--;
         if(pedestrian==0 && !lock.hasWaiters(pedestrianQueue)){
             carQueue.signal();
         }
         
	 lock.unlock();
    }

   

}

package B1;


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
    boolean canCross=true;
    
    
    
    public synchronized void carEnters() {
	while(!canCross){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(TrafficController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        canCross=false;
        
    }
     public synchronized void carExits() {
	 canCross=true;
         notify();
    }

    public synchronized void pedestrianEnters() {
	while(!canCross){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(TrafficController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        canCross=false;
        
    }

     public synchronized void pedestrianExits() {
	 canCross=true;
         notify();
    }

}

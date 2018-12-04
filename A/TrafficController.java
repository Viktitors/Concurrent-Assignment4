package A;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * Pablo.Bermejo@uclm.es
 *
 */
public class TrafficController {

    final ReentrantLock lock = new ReentrantLock();
    final Condition blueQueue = lock.newCondition();
    final Condition redQueue = lock.newCondition();
    
    private boolean redTurn = false;
    
    private int blueCars = 0;
    private int redCars = 0; 
    
    private int redCrossed = 0; 
    private int blueCrossed = 0;
    
    final int MAX = 5;

    public void redEnters() {
        lock.lock();
        
        if ((lock.hasWaiters(blueQueue) && !redTurn) || blueCars != 0) {
                try {
                    redQueue.await();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
        }
        redCars++; 
        redCrossed++;
        //if the crossed cars are less than the MAX, it's still the turn for them
        redTurn = redCrossed != MAX;
        
        lock.unlock();
    }

    public void blueEnters() {
        lock.lock();
        
        if ((lock.hasWaiters(redQueue) && redTurn) || redCars != 0) {
                try {
                    blueQueue.await();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
        }
        blueCars++; 
        blueCrossed++;
        
        redTurn = blueCrossed == MAX;
        
        lock.unlock();
    }

    public void blueExits() {
        lock.lock();
        //if it is the last car, restart the counter of cross and call the other queue
        if (blueCars == 1) {
            blueCrossed = 0;
            redQueue.signal();
        }
        blueCars--;
        
        lock.unlock();
    }

    public void redExits() {
        lock.lock();
        
        if (redCars == 1) {
            redCrossed = 0;
            blueQueue.signal();
        }       
        redCars--;
        
        lock.unlock();
    }

}


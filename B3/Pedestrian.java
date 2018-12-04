package concurrent_assignment4.B3;



import java.awt.*;

public class Pedestrian implements Runnable{


    public static final int VELOCIDAD = 15; //INVERSA
    
    private final static int tunnelY   = 95;
    private final static int tunnelX   = 530;//

    private final static int tunnelYLeft = 150;// punto parada peatones
    
    private final static int tunnelYMid = 410;//

    
    private final static int tunnelYRight = 320;// punto salida peatones

    
    private final static int tunnelXMid = 410;
    
    
    
    private final static int totalWidth = 900;
    private final static int totalHigh = 440;

    private final static int initX[]  = {390,420,450}; // posiciónes peatones vertical

    private final static int initY = -20;// altura en la que comienza
    private final static int outLeft = -200;
    private final static int outRight = totalWidth + 200;
    private final static int outDown = totalHigh+200;

    int izqDer;
    int cartype;
    int xpos, ypos; 
    Pedestrian inFront;
    Image image;
    TrafficController controller;

    public Pedestrian(Pedestrian inFront, Image image, TrafficController controller) {
	this.inFront = inFront;
        this.image = image;
	this.controller  = controller;

            izqDer = (int)(Math.random()*3);
            ypos = inFront==null ? outDown : Math.min(initY,inFront.getY()-90);
            xpos = initX[izqDer];
   	
    }
    
    public void move() {
	int xposOld =  xpos;
        int yposOld =  ypos;

        if (inFront.getY() - ypos > 30) {
           ypos += 2;
                if (ypos >= tunnelYLeft & yposOld < tunnelYLeft) controller.pedestrianEnters();
           
		else if (ypos >= tunnelYRight &&  yposOld < tunnelYRight) controller.pedestrianExits();
            }
    }



    public void run() {
	boolean outOfSight = ypos > totalHigh;
        while (!outOfSight) {
            move();
	    outOfSight = ypos > totalHigh;
	    try {
		Thread.sleep(VELOCIDAD);
	    } catch (InterruptedException e) {}
	    }

        ypos = outDown;
    }

    public int getX() {return xpos;}
    public int getY() {return ypos;}
    
    public void draw(Graphics g) {
	g.drawImage(image,xpos,ypos,null);
    }
}


package setUp;

import display.Display;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;


public class gameSetUP implements Runnable,KeyListener{

    private Display display;
    private String title;
    private int width,height;
    private Thread thread;
    private int score;
    private BufferStrategy buffer;
    private Graphics g;
    private int ballX=200;
    private int ballY=410;
    int batX=200;
    int batY=430;
    boolean left,right;
    boolean gameOver;
    private Rectangle[] Bricks;
    int moveX=1;
    int moveY=-1;
    
    int brickX=70;
    int brickY=60;
    
    Rectangle Ball=new Rectangle(ballX,ballY,15,15);
    Rectangle Bat=new Rectangle(batX,batY,40,10);
   
    public gameSetUP(String title, int width, int height) {
        
        this.title = title;
        this.width = width;
        this.height = height;
        score=0;
        gameOver=false;
    }
    
    public void init()
    {
        display=new Display(title, width, height);
        display.frame.addKeyListener(this);
         Bricks=new Rectangle[13];
         for(int i=0;i<Bricks.length;i++)
         {
             Bricks[i]=new Rectangle(brickX,brickY,50,20);
             if(i==5)
             {
                 brickX=60;
                 brickY=60+25;
             }
             if(i==9)
             {
                 brickX=80;
                 brickY=60+25+25;
             }
             brickX +=60;
         }
    }
    public void drawBat(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.fillRect(Bat.x, Bat.y, 40, 10);
    }
    public void drawBall(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillOval(Ball.x, Ball.y,Ball.width,Ball.height);
    }
    public void drawBricks(Graphics g)
    {
        for(int i=0;i<Bricks.length;i++)
        {
           if(Bricks[i]!=null){
            g.setColor(Color.WHITE);
            g.fillRect(Bricks[i].x-2, Bricks[i].y-2, 60+2, 20+2);
            g.setColor(Color.RED);
            g.fillRect(Bricks[i].x, Bricks[i].y, 50, 20);
           }
        }
    }
    public void drawScore(Graphics g)
    {
        String a=Integer.toString(score);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 33));
        g.drawString("Score:"+a, 160, 480);
    }
    public void gameOver(Graphics g)
    {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 38));
        g.drawString("Game Over", 130, 150);
    }
    public void tick()
    {
       if(Ball.x >=430||Ball.x<=40)
       {
           moveX=-moveX;
       }
       if( Ball.y<=40)
       {
           moveY=-moveY;
       }
        if (Ball.y>=430) {
            gameOver=true;
        }
       if(right)
       {
           if (Bat.x<=400) {
              Bat.x+=1; 
           }
           
       }
       if(left)
       {
           if(Bat.x>=40)
           {
               Bat.x-=1;
           }
           
       }
       if(Bat.intersects(Ball))
       {
           moveY=-moveY;
       }
       
       for(int i=0;i<Bricks.length;i++)
       {
           
           if(Bricks[i]!=null){
           if(Bricks[i].intersects(Ball)){
               moveY=-moveY;
               Bricks[i]=null;
               score +=5;
           }
       }
       }
        
        Ball.x +=moveX;
        Ball.y +=moveY;
    }
    
    public void draw(){
        buffer=display.canvas.getBufferStrategy();
        if(buffer==null)
        { 
            display.canvas.createBufferStrategy(3);
            return;
        }
        g=buffer.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        //draw here
        g.setColor(Color.WHITE);
        g.fillRect(40, 40, 400, 400);
        if (!gameOver) {
        drawBall(g);
        drawBat(g);
        drawBricks(g);
        drawScore(g);
        }
        if(gameOver)
        {
            gameOver(g);
        }
        
        //end here
        buffer.show();
        g.dispose();
    }
    public synchronized void start()
    {
        thread =new Thread(this);
        thread.start();
    }
    public synchronized void stop()
    {
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(gameSetUP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void run() {
      init();
        while (true) {            
            thread.currentThread();
          try {
              thread.sleep(10);
          } catch (InterruptedException ex) {
              Logger.getLogger(gameSetUP.class.getName()).log(Level.SEVERE, null, ex);
          }
            tick();
            draw();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
      int source=e.getKeyCode();
        if (source==KeyEvent.VK_RIGHT) {
            right=true;
        }
        if (source==KeyEvent.VK_LEFT) {
            left=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int source=e.getKeyCode();
        if (source==KeyEvent.VK_RIGHT) {
            right=false;
        }
        if (source==KeyEvent.VK_LEFT) {
            left=false;
        }
    }
    
    
}

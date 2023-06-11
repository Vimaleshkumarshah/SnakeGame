import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {

    static int width = 1350;
    static int height = 650;
    static int unit = 50;
    Timer timer;
    static int delay = 160;
    Random random;

    // food cordinate
    int fx,fy;

    // initial body length
    int body = 3;
    char dir = 'R';
    int score;

    //to keep a check on the state of game
    boolean flag = false;

    // arrays to keep the cordinate of snack
    int xsnack[] = new int[288]; // 24*12
    int ysnack[] = new int[288];
 panel(){
     // sets the size of the panel
     this.setPreferredSize(new Dimension(width,height));
     this.setBackground(Color.BLACK);
     // to enable keyboard input
     this.setFocusable(true);
     //adding a keyListener for processing the key input(it tells what key is pressed)
     this.addKeyListener(new mykey());
     random = new Random();
     gamestart();
 }

  public void gamestart(){
     flag = true;
     spawnfood();
     timer = new Timer(delay,this);
     timer.start();
  }

  public void spawnfood(){
      fx = random.nextInt((int) width/unit) * unit;
      fy = random.nextInt((int) height/unit) * unit;
  }

  public void paintComponent(Graphics graphic){
     super.paintComponent(graphic);
     draw(graphic);
  }

  public void draw(Graphics graphic){
    if(flag == true){
        // spawned the food particles
        graphic.setColor(Color.orange);
        graphic.fillOval(fx,fy,unit,unit);

        // spawning the snack
        for(int i=0;i<body;i++){
            if(i==0){
                graphic.setColor(Color.red);
                graphic.fillRect(xsnack[i],ysnack[i],unit,unit);
            }
            else{
                // the body of Snack
                graphic.setColor(Color.green);
                graphic.fillRect(xsnack[i],ysnack[i],unit,unit);
            }
        }

        // spawning the score display
        graphic.setColor(Color.CYAN);
        // setting the font
        graphic.setFont(new Font("comic sans",Font.BOLD,40));
        FontMetrics fme = getFontMetrics(graphic.getFont());// helping to get starting x location
        graphic.drawString("Score: "+score,(width - fme.stringWidth("Score:"+score))/2,graphic.getFont().getSize());


    }
    else{
          gameover(graphic);
    }
  }
  public void gameover(Graphics graphic){
     // drawing score
      graphic.setColor(Color.CYAN);
      // setting the font
      graphic.setFont(new Font("comic sans",Font.BOLD,40));
      FontMetrics fme = getFontMetrics(graphic.getFont());// helping to get starting x location
      graphic.drawString("Score: "+score,(width - fme.stringWidth("Score:"+score))/2,graphic.getFont().getSize());

      // drawing the gameover text
      graphic.setColor(Color.red);
      // setting the font
      graphic.setFont(new Font("comic sans",Font.BOLD,80));
      FontMetrics fme1 = getFontMetrics(graphic.getFont());// helping to get starting x location
      graphic.drawString("GAME OVER",(width - fme1.stringWidth("GAME OVER"))/2,height/2);

      graphic.setColor(Color.green);
      // setting the font
      graphic.setFont(new Font("comic sans",Font.BOLD,40));
      FontMetrics fme2 = getFontMetrics(graphic.getFont());// helping to get starting x location
      graphic.drawString("Press R to Replay",(width - fme2.stringWidth("Press R to Replay"))/2,height/2 - 150);
  }



  public void move(){

     for(int i=body;i>0;i--){
         xsnack[i] = xsnack[i-1];
         ysnack[i] = ysnack[i-1];
     }

     switch(dir){
         case 'R' :
             xsnack[0] = xsnack[0]+unit;
             break;
         case 'L' :
             xsnack[0] = xsnack[0]-unit;
             break;
         case 'U' :
             ysnack[0] = ysnack[0]-unit;
             break;
         case 'D' :
             ysnack[0] = ysnack[0]+unit;
             break;
     }
  }

  public void check(){  // for checking out of bound
      if(xsnack[0] < 0){
         flag = false;
      }
      else if(xsnack[0] > width){
          flag = false;
      }
      else if(ysnack[0] < 0){
          flag = false;
      }
      else if(ysnack[0] > height){
          flag = false;
      }
          // checking hit with body

          for(int i=body;i>0;i--){
              if((xsnack[0] == xsnack[i]) && (ysnack[0] == ysnack[i])){
                  flag = false;
              }
      }
  }
    public void eat() {
        if ((xsnack[0] == fx) && (ysnack[0] == fy)) {
            body++;
            score++;
            spawnfood();
        }
    }



    public class mykey extends KeyAdapter{
       public void keyPressed(KeyEvent e){
           switch(e.getKeyCode()){
               case KeyEvent.VK_UP :
                   if(dir != 'D'){
                       dir = 'U';
                   }
                   break;
               case KeyEvent.VK_DOWN:
                   if(dir != 'U'){
                       dir = 'D';
                   }
                   break;
               case KeyEvent.VK_RIGHT:
                   if(dir != 'L'){
                       dir = 'R';
                   }
                   break;
               case KeyEvent.VK_LEFT:
                   if(dir != 'R'){
                       dir = 'L';
                   }
                   break;
               case KeyEvent.VK_R:
                   if(!flag) {
                       score = 0;
                       body = 3;
                       dir = 'R';
                       Arrays.fill(xsnack, 0);
                       Arrays.fill(ysnack, 0);
                       gamestart();
                   }
                     break;
           }
       }
    }
    public void actionPerformed(ActionEvent e){
       if(flag){
           move();
           eat();
           check();
       }
       repaint();

    }
}
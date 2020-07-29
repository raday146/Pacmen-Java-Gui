
import javax.swing.*;
import java.awt.*;
/***
 * 
 * @author Andrey Leskov, Shlomo Raday
 * @exception Ghost, this class Responsible for creating the spirits, enemies in the game.
 * 
 *
 */
public class  Ghost{

private final int MAX_GHOSTS = 7;
private int N_GHOSTS = 7;
private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;
private Image[] ghost=new Image[N_GHOSTS];
private final int[] validSpeeds= {1, 2, 3, 4, 6, 8};
private final int maxSpeed = 6;

/***
 * @param  MAX_GHOSTS, N_GHOST
 * @param  ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed
 * @param ghots, validSpeeds, maxSpeed
 * 
 * exception loadGhost(), This method is responsible for loading the images into the image array
 */
public void loadGhost(){
   
  try {
    for(int i=0;i<N_GHOSTS;i++)
    {
        ghost[i] = new ImageIcon("images\\ghostt"+i+".png").getImage();
    }
  }catch(Exception e) {
	   JOptionPane.showMessageDialog(null,e.getMessage());
  }
   
}

/**
 * 
 * @param g2d
 * @param x
 * @param y
 * @param index
 * @see drawGhost(Graphics2D g2d, int x, int y, int index),This method accepts 4 variables and draws the screen, ghost, to the screen
 */
public void drawGhost(Graphics2D g2d, int x, int y, int index) {
	
  try {            
       g2d.drawImage(ghost[index], x, y, null);
    }catch(Exception e) {
 	   JOptionPane.showMessageDialog(null,e.getMessage());
    }
}
/**
 * 
 * @param max
 * @exception initialGhost(int max), A method that accepts a int type variable and initializes the parameters (which are arrays) 
 * of the class with the variable that the method accepts
 * 
 */
   public void initialGhost(int max)
   {   try{ 
             this.ghost_x=new int[max];
             this.ghost_dx=new int[max];
             this.ghost_y=new int[max];
             this.ghost_dy=new int[max];
             this.ghostSpeed=new int[max+1];
         }catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
         }
         
   }

   /**
    * getter and setter
    * @param index
    * @return ghost from ghost array
    * @return 0 if the index value is greater than the size of the array 
    */
    public int getGhost_x(int index) {
         if(index>MAX_GHOSTS)
         {        
          JOptionPane.showMessageDialog(null,"Error 78: Ghost_x ");
          return 0;   
         }
         return this.ghost_x[index];
    }

    public void setGhost_x(int x, int index) {
        try { if(index <this.MAX_GHOSTS)
              {
                this.ghost_x[index] = x;
              }
            } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }

  
    public int getGhost_y(int index) {
        if(index>MAX_GHOSTS)
        {        
         JOptionPane.showMessageDialog(null,"Error Ghost_y");
         return -1;   
        }
        return this.ghost_y[index];
   }

    public void setGhost_y(int y, int index) {
        try { if(index <this.MAX_GHOSTS)
              {
                this.ghost_y[index] = y;
              }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
    public int getGhost_dx(int index) {
       if(index>MAX_GHOSTS)
       {
        JOptionPane.showMessageDialog(null,"Error: parameter Ghost_dx");
        return -1;  
       }
      return this.ghost_dx[index];
    }
    public void setGhost_dx(int dx, int index) {
        try { if(index <this.MAX_GHOSTS)
              {
                this.ghost_dx[index] = dx;
              }
            } catch (Exception e) {
            e.getMessage();
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }

    public int getGhost_dy(int index) {
        if(index>MAX_GHOSTS)
        {
         JOptionPane.showMessageDialog(null,"Error: parameter Ghost_dy");
         return -1;  
        }
       return this.ghost_dy[index];
     }
    public void setGhost_dy(int dy, int index) {
        try { if(index <this.MAX_GHOSTS)
              {
                this.ghost_dy[index] = dy;
              }
            } catch (Exception e) {
            e.getMessage();
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }

    public int getGhostSpeed(int index) {
        return this.ghostSpeed[index];
    }
    public void setGhostSpeed(int speed, int index) {
        try { if(index >ghostSpeed.length)
              {
                this.ghostSpeed[1] = speed;
              }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
        this.ghostSpeed[index] = speed;
    }

    public int getMAX_GHOSTS() {
        return this.MAX_GHOSTS;
    }

    public int getN_GHOSTS() {
        return this.N_GHOSTS;
    }

    public int getValidSpeeds(int index) {
        try {
            if(validSpeeds.length<index)
            {
                return this.validSpeeds[1];
            }
            
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, e.getMessage());      
        }
        return this.validSpeeds[index];
      
    }

    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    public Ghost N_GHOSTS(int N_GHOSTS) {
        this.N_GHOSTS = N_GHOSTS;
        ghost[N_GHOSTS-1] = new ImageIcon("images\\ghostt"+(N_GHOSTS-1)+".png").getImage();
        return this;
    }
   
}
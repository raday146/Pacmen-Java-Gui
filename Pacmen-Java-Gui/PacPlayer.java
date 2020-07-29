import javax.swing.*;
import java.awt.*;
/***
 * 
 * @author Andrey Leskov, Shlomo Raday
 * @exception PacPlayer, with this class we can build the player and all the variables
 * that we need for player to move.  
 *
 */

public class PacPlayer {

  private int pacman_x, pacman_y, pacmand_x, pacmand_y;
  private int req_dx, req_dy, view_dx, view_dy;
  private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
  private Image pacman3up, pacman3down, pacman3left, pacman3right;
  private Image pacman4up, pacman4down, pacman4left, pacman4right;
  private final int PAC_ANIM_DELAY = 2;
  private final int PACMAN_ANIM_COUNT = 4;
  private final int speed = 6;
  private int pacAnimCount = PAC_ANIM_DELAY;
  private int pacAnimDir = 1;
  private int pacmanAnimPos = 0;

  /***
   * @param pacman1, pacman2up, pacman2left, pacman2right, pacman2down,pacman3up, pacman3down, pacman3left, pacman3right
   * @param  pacman4up, pacman4down, pacman4left, pacman4right
   * @see loadImages() load  the images of pacman player, which be use for the directions.
   */
  public void loadImages() {

    pacman1 = new ImageIcon("images\\pacman.png").getImage();
    pacman2up = new ImageIcon("images\\up1.png").getImage();
    pacman3up = new ImageIcon("images\\up2.png").getImage();
    pacman4up = new ImageIcon("images\\up3.png").getImage();
    pacman2down = new ImageIcon("images\\down1.png").getImage();
    pacman3down = new ImageIcon("images\\down2.png").getImage();
    pacman4down = new ImageIcon("images\\down3.png").getImage();
    pacman2left = new ImageIcon("images\\left1.png").getImage();
    pacman3left = new ImageIcon("images\\left2.png").getImage();
    pacman4left = new ImageIcon("images\\left3.png").getImage();
    pacman2right = new ImageIcon("images\\right1.png").getImage();
    pacman3right = new ImageIcon("images\\right2.png").getImage();
    pacman4right = new ImageIcon("images\\right3.png").getImage();
  }
  
  /**
   * @exception doAnim() this method run the animation  and the delay of  pacman  mouth.
   */
  public void doAnim() {

    pacAnimCount--;

    if (pacAnimCount <= 0) {
      pacAnimCount = PAC_ANIM_DELAY;
      pacmanAnimPos = pacmanAnimPos + pacAnimDir;

      if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
        pacAnimDir = -pacAnimDir;
      }
    }
  }
/**
 * 
 * @param g2d
 * @exception drawPacman(Graphics2D g2d) this mathod This method accepts g2d a type Graphics2D as a primer,
  The method draws the actions (movements) of the Pacman.
 */
  public void drawPacman(Graphics2D g2d) {
    if (view_dx == -1) {
      drawPacmanLeft(g2d);
    } else if (view_dx == 1) {
      drawPacmanRight(g2d);
    } else if (view_dy == -1) {
      drawPacmanUp(g2d);
    } else {
      drawPacmanDown(g2d);
    }
    
  }

  /**
   * 
   * @param g2d
   * @exception drawPacmanUp(Graphics2D g2d) this mathod This method accepts g2d a type Graphics2D as a primer,
     The method claims the images of the correct movements.
   */
  public void drawPacmanUp(Graphics2D g2d) {
	try {  
    switch (pacmanAnimPos) {
      case 1:
        g2d.drawImage(pacman2up, pacman_x + 1, pacman_y + 1, null);
        break;
      case 2:
        g2d.drawImage(pacman3up, pacman_x + 1, pacman_y + 1,null);
        break;
      case 3:
        g2d.drawImage(pacman4up, pacman_x + 1, pacman_y + 1,null);
        break;
      default:
        g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, null);
        break;
    }
	}catch(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage());
	}
  }
  /**
   * 
   * @param g2d
   * @exception drawPacmanDown(Graphics2D g2d) this mathod This method accepts g2d a type Graphics2D as a primer,
     The method claims the images of the correct movements.
   */
  public void drawPacmanDown(Graphics2D g2d) {
  try {	  
    switch(pacmanAnimPos) {
      case 1:
        g2d.drawImage(pacman2down, pacman_x + 1, pacman_y + 1, null);
        break;
      case 2:
        g2d.drawImage(pacman3down, pacman_x + 1, pacman_y + 1,null);
        break;
      case 3:
        g2d.drawImage(pacman4down, pacman_x + 1, pacman_y + 1, null);
        break;
      default:
        g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1,null);
        break;
     } 
   }catch(Exception e)
    {
	   JOptionPane.showMessageDialog(null,e.getMessage());
    }
  }

  /**
   * 
   * @param g2d
   * @exception drawPacmanLeft(Graphics2D g2d) this mathod This method accepts g2d a type Graphics2D as a primer,
     The method claims the images of the correct movements.
   */
  public void drawPacmanLeft(Graphics2D g2d) {
   try {	  
    switch(pacmanAnimPos) {
      case 1:
         g2d.drawImage(pacman2left, pacman_x + 1, pacman_y + 1, null);
         break;
      case 2:
         g2d.drawImage(pacman3left, pacman_x + 1, pacman_y + 1, null);
         break;
      case 3:
         g2d.drawImage(pacman4left, pacman_x + 1, pacman_y + 1, null);
         break;
      default:
         g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1,null);
         break;
    }
   }catch(Exception e) {
	   JOptionPane.showMessageDialog(null,e.getMessage());
   }
  }

  /**
   * 
   * @param g2d
   * @exception drawPacmanRight(Graphics2D g2d) this mathod This method accepts g2d a type Graphics2D as a primer,
     The method claims the images of the correct movements.
   */
  public void drawPacmanRight(Graphics2D g2d){
  try {	  
    switch (pacmanAnimPos){
      case 1:
        g2d.drawImage(pacman2right, pacman_x + 1, pacman_y + 1, null);
        break;
      case 2:
        g2d.drawImage(pacman3right, pacman_x + 1, pacman_y + 1,null);
        break;
      case 3:
        g2d.drawImage(pacman4right, pacman_x + 1, pacman_y + 1, null);
        break;
      default:
        g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, null);
        break;
    }
  }catch(Exception e){
	   JOptionPane.showMessageDialog(null,e.getMessage());
   }
  }
  
  /**
   * 
   * 
   * @exception this all Getters and setter 
   */
   
  public int getReq_dx() {
   
    return this.req_dx;
  }

  public void setReq_dx(int req_dx) {
   try {
        this.req_dx = req_dx;
   }catch(Exception e){
	   JOptionPane.showMessageDialog(null, e.getMessage());
   }
  }

  public int getReq_dy() {
    return this.req_dy;
  }

  public void setReq_dy(int req_dy) {
  try {
       this.req_dy = req_dy;
    }catch(Exception e) {
    	JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }

  public int getPacman_x() {
    return this.pacman_x;
  }

  public int getPacman_y() {
    return this.pacman_y;
  }



  public int setPacman_x(int pacman_x) { 
     return this.pacman_x = pacman_x;
  }

  public int setPacman_y(int pacman_y) {
    return this.pacman_y = pacman_y;
  
  }
  public int getPacmand_x() {
    return this.pacmand_x;
  }

  public int getPacmand_y() {
    return this.pacmand_y;
  }
  public int setPacmand_x(int pacmand_x) {

     return  this.pacmand_x = pacmand_x;

  }

  public int setPacmand_y(int pacmand_y) {
   return this.pacmand_y = pacmand_y;
    
  }

  public int setView_dx(int view_dx) {
   return this.view_dx = view_dx;
  
  }

  public int setView_dy(int view_dy) {

    return this.view_dy = view_dy;
  }


  public Image getPacman1() {
    return this.pacman1;
  }

  public Image getPacman2up() {
    return this.pacman2up;
  }

  public Image getPacman2left() {
    return this.pacman2left;
  }

  public Image getPacman2right() {
    return this.pacman2right;
  }

  public Image getPacman2down() {
    return this.pacman2down;
  }

  public Image getPacman3up() {
    return this.pacman3up;
  }

  public Image getPacman3down() {
    return this.pacman3down;
  }

  public Image getPacman3left() {
    return this.pacman3left;
  }

  public Image getPacman3right() {
    return this.pacman3right;
  }

  public Image getPacman4up() {
    return this.pacman4up;
  }

  public Image getPacman4down() {
    return this.pacman4down;
  }

  public Image getPacman4left() {
    return this.pacman4left;
  }

  public Image getPacman4right() {
    return this.pacman4right;
  }
  

  public int getSpeed() {
    return this.speed;
  }
  
}
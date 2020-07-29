import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.InputMismatchException;

import javax.swing.JOptionPane;
/***
 * 
 * @author Andrey Leskov, Shlomo Raday
 * @exception Listener, this kind of class we manages the input received from the player.
 * 
 *
 */
    class Listener extends KeyAdapter
    {   
    
        private TheBoard screen;
        /***
         * @param screen
         * @see Listener(TheBoard screen), A constructor method that accepts a parameter for to run Listener
         * 
         */
        public Listener(TheBoard screen)
        { try {
        	  this.screen=screen;
           }catch(Exception e) {
        	   JOptionPane.showMessageDialog(null,e.getMessage());
           }
        }
      
        /***
         *  
         *  @exception keyPressed(KeyEvent e), A method that accepts an Key event parameter (e) and fulfills the player's movement desires
         *  When the player  pressing on the direction arrows
         */
        @Override
        public void keyPressed(KeyEvent e) {
          try {
            int key = e.getKeyCode();

            if (this.screen.getInGame()){
                if (key == KeyEvent.VK_LEFT) {
                   this.screen.getPacman().setReq_dx(-1);
                   this.screen.getPacman().setReq_dy(0);
                } else if(key == KeyEvent.VK_RIGHT) {
                   
                    this.screen.getPacman().setReq_dx(1);
                    this.screen.getPacman().setReq_dy(0);
                } else if (key == KeyEvent.VK_UP) {
                   
                    this.screen.getPacman().setReq_dx(0);
                    this.screen.getPacman().setReq_dy(-1);
                } else if (key == KeyEvent.VK_DOWN) {
                    
                   this.screen.getPacman().setReq_dx(0);
                   this.screen.getPacman().setReq_dy(1);
                } else if (key == KeyEvent.VK_ESCAPE && !this.screen.getTimer().isRunning()) {
                    this.screen.setInGame(false);
                } else if (KeyEvent.VK_PAUSE==key) {
                    if (this.screen.getTimer().isRunning()) {
                        this.screen.getTimer().stop();
                    } else {
                        this.screen.getTimer().start();
                    }
                }
            } else { 
                if (key == KeyEvent.VK_ENTER) {
                    this.screen.getPacman_beginning().play(false);
                    this.screen.setInGame(true);
                    this.screen.initGame();
                }
            }
          }catch(Exception ex) {
       	   JOptionPane.showMessageDialog(null,ex.getMessage());
          }
        }

        /***
         *  
         *  @exception keyReleased(KeyEvent e), A method that accepts an Key event parameter (e) Resets the variables back to zero to continue the next action of the player
         */
        @Override
        public void keyReleased(KeyEvent e) {
           try {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_KP_LEFT|| key == KeyEvent.VK_KP_RIGHT
                    || key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_KP_DOWN) {
                        this.screen.getPacman().setReq_dx(0);
                        this.screen.getPacman().setReq_dy(0);
            }
           }catch(InputMismatchException ex) {
        	   JOptionPane.showMessageDialog(null,ex.getMessage());
            }
        }
     
    }


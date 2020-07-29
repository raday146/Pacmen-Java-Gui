

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.InputMismatchException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/***
 * 
 * @author Andrey Leskov, Shlomo Raday
 * @exception TheBoard, Is the game engine, all the different components that exist in this class are integral parts 
 * working separately and at the same time, together the game will rise to the screen
 * 
 */
public class TheBoard extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
	private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
    private Image img;
    private final Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;
	private SoundEffect  pacman_beginning=new SoundEffect("sound\\pacman_beginning.wav");
	private SoundEffect pacman_chomp=new SoundEffect("sound\\pacman_chomp.wav");
	private SoundEffect pacman_death=new SoundEffect("sound\\pacman_death.wav");
	private SoundEffect  pacman_eatfruit =new SoundEffect("sound\\pacman_eatfruit.wav");
    private ArrayList<Integer> scoreRecorde= new  ArrayList<Integer>(3);
    private boolean inGame = false;
    private boolean dying = false;
    private Listener listen=new Listener(this);
    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private int pacsLeft, score;
    private Timer time;
    private int currentSpeed = 3;
    private int[] dx, dy;
    private int[] screenData;
    private Timer timer;
    private PacPlayer pacman= new PacPlayer();
    private Ghost ghost=new Ghost();
    private MazeFile MAZE =new MazeFile();
    private  int  levelData[] =MAZE.Maze("levels\\level1.txt", N_BLOCKS);
    
    /***
     * @see  TheBoard(), This constructor method uses the various methods that are responsible for the various components 
     * that make up the game together.
     * 
     */
     public TheBoard(){
      ghost.loadGhost();   
      pacman.loadImages();
      initVariables();
      initBoard();
     }
     /**
      * @param listen
      * @exception  initBoard(),This method is responsible for KeyEvents and reloads each time 
      * so that user input is received so that the run is performed throughout the game
      * 
      */
     private void initBoard() {
        addKeyListener(listen);
 
         setFocusable(true);
 
         setBackground(Color.black);
     }
     /**
      * @param screenData, mazeColor,ghost,pacman_beginning
      * @param dx, dy, time, scoreRecorde
      * @exception  initVariables(), This method initializes the critical variables to the initial stage of game creation
      * 
      */
     private void initVariables() {
      try {  
        screenData = new int[N_BLOCKS * N_BLOCKS];
        this.mazeColor = Color.BLUE;
        d = new Dimension(400, 400);
        ghost.initialGhost(ghost.getMAX_GHOSTS());
        dx = new int[4];
        dy = new int[4];
        time = new Timer(45, this);
        scoreRecorde.add(0,0);
     	scoreRecorde.add(1,0);
     	scoreRecorde.add(2,0);
         
         pacman_beginning.play(true);
        
        time.start();
      }catch(Exception e) {
   	    JOptionPane.showMessageDialog(null,e.getMessage());
      }
    }
     
     /**
      * @see addNotify, Notifies this component because it now has a parent component. 
      * When this method is enabled, the parent component chain is integrated into KeyboardAction event listeners.
      */
    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }
    
/**
 * @exception initGame(), Set game settings even before uploading the game to the screen
 */
    public void initGame() {

        pacsLeft = 3;
        score = 0;
        initLevel();
        currentSpeed = 3;
    }

/**
 * @exception initLevel(), Initialize the maze to a new array
 */
    private void initLevel() {
     try {
        for (int i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel();
      }catch(Exception e) {
   	   JOptionPane.showMessageDialog(null,e.getMessage());
      }
    }

   /**
    * @exception contunueLevel(), Initialize player and ghost elements in defined locations
    */
    private void continueLevel() {

        int dx = 1;
        int random=0;
      try { 
        for (int i= 0; i < ghost.getMAX_GHOSTS(); i++) {
            ghost.setGhost_y(4 * BLOCK_SIZE, i);
            ghost.setGhost_x(4 * BLOCK_SIZE, i);
            
            ghost.setGhost_dx(0, i);
            ghost.setGhost_dx(dx, i);
            dx = -dx;
            try {
            random =(int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed){
                random = currentSpeed;
            }
            ghost.setGhostSpeed(ghost.getValidSpeeds(random), i); 
           }catch(ArithmeticException e) {
        	   JOptionPane.showMessageDialog(null,e.getMessage());
           }
        }
        pacman.setPacman_x(7*BLOCK_SIZE);
        pacman.setPacman_y(11*BLOCK_SIZE);
        pacman.setPacmand_x(0);
        pacman.setPacman_y(0);
        pacman.setReq_dx(0);
        pacman.setReq_dy(0);
        pacman.setView_dx(-1);
        pacman.setView_dy(0);
        dying=false;
      }catch(Exception e) {
   	   JOptionPane.showMessageDialog(null,e.getMessage());
      }
    } 
/**
 *  @param g
 * @exception  paintComponent(Graphics g), This method accepts a responsible type of paint, updating the components to the screen
 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    /**
     * 
     * @param g
     * @exception doDrawing(Graphics g), This method receives a Graphics type parameter, 
     * collects the data received and loads it, so that throughout the game
     * 
     * 
     */
    private void doDrawing(Graphics g) 
    {	
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);
     
        drawMaze(g2d);
        drawScore(g2d);
        pacman.doAnim();
      try 
      { 
        if (inGame) {
            playGame(g2d);
        } else if(pacsLeft==0)
        {
             int scoreTo1=scoreRecorde.get(0);
        	  int scoreTo2=scoreRecorde.get(1);
        	  int scoreTo3=scoreRecorde.get(2);
        	 int min=0;
        	 if(scoreTo1==0 || scoreTo2==0 || scoreTo3==0)
        	 {  
        		 if(scoreTo1==0)
        		 {
        			 scoreRecorde.set(0,score);
        			 
        		
        		 }
        		 if(scoreTo2==0 &&  scoreRecorde.get(0)>score)
        		 {    
        			 scoreRecorde.set(1,score);
        	
        			 
        		 }
        		 if(scoreTo3==0 &&  scoreRecorde.get(1)>score && scoreRecorde.get(0)>score)
        		 {
        			 scoreRecorde.set(2,score);
        			 
        			 
        		 }
        	} if(scoreTo1!=0 || scoreTo2!=0 || scoreTo3!=0)
        	{
        		if(scoreTo1 <score)
        		{  min=scoreTo2;
        		   scoreTo2=scoreTo1;
        		   scoreRecorde.set(0,score);
        		   scoreRecorde.set(1,scoreTo2);
        		   scoreRecorde.set(2,min);
        		 }else if(scoreTo2 <score && scoreTo1 >score && scoreTo3<score)
        		 {
        			 min=scoreTo2;
        			 scoreRecorde.set(1,score);
          		     scoreRecorde.set(2,min);
        		 }else {
        			 if(scoreTo3<score && scoreTo2 >score && scoreTo1 >score)
        			 {    min=0;
        				 scoreRecorde.set(2,score); 
        			 }
        		 }		
        	}	 
 	      showEndScreen(g2d);	
        }
        else {
            showIntroScreen(g2d);
        }

        g2d.drawImage(img, 5, 5, null);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
      }catch(Exception e) {
   	   JOptionPane.showMessageDialog(null,e.getMessage());
      }
    }
    
    /**
     * 
     * @param g2d
     *@exception drawMaze(Graphics2D g2d), This method accepts a Graphics2D type parameter and will load the maze 
     *including the player's food to the screen.
     * 
     */
    
    private void drawMaze(Graphics2D g2d) {

         int i = 0;
        int x, y;
       try {
        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) { 
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) { 
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) { 
                    g2d.setColor(dotColor.darker());
                    g2d.fillOval(x + 12, y + 11, 4, 4);
                
                }

                if ((screenData[i] == 32)) { 
                    g2d.setColor(dotColor.brighter());
                    g2d.fillOval(x + 12, y + 12, 8, 8);
                
                }

                i++;
            }
        }
       }catch(Exception e) {
    	   JOptionPane.showMessageDialog(null,e.getMessage());
       }
    }

    /**
     * 
     * @param g
     * @exception drawScore(Graphics2D g), This method accepts a Graphics2D type parameter and will load the score  of the player
     * Throughout the game.
     *
     */
    private void drawScore(Graphics2D g) {
        String s="";
       try { 
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);
     //   t= "Time: ";
      //  g.drawString(t, SCREEN_SIZE / 2 +10, SCREEN_SIZE + 16);
        for (int i = 0; i < pacsLeft; i++) {
            g.drawImage(pacman.getPacman3left(), i * 28 + 8, SCREEN_SIZE +1,null);
        }
      }catch(Exception e) {
   	   JOptionPane.showMessageDialog(null,e.getMessage());
      }
    }


    /**
     * 
     * @param g
     * @exception playGame(Graphics2D g2d), This method accepts a Graphics2D type parameter 
     * The method will be checked by a boolean variable if the player dies, if you do not continue to update and load normally
     *
     */
    private void playGame(Graphics2D g2d) {
      try {
        if (dying) {

            death();
            

        } else {
            movePacman();
           pacman.drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
      }catch(Exception e) {
   	   JOptionPane.showMessageDialog(null,e.getMessage());
      }
    }
    
    /*
     * @exception death(), This method checks whether the player finished the game or failed
     * 
     */

    private void death() {
     try {
        pacsLeft--;
        if (pacsLeft == 0) {
            pacman_death.play(true);
            levelData=MAZE.Maze("levels\\level1.txt", N_BLOCKS);
            currentSpeed=3;
            inGame = false;
        }

        continueLevel();
     }catch(Exception e) {
  	   JOptionPane.showMessageDialog(null,e.getMessage());
     }
    }
    /**
     * @param pos
     * @param ch
     * @param minus
     * @exception movePacman(), This method is responsible for moving the player in the maze.
     */
    private void movePacman() {

        int pos=0;
        int  ch=0;
        int minus=0;
     try {
        if (pacman.getReq_dx() == -pacman.getPacmand_x() && pacman.getReq_dy() == -pacman.getPacmand_y()) {
            pacman.setPacmand_x(pacman.getReq_dx());
            pacman.setPacmand_y(pacman.getReq_dy());
            pacman.setView_dx(pacman.getPacmand_x());
            pacman.setView_dy(pacman.getPacmand_y());
        }

        if ((pacman.getPacman_x() % BLOCK_SIZE) == 0 && ( pacman.getPacman_y() % BLOCK_SIZE) == 0) {
            pos = pacman.getPacman_x() / BLOCK_SIZE + N_BLOCKS * (int) (pacman.getPacman_y() / BLOCK_SIZE);
            if(pos <0)
            {
            	pos= pos*-1;
            }else if(pos>=225)
            {
               	minus=pos-225;
               	pos=pos-minus;
            }
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (int) (ch & 15);
                score++;
                pacman_chomp.play(true);
                
            }
            
            if ((ch ==32)) {
                screenData[pos] = (int) (ch & 0);
                score+=20;
                pacman_eatfruit.play(true);
                
            }

            if (pacman.getReq_dx() != 0 || pacman.getReq_dy() != 0) {
                if (!((pacman.getReq_dx() == -1 && pacman.getReq_dy() == 0 && (ch & 1) != 0)
                        || (pacman.getReq_dx() == 1 && pacman.getReq_dy() == 0 && (ch & 4) != 0)
                        || (pacman.getReq_dx() == 0 && pacman.getReq_dy() == -1 && (ch & 2) != 0)
                        || (pacman.getReq_dx() == 0 && pacman.getReq_dy() == 1 && (ch & 8) != 0))) {
                    pacman.setPacmand_x(pacman.getReq_dx());
                    pacman.setPacmand_y(pacman.getReq_dy());
                    pacman.setView_dx(pacman.getPacmand_x());
                    pacman.setView_dy(pacman.getPacmand_y());
                }
            }

            // Check for standstill
            if ((pacman.getPacmand_x() == -1 &&pacman.getPacmand_y() == 0 && (ch & 1) != 0)
                    || (pacman.getPacmand_x() == 1 && pacman.getPacmand_y()== 0 && (ch & 4) != 0)
                    || (pacman.getPacmand_x() == 0 && pacman.getPacmand_y() == -1 && (ch & 2) != 0)
                    || (pacman.getPacmand_x() == 0 && pacman.getPacmand_y() == 1 && (ch & 8) != 0)) {
                pacman.setPacmand_x(0);
                pacman.setPacmand_y(0);
            }
        }
        pacman.setPacman_x(pacman.getPacman_x() + pacman.getSpeed() *  pacman.getPacmand_x());
        pacman.setPacman_y(pacman.getPacman_y() + pacman.getSpeed() *  pacman.getPacmand_y());
     }catch(Exception e) {
  	   JOptionPane.showMessageDialog(null, e.getMessage());
      }
    }
    

    /**
     * @param pos
     * @param ch
     * @param minus
     * @exception moveGhosts(), This method is responsible for moving the ghost in the maze.
     */
  private void moveGhosts(Graphics2D g2d) {  
        int pos=0;
        int count=0;
        int minus=0;
       try{
        for (int i= 0; i < ghost.getN_GHOSTS(); i++) {
            if (ghost.getGhost_x(i) % BLOCK_SIZE == 0 && ghost.getGhost_y(i) % BLOCK_SIZE == 0) {
                pos = ghost.getGhost_x(i)/ BLOCK_SIZE + N_BLOCKS * (int) (ghost.getGhost_y(i) / BLOCK_SIZE);
                if(pos <0)
                {
                	pos= pos*-1;
                }else if(pos>=225)
                {
                   	minus=pos-225;
                   	pos=pos-minus;
                }
                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost.getGhost_dx(i) != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost.getGhost_dy(i) != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost.getGhost_dx(i) != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost.getGhost_dy(i) != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost.setGhost_dx(0, i);
                        ghost.setGhost_dy(0, i);
                    } else {
                        ghost.setGhost_dx( -ghost.getGhost_dx(i), i);
                        ghost.setGhost_dy( -ghost.getGhost_dy(i), i);
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }
                    ghost.setGhost_dx(dx[count], i);
                    ghost.setGhost_dy(dy[count], i);
                }

            }
            ghost.setGhost_x(ghost.getGhost_x(i)+(ghost.getGhost_dx(i) *ghost.getGhostSpeed(i)),i);
            ghost.setGhost_y(ghost.getGhost_y(i)+(ghost.getGhost_dy(i) *ghost.getGhostSpeed(i)),i);
            ghost.drawGhost(g2d, ghost.getGhost_x(i) + 1, ghost.getGhost_y(i)+ 1,i);

            if (pacman.getPacman_x()> (ghost.getGhost_x(i) - 12) && pacman.getPacman_x()< (ghost.getGhost_x(i) + 12)
                    && pacman.getPacman_y() > (ghost.getGhost_y(i) - 12) &&  pacman.getPacman_y()< (ghost.getGhost_y(i) + 12)
                    && inGame) {

                dying = true;
            }
        }
       }catch(InputMismatchException x)
       {   
    	   JOptionPane.showMessageDialog(null,x.getMessage());
    	 }
    }
  /**
   * 
   * @param g2d
   * 
   * @exception showIntroScreen(Graphics2D g2d), This method is responsible for the start view before the game starts, 
   * this method knows how to accept Graphics2D type variable.
   */
    private void showIntroScreen(Graphics2D g2d) {
    	try {
        Image startI= new ImageIcon("images\\start.PNG").getImage();
    	g2d.drawImage(startI,0, 0, null);
        g2d.drawRect(0, 0, SCREEN_SIZE, SCREEN_SIZE);
       }catch(Exception x)
    	 {   
    	   JOptionPane.showMessageDialog(null,x.getMessage());
    	 }
    }

    /**
     * @param g2d
     * 
     * @exception showIntroScreen(Graphics2D g2d), This method is responsible for the end view before the game starts, 
     * this method knows how to accept Graphics2D type variable.
     */
	public void showEndScreen(Graphics2D g2d) {
	 try {	
		Image endI = new ImageIcon("images\\end.PNG").getImage();
		g2d.drawImage(endI, 0, 0, null);
		g2d.drawRect(0, 0, SCREEN_SIZE, SCREEN_SIZE);
        g2d.setColor(new Color(167,18,1));
        g2d.fillRect(55, SCREEN_SIZE / 3 + 100, SCREEN_SIZE - 100, 55);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(55, SCREEN_SIZE / 3 + 100, SCREEN_SIZE - 100, 55);
        String startAgain="";
        int x=1;
   	    Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);
        for(int i=0; i< scoreRecorde.size();i++)
        { 
          startAgain = " The " + (i+1)+" Score : "+ "  "+scoreRecorde.get(i) +"";
		  g2d.setColor(Color.white.brighter());
		  g2d.setFont(small);
	      x+=8;
	      if(i>1)
	      {
	    	  x=16;
	      }
		 g2d.drawString(startAgain, ((SCREEN_SIZE - metr.stringWidth(startAgain)) / 2), (SCREEN_SIZE / 2 ) +i*x+55);
        }
	 }catch(Exception x)
	 {   
		   JOptionPane.showMessageDialog(null,x.getMessage());
		 }
	}
	
	/**
	 * @exception checkMaze(), This method checks if the player has finished eating all the food in the maze as well and 
	 * will receive extra points and move on to the next maze (new level)
	 * 
	 */
    private void checkMaze() {

        int i = 0;
        boolean finished = true;
     try {
        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screenData[i] & 48) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished){
            score += 50;
            
            if (currentSpeed < ghost.getMaxSpeed()){
                currentSpeed++;
            }
            
            levelData=MAZE.Maze("levels\\level2.txt", N_BLOCKS);
            initLevel();
        }
      }catch(InputMismatchException x)
     {   
    	   JOptionPane.showMessageDialog(null,x.getMessage());
    	 }
    }
    
    /***
     * @see actionPerformed(ActionEvent e), This method accepts an event from methods and is responsible for updating each event in the game continuously 
     * so as to produce a gaming experience and receive input from the player continuously.
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    
        repaint();
    }
    
    
   /**
    * getter and setter 
    * 
    */
    
	public boolean getInGame() {
		return this.inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public Timer getTimer() {
		return this.timer;
	}

	public PacPlayer getPacman() {
		return this.pacman;
    }
    
    public SoundEffect getPacman_chomp() {
        return this.pacman_chomp;
    }

    public SoundEffect getPacman_beginning() {
        return this.pacman_beginning;
    }
}
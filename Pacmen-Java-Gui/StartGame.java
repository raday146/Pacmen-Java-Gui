import javax.swing.JFrame;

public class StartGame extends JFrame {
	
    private static final long serialVersionUID = 1L;
    /**
     * @see Creating a temporary object of Runnable type and implementation of the run method
     */
     
    public StartGame() {
        
        add(new TheBoard());
        setResizable(false);
        pack();
        setTitle("Pacman");        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 430);
        setLocationRelativeTo(null);
    }
    
   // private void initUI() {
         
    //}
    public static void main(String[] args)throws InterruptedException {

        Thread t= new Thread(new Runnable(){
            @Override
            public void run() {
                JFrame frame = new StartGame();
                frame.setVisible(true);
            }
        
        });
        t.start();
    }
}
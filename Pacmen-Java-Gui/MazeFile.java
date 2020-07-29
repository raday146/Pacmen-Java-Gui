
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;


/***
 * 
 * @author Andrey Leskov, Shlomo Raday
 * @exception MazeFile, Responsible for reading a file containing the maze game design, which is an integral part of the maze creation phase
 * 
 *
 */

public class MazeFile{

 private int[] maze;
 /***
  * 
  * @param filePath
  * @param N_BLOCKS
  * @return  The method will return an array containing the maze.
  * @see  int[] Maze(String filePath, int N_BLOCKS),This method is responsible for reading from the file and extracting the maze design.
  */
 public  int[] Maze(String filePath, int N_BLOCKS)
 {  try {
    File f = new File(filePath);   
    Scanner s = new Scanner(f);
    maze=new int[N_BLOCKS*N_BLOCKS];
    int x=0;
    while(s.hasNext())
    { for(int i=0;i<N_BLOCKS;i++)
      {
        for(int j=0;j<N_BLOCKS;j++)
         {   
             maze[x]=s.nextInt();
            if(s.hasNext())
            {
                x++;
            }else{
                break;
            } 
         }  
      } 
    }
    s.close();
    return maze;

 } catch (FileNotFoundException e) {
	 JOptionPane.showMessageDialog(null,e.getMessage());
 }catch(InputMismatchException x)
 {   
   JOptionPane.showMessageDialog(null,x.getMessage());
 }
    return maze;

 }

}
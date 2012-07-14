import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import javax.swing.JOptionPane;


public class MazeCreator {
	
	public static int WAIT_TIME = 0;
	public static int SQUARE_SIZE = 40;
	public static boolean truncateMaze[] = {true,false};

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException 
	{
		String response = JOptionPane.showInputDialog(null, "Enter file name: (i.e. SimpleMaze or HamptonMaze)");
		while(true)
		{
			File f = new File(response + ".txt");
			if (f.exists())
				break;
			response = JOptionPane.showInputDialog(null, "Not a maze. Enter file name: (i.e. SimpleMaze or HamptonMaze)");
		}
		File f = new File(response + ".txt");
		int[] size = calculateSize(f);
		
		MazeNode[][] matrix = new MazeNode[size[0]][size[1]];
		SQUARE_SIZE = Math.min(600 / size[0], 600 / size[1]);
		SQUARE_SIZE = Math.min(SQUARE_SIZE, 40);
		DrawingPanel panel = new DrawingPanel(size[1]*SQUARE_SIZE, size[0]*SQUARE_SIZE);
		Graphics panelGraphics = panel.getGraphics();
		MazeNode start = null, end = null;
		
		Scanner rowScanner = new Scanner(f);
		for (int i = 0; i < matrix.length; i++)
		{
			String row = rowScanner.nextLine();
			for (int j = 0; j < row.length(); j++)
			{
				int spot = Integer.parseInt(row.substring(j, j+1));
				if (spot> 0)
				{
					matrix[i][j] = new MazeNode(new Point(i,j));
					if (spot == 2)
					{
						start = matrix[i][j];
					}
					else if (spot == 3)
					{
						end = matrix[i][j];
					}
				}
			}
		}
		
		
		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[i].length; j++)
			{
				if(matrix[i][j] == null)
				{
					panelGraphics.setColor(Color.BLACK);
					panelGraphics.fillRect(SQUARE_SIZE*j, SQUARE_SIZE*i, SQUARE_SIZE, SQUARE_SIZE);
				}
				else
				{
					if (matrix[i][j] == start)
					{
						panelGraphics.setColor(Color.GREEN);
					} else if (matrix[i][j] == end)
					{
						panelGraphics.setColor(Color.BLUE);
					} else
					{
						panelGraphics.setColor(Color.WHITE);
					}
					
					matrix[i][j].defaultColor = panelGraphics.getColor();
					
					panelGraphics.fillRect(SQUARE_SIZE*j, SQUARE_SIZE*i, SQUARE_SIZE, SQUARE_SIZE);
					
					if (i+1 < matrix.length && matrix[i+1][j] != null)
					{
						matrix[i][j].addToGraph(matrix[i+1][j]);
					}
					if (j+1 < matrix[i].length && matrix[i][j+1] != null)
					{
						matrix[i][j].addToGraph(matrix[i][j+1]);
					}
				}
			}
		}
		
		if (truncateMaze[0])
		{
			while (true)
			{
				int count = 0;
				for (int i = 0; i < matrix.length; i++)
				{
					for (int j = 0; j < matrix[i].length; j++)
					{
						if (matrix[i][j] != null && matrix[i][j] != start && matrix[i][j] != end && matrix[i][j].adjacents.size() == 1)
						{
							matrix[i][j].remove(matrix[i][j].adjacents.get(0));
							panelGraphics.setColor(Color.DARK_GRAY);
							panelGraphics.fillRect(j*SQUARE_SIZE, i*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
							count++;
						}
					}
				}
				pause(WAIT_TIME*10);
				if (count == 0)
					break;
			}
		}
		if (truncateMaze[1])
		{
			for (int i = 0; i < matrix.length; i++)
			{
				for (int j = 0; j < matrix[i].length; j++)
				{
					if (matrix[i][j] != null && matrix[i][j] != start && matrix[i][j] != end && matrix[i][j].adjacents.size() == 2)
					{
						MazeNode a = matrix[i][j].adjacents.get(0), b = matrix[i][j].adjacents.get(1);
						a.remove(matrix[i][j]);
						b.remove(matrix[i][j]);
						a.addToGraph(b);
					}
				}
			}
		}
		
		List<ArrayList<MazeNode>> tree = new ArrayList<ArrayList<MazeNode>> ();
		tree.add(new ArrayList<MazeNode> ());
		tree.get(0).add(start);
		
		while (true)
		{
			ArrayList<MazeNode> thisItteration = new ArrayList<MazeNode> ();
			tree.add(thisItteration);
			for(MazeNode m : tree.get(tree.size()-2))
			{
				for(MazeNode l : m.adjacents)
				{
					boolean contained = false;
					
					for(ArrayList<MazeNode> listInTree : tree)
					{
						contained = listInTree.contains(l) || contained;
					}
					
					if (!contained)
					{
						m.addToTree(l);
						thisItteration.add(l);
						panelGraphics.setColor(Color.GREEN);
						panelGraphics.drawLine(SQUARE_SIZE * m.position.y + SQUARE_SIZE / 2, SQUARE_SIZE * m.position.x + SQUARE_SIZE / 2, SQUARE_SIZE * l.position.y + SQUARE_SIZE / 2, SQUARE_SIZE * l.position.x + SQUARE_SIZE /2);
					}
				}
				pause(WAIT_TIME);
			}
			if (thisItteration.contains(end))
				break;
		}
		
	
		MazeNode currentNode = end;
		while (currentNode != start)
		{			
			panelGraphics.setColor(Color.RED);
			panelGraphics.drawLine(SQUARE_SIZE * currentNode.position.y + SQUARE_SIZE / 2, SQUARE_SIZE * currentNode.position.x + SQUARE_SIZE / 2, SQUARE_SIZE * currentNode.parent.position.y + SQUARE_SIZE /2, SQUARE_SIZE * currentNode.parent.position.x + SQUARE_SIZE /2);
			pause(WAIT_TIME);
			currentNode = currentNode.parent;
			
		}

	}
	
	/**
	* This method takes a pause based on the number of miliseconds asked by the param.
	* @param double millis The number of milliseconds the program will pause for.
	*/
	public static void pause(double millis) { //By taking doubles eases input possibilities from the user
		try {
			Thread.currentThread().sleep((int)millis); //Only takes integers
		}
		catch (InterruptedException e) {
			e.printStackTrace(); //If the sleep cannot happen
		}
	}  //End Pause Method
	
	public static int[] calculateSize(File f) throws FileNotFoundException
	{
		Scanner scan = new Scanner (f);
		int[] size = new int[2];
		while (scan.hasNextLine())
		{
			size[0]++;
			scan.nextLine();
		}
		scan = new Scanner (f);
		size[1] = scan.nextLine().length();
		return size;
	}

}

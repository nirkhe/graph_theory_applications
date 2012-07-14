import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;



public class MazeNode
{
	public Point position;
	
	public Color defaultColor = Color.WHITE;
	
	public MazeNode(Point position)
	{
		this.position = position;
		this.adjacents = new ArrayList<MazeNode> ();
		this.children = new ArrayList<MazeNode> ();
		
	}
	
	public List<MazeNode> adjacents;
	
	public List<MazeNode> children;
	
	public MazeNode parent;
	
	/**
	 * Adds child node in the Tree
	 * @param mn
	 */
	public void addToTree(MazeNode mn)
	{
		this.children.add(mn);
		mn.parent = this;
	}
	
	public void addToGraph(MazeNode mn)
	{
		this.adjacents.add(mn);
		mn.adjacents.add(this);
	}
	
	public void remove(MazeNode mn)
	{
		this.adjacents.remove(mn);
		mn.adjacents.remove(this);
	}
	
}

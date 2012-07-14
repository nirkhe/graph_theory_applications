import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ConstructMaze
{

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		File file= new File("circle.gif");
  		BufferedImage image = ImageIO.read(file);
		File f = new File("circle.txt");
		PrintStream p = new PrintStream(f);
		
		for (int i = 0; i < 584; i++)
		{
			for (int j = 0; j < 584; j++)
			{
				int clr=  image.getRGB(j,i); 
				int  red   = (clr & 0x00ff0000) >> 16;
 				int  green = (clr & 0x0000ff00) >> 8;
  				int  blue  =  clr & 0x000000ff;
				
				p.print(green > 55 ? 1 : 0);
			}
			p.println("");
		}
	
	}


}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;


public class Runner
{

	static LinkedList<Node> teachers = new LinkedList<Node> ();
	static LinkedList<Node> students = new LinkedList<Node> ();
	static LinkedList<Node> dummyStudents = new LinkedList<Node> ();
	static int totalColors;

	public static void main(String[] args) throws FileNotFoundException
	{
		constructNodes();

		createDummySetUp();

		LinkedList<LinkedList<Node>> studentparts = new LinkedList<LinkedList<Node>> ();

		for(int i = 0; i < students.size(); i+= teachers.size())
		{
			LinkedList<Node> subList = new LinkedList<Node> ();
			for (int j = i; j < Math.min(i + teachers.size(), students.size()); j++)
			{
				subList.add(students.get(j));
			}
			studentparts.add(subList);
		}

		LinkedList<LinkedList<Node>> teacherClones = new LinkedList<LinkedList<Node>> ();

		for(int i = 0; i < studentparts.size(); i++)
		{
			LinkedList<Node> temp = new LinkedList<Node> ();
			for(Node n : teachers)
			{
				temp.add(new Node(n.classification, n.name, n.classes));
			}
			teacherClones.add(temp);
		}

		for(int h = 0; h < studentparts.size(); h++)
		{
			LinkedList<Node> studs = studentparts.get(h);
			LinkedList<Node> teach = teacherClones.get(h);
			for(int i = 0; i < studs.size(); i++)
			{
				Node stud = studs.get(i);
				for(int j = 0; j < stud.classes.size(); j++)
				{
					String class1 = stud.classes.get(j);
					for(int k = 0; k < teach.size(); k++)
					{
						Node teacher = teach.get(k);
						if (teacher.classes.contains(class1))
						{
							Edge e = teachers.get(k).getEdge(dummyStudents.get(i));
							Edge f = new Edge(stud, teacher, e.color);
							f.classname = class1;
							stud.neighbors.add(f);
							teacher.neighbors.add(f);
						}
					}
				}

			}
			
			totalColors = teachers.size();
			
			//INSERT RESORTING FOR CLASSES HERE
			for(int i = 0; i <teachers.size(); i++)
			{
				LinkedList<Node> listOfClones = new LinkedList<Node> ();
				for(int j = 0; j < teacherClones.size(); j++)
				{
					listOfClones.add(teacherClones.get(j).get(i));
				}
				for(int j = 0; j < totalColors; j++)
				{
					String[] classes = new String[listOfClones.size()];
					for(int k = 0; k < classes.length; k++)
					{
						classes[k] = listOfClones.get(k).getCourse(j);
					}
					

					for(int k = 0; k < classes.length - 1; k++)
					{
						if(!classes[k].equals(classes[k+1]))
						{
							Node MessedUpTeacher = teacherClones.get(k+1).get(i);
							Edge e = MessedUpTeacher.getEdge(j);
							e.color = (e.color + 1) % totalColors;
							
							Edge be = null;
							for(int m = 0; m < MessedUpTeacher.neighbors.size(); m++)
							{
								if (MessedUpTeacher.neighbors.get(m).color == e.color && MessedUpTeacher.neighbors.get(m) != e)
								{
									be = MessedUpTeacher.neighbors.get(m);
								}
							}
							if (be != null)
							{
								be.color = 0;
								Node opposite = be.getOther(MessedUpTeacher);
								
								for(Edge epsilon: MessedUpTeacher.neighbors)
								{
									if(opposite.getEdge(epsilon.color) == null)
									{
										EdgeSquare n1 = new EdgeSquare(be, epsilon, true);
										be.connections.add(n1);
										epsilon.connections.add(n1);
									}
								}
								
								for(Edge epsilon:  opposite.neighbors)
								{
									if(epsilon != be && epsilon.getOther(opposite).getEdge(epsilon.color) == null)
									{
										EdgeSquare n2 = new EdgeSquare(epsilon, null);
									}
								}
								}
															
								
							}
							
							
							//RECOLOR
						}
					}
					
				}
								
			}
			
			
		}

	}

	public static void constructNodes() throws FileNotFoundException
	{
		
		Scanner scan = new Scanner(new File("data.txt"));
		
		while(scan.hasNextLine())
		{
			String line = scan.nextLine();
			Scanner temp = new Scanner(line);
			String name = temp.next();
			String classification = temp.next();
			LinkedList<String> classes = new LinkedList<String> ();
			while(temp.hasNext())
			{
				classes.add(temp.next());
			}
			if (classification.equals("T"))
			{
				teachers.add(new Node(Node.Classification.TEACHER, name, classes));
			}
			else
			{
				students.add(new Node(Node.Classification.STUDENT, name, classes));
			}
			
		}

	}

	static void createDummySetUp()
	{
		for(int i = 0; i < teachers.size(); i++)
		{
			dummyStudents.add(new Node(Node.Classification.STUDENT, ""));
		}

		for (int i = 0; i < teachers.size(); i++)
		{
			int count = i;
			for(Node d: dummyStudents)
			{
				d.add(teachers.get(i), (++count % 3));
			}
		}
	}

}

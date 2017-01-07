import java.io.FileNotFoundException;

public class Main
{
	public static void main(String[] args) throws FileNotFoundException
	{
		
		Graph graph = new Graph();
		graph.CreateGraphFromFile();
		//graph.printNodes();
		//graph.printEdges();
		//graph.Create_hMatrix();
		//graph.Create_sMatrix();
		//graph.Create_gMatrix();
		graph.DescribeGraph();
		graph.ComputePageRank();
	}
}
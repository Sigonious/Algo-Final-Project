import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Graph
{
	String name;
	NodeSet ns;
	EdgeSet es;
	double[][] hMatrix, hMatrixClone;
	double[][] sMatrix;
	double[][] gMatrix;
	double[] piVector;
	int[] nodes = new int[10];
	double[][] edges = new double[10][10];
	int[] edgeCount = new int[10];
	double[][] dangle = new double[10][1];
	
	public void CreateGraphFromFile() throws FileNotFoundException
	{
		String temp, key="";
		int a = 0, i = 0, state = 0, thing = 1;
		Edge edge;
		Node node;
		es = new EdgeSet();
		ns = new NodeSet();
		Scanner scanner = new Scanner(new File("PageRank_03.txt"));
		Scanner in = scanner.useDelimiter("");
		while(in.hasNext())
		{
			if(in.hasNextInt())
			{
				temp = in.next();
				a = Integer.parseInt(temp);
				i++;
				if(in.hasNextInt())
				{
					temp = temp + in.next();
					a = Integer.parseInt(temp);
				}
				if(i%11 == 0 && state == 0)
				{
					state = 1;
					i=1;
				}
				if(state == 0)
				{
					node = new Node(temp);
					ns.Add(node);
				}
				if(i%2 != 0 && state == 1)
				{
					key = temp;
				}
				if(i%2 == 0 && state == 1)
				{
					edge = new Edge(Integer.parseInt(key), a);
					es.Add(Integer.toString(thing), edge);
					edges[Integer.parseInt(key) - 1][a - 1] = 1;
					edgeCount[Integer.parseInt(key) - 1] += 1;
					thing++;
					i = 0;
				}
			}
			else
			{
				in.next();
			}
		}
		scanner.close();
		Create_hMatrix();
		Create_sMatrix();
		Create_gMatrix();
	}
	
	public void Create_hMatrix()
	{
		hMatrix = new double[edges.length][edges[0].length];
		hMatrixClone = new double[edges.length][edges[0].length];
		DecimalFormat format = new DecimalFormat("#.00");
		for(int i = 0; i<edges.length; i++)
		{
			for(int j = 0; j<edges[i].length; j++)
			{
				if(edges[i][j] == 1)
				{
					hMatrix[i][j] = edges[i][j]/edgeCount[i];
					hMatrixClone[i][j] = edges[i][j]/edgeCount[i];
					//System.out.print(format.format(hMatrix[i][j])+"\t");
				}
				else
				{
					hMatrix[i][j] = edges[i][j];
					hMatrixClone[i][j] = edges[i][j];
					//System.out.print(hMatrix[i][j]+"\t");
				}
			}
			//System.out.println("\n");
		}
	}
	
	public void Create_sMatrix()
	{
		sMatrix = new double[hMatrix.length][hMatrix[0].length];
		DecimalFormat format = new DecimalFormat("#.00");
		double temp = 0;
		for(int i = 0; i<hMatrix.length; i++)
		{
			for(int j = 0; j<hMatrix[i].length; j++)
			{
				temp += hMatrix[i][j];
				sMatrix[i][j] = hMatrix[i][j];
			}
			dangle[i][0] = 0;
			if(temp == 0)
			{
				dangle[i][0] = 1;
				for(int j = 0; j<hMatrix[i].length; j++)
				{
					sMatrix[i][j] = ((double)(1))/((double)(sMatrix.length));
				}
			}
			temp = 0;
			/*for(int j = 0; j<sMatrix[i].length; j++)
			{
				System.out.print(format.format(sMatrix[i][j])+"\t");
			}
			System.out.println("\n");*/
		}
	}
	
	public void Create_gMatrix()
	{
		MatrixMult mm = new MatrixMult();
		double damp = 0.9;
		double[][] a = dangle;
		double[][] e = {{1},{1},{1},{1},{1},{1},{1},{1},{1},{1}};
		double[][] eT = mm.transpose(e);
		double[][] temp = hMatrix;
		double[][] temp2 = new double[a.length][a[0].length];
		
		for(int i = 0; i<hMatrix.length; i++)
		{
			for(int j = 0; j<hMatrix[i].length; j++)
			{
				temp[i][j] = ((double)temp[i][j] * (double)damp);
			}
		}
		for(int i = 0; i<a.length; i++)
		{
			a[i][0] = ((double)a[i][0]*(double)damp);
			e[i][0] = ((double)e[i][0]*(double)(1-damp));
			temp2[i][0] = ((double)a[i][0] + (double)e[i][0]);
		}
		for(int i = 0; i<eT.length; i++)
		{
			for(int j = 0; j<eT[0].length; j++)
			{
				eT[i][j] = ((double)(1))/((double)(sMatrix.length)) * eT[i][j];
			}
		}
		double[][] temp3 = mm.matrixMult(temp2, eT);
		gMatrix = mm.matrixAdd(temp, temp3);
		//DescribeMatrix(gMatrix);
	}
	
	public void ComputePageRank()
	{
		System.out.println("============================\n\tPage Rank\n============================");
		DecimalFormat format = new DecimalFormat("#.00000");
		MatrixMult mm = new MatrixMult();
		double[][] factor = {{.1,.1,.1,.1,.1,.1,.1,.1,.1,.1}};
		double[][] temp = new double[1][10];
		for(int i = 0; i < 10; i++)
		{
			temp = mm.matrixMult(factor, gMatrix);
			System.out.print("Iteration #"+(i+1)+"\n[");
			for(int j = 0; j < temp[0].length; j++)
			{
				if(j==temp[0].length - 1)
				{
					System.out.print(format.format(temp[0][j])+"]");
					continue;
				}
				System.out.print(format.format(temp[0][j])+", ");
			}
			System.out.println("\n");
			factor = temp;
		}
	}
	
	public void DescribeGraph()
	{
		System.out.println("CSCI 5330 Spring 2016\nAdam Murr\n900853718\n\nPageRank_03.txt");
		ns.Describe();
		es.Describe();
		System.out.println("================================\n\tAdjacency Matrix\n================================");
		DescribeMatrix(hMatrixClone);
		System.out.println("============================================\n"
				+ "\tTransition Stochastic Matrix\n"
				+ "============================================");
		DescribeMatrix(sMatrix);
		System.out.println("========================\n\tgMatrix\n========================");
		DescribeMatrix(gMatrix);
	}
	
	public void DescribeMatrix(double[][] matrix)
	{
		DecimalFormat format = new DecimalFormat("#.00");
		System.out.println();
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[0].length; j++)
			{
				if(j == matrix[0].length - 1)
				{
					System.out.print(format.format(matrix[i][j]));
					continue;
				}
				System.out.print(format.format(matrix[i][j])+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printNodes()
	{
		ns.Describe();
	}
	
	public void printEdges()
	{
		es.Describe();
	}
}
public class MatrixMult
{
	public double[][] transpose(double[][] a)
	{
		double[][] b = new double[a[0].length][a.length];
		for(int i = 0; i<a.length;i++)
		{
			for(int j = 0; j<a[0].length;j++)
			{
				b[j][i] = a[i][j];
			}
		}
		return b;
	}
	
	public double[][] matrixMult(double[][] a, double[][] b)
	{
		int aLength = a[0].length, bLength = b.length;
		if(aLength != bLength) throw new RuntimeException("Invalid dimensions: "+aLength+" "+bLength+" were the dimensions used.");
		double[][] c = new double[a.length][b[0].length];
		for(int i = 0; i < a.length; i++)
		{
			for(int j = 0; j < b[0].length; j++)
			{
				for(int k = 0; k < a[0].length; k++)
				{
					c[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return c;
	}
	
	public double[][] matrixAdd(double[][] a, double[][] b)
	{
		for(int i = 0; i < a.length; i++)
		{
			for(int j = 0; j < a[i].length; j++)
			{
				a[i][j] += b[i][j];
			}
		}
		return a;
	}
}
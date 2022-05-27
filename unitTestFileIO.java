public class unitTestFileIO
{
	public static void main(String [] args)
	{	
		int count = 0;
		DSAGraph graph = new DSAGraph(); //variable declaration
		journeyClass journey = new journeyClass();
		System.out.println("************Testing FileIO Methods using 'Map_int_314.txt' and 'Journey_314.txt'**********\n\n");
		System.out.println("Testing readFile method (for graph)");
		try{
			fileIO.readFile("Map_int_314.txt",graph);
			System.out.println("Map read into graph successfully\n");
			count++;
		}
		catch(Exception e)
		{
			System.out.println("Read in failed. Error: "+e);
		}
		System.out.println("Testing readFile method (for journey)");
		try
		{
			fileIO.readFile("Journey_314.txt",journey);
			System.out.println("Journey read into journeyClass object successfully\n");
			count++;
		}
		catch(Exception e)
		{
			System.out.println("Read in failed. Error: "+e);
		}

		System.out.println("Testing writeFile. Writing to 'testFileIO.txt'");
		try
		{
			String[] testArray = {"this","is","a","test","to","write","to","a","file"};
			fileIO.writeFile("testFileIO.txt",testArray);
			System.out.println("File written successfully\n");
			count++;
		}
		catch(Exception e)
		{
			System.out.println("File writing failed. Error: "+e);
		}
		System.out.println("***********Passed "+count+"/3 test cases************");
	}
}

		



//SALMAN MAULAVIZADA
//DSA ASSIGNMENT MODIFIED DSAGRAPH
import java.util.*;
import java.io.*;

public class DSAGraph implements Serializable {

	private class edgeClass implements Serializable {
		private double weight;
		private String from;
		private String to;
		private String security;
		private String barriers;		//declaring inner class fields
		private String direction;

		private edgeClass(double pWeight, String pFrom, String pTo, String pDirection, String pSec, String pBarrier) {
			weight = pWeight;
			from = pFrom;
			to = pTo;
			security = pSec;
			barriers = pBarrier;
			direction = pDirection;		//constructor
		}

		private double getWeight() {
			return weight;
		}

		private String getFrom() {
			return from;
		}

		private String getTo() {
			return to;
		}

		private String getBarriers() {
			return barriers;
		}

	}

	private class DSAGraphVertex implements Serializable {
		private String label;
		private boolean visited;
		private LinkedList link;			//declaring class fields of inner class
		private Object value;

		private DSAGraphVertex(String pLabel, Object pValue) {
			label = pLabel;
			visited = false;
			this.link = new LinkedList();
			value = pValue;
		}

		private void addEdge(edgeClass pEdge) {
			link.insertFirst(pEdge);
			//adds edge to the vertex's 'link' LinkedList and to the edge class field

		}

		private edgeClass getEdge(String edge) {
			edgeClass returnEdge = null;
			for (Object o : link) {
				if (((edgeClass) o).to.equals(edge)) {
					returnEdge = ((edgeClass) o);		//iterates through the LinkedList of edge of the vertex 
				}										//and returns the edge which matches the imported string
			}
			return returnEdge;
		}

		private void removeEdge(edgeClass edge) {
			for (Object o : link) {
				if (((edgeClass) o) == edge) {
					link.remove(edge);
					
						//iterates through LinkedList of edges of the vertex and removes edge using remove function
				} 
			}
		}

		public LinkedList getAdjacent() {
			return link;
		}

		public void setVisited() {
			visited = true;
		}

		public void clearVisited() {
			visited = false;
		}

		public boolean getVisited() {
			return visited;
		}


	}

	private LinkedList vertices;		//class fields - LinkedList of vertices of the graph

	public DSAGraph() {					//public graph class
		vertices = new LinkedList();
	}

	public void addVertex(String label, Object value)
	{

		if (!hasVertex(label)) {

			DSAGraphVertex vertex = new DSAGraphVertex(label, value);//adds vertex to graph's vertices LinkedList

			vertices.insertFirst(vertex);
		}
	}

	public void removeVertex(String pLabel) {
		for (Object o : vertices) {
			if (((DSAGraphVertex) o).label.equals(pLabel)) {
				vertices.remove(getVertex(pLabel));
			}
			for (Object p : ((DSAGraphVertex) o).link) {
				if ((((edgeClass) p).from).equals(pLabel)) {	//iterates through vertices list and removes vertex once found
					((DSAGraphVertex)o).link.remove(((edgeClass) p));
				} else if ((((edgeClass) p).to).equals(pLabel)) {
					((DSAGraphVertex)o).link.remove(((edgeClass) p));
				}

			}
		}
	}

	public void addEdge(double pWeight, String labelOne, String labelTwo, String pDirection, String pSecurity,String pBarriers) {			//adds edge to vertexex using imported parameters
		edgeClass edge = new edgeClass(pWeight, labelOne, labelTwo, pDirection, pSecurity, pBarriers);

		addVertex(labelOne,0);
		addVertex(labelTwo,0);
		getVertex(labelOne).addEdge(edge);
	}

	public void updateNode(String name, String newName) {
		for (Object o : vertices) {
			if (((DSAGraphVertex) o).label.equals(name)) {
				(((DSAGraphVertex) o).label) = newName;
			}
			for (Object p : ((DSAGraphVertex) o).link) {		//iterates through vertices list to change name of the node
				if ((((edgeClass) p).from).equals(name)) {		//also changes all the names of the matching 'from' and 'to' edges in the edge linkedlist of each vertex
					(((edgeClass) p).from) = newName;			//this is because vertex names were used in edges as well, therefore if node name is changed, the edges have to be modified as well
				} else if ((((edgeClass) p).to).equals(name)) {
					(((edgeClass) p).to) = newName;
				}
			}
		}
	}

	public static edgeClass[] insertionSort(edgeClass[] arr)
    {
	int length = arr.length;
	for(int j = 1; j<length; j++)
	{
		double index = arr[j].weight;
		int i = j-1;
		while ((i>-1) && (arr [i].weight > index)) {
			arr[i+1].weight = arr[i].weight;
			i--;
		}
		arr[i+1].weight = index;
	}
    return arr;
    
    }

	boolean findEdge(String from, String to, double dis, String obst, String sec) {
		int count = 0;
		boolean find = false;
		for (Object o : vertices) {
			for (Object p : ((DSAGraphVertex) o).link) {		//iterates through every edge list in each vertex to find matching edge
				if (((edgeClass) p).from.equals(from) && (((edgeClass) p).to.equals(to))	//prints out relevant statement with edge
						&& (((edgeClass) p).weight == dis) && (((edgeClass) p).barriers.equals(obst))
						&& (((edgeClass) p).security.equals(sec))) {
					count++;
					System.out.println("\n Edge from " + from + " to " + to + " with " + dis + " distance " + " with "
							+ obst + " obstacles " + " with " + sec + " security level exists\n");
					find = true;
				}
			}
		}
		if (count == 0) {		//if edge isnt found, prints message to user
			System.out.println("Edge not found");
		}
		return find;
	}

	public void displayWorld()

	{
		for (Object o : vertices) {
			for (Object p : ((DSAGraphVertex) o).link)	//prints every route in the map, to the user

			{
				System.out.println(((((edgeClass) p).from) + ", To: " + (((edgeClass) p).to) + ", Distance is: "
						+ (((edgeClass) p).weight) + ", Security Level: " + (((edgeClass) p).security) + ", Obstacles: "
						+ (((edgeClass) p).barriers)));
			}
		}
	}

	public void displayWorld(String fileName) {
		FileOutputStream fileStrm = null;
		PrintWriter pw;
		try {
			fileStrm = new FileOutputStream(fileName, true);//prints everty route in the map to the user, and saves it to the file input by user
			pw = new PrintWriter(fileStrm);
			for (Object o : vertices) {
				for (Object p : ((DSAGraphVertex) o).link)

				{
					pw.println(((((edgeClass) p).from) + ", To: " + (((edgeClass) p).to) + ", Distance is: "
							+ (((edgeClass) p).weight) + ", Security Level: " + (((edgeClass) p).security)
							+ ", Obstacles: " + (((edgeClass) p).barriers)));
				}
			}
			pw.close();
		}

		catch (IOException e) {
			if (fileStrm != null) {
				try {
					fileStrm.close();
				} catch (IOException ex2) {
				}
			}
			System.out.println("Error writing to file " + e.getMessage());	//exception handling
		}

	}

	public void removeEdge(String from, String to, double dis, String obst, String sec) {
		int count = 0;
		for (Object o : vertices) {	//iterates through every vertices edge list to find matching edge, and removes them
			for (Object p : ((DSAGraphVertex) o).link) {
				if (((edgeClass) p).from.equals(from) && (((edgeClass) p).to.equals(to))
						&& (((edgeClass) p).weight == dis) && (((edgeClass) p).barriers.equals(obst))
						&& (((edgeClass) p).security.equals(sec))) {
					count++;
					System.out.println("Edge Removed");
					getVertex(from).removeEdge((edgeClass) p);
				}
			}
		}
		if (count == 0) {	//if edge isnt found, message is printed to user
			System.out.println("Edge not found");
		}
	}

								//following methods change edge class field values by iterating through vertex's 'link' list of edges and modifying the matching edges

	public void setSecurity(String from, String to, double dis, String obst, String sec, String newSec) {
		for (Object o : vertices) {
			for (Object p : ((DSAGraphVertex) o).link) {
				if (((edgeClass) p).from.equals(from) && (((edgeClass) p).to.equals(to))
						&& (((edgeClass) p).weight == dis) && (((edgeClass) p).barriers.equals(obst))
						&& (((edgeClass) p).security.equals(sec))) {		
					((edgeClass) p).security = newSec;
				}
			}
		}
	}

	public void setObstacle(String from, String to, double dis, String obst, String sec, String newObstacle) {
		for (Object o : vertices) {
			for (Object p : ((DSAGraphVertex) o).link) {
				if (((edgeClass) p).from.equals(from) && (((edgeClass) p).to.equals(to))
						&& (((edgeClass) p).weight == dis) && (((edgeClass) p).barriers.equals(obst))
						&& (((edgeClass) p).security.equals(sec))) {
					((edgeClass) p).barriers = newObstacle;
				}
			}
		}
	}

	public void setDis(String from, String to, double dis, String obst, String sec, double newDis) {
		for (Object o : vertices) {
			for (Object p : ((DSAGraphVertex) o).link) {
				if (((edgeClass) p).from.equals(from) && (((edgeClass) p).to.equals(to))
						&& (((edgeClass) p).weight == dis) && (((edgeClass) p).barriers.equals(obst))
						&& (((edgeClass) p).security.equals(sec))) {
					((edgeClass) p).weight = newDis;
				}
			}
		}
	}

	public DSAGraphVertex getVertex(String pLabel) {
		DSAGraphVertex returnVrtx = null;
		if (!hasVertex(pLabel)) {	//iterates through vertices list and returns matching vertex
			addVertex(pLabel, 0);
		}

		for (Object o : vertices) {
			if ((((DSAGraphVertex)o).label).equals(pLabel)) {
				returnVrtx = (DSAGraphVertex)o;
			}
		}
		return returnVrtx;
	}


	public boolean hasVertex(String pLabel) {
		boolean check = false;
		for (Object o : vertices) {		//iterates through vertices list to check if vertex exists and returns boolean
			if (((DSAGraphVertex) o).label.equals((pLabel))) {
				check = true;
			}
		}
		return check;
	}

	public int getVertexCount() {
		int num = 0;
		{
			for (Object o : vertices) {
				num += 1;		//returns count for amount of vertices
			}
		}
		return num;
	}

	public boolean isAdjacent(String labelOne, String labelTwo) {
		boolean check = false;
		LinkedList list = getAdjacent(labelOne);
		for (Object p : list) {				//iterates through vertices 'link' list to check if imported vertex is adjacent 
			if (((edgeClass) p).to.equals(labelTwo)) {		//if imported vertex is found in 'link' list, true is returned
				check = true;
			}
		}
		return check;
	}

	public LinkedList calcJourney(journeyClass journey) {
		LinkedList list = new LinkedList();	//this method is tested in the journeyClass unittest
		LinkedList pList = vertices();
		LinkedList returnList = new LinkedList();

		for (Object o : pList) {
			list = ((DSAGraphVertex) o).link;	//iterates through every vertex's 'link' list of edges
			for (Object p : list) { // iterating through edges
				if (((((edgeClass) p).from).equals(journey.getStart()))
						&& ((((edgeClass) p).to).equals(journey.getEnd()))	//multiple checks done to check if edge is compliant with journey preferences
						&& ((((edgeClass) p).security).equals(journey.getSecurity())
								|| (((edgeClass) p).security.equals("") || (((edgeClass) p).security.equals("none"))))
						&& (((!(((edgeClass) p).barriers).contains(journey.getObstacles()))
								&& (!(journey.getObstacles().contains(((edgeClass) p).barriers)))
								|| journey.getObstacles().equals("none") || (((edgeClass) p).barriers.equals("none")))))

				{	//if edge is valid, inserted into linkedlist
					returnList.insertFirst((edgeClass) p);
				}

			}
		}
			//linkedlist of valid edges are returned
		return returnList;
	}

	public String[] sorter(LinkedList list, int print) {
		int count = 0;
		edgeClass[] array = new edgeClass[list.getSize()];
		String[] text = new String[list.getSize()];
		for (Object o : list) {
			array[count] = ((edgeClass) o);//copies each linkedlist edge value to a index of the array
			count++;
		}
		insertionSort(array); // sorts array by distance using bubblesort
		for (int i = 0; i < count; i++) {
				//inserts a 'tostring' statement of each edge into each index of the 'text' array
			text[i] = array[i].from + ", To: " + array[i].to + ", Distance is: " + array[i].weight
					+ ", Security Level: " + array[i].security + ", Obstacles: " + array[i].barriers; 
			if (print == 1)

			{
				System.out.println(text[i]); //prints out edges in order, depending on user input
			}
		}

		return text;
	}

	public LinkedList vertices() {
		return vertices; //returns entire vertices list
	}

	public void convertKms(String check) {		//used for parameter tweak option
		for (Object o : vertices) {
			for (Object p : ((DSAGraphVertex) o).link) {
				if (check.equals("metres")) {		//checks String 'check' value, to keep track of what the current unit is
					((edgeClass) p).weight /= 1000;
				} else if (check.equals("miles")) {		
					((edgeClass) p).weight *= 1.609;
				}

			}
		}
	}

	public void convertBack(String check) {
		for (Object o : vertices) {
			for (Object p : ((DSAGraphVertex) o).link) {
				if (check.equals("km")) {
					((edgeClass) p).weight *= 1000;	//dependant on 'check' value, convert accordingly
				} else if (check.equals("miles")) {
					((edgeClass) p).weight *= 1609;
				}

			}
		}
	}

	public LinkedList getAdjacent(String plabel) {
		LinkedList returnList = new LinkedList();
		for (Object o : vertices) {
			if (((DSAGraphVertex) o).label.equals(plabel)) {
				returnList = ((DSAGraphVertex) o).link;//returns adjacency list of the specified vertex
			}
		}
		return returnList;
	}

	public void displayAdjacency() {

		for (Object o : vertices) {
			System.out.println();
			System.out.print(((DSAGraphVertex) o).label + "| ");	//prints out adjacency list

			LinkedList pList = getAdjacent(((edgeClass) o).to);
			for (Object p : pList) {

				System.out.print(p.toString() + " ");
			}
		}
	}

	public void displayMatrix() {
		if (vertices.getSize() < 1) {
			System.out.println("No nodes in map to display matrix");
		}
		else
		{
		if (vertices.getSize() > 15) {	//prints out adjacency matrix,
			System.out.println(			//if size of nodes is more than 15, only prints out first 15, otherwise will be unreadable
					"Map is too big to display, max size of 15 locations\n Matrix will be displayed using first 15 nodes");
		}

		String[] array = new String[getVertexCount()];//array to hold vertices
		int[][] matrixArr = new int[getVertexCount()][getVertexCount()]; //2d array to hold 1's and 0's
		int i = 0;
		int j = 0;

		for (Object o : vertices) {
			array[i] = ((DSAGraphVertex) o).label;
			i++;//copies vertices list into the array
		}

		for (i = 0; i < 20; i++) {
			for (j = 0; j < 20; j++) {
				if (isAdjacent(array[i], array[j])) {
					matrixArr[i][j] = 1; //sets the 1's and 0's depending on if theyre adjacent or not
				} else {
					matrixArr[i][j] = 0;
				}
			}
		}

		System.out.println();
		System.out.println();
		for (i = 0; i < 20; i++) {
			System.out.print("  |" + array[i]);//printing out vertex nodes
		}

		for (i = 0; i < 20; i++) { //printing out and spacing out format for matrix

			System.out.println();
			System.out.print(array[i]);

			for (j = 0; j < 20; j++) {
				System.out.print(" | " + matrixArr[i][j]); //printing out the 1's and 0's

			}
		}
		System.out.println();
	}}

	public void displayMatrix(String fileName) {
		if (vertices.getSize() > 10) {
		} else {
			FileOutputStream fileStrm = null;
			PrintWriter pw;
			try {
				fileStrm = new FileOutputStream(fileName, true);
				pw = new PrintWriter(fileStrm);				//opening file to write matrix to file specified by user

				String[] array = new String[getVertexCount()];
				int[][] matrixArr = new int[getVertexCount()][getVertexCount()];
				int i = 0;
				int j = 0;
				for (Object o : vertices) {
					array[i] = ((DSAGraphVertex) o).label;
					i++;
				}

				for (i = 0; i < getVertexCount(); i++) {
					for (j = 0; j < getVertexCount(); j++) {
						if (isAdjacent(array[i], array[j])) {
							matrixArr[i][j] = 1;
						} else {
							matrixArr[i][j] = 0;
						}
					}
				}

				pw.println();
				pw.println();

				for (i = 0; i < getVertexCount(); i++) {
					pw.print("  |" + array[i]);
				}

				for (i = 0; i < getVertexCount(); i++) {

					pw.println();
					pw.print(array[i]);		//same as method above, except using 'pw.println' instead

					for (j = 0; j < getVertexCount(); j++) {
						pw.print(" | " + matrixArr[i][j]);

					}
				}
				pw.println();
				pw.close();
			} catch (IOException e) {
				if (fileStrm != null) {
					try {
						fileStrm.close();
					} catch (IOException ex2) {
					}
				}
				System.out.println("Error writing to file " + e.getMessage());
			}

		}
	}
}

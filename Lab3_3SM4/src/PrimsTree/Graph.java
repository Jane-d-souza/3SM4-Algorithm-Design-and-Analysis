//Jane D'Souza
//Student Number: 400366436
//LAB 3


package PrimsTree;

import java.util.Scanner;

//provided instance fields 
public class Graph {
    public static final int infinity = 10000;       // logical infinity
    Vertex[] v;                                     // array of vertices
    Edge[] adj;                                     // array storing headers of adjacency lists (adj[i] is a reference to the first Edge object in the adjacency list of vertex v[i])
    int size=0;                                     //num of vertices

//CONSTRUCTOR
    
    public Graph(String inputString) {
        Scanner input = new Scanner(inputString); 
        size = input.nextInt(); 
        v = new Vertex[size]; // allocate the array of vertices 
       
//Added for loop to create vertex objects and place in array 
        for (int vertexIndex = 0; vertexIndex < size; vertexIndex ++) //iterates fron 0 to the size, which is the number of vertices in the graph
        {
            v[vertexIndex] = new Vertex(vertexIndex); //create new object with current index value (Vertexindex) for identification
        }

        adj = new Edge[size]; //allocate the array of headers to adjacency lists (p)
        
        //read the info from the string
        int end1;int end2; int w; 
        
        while(input.hasNext())
        {
           //read next edge
            end1 = input.nextInt(); //read 1st vertex of the edge 
            end2 = input.nextInt(); //read 2nd vertex of the edge 
            w = input.nextInt(); //read weight of the edge

      //beignning 
            adj[end1] = new Edge(adj[end1], v[end2], w); //make an edge with endPoint =  end2 and insert it in adj. list of end1. 
            adj[end2] = new Edge(adj[end2], v[end1], w); //edge with endPoint=end1 and place in the beginning of adj. list of end2

            }

    }

  //METHOD #1 (from Lab3 manual)
public String adjListString() {

    Edge p; //edge pointer                                       
    String s = " ";
    for(int i= 0; i < size; i++) 
    {
        p = adj[i]; //p points to first edge in the adjacency list of v[i]                               
                                                  
        while(p != null)  //scan adjacency list of v[i]
        {   
            s += " \n edge: (v" + i +", v" + p.endPoint.index + "), weight: " + p.weight;
            p = p.next; //move to next edge in the current list
            
        }                                      
    }                             
    return s;
    }                                               


//METHOD #2
public String minSTPrim (int r) {
	
	Vertex currentMinVertex = null;    //intaliaze this varaible to null. currentMinVertex will be used to store 
	StringBuilder mstResultBuilder = new StringBuilder(); //build string representation
	String mstResult = ""; //initlzie empty string 'mstResult'. will store the final MST result string 
	mstResult = mstResultBuilder.toString(); //convert to string 
    MinBinHeap vertexPriorityQ = new MinBinHeap(this, r); //intizialte queue, with the graph (this) and starting vertex 'r'. used to select the next vertex with the smallest key 
    
    //if statemnt to check if grpah contains more than one vertex that could be aprt of MST
    
    if (vertexPriorityQ.heap.length > 1)   //check if there is more than 1 element in the heap
    {
        mstResultBuilder.append("MST: "); //build the string representation with set prefix MST
    }

    while (vertexPriorityQ.heap.length >1) //while loop as long as there are vertices in the priority queue to process 
    {
        Vertex tempVar = vertexPriorityQ.extractMin(); //extract vertex with minimum key (edge weight) from the queue
        
        
        if (currentMinVertex != null) //  append to mstResultBuilder if currentMinVertex is not null, meaning it's not the first vertex
        {
            mstResultBuilder.append("(v").append(tempVar.index).append(" , v") //append details of edge connecting the current min vertex to its predecessor
                .append(tempVar.prev.index).append("),  "); 
        }
        	
        
        currentMinVertex = tempVar; //update currentMinVertex to the vertex just extracted from queue...
        
        currentMinVertex.isInQ = false; //assign(mark) it to be no longer in the queue

        for (Edge edge = adj[currentMinVertex.index]; edge != null; edge = edge.next) 
        {
            Vertex neighbor = edge.endPoint; // More descriptive naming for the adjacent vertex
            
            // If the neighbor is in the queue and the edge offers a shorter path, update it
            if (neighbor.isInQ && edge.weight < neighbor.key) 
            {
                neighbor.prev = currentMinVertex; // Set the predecessor to the current min vertex
                
                neighbor.key = edge.weight; // Update the key to the new lower weight

                vertexPriorityQ.decreaseKey(neighbor.index, edge.weight);// Update the neighbor's position in the priority queue based on the new key
            }
        
        }
    }

  //remove comma trail if there are any from stringbuilder
    int resultLength = mstResultBuilder.length(); //measure current length of MST 
    
    if (resultLength > 5) //checks if there is enough characters to remove trailing charters. checks if there is more than jsut: "MST: " (5 characers)
    {
        mstResultBuilder.setLength(resultLength - 3); //take out last 3 characters from mstResultBuidler (traling comma and space)
    }
    
    return mstResultBuilder.toString(); //convert stringbuilder object (mstResultBuilder) into a string 
}


//METHOD #3
public int[][] adjMatrix(){

	int[][] adjArr = new int[size][size]; //create a matrix based in the number of vertices in the graph 

	// Nested for loop Manually fill each cell in the matrix with infinity (set as 10000)
	for (int MatRow = 0; MatRow < size; MatRow++) // Goes through each row setting value to inf
	{
	    for (int MatCol = 0; MatCol < size; MatCol++) //goes throguh each column to set value to inf 
	    {
	        
	        adjArr[MatRow][MatCol] = infinity; // Set each cell to infinity, manually initializing the matrix. (filled with inf bc of absece of edge between 2 vertices)
	    }
	}

	
	for (int vertexIndex = 0; vertexIndex < size; vertexIndex++) // fill the adjacey matrix with actual edge weights. Outer loop goes through each vertex's adjaceney list 
	{
	    for (Edge edge = adj[vertexIndex]; edge != null; edge = edge.next) //inner for loop goes through the linked list of edges for each vertex
	    {
	        adjArr[vertexIndex][edge.endPoint.index] = edge.weight; //update cell of matrix corresponding to edge (vertexIndex, edge endpoint)
	    }
	}

	return adjArr; //returns filled adj matrix
	
}

}
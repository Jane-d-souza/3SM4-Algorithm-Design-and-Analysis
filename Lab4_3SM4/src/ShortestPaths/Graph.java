	//Jane D'Souza
	//Student Number: 400366436
	//LAB 4

package ShortestPaths;


	import java.util.Scanner;

	//LAB 3 CODE 
	
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

	            adj[end1] = new Edge(adj[end1], v[end2], w); //for undirected graphs, we add both directions. For directied, just one. We are inserting in adj. list of end1
	            }
	        	
	        input.close(); // close input after opening it in the neggining. Can cause memeory leaks if there is no close call after it has opened. 
	    }

	  //METHOD #1 (from Lab3 manual)
	public String adjListString() {

	    Edge p; //edge pointer                                       
	    String s = " ";
	    for(int i = 0; i < size; i++) 
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


	
	//METHOD #2 form Lab 3 
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

	//UPDATED CODE FOR LAB 4 STARTS HERE
	
	public String dijkstraSP(int i, int j) {
        // Initialize vertices
        for (Vertex vert_point : v) 
        {
            vert_point.key = infinity; //set inital distance to infinity 
            
            vert_point.prev = null; //previous vertex is on path 
            
            vert_point.isInQ =true; // vertex initally considered in queue
        }

        
        MinBinHeap priority_q = new MinBinHeap(this, i); // Initialize  priority queue with  source vertex
        
        v[i].key=0; // Distance from source to  itself is zero

        while (priority_q.size > 0) 
        {
            Vertex currVertex= priority_q.extractMin(); // Extract vertex with the smallest distance (key)
            
            currVertex.isInQ = false; //mark vertex as processed

            if (currVertex.index== j) //when destination is reached....
            { 
                break; //stop alogrthim
            }

            for (Edge e = adj[currVertex.index]; e!= null; e= e.next)
            {
                Vertex v =e.endPoint;
                
                if (v.isInQ && currVertex.key+ e.weight<v.key) //if there is a shorter path to the neighbour thorugh currentVertex
                {
                    v.key=currVertex.key + e.weight; //update the distance 
                  
                    v.prev =currVertex; //update the previous vertex in path
                    
                    priority_q.decreaseKey(v.index, v.key); //rebalance heap with new distacne 
                }
            }
        }

        if (v[j].prev ==null) //if no path to destination...
        	
        	return null; // then , no path found, return null

        return constructPath(i, j); // Reconstruct the path from source to destination (use helper method #1)
    }

    public String bellmanFordSP(int i, int j) {
        int[] dist_val = new int[size]; //Distance from source to every vertex
        
        int[] pathBacktrace = new int[size]; //Array to back track (Predecessor) the path from  every vertex to the source 
       
        for (int pass = 0; pass < size; pass++) 
        {
            dist_val[pass] = infinity; //Initialize distances to infinity
            
            pathBacktrace[pass] = -1; //Initialize predecessors with -1 (undefined)
        }
        dist_val[i] = 0; // Distance from source to itself is 0

        // Relax edges repeatedly
        for (int pass = 0; pass < size - 1; pass++)  //this loop goes for -1 times over all edges to relax them 
        {
            for (int vertexIndex = 0; vertexIndex < size; vertexIndex++)  //goes through each vertex to relax edges coming from it 
            {
                for (Edge e = adj[vertexIndex]; e!= null; e =e.next) // goes through adj list of current vertex to examin all outgoing edges 
                {
                    int v_destination = e.endPoint.index; //identifies end point (destination) of current edge
                    int weight = e.weight; // gets weight of current edge
                    
                    if (dist_val[vertexIndex] != infinity && dist_val[vertexIndex] + weight < dist_val[v_destination]) //if current path to endpoint vertex can be improved, update dsitance and predecessor 
                    {
                        dist_val[v_destination] = dist_val[vertexIndex] + weight;//updates distance to endpoint vertex 
                        pathBacktrace[v_destination] = vertexIndex; //updates the predecessor of the endpoint vertex
                        
                    }
                }
            }
        }

        // Check for negative-weight cycles
        for (int vertexIndex = 0; vertexIndex < size; vertexIndex++)  
        {
            for (Edge e = adj[vertexIndex]; e!= null; e = e.next) //goes through each vertexs adj list to see all outgoing edges 
            {
                int v_destination = e.endPoint.index; //the destination vertex of current edge 
                
                //checks if relaxing this edge would decrease distance further to vertex v_destination.
                if (dist_val[vertexIndex] !=infinity && dist_val[vertexIndex] + e.weight <dist_val[v_destination]) // if so, negaive weight cycle is reachable from soruce
                {
                    return "negative-weight cycle!"; // Negative-weight cycle found
                }
            }
        }

        // No path from i to j
        if (dist_val[j] == infinity) //if shortest distance to 'j' remains inf, it indicates no such path exists 
        {
            return null; // nulttl is reutrned show now path from i to j
        }

        // Reconstruct the path from source to destination
        return reconstructPath(pathBacktrace, i, j, dist_val[j]); //calls helper method to make the path from source i to destination j 
    }

  
    
    public Vertex[] topologicalSort() {
        //Indgree: num of edges coming to it 
    	int[] indeg= new int[size]; //intilize array to keep track of indgree of each vertex 
        
        for(int vertexInd =0; vertexInd< size; vertexInd++)  //goes over all vertices in grpah
        {
            for (Edge e= adj[vertexInd]; e!= null; e =e.next) //goes over adj list of curretn vertex (vertexInd) which has all edges leaving from it 
            {
                indeg[e.endPoint.index] ++; //incrment indgree of vertex at endpoint of each edge. counts how many edges coming into each vertex 
            }
        }

        Queue zeroInd_queue = new Queue(size); //intialize zeroInd_queue to hold all vertices with indegree of 0 
        
        for (int vertexInd= 0; vertexInd <size;vertexInd++) //enqeue all vertices with indgree 0 to start topological sort process 
        {
            if (indeg[vertexInd]==0) 
            {
                zeroInd_queue.enqueue(vertexInd); 
            }
        }

        int Visited_count = 0; //counter to keep track of num of vertices added to topolgical order 
        
        Vertex[] order = new Vertex[size]; //array to store vertices in topological order 
        
        while (!zeroInd_queue.isEmpty())  //process queue until its empty 
        {
            int currVert = zeroInd_queue.dequeue(); //dequeue a vertex with indegree 0
            
            order[Visited_count++] = v[currVert]; //add it to topological order 

            for (Edge e =adj[currVert]; e!= null; e = e.next)  //for every leaving edge from the dequeed vertex 
            {
                if (--indeg[e.endPoint.index] ==0) //decrement indgree of destination vertex 
                {
                    zeroInd_queue.enqueue(e.endPoint.index); //if indegree of destination vertex becomes 0, eneqeue it 
                }
            }
        }

        if (Visited_count != size) //check if topolgical sort was compelted ( all vertices is vsited)
        {
            return null; // Cycle found  or graph not fully connected
        }

        return order; //return array of vertices in topological order 
    }
    
    public String dagSP(int i, int j) {
        
    	Vertex[] topoSort = topologicalSort(); //performs topological sort in grpah to order vertices linearily  
        
    	if (topoSort==null) // if grpah cannot be topolgically sorted, it is not a DAG, or has a cycle Graph is not a DAG...
    		return null; // return null to indicate that graph is not DAG or failed in sorting 

        int[] distArr = new int[size]; //intizliae distance array to hold shortest dsitance from source to all vertices 
        
        
        for (int index =0; index < size; index++) // intialize array 
        {
            distArr[index] = infinity; //all dsitances set to inf
        }
        
        
        distArr[i] = 0; //set  Distance from source to itself to 0

        for (int a = 0; a < size; a++)  //go through vertices in topological order 
        {
            if (distArr[topoSort[a].index] != infinity) //check if current vertex is reachable from the source 
            {
                for (Edge e = adj[topoSort[a].index]; e!= null; e =e.next) //goes through adj list of current vertex 
                {
                    int v = e.endPoint.index; //endpoint vertex of current edge 
                    
                    if (distArr[v]> distArr[topoSort[a].index] +e.weight) //if shorter path to v through current vertex is found, update v's distance
                    {
                        distArr[v]= distArr[topoSort[a].index] +e.weight; // update shortets distance 
                    
                        topoSort[v].prev = topoSort[a];//sets predecessor for path reconstruction 
                    }
                }
            }
        }

        if (distArr[j] == infinity) //check if destination is reachable form source...
        {
            return null; // No path from i to j (not reachable), return null 
        }

       
        return reconstructPath(topoSort, i, j, distArr[j]); //reconstruct and return the shortest path from source to destination using updated preecessor links
    }
  
    
  
    
    public String shortestPath(int i, int j) {
    	
    	//decalre these variables to store name of alogrthim used and the path reuslt
        String algor_chosen; String pathResult;

        if (isDAG()) //check if graph is DAG ( directied acyclic graph...
        { 
            algor_chosen = "dagSP, "; //... if so, indicates DAG alorgthim will be used
            pathResult = dagSP(i, j); //calls dapSP method find shrotest path in a DAG
               
        } 
        
        else if (hasNegativeWeights()) // If the graph has negative weights...
        { 
            algor_chosen = "bellmanFordSP, ";//if so.. indicates Bellman-ford alogrthim will used ( BF can handle negative weights)
            pathResult = bellmanFordSP(i, j); //calls the BF method 
        } 
        
        else  // Default to Dijkstra's algorithm
        
        { 
            algor_chosen = "dijkstraSP, "; //used for grpahs without negative weights 
            pathResult= dijkstraSP(i, j); //calls method to find shortest path 
        }

        if (pathResult== null) //result obtained from selected shortest path 
        {
            return algor_chosen + "no path exists"; //if reuslt is null, no path exists between the vertices 
        } 
        
        else if (pathResult.equals("negative-weight cycle!")) //if result indicates a nehative-weoght cycle..
        {
            return algor_chosen +pathResult; //return such outcome
        } 
        
        else //otherwise...
        {
            return algor_chosen +pathResult;  //return alorgthim used and the actual path found 
        }
    }

    
  //Helper method #1
    private String constructPath(int sourceIndex, int destIndex) {
        StringBuilder pathVals = new StringBuilder(); //make the path description 
        Vertex current = v[destIndex]; //starting from destination, trace the path back to the source 
        int totalWeight = current.key; //total cost or distance from the source 

        while (current != null) //traverse back from destination vertex to source vertex using the previous links 
        {
            if (pathVals.length() > 0) pathVals.insert(0, ", "); //if this is not the first vertex in path, prepend  a comma & space
            
            pathVals.insert(0, "v" + current.index); //prepend current vertex identifer ( 'v' follwoed by index value) to the path description
            
            current = current.prev; //move to preceding vertex in shorten path
        }

        return "path: " + pathVals + ", weight: " + totalWeight; //return formatted path string along with total weight of path
    }
       
    
    
    //Helper Method #2 
    private String reconstructPath(int[] pathBacktrace, int start, int end, int weight) {
        StringBuilder pathVals = new StringBuilder(); //int stringbuidler to make string represenation of the path 
        
        for (int currVertex = end; currVertex != -1; currVertex = pathBacktrace[currVertex]) //iterate backwards form destination vertex (end) to source vertex (start). we use 'pathBacktrace' array to find each preecessor
        {
            if (pathVals.length() > 0)  //if pathVals alreay has vertices...
            	
            	pathVals.insert(0, ", "); //.. add comma & space
            
            pathVals.insert(0, "v" + currVertex); //makes path in rverse order. from desitnation back to source
        }
        return "path: " + pathVals + ", weight: " + weight; // returns constructed path and total weight as a string 
    }
     
    
    
    //Helper Method #3
    private String reconstructPath(Vertex[] topoSort,int i,int j,int weight) {
        
    	StringBuilder path = new StringBuilder(); //insitalize stringbuilder to efficently build path string 
        
    	for (Vertex currentVertex = topoSort[j]; currentVertex != null; currentVertex = currentVertex.prev)  //iterates backwards from destination vertex (topOrder[j] - starting point) to source vertex using 'prev' field in each vertex
    	{
            if (path.length()>0) //if path is not empty..
            	
            	path.insert(0, ", "); //..insert comma and space at the start for formatting 
            
            path.insert(0, "v" +currentVertex.index); //prepends current vertex's identifier ( v - follwed by index value) to the path string. makes path in reverse order ( from destination to source)
        }
        
    	return "path: " + path + ", weight: " + weight; //constructs and returns final path string which includes  sequence of vertices (path) and the ttola weight of path (lsited from soruce to desitnation)
} 
    
    
    
  //Helper Method #4
    // Placeholder for isDAG method
    private boolean isDAG() { // Implement logic to check if the graph is a DAG
      
        return false; // Placeholder return statement
    }

    
    //Helper Method #5
    // Placeholder for hasNegativeWeights method
    private boolean hasNegativeWeights() {// Implement logic to check for negative weights in the graph
        
        return false; // Placeholder return statement
    }
    
	}
	



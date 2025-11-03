	//Jane D'Souza
	//Student Number: 400366436
	//LAB 4
package ShortestPaths;
//directed graphs
public class MinBinHeap {

	  Vertex[] heap;
	    int size =0;

//MinBinHeap class is pulled from my LAB 3 code 
	    
	//CONSTRUCTOR
	    public MinBinHeap(Graph g, int r) {
	    	
	    	size = g.v.length; //sets the size of heap to the num of vertices in the graph
	        heap = new Vertex[size + 1]; //make space for heap. + 1 becuase index 0 will not be used 
	        
	        
	        heap[1] = g.v[r]; //starting vertex at root of heap (index 1)
	        heap[1].key = 0; // Set the key of the starting vertex to 0 (selected first)
	        heap[1].heapIndex = 1; //Update  heapIndex to 1 of the starting vertex.

	       
	        for (int i = 0, heapIndex = 2; i < g.v.length; i++) //go through the rest of the vertices and add to heap
	        {
	            if (i != r) //skip the starting vertex since its already in the heap
	            { 
	                heap[heapIndex] = g.v[i]; //for each remaining vertex, place in heap
	                heap[heapIndex].key = Graph.infinity; // set key to inf
	                heap[heapIndex].heapIndex = heapIndex; // Update each vertex's heapIndex
	                heapIndex++; //go to next postion in heap for following vertex
	            }
	        }
	    }
	  
	    
	    Vertex extractMin() {
	    	
	      int heapLen= heap.length - 1; //length of heap -1
	      Vertex smallVal = heap [1];//store smallest value which is at the root of heap (index 1)
	        switchElem(heapLen, 1); //run the switchElem  method created,to swap last elelemnt in the heap with root (smallest elemnt)
	        Vertex[] startingHeap = heap; //mkae copy of heap refernce 
	        heap = new Vertex[startingHeap.length-1]; //make new heap array with 1 less elemt
	        
	        System.arraycopy(startingHeap, 1, heap,1, startingHeap.length-2 ); //copy elem form old heap to new one, excluding the last element (smallest)
	        size --; //decrement heap size to show removal of small element 
	        HeapifyDown(1); //run heapifyDown method created, to resotre min-heap property by heapifying down from root ( it now contains elemnet that was last)
	        
	        return smallVal; //return smallest value that was remvoed from heap

	  
	    }
	    
	    void decreaseKey(int i, int newKey) {
	  
	    
	       for (int pos = 1; pos < heap.length; pos++) //loop trhough heap starign from 1st index 
	       {
	            if (heap[pos].index == i) //check if current vertex index val mathces the target index (1), if so....
	            {
	                i = pos; break; //update (i) to represent postion of this vertex in heap array
	            }
	        }
	        
	       
	       if (newKey <= heap[i].key) //check if new key is smaller <= to current key of vertex, if so...
	       {
	    	    heap[i].key =newKey; // then update vertex's key to new value 
	    	    HeapifyDown(i); //call created method to restore Restore the heap property downwards from i
	    	    Heapifyup(i); //Restore the heap property upwards from i
	    	    
	       }
	    }
	    
	    //String fucntion Code from Lab 3 manual
	    public String toString(){
	    	String s = "\n The heap size is " + size + "\n The items’ labels are: \n"; //build string representation with heap info
	    	for(int i= 1; i < size+1; i++) //go trhoguhe each elemnt in the heap starting from 1
	    	{
	    	s+= heap[i].index + " key: "; // append the index of current elemnt to string 
	    	s += heap[i].key + "\n"; //append key of current element to string 
	    	} 
	    	return s; //return string 
	    	}
	  


	    
	    
	  //PRIVATE METHODS 
	  

	  private void HeapifyDown(int nodePos) {

		  int rightChild = 2*nodePos+1; int leftChild = 2*nodePos ; //left and right postiion of node 
		  

		    if (leftChild > size) //check if node is leaf (no left/right child)
		    {
		        return; //if leaf node, no need to heapify more
		    }

		    //find the position of child with smaller key 
		    int minChild = leftChild; // Assume left child is smaller initially.
		    
		    if (rightChild <= size && heap[rightChild].key < heap[leftChild].key) //if RChild key is smaller than LChilds key....
		    {
		        minChild = rightChild; //then Rhild becomes minChild 
		    }
		    
		    if (heap[nodePos].key > heap[minChild].key)// Compare the current index's key with the smaller child's key
		    {
		        switchElem(nodePos, minChild); //swich the nodes 
		       
		        HeapifyDown(minChild);// Heapify down from the smaller child's position
		    }
		   
	   
	    }
	  
	  private void switchElem(int a, int b) // switch two elements in the heap
	  {
	      Vertex holder=heap[a]; //store vertex at index a in a holder varaible 
	      
	      heap[a]=heap[b]; //move from index b to a
	      
	      heap[b]=holder; //move vertex from holder to index b 
	  }

	  private void Heapifyup(int nodePos) {
		  //loop continues by 2 conditions: (1)current node is not root of heap (nodePos >1). Root element is at index 1....
		  while (nodePos > 1 && heap[nodePos / 2].key > heap[nodePos].key) // (2) key of parent node is greater than key of current node. the parent node  = nodePos/2
		  {
		        switchElem(nodePos, nodePos / 2); //swap current node with parent if current nodes key is less. moves the the smaller key (current nodes key) up the heap
		        
		        nodePos = nodePos / 2; //current node takes psotion of parent. loop contues to check violations to min-heap property 
	      }
	     
	  }

	}


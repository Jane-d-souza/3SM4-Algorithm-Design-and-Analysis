	//Jane D'Souza
	//Student Number: 400366436
	//LAB 4


package ShortestPaths;

public class Queue {  //Queue class
	
	    private int[] elements; //array to hold queue elemnts
	   
	    private int size; //tracks num of elolemnts in the queue
	    
	    private int front; //front element index in the queue. this is where elemnts will be dequeued from 
	    
	    private int rearIndex; // rearIndex index of queue. New elements are enqueued at this position

	    public Queue(int capacity) {
	        
	    	elements =new int[capacity]; //intialize array that holds the queue's elemtns
	        
	    	size =0; //intialially, the queue is empty, so size is 0
	        
	        front =0; //the front of the queue is set to the start iof the array
	        
	        rearIndex =-1; //rearIndex is set to -1 to indicate that there  is no elements yet 
	    }

	    public void enqueue(int data) { //method to add/enqueue an element to the back of the queue
	    	
	        rearIndex =(rearIndex +1) %elements.length; //advances rearIndex cyclically within the  array bounds 
	        
	        elements[rearIndex] =data; // insert new element at rear of the queue
	        
	        size++; //increment queue size 
	    }

	    public int dequeue() { //method to remove and return an element from front of queue
	    	
	        int data = elements[front]; //get the front elemnt 
	        front =(front+1) %elements.length; //goes front cyclically within the bounds of array
	        
	        size--; //decrement queue size
	        return data; //return dequeed elemnt 
	    }

	    public boolean isEmpty(){ //check if queue is empty
	        
	    	return size ==0; //returns true if queue size is 0 = indicating its empty 
	    }
	}
	
   
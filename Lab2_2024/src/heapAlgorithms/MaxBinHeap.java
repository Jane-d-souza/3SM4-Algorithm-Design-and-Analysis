//Jane D'Souza(400366436)

package heapAlgorithms;

public class MaxBinHeap { //Create the instance fields
	  	private int[] heapArr;   //array to store the heapArr elements 
	    private int sizeArr;    //num of elements in the heapArr

	  //create constructors
	    	    	    
	    public MaxBinHeap(int n) {     //make an empty heap with intial capcity of n
	        if (n < 10) 
	        {
	            n = 10;  //set the min intial capacity to 10
	        }
	        this.heapArr = new int[n]; //create the heap array
	        this.sizeArr = 0;   //set size to 0
	    }

	    public MaxBinHeap(int[] a) {   //second constructor that stores the integers from array a
	        this.sizeArr = a.length;  //set the size to the length of array a 
	        this.heapArr = new int[sizeArr]; //create heap array with the size of array a 
	        for (int i = 0; i < sizeArr; i++) 
	        { 
	            this.heapArr[i] = a[i]; //copy elements to heap array
	        }
	        buildHeap(); //start building the heap
	    }

	    private void buildHeap() {   //private method to help build the heap
	        for (int i = (sizeArr - 1) / 2; i >= 0; i--)
	        
	        {
	            HeapMax(i); //this method can help heapify the array
	        }
	    } 

	    
	    
	    
	    //Following are public methods for MaxBinHeap
	    
	    public int getSize() {
	        return sizeArr; ///returns the size of heap ( num of values in heap)
	    }

	    public void insert(int x) {   //insert elements into heap
	        if (sizeArr == heapArr.length)   //check if array is the same size (if it is full)
	        {
	            int[] expandedHeap = new int[2 * heapArr.length];  //if so.. creat a new array with double the capcaity
	            for (int value = 0; value < sizeArr; value++) //go the the elemtns and copy it into the new one 
	            {
	                expandedHeap[value] = heapArr[value];
	            }
	            heapArr = expandedHeap; //assign the new heap array to the heap
	        }
	        
	        sizeArr++; //increment size
	        heapArr[sizeArr - 1] = x;  //insert new value at the end of heapArr
	        int value = sizeArr - 1; //initialize i at last element 
	        while (value > 0 && heapArr[parentNode(value)] < heapArr[value]) //percolate up to the inserted elements 
	        {
	            swap(value, parentNode(value)); //swap selected value with its parentNode
	            value = parentNode(value); //move to the parentNodes index value
	        }
	    }

	    
	    public int readMax() throws RuntimeException {
	        if (sizeArr == 0)  //if heap is empty...
	        {
	            throw new RuntimeException(" Heap stores no values. It is empty! "); //throw an excpetion 
	        }
	        return heapArr[0]; //return max value stored in the heap
	    }

	    
	    public int deleteMax() throws RuntimeException { 
	        if (sizeArr == 0) // check if the heap has nothing in it 
	        {  
	            throw new RuntimeException("Heap stores no values. Heap is empty!!"); //throw an exception 
	        }
	        int maxVal = heapArr[0]; // store the max value of the heap (root) in 'maxVal'
	        heapArr[0] = heapArr[sizeArr - 1]; //reaplce with last element  
	        sizeArr--; //decrement size of array 
	        HeapMax(0); // initiate  max heap property starting from new root 
	        return maxVal; //return the removed max Value 
	    }
	    
	   
	    public String toString() 
	    {
	        if (sizeArr == 0) //check if heap is empty, if so....
	        
	        { 
	            return ""; //return empty string 
	        }
	        String outputString  = Integer.toString(heapArr[0]); //mkae output string with the frist element of he heap, creating it to string 
	        
	        for (int i = 1; i < sizeArr; i++) //start iterating through the string 
	        {
	            outputString  += ", " + heapArr[i]; //appenend each elemrnt to the "outputString"
	        }
	        return outputString ; //return the finsihed string that represents the heap
	    } 
	     	    
	    
 public static void sortArray(int k, int[] a) {
	        MaxBinHeap heapArr = new MaxBinHeap(a); //create new bin heap for array 'a'
	        
	        for (int i = 0; i < a.length; i++) //go through the array
	        {
	            a[i] = heapArr.deleteMax();// replace element in array with max element from heap 
	        }
} 

 //HELPER FUCNTIONS

	    private void HeapMax(int i) {
	        int left = LChild (i);   //left child index 
	        int right = RChild (i); //right child index 
	        int greatestIndex = i; // intitlizae the largest index val as the current nodes index 
	        if (left < sizeArr && heapArr[left] > heapArr[greatestIndex])//if the LChild exisits ( by comapring its index with heap size) and if the value of Lchild is grater than the current nodes value...
	        {
	            greatestIndex = left; //then assign the Lchild indec to the greatest index 
	        }
	        if (right < sizeArr && heapArr[right] > heapArr[greatestIndex]) //check if there isa RChild and if it greater than the current greatest value. Is so...
	        {
	            greatestIndex = right; //update the greatindex with the right child idnex 
	        }
	        if (greatestIndex != i)  //if the largest index is not  the current node ( the child nodes have a greater value)...
	        {
	            swap(i, greatestIndex); //swap values of current node with the node that has the greatest value among its children 
	            HeapMax(greatestIndex);  //call HeapMax to maintian heap proeprty 
	        }
	    }

 
	    private void swap(int a, int b) {
	        int tempVar = heapArr[a];  //store value at A in a temporary vraible 
	        heapArr[a] = heapArr[b]; //set value at index A to value at index B
	        heapArr[b] = tempVar; //set value at index B to the orgiignal value of index A ( whcih was stored in tempVar)
	    }

	    private int parentNode(int p) {  //Calculate parent node and return the value 
	        return (p - 1) / 2; 
	    }

	    private int LChild (int L) { //calculate left child and return the value 
	        return 2 * L + 1;
	    }

	    private int RChild (int R) { //calculate the right child and return its value 
	        return 2 * R + 2;  
	    }
	}

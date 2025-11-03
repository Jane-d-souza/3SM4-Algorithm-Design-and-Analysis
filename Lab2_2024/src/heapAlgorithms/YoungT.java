

//Jane D'Souza (400366436)

package heapAlgorithms;

public class YoungT {
    private int num_finINT; //1st instance field: num of finite integers in the tableau
	private final int INFINITY_int; //infifnity value 
	private final int[][] twoDArr_tableau; //2D array storing integers 

// 2 CONSTRUCTORS 
	
    public YoungT(int k, int n, int INF) { 
    	if (n < 10) n = 10; //if n is less than 10, set to 10 for a min size
    	if (k < 10) k = 10; //if k is less than 10, set to 10 for a min size
    	if (INF < 100) INF = 100; //if the INF (infinity val) is less than a 100, set it to a 100
    	
        this.INFINITY_int = INF; //set the infinity value 
        this.twoDArr_tableau = new int[k][n];  //create the array with KxN values
        this.num_finINT = 0; //array initialized values at 0
        
        initArr_inf(); //call private method to fill array with infinity value
    }
    

    public YoungT(int[][] a) { // storing the integers in the 2D-array 
    	int col_int  = a[0].length; //num of coloumns in the first row of input array 
    	int row_int = a.length; //number of rows in array 
        int maxINT = Integer.MIN_VALUE; //intilziae variable to track max intger value found in input array. It starts with the minimum integer value 
        
        
        for (int[] rowArr : a) //go through each row in the array a
        {  
            for (int numArr : rowArr)  //in each row of the array, go through each number
            {
                maxINT = Math.max(maxINT,numArr); //updated maxINT. this fins max value in the entire array
            }
        }
        
        this.INFINITY_int = (maxINT * 10); //set inifity to 10 times the max value found in array
        this.num_finINT = 0; // insitilaizes num of finte integers to 0
        this.twoDArr_tableau = new int[row_int][col_int ]; //create 2D array to store tableu values
        
        initArr_inf(); //call the privqte method to fill  array with infinity valus
        
        for (int[] rowArr : a) //iterate each row in array a
        {
            for (int numArr : rowArr) //interate each integer value in the rows in the array (rowArr)
            {
                insert(numArr);  //tableau remains sorted in ascending order
            }
        }
    }
    
 
//PUBLIC METHODS 

    public int getNumElem() { 
        return num_finINT; //returns count of finte integers in tableau

    }

    public int getInfinity() {
        return INFINITY_int; //returns num that represents infinity 
    }
    
    
    public boolean isFull() {
        return num_finINT == twoDArr_tableau.length * twoDArr_tableau[0].length; // returs true if tableu can not take any more values (is full). More spiecfcailly, checks if num of fintite integers matches capacitity
    }
    
    
    public boolean isEmpty() {
        return num_finINT == 0; //checks if the num of fintie integers is 0, meaning empty tableu, results in TRUE
    }

 
    public boolean insert(int x) { //public method to insert value into tableu
    	
    	boolean isInvalidOrFull = x >= INFINITY_int || isFull();  //check if value if infinity or tableu is full
    	if (isInvalidOrFull)  //if ehtier condition is true, no insertion 
    	{
    	    return false; //represented by reutring false 
    	}
    	
    	int b = twoDArr_tableau[0].length - 1; //last coloumn
        int a = twoDArr_tableau.length - 1; //last row
        
        twoDArr_tableau[a][b] = x; //place new value at bottom-right corner 
        num_finINT++; //incrment the count of fintie integers in the tableu
    


       while (true) {
            int targetRow = a, targetColumn = b;  //intialize target psotion as the current psotion
    
            if (a > 0 && twoDArr_tableau[a - 1][b] > twoDArr_tableau[targetRow][targetColumn])  //check above the current psotion for larger value 
            {
            	 
            	targetRow = a - 1; //move target row up
            
            }
    
            
            if (b > 0 && twoDArr_tableau[a][b - 1] > twoDArr_tableau[targetRow][targetColumn]) //check if theres a alrger value to the left of the current postion
            {
               targetRow = a; 
               targetColumn = b - 1; //move target coloumn up
            }
    
            
            if (targetRow == a && targetColumn == b) //if target psotion remains uncahnged, its alreayd in correct psotion
            {
                break; //exit loop
            }
    
            
            int holderVar = twoDArr_tableau[a][b];  //SWAP the current psotion and the target psotion using a temperorary varaible 
            twoDArr_tableau[a][b] = twoDArr_tableau[targetRow][targetColumn];
            twoDArr_tableau[targetRow][targetColumn] = holderVar;
    
            
            a = targetRow; b = targetColumn; //update the row and column psotions
        } 
    
    
        return true; //true when the insertion is comeplted 
    } 
    
    
    public int readMin() throws RuntimeException {
        if (isEmpty()) //call method to check if array is empty 
        {
            throw new RuntimeException("No value found, tableau is empty!"); //if so, throw an expection 
        }
    
        int smallElem = INFINITY_int; //create variable that holds the smallest elemetn, set to infinity becuase we r assuming actual values will be less than inf
        
        for (int[] rowArr : twoDArr_tableau) //go through each row in array
        {
            for (int element : rowArr)  //each elemtn in row...
            {
                if (element < smallElem) //if element is smaller than the previously marked smallelem then...
                {
                    smallElem = element; //update smallElem to new minimum value
                }
            }
        }
        return smallElem; //return the smallest value found 
    }
    

    public int deleteMin() throws RuntimeException {
        if (isEmpty()) //runs method to check if array is empty, if so...
        {
            throw new RuntimeException("No value found, tableau is empty!"); //throw expception
        }
    
        int smallElem = twoDArr_tableau[0][0]; //get smallest elemnt which is at top-left corner 
        
        twoDArr_tableau[0][0] = INFINITY_int; //replace smallest elem (deleting) with Inf 
        num_finINT--; //decrement the count becuase of deletion
    
        
        rebalanceTableau(0,0); //run the helper method to maintin the property of tableu
    
        return smallElem; //returns smallest elemtn 
    }
    

    public boolean find(int x) throws RuntimeException {
        if (isEmpty()) //run method to checkif tableu is empty, if so...
        {
            throw new RuntimeException("No value found, tableau is empty!"); //throw exception 
        }
        
        else if (x >= INFINITY_int)  //check is value x is >= to the 'infinity' value assigned, if so...
        { 
            throw new RuntimeException("value is larger or equal to infinity!"); //throw exception 
        }
    
        
        int a = 0,  b = twoDArr_tableau[0].length - 1; //starting with the first row and last coloum
    
        while (a < twoDArr_tableau.length && b >= 0) //runs within bounds of the arrray
        { 
            if (twoDArr_tableau[a][b] == x) //if value matches 'x', then....
            {
                return true; //return true becuase value has been found 
            } 
            
            else if (twoDArr_tableau[a][b] > x) //if value s greater than 'x'...
            { 
                b--; //move left by decreasing b (current coloum)
            } 
            
            else //if less than 'x'...
            {
                a++; //increase a (current row)
            }
        }
    
        return false; //return false if value is NOT found 
    }
    
    
 
    public String toString() {
        StringBuilder newStr = new StringBuilder(); //build string representation 
       
        for (int a = 0; a < twoDArr_tableau.length; a++) //go through each row of the array
        {
            for (int b = 0; b < twoDArr_tableau[a].length; b++)  //go through each coloumn 
            {
                if (twoDArr_tableau[a][b] == INFINITY_int) //check if the value is a infinity value 
                {
                    newStr.append("inf"); //if so, append to string ;inf' to represent the infinity value 
                } 
                
                else 
                {
                    newStr.append(twoDArr_tableau[a][b]); //otherwise, append the actual value of the elemtn
                }
                
                if (b < twoDArr_tableau[a].length - 1) //if it is not at the last elemnt of the row...
                {
                    newStr.append(", "); //add a comma and space
                }
            }
           
            if (a < twoDArr_tableau.length - 1) //after each row (expect the last one)...
            {
                newStr.append(", "); //add comma and space
            }
        }
        
        return newStr.toString(); //make into string and return
    }

//PRIVATE METHODS
    private void initArr_inf() {   //Private method to intialize the young tableau with infinity values
        for (int row = 0; row < twoDArr_tableau.length; row++) // go trhough each row of the array
        {
            for (int col = 0; col < twoDArr_tableau[row].length; col++) //for each row, go through each coloumn
            {
                this.twoDArr_tableau[row][col] = INFINITY_int;//set selected cell to the infinity value 
            }
        }
    }    
    
    
    
    private void rebalanceTableau(int a, int b) { 
    	
        int targetRow = a, targetCol = b; // intiizle variables to current psoition
    
        if (a + 1 < twoDArr_tableau.length && twoDArr_tableau[a + 1][b] < twoDArr_tableau[targetRow][targetCol]) //check if moving down a row (a+1) is within the boundaries of the tableu and if the value below is smaller than the current or prev target value
        {
        	targetRow = a + 1;//if so... update targetRow to go downwards, with no change to the coloumn
        }
    
        if (b + 1 < twoDArr_tableau[0].length && twoDArr_tableau[a][b + 1] < twoDArr_tableau[targetRow][targetCol])  //check if moving down a coloumn (b+1) is within the boundaries and if the value to the right is smaller
        {
        	targetCol = b + 1; //if so, update targetCol to go right
            targetRow = a; //set targetRow to current
        }
    
        if (targetRow != a || targetCol != b)  //if the targetrow and coloumn is differn from their intial values set above...
        {
            int holderVar = twoDArr_tableau[a][b]; //Swap is needed bettween current postion and target psoiton using a temporary variable (holderVAR)
 
            twoDArr_tableau[a][b] = twoDArr_tableau[targetRow][targetCol]; 
            twoDArr_tableau[targetRow][targetCol] = holderVar; //Move current val to target postion
            
            rebalanceTableau(targetRow, targetCol); //call private emthod to make sure it is following tableu properties
        }
    }
    
    

    }
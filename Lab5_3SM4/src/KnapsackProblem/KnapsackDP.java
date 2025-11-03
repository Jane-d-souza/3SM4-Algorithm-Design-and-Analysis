//LAB 5
//Jane D'Souza 
//400366436
package KnapsackProblem;

public class KnapsackDP {
	
		private Integer[][] memoTable; // 2D array for memoization in the recursive solution 
	    private int itemCount; // Number of items
	    private int maxWeightCap; // max weight capcity of kanpsack
	    private int[] itemValues; // array storing Values of items
	    private int[] itemWeights; // array storing Weights of items
	    

	    // Constructor to parse input string and initialize instance fields
	    public KnapsackDP(String input) {
	     
	  
	    	String[] inputSegments = input.split("\\s+"); // split up the input sring based on whitespace to extract indivual paramteres 
	        
	        
	        this.itemCount = Integer.parseInt(inputSegments[0]);//first element represents total num of items.
	        //second element reps the max weight capacity of knapsack.
	        this.maxWeightCap = Integer.parseInt(inputSegments[1]);
	        
	        //Initialize arrays to store the values & weights of the items based on the itemCount.
	        this.itemValues = new int[itemCount];
	        this.itemWeights = new int[itemCount];
	        
	        // Iterate over inputSegments array to fill in the itemValues and itemWeights arrays.
	        for (int index =0; index < itemCount; index++) {
	            // Each item's value is located at 2+2 * index in the inputSegments array.
	            this.itemValues[index] = Integer.parseInt(inputSegments[2+(2*index)]);
	            
	            // Each item's weight is located at 3+ 2*index in the inputSegments array.
	            this.itemWeights[index] = Integer.parseInt(inputSegments[3+(2 *index)]);
	        }
	    }
	
	    
	   //recursion with memo   
	    public static String recMemo(String input) {
	    	
	        KnapsackDP KSProb = new KnapsackDP(input); //intialzie KnpaSackDP object with input string 
	        // Initialize memoization table
	        //table sized based on num of items + 1 (case of 0 items),
	        KSProb.memoTable = new Integer[KSProb.itemCount+1][KSProb.maxWeightCap+1]; //table to store results of subproblesm
	        // Start the recursive problem solving
	        KSProb.solveRecursively(KSProb.itemCount, KSProb.maxWeightCap); //start with full probklem and work its way through smaller subproblems 
	        // Construct and return the solution string
	        
	        return KSProb.buildSolutionString(); //after memeo table is full, helper method is called to make a resultign string 
	    }


	    // Bottom-up dynamic programming
	    public static String nonRec(String input) {

	    	
	    	KnapsackDP KSProb = new KnapsackDP(input); //mkae new isntance of KnapsackDp class with input string 
	        //make the DP table
	        int[][] valueTable = new int[KSProb.itemCount +1][KSProb.maxWeightCap+1]; //make 2d array to store max value that can be achevied

	        // fill  DP table
	        for (int itemIndex =1; itemIndex<= KSProb.itemCount; itemIndex++)  //go through each item and possbikle weight capacity to fill valueTable with max values that can be achieved 
	        {
	            for (int currCapacity=1; currCapacity<= KSProb.maxWeightCap; currCapacity++)
	            
	            {
	                if (KSProb.itemWeights[itemIndex -1] <=currCapacity) //check if the currents iterm's weight is less than or equal to the curr5ent capcity 
	                {
	                    // if so....Include the current item or exclude it, choose the max
	                    valueTable[itemIndex][currCapacity] = Math.max(valueTable[itemIndex-1][currCapacity], valueTable[itemIndex -1][currCapacity - KSProb.itemWeights[itemIndex - 1]] + KSProb.itemValues[itemIndex - 1]);
	                }
	                else 
	                {
	                    // If the current item's weight is more than the current capacity, skip it
	                    valueTable[itemIndex][currCapacity] =valueTable[itemIndex -1][currCapacity];
	                }
	    	
	    }
	            
	        }
	     // Reconstruct the solution to find which items to include
	        String ksSol = KSProb.makeOptimalString(valueTable);
	        return ksSol; //reutrn 
	        
	    }
	    
	    //Helper Method 1
	    private int solveRecursively(int currentItem, int remainingWeight) {
	        // Base case: no items left or no capacity remaining
	        if (currentItem ==0||remainingWeight== 0) //no items or no remaning weight....
	        {  
	        	return 0; //no value can be added to the knapsack, return 0
	        }
	       
	        // Return memoized result if available
	        if (memoTable[currentItem][remainingWeight] !=null) //check if solution for current subprob has been computed and updated to table 
	        	
	            return memoTable[currentItem][remainingWeight]; //if so, return stored value to avoid recalcution 
	        // Skip the current item if its weight is more than the remaining capacity
	       
	        if (itemWeights[currentItem -1]>remainingWeight) 
	        {
	            memoTable[currentItem][remainingWeight] = solveRecursively(currentItem-1, remainingWeight); //stopre result in memeo table 
	        } 
	        
	        else 
	        {
	            // Consider the max value between taking and skipping the current item. Max of these chopices is the stored in the memo table for current sub-problems
	            memoTable[currentItem][remainingWeight] = Math.max(solveRecursively(currentItem - 1, remainingWeight),
	                solveRecursively(currentItem -1, remainingWeight - itemWeights[currentItem- 1])+itemValues[currentItem -1]);
	        }
	        
	        return memoTable[currentItem][remainingWeight]; //reutn computed value for the current sub-problem 
	    }

	  //Helper Method 2
	    private String buildSolutionString() {
	       
	    	StringBuilder solutionBuilder = new StringBuilder(); //cosntruct solution string effciently 
	        int sumVal = 0, sumWeight = 0; //variabels to keep track of sum of values and weights of selected items 
	        int[] itemsSelected = new int[itemCount]; //array to mark whcih items are slected 
	        int weightCapacity = maxWeightCap; //varaible to ttrack remanign weight capcity 

	        // Determine which items were selected
	        for (int itemIndex = itemCount; itemIndex > 0; itemIndex--) //iterate backwards (last item to first)
	        { 
	           //checks if current item sslected is optimal
	        	boolean isSelected = memoTable[itemIndex][weightCapacity] != null && (itemIndex == 1?memoTable[itemIndex][weightCapacity] >0 : memoTable[itemIndex][weightCapacity] != memoTable[itemIndex - 1][weightCapacity]);
	            
	        	if (isSelected) 
	        	{
	                itemsSelected[itemIndex- 1]= 1; // Mark as selected
	                sumVal+= itemValues[itemIndex -1]; // Add value to total sums 
	                sumWeight+= itemWeights[itemIndex-1]; // Add weight to toal sums 
	                weightCapacity-= itemWeights[itemIndex -1]; // Update remaining capacity
	            }
	        }

	        // Construct the solution string
	        for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) //append selection stsaus of eah time to the solutin string 
	        {
	            solutionBuilder.append(itemsSelected[itemIndex]);
	           
	            if (itemIndex <itemCount -1) solutionBuilder.append(", "); //add comma, space excpet for last items 
	        }
	        
	        solutionBuilder.append(String.format(", total value = %d, total weight = %d", sumVal, sumWeight)); //lastly, append to9tla value adn weight of sslected items to solution string 

	        return solutionBuilder.toString(); //return string 
	    }

	  //Helper Method 3
	    // Reconstruct solution for nonRec method    
	    private String makeOptimalString(int[][] valueTable) {
	    	
	        StringBuilder solutionBuilder = new StringBuilder(); //constrcut solution string 
	        
	        int remainingCapacity = maxWeightCap; //keep track of remainign capcity whilst backtracking throguh table 
	        int totalValue = valueTable[itemCount][maxWeightCap]; //gets total value achieved with optimal set of items 
	        
	        // Array to keep track of which items are included
	        int[] included = new int[itemCount];

	        // Start from the last item and backtrack to find which items are included
	        for (int itemIndex = itemCount; itemIndex > 0; itemIndex--) 
	        {
	            if (valueTable[itemIndex][remainingCapacity] != valueTable[itemIndex - 1][remainingCapacity]) //if value at curernt cell is diff from value in the cell above, it means that current item is included in optimal solution 
	            {
	                included[itemIndex -1] = 1; // Mark this item as included
	                remainingCapacity -= itemWeights[itemIndex -1]; // Reduce the remaining capacity
	            }
	        }

	        // Constructing the solution string
	        for(int itemIndex = 0; itemIndex <itemCount; itemIndex++) //go through array to make string along with appending the stuatus of each item
	        {
	            solutionBuilder.append(included[itemIndex]);
	            if (itemIndex < itemCount - 1) solutionBuilder.append(", "); //append a comma and space between each item 
	        }

	        // Append the total value and calculated total weight
	        solutionBuilder.append(String.format(", total value = %d, total weight = %d", totalValue, maxWeightCap - remainingCapacity));

	        return solutionBuilder.toString(); //return cosntructed string 
	    }
	}


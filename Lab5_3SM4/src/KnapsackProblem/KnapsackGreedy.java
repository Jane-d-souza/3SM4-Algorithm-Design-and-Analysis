package KnapsackProblem;


public class KnapsackGreedy {
	
		
	//instance fields 
	    private int[] val; //item values
	    private int maxW; //Max weight
	    private int num; //Num of items
	    private int[] weight; // item weights 
	    
	    //ksItem = Knapsack Item
	    private static class ksItem {
	       
	    	int itemWeight; // The weight of the item
	        int itemIndex; // The index of the item in some collection
	    	int itemValue; // The value of the item
	       
	        double ratioValueToWeight; // The ratio of value to weight for  item


	        ksItem(int Val, int ItemWeight, int indexVal) 
	        {
	            
	        	this.itemWeight =ItemWeight; //assign weight paremater to itemWeight fiedl of obejct 
	            this.itemIndex =indexVal; //assign Index/pos paremater to itemIndex fiedl of obejct 
	        	this.itemValue =Val; //assign value paremater to itemValue fiedl of obejct 
	            
	            
	            // Direct calculation of value-to-weight ratio within constructor
	            this.ratioValueToWeight = ValuePerWeight(Val, ItemWeight); //helpful for optimzization 
	        }

//Helper Method 1
	        private double ValuePerWeight(int value, int weight) {
	            // Ensures that division by zero is handled, defaulting to 0 if weight is 0
	            return weight == 0 ? 0.0 : (double) value / weight; //check if items weight is 0 to repvent division by 0 (undfinded), if it is, return 0
	        }//otherwise, do divison to calculate ration and return it
	    }
	    // Constructor to parse input string and initialize instance fields.
	    
	    public KnapsackGreedy(String input) {
	     
	    	int currentNumberStart = 0; // keeps track of startign index of current num in input string 
	        int arrayIndex = 0; //index for placing parsed intger in num array
	        
	     // Assuming each value and weight pair takes at least two characters, this is a safe upper bound
	        int[] numbers = new int[2 +2*(input.length() / 2)]; //array to store parsed numbers. size estimates assumes each item (value & weight) takes at leats 2 characters 

	        // Iterate through string to extract numbers
	        for (int i = 0; i <= input.length(); i++) ///go through charcters in the input string to extract numbers 
	        { 
	            // Check if there is space or the end of string
	            if(i == input.length() ||input.charAt(i) == ' ') {
	            	
	                // Parse integer from the substring and store it in numbers array
	                numbers[arrayIndex++] =Integer.parseInt(input.substring(currentNumberStart, i));
	                currentNumberStart = i+1; // update start index for next number to character after the curent space 
	            }
	        }

	        // Assign the first two numbers to num and maxW
	        num = numbers[0]; //frist num in array represents num of items (num) 
	        maxW = numbers[1]; //second number represents max weight capcity (maxW)

	        // Initialize the arrays for values and weights
	        val = new int[num]; 
	       
	        weight = new int[num];

	        // Fill the value and weight arrays
	        for (int itemIndex= 0; itemIndex <num; itemIndex++) 
	        {
	            val[itemIndex] = numbers[2+(2*itemIndex)]; //for each item, value is stored in numbers array at index 2+2*itemIndex
	            
	            weight[itemIndex] = numbers[3+(2*itemIndex)];//corresponding  weight is stored right after the value at indec 3+2*itemIndex
	        }	
	    	
	    }

	    
	    // within the loop over the sorted items array, based on their value-to-weight ratio. 
	    // Fractional knapsack problem solver
	    public static String fractional(String input) {
	      
	    	KnapsackGreedy knapsackProb = new KnapsackGreedy(input); // Initializes object using input string which contians number of items, max weight, pairs of values & weigths
	        ksItem[] KSitems = new ksItem[knapsackProb.num]; // Creates an array of item objects to store each KSitems value, weight, and/or index 
	        
	        // fills  array with item instances, including their value, weight, and index
	        for (int index= 0; index < knapsackProb.num; index++) 
	        {
	            KSitems[index] = new ksItem(knapsackProb.val[index], knapsackProb.weight[index], index); //
	        }
	        
	        // sort KSitems by their value-to-weight ratio in descending order using selection sort
	        for (int currItemIndex = 0; currItemIndex < KSitems.length - 1; currItemIndex++) 
	        {
	            int maxIndex = currItemIndex;
	            
	            for (int compareIndex = currItemIndex + 1; compareIndex < KSitems.length; compareIndex++) 
	            {
	                if (KSitems[compareIndex].ratioValueToWeight> KSitems[maxIndex].ratioValueToWeight)
	                {
	                    maxIndex = compareIndex;
	                }
	            }
	            // Swaps currently foudn item with max value-to-weight ratio into the correct position 
	            ksItem temp = KSitems[maxIndex];
	           
	            KSitems[maxIndex] = KSitems[currItemIndex];
	            
	            KSitems[currItemIndex] = temp;
	        }
	        
	        double accumulatedVal = 0; //intizlaies variables to keep track of totla value & weights of KSitems that are added to knapsack
	        
	        int accumulatedWeight = 0;
	        
	        double[] itemFrac = new double[knapsackProb.num]; // intializes an array to keep track of the fraction of each item that is added to knapsack
	        
	        // Iterates over each item, and adding them to the knapasack as full as it can without exceedinhg the weight limti 
	        for (int itemIndex = 0; itemIndex < knapsackProb.num; itemIndex++) 
	        {
	            if (accumulatedWeight + KSitems[itemIndex].itemWeight<= knapsackProb.maxW)
	            {
	                itemFrac[KSitems[itemIndex].itemIndex] = 1; //adds item comptletey if it doesnt exceed the knapsacks weight limti 
	                accumulatedVal += KSitems[itemIndex].itemValue;
	                accumulatedWeight += KSitems[itemIndex].itemWeight;
	            } 
	            
	            else 
	            
	            {
	                double fraction = (double) (knapsackProb.maxW - accumulatedWeight) / KSitems[itemIndex].itemWeight; //calulates and adds fractional part of item that can fit into remaning space in kanpsack
	                
	                itemFrac[KSitems[itemIndex].itemIndex] = fraction;
	                accumulatedVal += KSitems[itemIndex].itemValue * fraction;
	                accumulatedWeight += KSitems[itemIndex].itemWeight * fraction;
	               
	                
	                break; // No more KSitems can be added after this because knapsack is full
	            }
	        }
	        
	        //builds the output string with item itemFrac, total value, and weight
	        StringBuilder stringOutput = new StringBuilder();
	        
	        for (int itemIndex = 0; itemIndex < knapsackProb.num; itemIndex++) 
	        {
	            stringOutput.append(itemFrac[itemIndex]);
	            
	            if (itemIndex < knapsackProb.num - 1) 
	            {
	                stringOutput.append(", "); ///adding commas and space between fraqctions
	            }
	        }
	        
	        stringOutput.append(", total value = ").append(accumulatedVal); //appned total val of items in kknapsack 
	        stringOutput.append(", total weight = ").append(accumulatedWeight); //append total weight of tiems in knapsack
	        
	        return stringOutput.toString(); //return cosntructed string with knapsack solution 
	    }

	    // Greedy algorithm approach  for 0-1 knapsack problem solver based on item value-to weight ratio
	    public static String greedy01(String input) {
	    	
	    	KnapsackGreedy knapsackProb = new KnapsackGreedy(input); // Initialize knapsack  object with input
	        
	    	ksItem[] KSitems = new ksItem[knapsackProb.num]; // Create an array to hold item objects. represnts: value, weight, and index 
	        
	    	for (int itemIndex = 0; itemIndex < knapsackProb.num; itemIndex++) //fill in KSitems array with new item objects intialized with values adn weight from input 
	    	{
	            KSitems[itemIndex] = new ksItem(knapsackProb.val[itemIndex], knapsackProb.weight[itemIndex], itemIndex); // fill in the items arr with new items objects intilziaed with values, weights form input 
	        }

	        // sorting of items by value-to-weight ratio using insertion sort
	        for (int itemIndex = 1; itemIndex < KSitems.length; itemIndex++) 
	        {
	            ksItem key = KSitems[itemIndex];
	            int j = itemIndex - 1;

	            while (j >= 0 && KSitems[j].ratioValueToWeight < key.ratioValueToWeight) //move elemnts of KSitems that are less than the value-to-weight ratio of the 
	            {
	                KSitems[j + 1] = KSitems[j];
	                
	                j = j - 1;
	            }
	            KSitems[j + 1] = key;
	        }

	        int accumulatedWeight = 0; // Tracks the total weight of selected items
	        int[] itemSelectionStat = new int[knapsackProb.num]; // Tracks the selection of items
	        
	        int accumulatedVal = 0; // Tracks the total value of selected items
	        

	        // Greedy selection of items based on their sorted order
	        for (int itemIndex = 0; itemIndex<knapsackProb.num;itemIndex++) 
	        {
	            if (accumulatedWeight + KSitems[itemIndex].itemWeight <= knapsackProb.maxW) 
	            {
	                itemSelectionStat[KSitems[itemIndex].itemIndex] =1; // Mark item as selected
	               
	                accumulatedVal +=KSitems[itemIndex].itemValue; // Add item's value to total
	                accumulatedWeight+= KSitems[itemIndex].itemWeight; // Add item's weight to total
	            }
	        }

	        // Building the output string
	        StringBuilder stringOutput = new StringBuilder();
	        
	        for (int itemIndex = 0; itemIndex< knapsackProb.num;itemIndex++) 
	        {
	        	
	            stringOutput.append(itemSelectionStat[itemIndex]); // Append selection status
	            
	            if (itemIndex < knapsackProb.num - 1)
	            {
	                
	            	stringOutput.append(", "); // Append comma between items
	            }
	        }
	       
	        stringOutput.append(", total value = ").append(accumulatedVal); // Append total value to strinbuilder
	        
	        stringOutput.append(", total weight = ").append(accumulatedWeight); // Append total weight to string buidler

	        return stringOutput.toString(); //return resulting string 
	    	
	    }

	 
	    
	    
	}


	
	


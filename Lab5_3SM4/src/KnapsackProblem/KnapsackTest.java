//LAB 5
//Jane D'Souza 
//400366436

package KnapsackProblem;

public class KnapsackTest {
public static void main( String[] args) {
		
		String s1= "4 8 7 10 2 3 1 5 9 2";
		System.out.println("Input: " + s1);
		
		System.out.println();
		System.out.println("Solution using recursion with memoization:");
		System.out.println(KnapsackDP.recMemo(s1));
		
		System.out.println();
		System.out.println("Solution based on bottom-up dynamic programming:");
		System.out.println(KnapsackDP.nonRec(s1));
		
		
		System.out.println();
		System.out.println("Solution to the fractional variant:");
		System.out.println(KnapsackGreedy.fractional(s1));
		
		System.out.println();
		System.out.println("Result of the greedy algorithm for the 0-1 variant:");
		System.out.println(KnapsackGreedy.greedy01(s1));
		
		String s2= "1 8 7 3";
		System.out.println();
		System.out.println("Input: " + s2);
		
		System.out.println();
		System.out.println("Solution using recursion with memoization:");
		System.out.println(KnapsackDP.recMemo(s2));
		
		System.out.println();
		System.out.println("Solution based on bottom-up dynamic programming:");
		System.out.println(KnapsackDP.nonRec(s2));
		
		System.out.println();
		System.out.println("Solution to the fractional variant:");
		System.out.println(KnapsackGreedy.fractional(s2));
		
		System.out.println();
		System.out.println("Result of the greedy algorithm for the 0-1 variant:");
		System.out.println(KnapsackGreedy.greedy01(s2));
		
		String s3= "5 12 9 10 1 4 5 6 5 6 1 5";
		System.out.println();
		System.out.println("Input: " + s3);
		
		System.out.println();
		System.out.println("Solution using recursion with memoization:");
		System.out.println(KnapsackDP.recMemo(s3));
		
		System.out.println();
		System.out.println("Solution based on bottom-up dynamic programming:");
		System.out.println(KnapsackDP.nonRec(s3));
		
		System.out.println();
		System.out.println("Solution to the fractional variant:");
		System.out.println(KnapsackGreedy.fractional(s3));
		
		System.out.println();
		System.out.println("Result of the greedy algorithm for the 0-1 variant:");
		System.out.println(KnapsackGreedy.greedy01(s3));
		
		
		 System.out.println("Testing different scenarios:");

	        // TEST 1: OPTIMAL SOLUTION
	        String s4 = "4 10 70 4 50 2 40 5 30 3"; //simple item weights/values to mathc DP solution
	        
	        System.out.println("\nInput: " + s4);
	        System.out.println("Greedy 0-1 Variant (Expect Optimal):");
	        System.out.println(KnapsackGreedy.greedy01(s4));
	        System.out.println("Expected Solution:");
	       
	        System.out.println(KnapsackDP.nonRec(s4));

	        // TEST 2: SUBOPTIMAL SOLUTION
	        //slwected items using greedy appraoch based on val-to-weight ratio may not yeild max possbile value 
	        String s5 = "4 10 60 2 100 4 120 6 240 8";
	        
	        System.out.println("\nInput: " + s5);
	        System.out.println("Greedy 0-1 Variant (Expect Suboptimal):");
	        System.out.println(KnapsackGreedy.greedy01(s5));
	        System.out.println("Expected Solution:");
	        System.out.println(KnapsackDP.nonRec(s5));

	        // TEST 3: OPTIMAL SOLUTION - using all aviable target weights 
	      //adding correct combo of items fill KS perfectly
	        String s6 = "3 10 60 6 50 3 70 4";
	        
	        System.out.println("\nInput: " + s6);
	        System.out.println("Expected Solution:"); 
	        System.out.println(KnapsackDP.nonRec(s6)); 

	        // TEST 4: OPTIMAL SOLUTION -  not using all available weight
	        String s7 = "3 10 100 3 90 4 70 5";
	        //highest value can be achived with less weight 
	        System.out.println("\nInput: " + s7);
	        System.out.println("Expected Solution:");
	        System.out.println(KnapsackDP.nonRec(s7));
		
	}
	

}

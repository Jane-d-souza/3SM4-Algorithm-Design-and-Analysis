//dsouzj22 - 400366436- Jane D'Souza




package binNode;    

//Methods Allowed in the lab 
import java.util.ArrayList;
import java.lang.String;
//import java.lang.Math;

public class BinTree {
	private TNode root; //one instance field.  Reference to the roots of the tree

	
//	Time Complexity: O(n*m)   Space complexity: O(n*m)
//  Reasoning: the outer loop iterates through the array making it O(n). The inner loop compeltixity is proprotional to the length of the code word (m) when traversing the tree for the codeword bits.  The worst case is when it traverses for each codeword bit for the time compleixity.Worst case for space complexity is if each bit results in making a new node which requires storage.

	
//build a binary tree from an array of code words, where each code word represents a unqiue path from the root to a particular node in tree	
	
	
//Constructor for the BinTree class, takes array of codewords as input 
public BinTree(String[] a) throws IllegalArgumentException{ //constructor
	root = new TNode(null,null,null);  //initialize root of bin tree
	
	
	for(int i = 0; i < a.length; i++) {   //go through the array of codewords 
		TNode trav = root;  //root of each code word (pointer)
		
		isValidword(a[i]); //check to see if the codeword has only 0s and 1s
		
		for (char bit: a[i].toCharArray()) //traverse tree based on codeword bit 
		{
			if(bit == '0')  //if the bit is 0, move to left child 
			{
				if(trav.left == null) //if no left child, make new node
				{
					trav.left = new TNode(null, null, null);
				}
				trav = trav.left;
			}
			
			else if(bit == '1') {    //if bit is 1, move to right child 
				if(trav.right == null) //if no right child, create new node 
				{
					trav.right = new TNode(null, null, null);
				}
				trav = trav.right;
				
			}
		
		}
		
		if(trav.data != null) //check if the current node is storing  data from previous node (is prefix condition correct?)
		{
			throw new IllegalArgumentException(" Prefix condition violated! "); //violates prefix condition 
		}
		
		trav.data = "c" + i; //assign the current codeword to the data of the current node 
			
	}
	
}

private void isValidword(String codeword) //checks if codeword contains 0s and 1s
{
	if(!codeword.matches("[01]+")) { //if it doesnt consist of 0s and 1s
		throw new IllegalArgumentException("Invalid Argument!"); //throw an excpetion
	}
}



//PRINT TREE FUNCTION
//Time and Space Complexity: O(n) 

public void printTree(){ printTree(root);}     //inorder traversal and printing 

private void printTree(TNode t){
	if(t!=null){ //if the slected node is not null
		printTree(t.left);  //go thorugh (trav left subtree)
		if(t.data == null ) //check if slected node is inner node (null)
			System.out.print("I ");   //prints I for each internal node 
		else
			System.out.print(t.data + " ");   //print data of node selected
		printTree(t.right); //go throguht right tree (recursive)
	}
}   
 
// OPTIMIZE FUNCTION - optimizes the binary tree by  removing unnessary nodes 
// Time complexity: O(n)  Space Complexity: O(m)
// Reason:  the private FullBinTree function checks each node in the tree and the optimize tree method traverses the entire tree. For the space complexity, m is the height of the bin tree. the complexity depends on the depth of the call stack in recursion. worst case,the space compelxity would be O(n) because the the height (m) is equal to # of nodes. In a balanced tree, m would log(n) where n is # of nodes making the complexity O(log n)


public void optimize() {
	if (!FullBintree(root)) { //if tree is not a a full binary tree...
		optimizeTree(root); //calls helper method 
        }
    }	
	
private boolean FullBintree(TNode t) { // check if bintree is full
    if (t == null) {  //base case (if slected node = null....)
        return true; //return true 
    }	
	
    if ((t.left == null && t.right != null) || (t.left != null && t.right == null)) {  //check if the current node has 2 children or none 
        return false; 
    }

    return FullBintree(t.left) && FullBintree(t.right);  //check left and right subtrees (recursive method)
}

private void optimizeTree(TNode t) {    //helper method to optimize binary tree 
    if (t != null) {   //if current node is not null (base case)
        if (t.left == null && t.right != null)   //if the left child is null and the right child is not null...
           {
        	t.data = t.right.data;  //copy data from the right child to the current node data
            t.left = t.right.left; //update left child
            t.right = t.right.right; //update right child 
            optimizeTree(t); //continue recursive optimization
            
        } 
        else if (t.left != null && t.right == null)  //if the left child is not null and the right child is null
        	{ t.data = t.left.data; //update data from the left child to the current node 
            t.right = t.left.right; //update right child 
            t.left = t.left.left; //update left child  
            optimizeTree(t); //cont. recursive optimization
        }
        
        else //if both left and right child are present, recursvesly optimzie the trees
        {
            optimizeTree(t.left);
            optimizeTree(t.right);
        }
    }
}


//GET CODE WORDS FUNCTION
//	Time complexity: O(log n) Space Complexity: O(n)
//	Reasoning: since bubble sort was imeplemnted, the worst case would be O(n). due to the nested loops we have to consider the depth in the binary tree whcih reuslts in O(log n). O(m) where m is the hegith of the tree. 


public ArrayList<String> getCodewords() {
    ArrayList<String> CW = new ArrayList<>(); //create an array list to store codewords
    CodewordRetrieve(root, "", CW); //collect codewords (cw)

    // Sort codewords lexicographically 
    int n = CW.size();
    for (int i = 0; i < n - 1; i++) 
    {
        for (int j = 0; j < n - i - 1; j++) 
        {
            if (CW.get(j).compareTo(CW.get(j + 1)) > 0) //if the slected codeword is lexicogrpahically greater than the followng one, swap
            {
                // if element is in wrong order, swap
                String temp = CW.get(j);
                CW.set(j, CW.get(j + 1));
                CW.set(j + 1, temp);
            }
        }
    }

    return CW; //returns list contains string of 0,1
}

//recursive helper method to collect codewords from the binary tree
private void CodewordRetrieve(TNode node, String currentCodeword, ArrayList<String> codewords) {
    if (node != null) {  // if the node is not null....
        if (node.data != null) { //check if the current node has data ( not equal to null)
            codewords.add(currentCodeword);   //add the current codeword to the array list 
        }
        CodewordRetrieve(node.left, currentCodeword + "0", codewords);   //go through left child
        CodewordRetrieve(node.right, currentCodeword + "1", codewords);  // go through right child 
    }
}



//TO ARRAY FUNCTION
//use the level order method from 2si3 ( Level order: root, nodes on level 1 from L to R, nodes on level 2 from L to R)

//	Time Complexity: O(n)   Space Complexity: O(n)
//	Reasoning: with the level order method from 2si3, it goes to each node. the loop goes through the full tree. considering the loops and implemtning the array, the compelxity is  O(n).
// 	the array stores values by each level. this requires sapce to be poroptional to the # of nodes in the tree. the ArrayList method require sspace to stoer the resulting array. reuslting in a sapce compelxity of O(n)		   

public String[] toArray() {
    ArrayList<String> treeArray = new ArrayList<>(); //creat list to store elements from tree
    treeArray.add(null); // Add null at the beginning (root)
    levelOrTrav(treeArray); //level order method 

    String[] Arrayresult = treeArray.toArray(new String[0]); // Convert the ArrayList to an array

    return Arrayresult; //return 
}

private void levelOrTrav(ArrayList<String> treeArray) {
    if (root == null) {  //if root is null...
        return; //return
    }

    ArrayList<TNode> CurrLevelNodes = new ArrayList<>(); //intilaize array for nodes at the level (starting at root)
    CurrLevelNodes.add(root);

    while (!CurrLevelNodes.isEmpty()) {  // run while the slected level is not empty 
        ArrayList<TNode> nextLevelNodes = new ArrayList<>(); //start ArrayList for the nodes at the following level 
       
      

        for (TNode node : CurrLevelNodes) { //process each node at the selected level 
            if (node == null) {
                treeArray.add(null); //add null value to represent null node
            } else {
                if (node.data == null) {
                    treeArray.add("I"); // Add "I" to represent internal node
                } else {
                    treeArray.add(node.data); //add data to the selected node
                    
                }

                nextLevelNodes.add(node.left);   //add both the children to the list
                nextLevelNodes.add(node.right);
            }
        }

        CurrLevelNodes = nextLevelNodes; //updated the slected level to the next level 
        
        }
    
    while (!treeArray.isEmpty() && treeArray.get(treeArray.size() - 1) == null) { //remove unnesserary nulls from the end of the array
        treeArray.remove(treeArray.size() - 1); }
}



//ENCODE FUNCTION
//Encodes a list of symbols into a binary bitstream using the binary tree.

//	Time Complexity: O(n*h)    Space Complexity: O(n)
// Reasoning: for every symbol, method locateCW is called. worst case it traverses bin tree height resulting in O(n*h) where n is # of symbols and h is height of tree. The space complexity in this function only considers the hegiht of the tree. it is prooptional to the hegiht if the tree becuase of the recursive call in the locateCW func.


public String encode(ArrayList<String> a)
{
    StringBuilder SetBitStr = new StringBuilder();

    for (String AlphabetSymbol : a)  //go through each element in the array
    	
    { String CW = locateCW(root,  AlphabetSymbol); //look for codeword for the present symbol
    	
        if (CW != null) //if the codeword is not null....
        {
        	SetBitStr.append(CW); //append to the bit stream 
        }
        else 
        {
           
            throw new IllegalArgumentException("Symbol not located: " + AlphabetSymbol); //throw excpetion when symbol is  not located
        }
    }

    return SetBitStr.toString(); //return final bit stream to string
}

private String locateCW(TNode Currentnode, String desired) { //if node is null...
    
	if (Currentnode == null)  //if node is null....
    {
        return null; //return null
    }

    if (Currentnode.data != null && Currentnode.data.equals(desired) ) //check if slected node has the required symbol 
    {
        
        return ""; //when the symbol is located, return an empty string as the codeword 
    }

   
    String CWLeft = locateCW(Currentnode.left, desired);  //serach for symbol in left sub tree
    
    String CWRight = locateCW(Currentnode.right, desired); //serach for symbol in right sub tree

    
    //if CWLeft is not equal to null, append 0, if it is null, check CWright. appoend 1 to show right child is in codeword.
    return (CWLeft != null) ? "0" + CWLeft : (CWRight != null) ? "1" + CWRight : null; //return null if both child are null
  
}

//DECODE FUNCTION 
//	Time complexity: O(n)       Space Complexity: O(m)
//	Reasoning: for the time complexity, n is the length of the bit stream (s). in the worst case, this function goes through each bit and conducts the demanded operations (element update, traversing, adding the updated symbol to element etc). for the space compelxity, we have to consider the local variables (element, selected, Currentbit) have constant space.the SymbolResult list stores the symbols that were decoded. cosnidering these 2 factors, the space complexity is O(m) where m is the # of symbols that are decoded.

public ArrayList<String> decode(String s) throws IllegalArgumentException {
    
	ArrayList<String> SymbolResult = new ArrayList<>(); //create list to store the decoded symbols 
    
    int element = 0; //Initialize value 

    while (element< s.length()) //go through the bit stream 
    {
        TNode selected = root;  //start at the root 
        
        element= travBitStr( s, element, selected, SymbolResult); //update value based on the traversal 
    }

    return SymbolResult; //return the list 
}

private int travBitStr(String s, int element, TNode selected, ArrayList<String> SymbolResult) throws IllegalArgumentException {
    while (selected.data == null && selected!= null) //traverse until leaf is reached
    { 
        char Currentbit = s.charAt(element++);
        if (Currentbit == '0')  //if bit is 0....
        {
            selected = selected.left; //go to left child
        } 
        
        else if (Currentbit == '1') //if bit is 1.....
        {
            selected = selected.right; //go to right child
        } 
        
        else 
        {
            throw new IllegalArgumentException("Bit is invalid: " + Currentbit); //throw exception error for invalid bit 
        }
    }

    if (selected != null && selected.data != null) //check if leaf with data is obtained 
    {
       
        SymbolResult.add(selected.data); //add symbol to result
    } 
    
    else 
    {
        
        throw new IllegalArgumentException("bit sequence Incorrect: " + s );  //throw exception error
    }

    return element; //return new values
} 
	
//TO STRING FUNCTION
//	Time complexity: O(n)  Space Complexity: O(n+m)
//	Reasoning: we have to consider the loops that iterate through each codeword. the getCodeword method traveres the entire tree. therefore, the time compelxity where n is # of codewords is O(n). The space compelxity considers the getCodeword method and the space the Java StringBuilder uses.  the getCodewords method covers the hight of the bin tree which is m, and stringbuilder used to link the codewords is O(n) where n is the total length of all the codwords. therefore, the space complexity is O(n+m)
	
public String toString() {
	ArrayList<String> codewords = getCodewords(); //get codeword list from the method 
    
	StringBuilder result = new StringBuilder(); //to store the codewords 

    for (String codeword : codewords)   //loop through each element in list 
        {
    	
    	result.append(codeword).append(" "); //append codeword ( along with space in between )
        }

    result.setLength(result.length() - 1); //remove unnecessary sapce at the end 

    return result.toString(); //return the converted string
}

}

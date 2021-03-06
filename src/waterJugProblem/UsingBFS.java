/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waterJugProblem;

import java.util.Scanner;
import static java.lang.System.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author user
 */
public class UsingBFS {
	
    public static int MAX_CAPACITY_OF_LEFT_JUG, MAX_CAPACITY_OF_RIGHT_JUG, MAX_DEPTH_OF_TREE;
	public static Queue<Node> queue;
	public static Set<Node> alreadyEncountered;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        out.println("Enter the capacities of the two jugs respectively");
		out.println("The capacity of the larger jug should be followed by the capacity of the smaller jug and they should not be equal");
        MAX_CAPACITY_OF_LEFT_JUG = sc.nextInt();
        MAX_CAPACITY_OF_RIGHT_JUG = sc.nextInt();
		MAX_DEPTH_OF_TREE = 15;
        out.println("Enter the required capacity");
        int t = sc.nextInt();
		Node root = new Node(0, 0);
//		out.println("("+root.x+","+root.y+")");
		//root.height = 0;
		queue = new LinkedList<>();		//To store nodes in order to process them in the BFS manner
		queue.add(root);
		alreadyEncountered = new HashSet<>();	//To store which states have already been visited
		alreadyEncountered.add(root);
//		int noOfChildrenAtThisLevel = 0;
		int numberOfNodesTraversed = 0;
		int level = 1;
		boolean flag = false;
//		Queue<Integer> noOfChildrenOfNodes = new LinkedList<>();
		while(!queue.isEmpty()){
			numberOfNodesTraversed++;
//			if(noOfChildrenAtThisLevel==0 && !noOfChildrenOfNodes.isEmpty()){
//				noOfChildrenAtThisLevel = noOfChildrenOfNodes.poll();
//				level++;
//			}
			int tempVariableForChildren = 0;
			Node node = queue.poll();
//			out.println("("+node.x+","+node.y+")");
			if(node.x==-1 && node.y==-1){
				++level;
				queue.add(new Node(-1, -1));
				continue;
			}
			if(node.x == t || node.y == t){
				out.println("The required capacity can be achieved after "+(level+1)+" move(s)");
				out.println("Number of nodes traversed: "+numberOfNodesTraversed);
				break;
			}
			if(level > MAX_DEPTH_OF_TREE){
				out.println("The desired capacity was not achieved within a tree depth of "+MAX_DEPTH_OF_TREE);
				out.println("Number of nodes traversed: "+numberOfNodesTraversed);
				break;
			}
			if(node.x == MAX_CAPACITY_OF_LEFT_JUG && node.y == 0){
				Node newNode = new Node(MAX_CAPACITY_OF_LEFT_JUG-MAX_CAPACITY_OF_RIGHT_JUG, MAX_CAPACITY_OF_RIGHT_JUG);
				if(checkIfEncountered(newNode))
					++tempVariableForChildren;
			} else if(node.y == MAX_CAPACITY_OF_RIGHT_JUG && node.x == 0){
				Node newNode = new Node(MAX_CAPACITY_OF_RIGHT_JUG, 0);
				if(checkIfEncountered(newNode))
					++tempVariableForChildren;
			} else if(node.x == MAX_CAPACITY_OF_LEFT_JUG && node.y == MAX_CAPACITY_OF_RIGHT_JUG){
				Node newNode1 = new Node(0, MAX_CAPACITY_OF_RIGHT_JUG);
				if(checkIfEncountered(newNode1))
					++tempVariableForChildren;
				Node newNode2 = new Node(MAX_CAPACITY_OF_LEFT_JUG, 0);
				if(checkIfEncountered(newNode2))
					++tempVariableForChildren;
			} else if(node.x != 0 && node.y != 0){
				Node newNode1 = new Node(0, node.y);
				if(checkIfEncountered(newNode1))
					++tempVariableForChildren;
				Node newNode2 = new Node(node.x, 0);
				if(checkIfEncountered(newNode2))
					++tempVariableForChildren;
				Node newNode3 = new Node(node.x-MAX_CAPACITY_OF_RIGHT_JUG+node.y, MAX_CAPACITY_OF_RIGHT_JUG);
				if(checkIfEncountered(newNode3))
					++tempVariableForChildren;
				Node newNode4 = new Node(MAX_CAPACITY_OF_LEFT_JUG, node.y-MAX_CAPACITY_OF_LEFT_JUG+node.x);
				if(checkIfEncountered(newNode4))
					++tempVariableForChildren;
			} else if(node.x != 0){						// => y=0
				if(node.x>MAX_CAPACITY_OF_RIGHT_JUG){
					Node newNode = new Node(node.x-MAX_CAPACITY_OF_RIGHT_JUG, MAX_CAPACITY_OF_RIGHT_JUG);
					if(checkIfEncountered(newNode))
						++tempVariableForChildren;
				} else{
					Node newNode = new Node(0, node.x);
					if(checkIfEncountered(newNode))
						++tempVariableForChildren;
					Node newNode2 = new Node(MAX_CAPACITY_OF_LEFT_JUG, node.x);
					if(checkIfEncountered(newNode2))
						++tempVariableForChildren;
					Node newNode3 = new Node(node.x, MAX_CAPACITY_OF_RIGHT_JUG);
					if(checkIfEncountered(newNode3))
						++tempVariableForChildren;
				}
			} else if(node.y != 0){						// => x=0
				Node newNode = new Node(node.y, 0);
				if(checkIfEncountered(newNode))
					++tempVariableForChildren;
				Node newNode2 = new Node(node.y, MAX_CAPACITY_OF_RIGHT_JUG);
				if(checkIfEncountered(newNode2))
					++tempVariableForChildren;
				Node newNode3 =  new Node(MAX_CAPACITY_OF_LEFT_JUG, node.y);
				if(checkIfEncountered(newNode3))
					++tempVariableForChildren;
			} else {									// When x and y are both 0 
				Node newNode1 = new Node(MAX_CAPACITY_OF_LEFT_JUG, 0);
				if(checkIfEncountered(newNode1))
					++tempVariableForChildren;
				Node newNode2 = new Node(0, MAX_CAPACITY_OF_RIGHT_JUG);
				if(checkIfEncountered(newNode2))
					++tempVariableForChildren;
			}
			
			//queue.add(new Node(-1, -1));
			
//			noOfChildrenOfNodes.add(tempVariableForChildren);
			if(!flag)
				queue.add(new Node(-1, -1));
			flag = true;
		}
    }
	
	public static boolean checkIfEncountered(Node node){
		if(!alreadyEncountered.contains(node)){
			queue.add(node);
			alreadyEncountered.add(node);
			return true;						//Child added to the queue
		}
		return false;							//No new child added to the queue
	}
}


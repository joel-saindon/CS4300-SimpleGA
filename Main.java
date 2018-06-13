/*Joel Saindon
 * CS4300 Proj3
 * Simple GA
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;



public class Main {
	public static int population;
	public static int strLength;
	public static Random rand = new Random();
	public static ArrayList<Node> nextPop = new ArrayList<Node>();
	
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Node> currentPop = new ArrayList<Node>();
		System.out.print("Please enter a number N for population size: ");
		population = input.nextInt();
		
		System.out.print("Please enter string length n: ");
		strLength = input.nextInt();
		
		System.out.println("Population: " + population + " " + "strLength: " + strLength);
		System.out.println(" ");
		
		Node newNode;
		for(int i = 1; i <= population; i++) {
			newNode = new Node(strLength);
			currentPop.add(newNode);
		}
		printStats(currentPop);
		printNodes(currentPop);
		/*for(Node node : currentPop) {
			System.out.println(node.toString() + " " + node.getNodeFitness());
		}
		*/
		
		//initialize nodes before selection, recombination,etc.
		Node testParent1;
		Node testParent2;
		Node child1;
		Node child2;
		int prob = 0;
		ArrayList<Double> plateauCheck = new ArrayList<Double>();
		int pMarker = 0;
		int rounds = 0;
		
		for (int g = population; g < 1000; g += 10, rounds++) {
			// check for global optimum
			if(optimumCheck(currentPop)) {
				System.out.println("Global Optimum took " + (rounds+1) + " rounds.");
				break;
			}
			/*
			//check for plateau of average fitness
			if(pMarker == 0 || pMarker == 1 || pMarker == 2) {
				plateauCheck.add(findAvg(currentPop));
				pMarker++;
			}else if(pMarker > 2) {
				if((plateauCheck.get(pMarker-1) == plateauCheck.get(pMarker-2)) && (plateauCheck.get(pMarker-1) == plateauCheck.get(pMarker-3))) {
					System.out.println("Plateau found at " + plateauCheck.get(pMarker-2).toString());
					break;
				} else {
					plateauCheck.remove(plateauCheck.get(0));
				}
			}
			*/
			
			
		//do selection, recombination, mutation, fill nextPop to N-2
			for(int q = 0; q < currentPop.size() - 3; q+=2 ) {
				
				testParent1 = getParent(currentPop);
				testParent2 = getParent(currentPop);
				
				//System.out.println("Parent 1: " + testParent1.toString());
				//System.out.println("Parent 2: " + testParent2.toString());
				
				
				//calculate probability
				prob = rand.nextInt(100)+1;
				if(prob <= 40) {
					//System.out.println("Prob fail: copying parents");
					child1 = testParent1;
					//child1.setNodeFitness(child1.calculateFitness());
					child2 = testParent2;
					//child2.setNodeFitness(child2.calculateFitness());
				}else if (prob >=41) {
					//System.out.println("Crossing Parents: ");
					child1 = crossParents(testParent1, testParent2);
					child1.setNodeFitness(child1.calculateFitness());
					//System.out.println(child1.toString() + " " + child1.getNodeFitness());
					child2 = crossParents(testParent1, testParent2);
					child2.setNodeFitness(child2.calculateFitness());
					//System.out.println(child2.toString() + " " + child2.getNodeFitness());
				}else {
					System.out.println("Shouldn't reach here: copying parents");
					child1 = testParent1;
					child2 = testParent2;
				}
				
				//run mutation dice rolls
				child1 = maybeMutate(child1);
				child1.setNodeFitness(child1.calculateFitness());
				child2 = maybeMutate(child2);
				child2.setNodeFitness(child2.calculateFitness());
				
				nextPop.add(child1);
				nextPop.add(child2);
			}
			//find best parents from currentPop and add to nextPop
			nextPop.add(findHigh(currentPop));
			currentPop.remove(findHigh(currentPop));
			nextPop.add(findHigh(currentPop));
			
			//System.out.println("Size of nextPop " + nextPop.size());
			//printNodes(nextPop);
			System.out.println("------------------------------------------------------");
			
			//clear currentPopulation, make nextPop the currentPop
			currentPop.clear();
			for(Node node : nextPop) {
				currentPop.add(node);
			}
			printNodes(currentPop);
			nextPop.clear();
			}
		input.close();
	}
	
	//Get random Node
	public static Node getRandom(ArrayList<Node> pop) {
		return pop.get(rand.nextInt(pop.size()));
	}
	
	//print nodes of given ArrayList along with fitness of each node
	public static void printNodes(ArrayList<Node> pop) {
		for(Node node : pop) {
			System.out.println(node.toString() + " " + node.getNodeFitness());
		}
	}
	
	//evaluate two nodes fitnesses
	public static Node evalNodes(Node parent1, Node parent2) {
		if(parent1.getNodeFitness() <= parent2.getNodeFitness()) {
			return parent2;
		}else
			return parent1;
	}
	
	//get parent from given list, select two, measure fitness, return better parent
	public static Node getParent(ArrayList<Node> currentPop) {
		Node parent1;
		Node parent2;
		
		parent1 = getRandom(currentPop);
		parent2 = getRandom(currentPop);
		//System.out.println(getRandom(currentPop).toString());
		
		return evalNodes(parent1, parent2); //determine best parent out of two selected
				
	}
	//If probability is correct, cross parents of two given nodes
	//with 50/50 chance across the two nodes of which parent to grab
	public static Node crossParents(Node p1, Node p2) {
		Node childcross = new Node(p1.getStrLen());
		ArrayList<Integer> temp = new ArrayList<Integer>();
		int prob;
		for(int i = 0; i < p1.getStrLen(); i++) {
			prob = rand.nextInt(100) +1;
			if(prob <=50) {
				temp.add(p1.getStr().get(i));
			} else if (prob >50) {
				temp.add(p2.getStr().get(i));
			}	
		}
		
		childcross.setStr(temp);
		return childcross;
	}
	
	//run probability on each bit to mutate or not
	public static Node maybeMutate(Node child) {
		Node childNode = new Node(child.getStrLen());
		int prob;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		//System.out.println("running probability-mutating");
		for(int i =0; i<child.getStrLen();i++) {
			prob = rand.nextInt(child.getStrLen())+ 1; //establish range for random 1 to n
			if (prob == 1) {//1/n chance to mutate a bit, ran for each position in the string of the node
				//System.out.println("DEBUG: Mutation at " + i +"!");
				if(child.getStr().get(i) == 0) {
					temp.add(1);
				} else if(child.getStr().get(i) == 1) {
					temp.add(0);
				}
			} else {
				temp.add(child.getStr().get(i));
			}
		}
		
		childNode.setStr(temp);
		//System.out.println("changed " + child.toString() + " to " + childNode.toString());
		return childNode;
	}
	
	//calculate and print stats of current Population
	public static void printStats(ArrayList<Node> popList) {
		//calculate and print average fitness of nodes
		double avg = 0.0;
		double runTotal = 0.0;
		for(Node node : popList) {
			runTotal = runTotal + (double) node.getNodeFitness();
		}
		avg = runTotal/popList.size();
		System.out.println("Avg Fitness of Pop: " + avg);
		
		//calculate position of lowest fitness node
		int posLow = 0;
		int marker = 0;
		int i = 0;
		/*System.out.println("DEBUG:");
		System.out.println(popList.get(i).getNodeFitness());
		System.out.println(popList.get(marker).getNodeFitness());
		System.out.println(popList.get(posLow).getNodeFitness());
		*/
		for (i = 1; i < popList.size(); i++, marker++) {
			if ((popList.get(i).getNodeFitness() < popList.get(marker).getNodeFitness()) && (popList.get(i).getNodeFitness() < popList.get(posLow).getNodeFitness())) {
				posLow = i;
			}
		}
		System.out.println("Lowest Fitness Node " + popList.get(posLow).toString() + " " + popList.get(posLow).getNodeFitness());
		
		//calculate position of highest fitness node
		int posHigh = 0;
		marker = 0;
		for(i = 1; i < popList.size(); i++, marker++) {
			if((popList.get(i).getNodeFitness() > popList.get(marker).getNodeFitness()) && (popList.get(i).getNodeFitness() > popList.get(posHigh).getNodeFitness())) {
				posHigh = i;
			}
		}
		System.out.println("Highest Fitness Node " + popList.get(posHigh).toString() + " " + popList.get(posHigh).getNodeFitness());
	}
	
	//find highest fitness node in given list
	public static Node findHigh(ArrayList<Node> popList) {
		//calculate position of highest fitness node
				int posHigh = 0;
				int marker = 0;
				for(int i = 1; i < popList.size(); i++, marker++) {
					if((popList.get(i).getNodeFitness() > popList.get(marker).getNodeFitness()) && (popList.get(i).getNodeFitness() > popList.get(posHigh).getNodeFitness())) {
						posHigh = i;
					}
				}
				return popList.get(posHigh);
	}
	//find 
	public static Node findLow(ArrayList<Node> popList) {
		int posLow = 0;
		int marker = 0;
		for (int i = 1; i < popList.size(); i++, marker++) {
			if ((popList.get(i).getNodeFitness() < popList.get(marker).getNodeFitness()) && (popList.get(i).getNodeFitness() < popList.get(posLow).getNodeFitness())) {
				posLow = i;
			}
		}
		return popList.get(posLow);
	}
	
	public static double findAvg(ArrayList<Node> popList) {
		double avg = 0.0;
		double runTotal = 0.0;
		for(Node node : popList) {
			runTotal = runTotal + (double) node.getNodeFitness();
		}
		avg = runTotal/popList.size();
		return avg;
	}
	
	public static boolean optimumCheck(ArrayList<Node> popList) {
		for(Node node : popList) {
			if(node.getNodeFitness() == strLength) {
				System.out.println("Global Optimum found! " + node.toString());
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}

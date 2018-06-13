/*Joel Saindon
 * CS4300 Proj3
 * Simple GA
 */

import java.util.Random;
import java.util.ArrayList;

public class Node {
	private int strLen;
	private ArrayList<Integer> str;
	private int nodeFitness;
	
	public Node(int strLength) {
		this.strLen = strLength;
		this.str = generateStr(strLength);
		this.nodeFitness = calculateFitness();
	}
	
	//populate array with random 1's and 0's
	public ArrayList<Integer> generateStr(int strLength) {
		ArrayList<Integer> bitArray = new ArrayList<Integer>();
		int checker;
		Random rand = new Random();
		for(int i = 0; i< strLength; i++) {
			checker = rand.nextInt(100) + 1;
			if(checker <= 50) {
				bitArray.add(0);
			} else if(checker >= 51) {
				bitArray.add(1);
			}
		}
		return bitArray;
	}
	/*public void printArray() {
		for(int i = 0; i< this.strLen; i++) {
			System.out.print(this.str[i] + " ");
		}
	}*/
	//calculate fitness of node, returns # of 1's in string
	public int calculateFitness() {
		int temp = 0;
		for(Integer num : this.str) {
			if(num == 1) {
				temp++;
			}
		}
		return temp;
	}

	
	public int getNodeFitness() {
		return nodeFitness;
	}

	public int getStrLen() {
		return strLen;
	}

	public void setStrLen(int strLen) {
		this.strLen = strLen;
	}

	public ArrayList<Integer> getStr() {
		return str;
	}

	public void setStr(ArrayList<Integer> str) {
		this.str = str;
	}

	public void setNodeFitness(int nodeFitness) {
		this.nodeFitness = nodeFitness;
	}
	
	@Override
	public String toString() {
		return this.str.toString();
		}
		
	
	
}

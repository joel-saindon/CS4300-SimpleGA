Joel Saindon
CS4300 Project 3 - Simple GA


This project was written in Eclipse so the submission should include:
	A zip of the proj3, containing 2 source files (amongst the usual package stuff):
			Main.java
			Node.java
	This readme file
	
	
Main structure of the program
	This assumes any entered population size N will be even as loop counters don't account for an odd number of 
	I created a node class to hold information on each member of the population, including a fitness and the randomly generated string of 1's and 0's
	I also created an ArrayList to hold all the nodes of the current population and another ArrayList to hold the nodes of the children.
	At the end of each loop, the old population is cleared out and the children are moved into the current population list to begin the next round.
	
Items not implemented:
	TODO: Bisection calculations
	TODO: Generational fitness plateau calculations (no change in avg fitness for 3 generations)
	TODO: Global optimum checking
	TODO: Odd population checking and reconciliation
	
Items implemented:
	Initialization of population N with string length n.
	Selection of random parents to be crossed in recombination step
	Recombination of selected parents to produce 2 children a piece, randomly taking bits from each, or if probability checker is too low, copy parents to children
	Chance to mutate random bits in newly created child nodes within each iteration of procedural loop
	Replacement of current population with population of children
	Fitness checking of each node upon creation/mutation
	
Other notes:
	I had a lot of fun with this project. It felt very do-able/not far out of reach; but still took some effort to get right. It also felt like a good teacher of the content.
	Obviously these projects can end up rough sometimes or not very optimized but this one felt good. Even if I didn't get all the way through to the end of the last pieces.
	
	
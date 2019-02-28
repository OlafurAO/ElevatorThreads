package com.ru.usty.elevator;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ElevatorScene {

	//TO SPEED THINGS UP WHEN TESTING,
	//feel free to change this.  It will be changed during grading
	public static final int VISUALIZATION_WAIT_TIME = 1000;  //milliseconds

	private int numberOfFloors;
	private int numberOfElevators;
	
	ArrayList<Elevator> elevators;
	ArrayList<Integer> personCount; 
	ArrayList<Integer> exitedCount = null;
	
	public static Semaphore exitedCountMutex;
	public static Semaphore sem;
	public static Thread elevatorThread;
	//public static Elevator elevator;

	//Base function: definition must not change
	//Necessary to add your code in this one
	public void restartScene(int numberOfFloors, int numberOfElevators) {
		elevators = new ArrayList<Elevator>();
		
		for(int i = 0; i < numberOfFloors; i++) {
			Elevator elevator = new Elevator(numberOfFloors, VISUALIZATION_WAIT_TIME);
			elevatorThread = new Thread(elevator);
			
			
			elevators.add(elevator);
		}
				
		sem = new Semaphore(0);
		
		this.elevatorThread.start();

		this.numberOfFloors = numberOfFloors;
		this.numberOfElevators = numberOfElevators;

		personCount = new ArrayList<Integer>();
		
		for(int i = 0; i < numberOfFloors; i++) {
			this.personCount.add(0);
		}

		if(exitedCount == null) {
			exitedCount = new ArrayList<Integer>();
		}
		else {
			exitedCount.clear();
		}
		for(int i = 0; i < getNumberOfFloors(); i++) {
			this.exitedCount.add(0);
		}
		
		exitedCountMutex = new Semaphore(1);
	}

	//Base function: definition must not change
	//Necessary to add your code in this one
	public Thread addPerson(int sourceFloor, int destinationFloor) {
		Person person = new Person(sourceFloor, destinationFloor);
		Thread personThread = new Thread(person);
		
		personThread.start();
		
		elevators.get(0).addPerson(new Person(sourceFloor, destinationFloor));
		
		/**
		 * Important to add code here to make a
		 * new thread that runs your person-runnable
		 * 
		 * Also return the Thread object for your person
		 * so that it can be reaped in the testSuite
		 * (you don't have to join() yourself)
		 */

		//dumb code, replace it!
		personCount.set(sourceFloor, personCount.get(sourceFloor) + 1);
		
		return personThread;  //this means that the testSuite will not wait for the threads to finish
	}

	//Base function: definition must not change, but add your code
	public int getCurrentFloorForElevator(int elevator) {		
		return elevators.get(elevator).getCurrentFloor();
	}

	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleInElevator(int elevator) {
		return elevators.get(elevator).getPeopleCount();
	}

	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleWaitingAtFloor(int floor) {

		return personCount.get(floor);
	}
	
	//Person threads must call this function to
		//let the system know that they have exited.
		//Person calls it after being let off elevator
		//but before it finishes its run.
	public void personExitsAtFloor(int floor) {
		try {
			exitedCountMutex.acquire();
			exitedCount.set(floor, (exitedCount.get(floor) + 1));
			exitedCountMutex.release();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	///////////////////////Don't need to change////////////////////////////////////
	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	public int getNumberOfElevators() {
		return numberOfElevators;
	}

	public void setNumberOfElevators(int numberOfElevators) {
		this.numberOfElevators = numberOfElevators;
	}

	public boolean isElevatorOpen(int elevator) {
		return isButtonPushedAtFloor(getCurrentFloorForElevator(elevator));
	}
	
	public boolean isButtonPushedAtFloor(int floor) {
		return (getNumberOfPeopleWaitingAtFloor(floor) > 0);
	}

	//Base function: no need to change, just for visualization
	//Feel free to use it though, if it helps
	public int getExitedCountAtFloor(int floor) {
		if(floor < getNumberOfFloors()) {
			return exitedCount.get(floor);
		}
		else {
			return 0;
		}
	}


}

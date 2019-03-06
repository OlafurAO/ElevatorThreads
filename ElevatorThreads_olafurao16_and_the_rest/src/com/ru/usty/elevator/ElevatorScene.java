package com.ru.usty.elevator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

public class ElevatorScene {

	//TO SPEED THINGS UP WHEN TESTING,
	//feel free to change this.  It will be changed during grading
	public static final int VISUALIZATION_WAIT_TIME = 1000;  //milliseconds

	ArrayList<Elevator> elevators;
	ArrayList<Integer> personCount; 
	ArrayList<Integer> exitedCount = null;
	
	public static Thread elevatorThread;
	
	public static Semaphore exitedCountMutex;
	public static Semaphore sem;
	
	private int numberOfFloors;
	private int numberOfElevators;
	private int numberOfPeopleWaiting;
	
	
	//Added to make restartScene() less crowded
	public ElevatorScene() {		
		if(exitedCount == null) {
			exitedCount = new ArrayList<Integer>();
		} else {
			exitedCount.clear();
		}
		
		elevators = new ArrayList<Elevator>();
		personCount = new ArrayList<Integer>();
		
		exitedCountMutex = new Semaphore(1);
		sem = new Semaphore(6);
		
		
		numberOfPeopleWaiting = 0;
	}
	

	//Base function: definition must not change
	//Necessary to add your code in this one
	public void restartScene(int numberOfFloors, int numberOfElevators) {
		resetVariables();		
		
		this.numberOfElevators = numberOfElevators;
		this.numberOfFloors = numberOfFloors;
		
		for(int i = 0; i < numberOfFloors; i++) {
			Elevator elevator = new Elevator(numberOfFloors, VISUALIZATION_WAIT_TIME);
			elevatorThread = new Thread(elevator);
			
			elevators.add(elevator);
			elevatorThread.start();
		}

		/////////////////////////////////////////////////////////
		for(int i = 0; i < numberOfFloors; i++) {
			this.personCount.add(0);
		}
		
		for(int i = 0; i < getNumberOfFloors(); i++) {
			this.exitedCount.add(0);
		}
	}
	
	private void resetVariables() {
		for(Elevator e : elevators) {
			e = null;
		}
		
		elevatorThread = null;
		
		elevators = new ArrayList<Elevator>();
		personCount = new ArrayList<Integer>();
		
		exitedCountMutex = new Semaphore(1);
		sem = new Semaphore(6);
		
		numberOfPeopleWaiting = 0;
	}

	//Base function: definition must not change
	//Necessary to add your code in this one
	public Thread addPerson(int sourceFloor, int destinationFloor) {
		Person person = new Person(this, sourceFloor, destinationFloor);
		Thread personThread = new Thread(person);
		
		personThread.start();

		//dumb code, replace it!
		personCount.set(sourceFloor, personCount.get(sourceFloor) + 1);
		
		return personThread;  //this means that the testSuite will not wait for the threads to finish
	}
	
	public void removePerson(Person person) {
		Iterator<Person> iterator = elevators.get(0).peopleList.iterator();
		
		while(iterator.hasNext()) {
			if(iterator.next() == person) {
				try {
					iterator.remove();
				} catch(Exception e) {
					
				}
			}
		}
	}
	
	public void decrementPeopleWaiting(int floor) {
		personCount.set(floor, personCount.get(floor) - 1);
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
		
		elevators.get(0).removePerson();
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
		} else {
			return 0;
		}
	}
}

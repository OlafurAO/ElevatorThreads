package com.ru.usty.elevator;

import java.util.ArrayList;

public class Elevator implements Runnable {
	public static final int CAPACITY = 6;
	
	private ArrayList<Person> peopleList;	
	
	public int numberOfFloors;
	private int currentFloor;
	private int peopleCount;
	private int waitTime;
	
	private boolean elevatorOpen;
	private boolean goingUp; 	
	
	public Elevator(int numberOfFloors, int waitTime) {
		this.peopleList = new ArrayList<Person>();
		this.numberOfFloors = numberOfFloors;
		this.waitTime = waitTime;
		
		this.currentFloor = 0;
		this.peopleCount = 0;
		
		this.elevatorOpen = true;
		this.goingUp = true;
	}
	
	public void addPerson(Person person) {
		if(peopleCount < CAPACITY) {
			peopleList.add(person);
			peopleCount++;
		}
	}
	
	public void removePerson() {
		if(peopleCount > 0) {
			peopleCount--;
		}
	}
	
	public void move() {
		if(goingUp) {
			currentFloor++;
		} else {
			currentFloor--;
		}
	}
	
	public void open() {
		elevatorOpen = true;
	}
	
	public void close() {
		elevatorOpen = false;
	}
	
	public void elevatorWait() {
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getCurrentFloor() {
		return currentFloor;
	}
	
	public int getPeopleCount() {
		return peopleCount;	
	}
	
	public boolean isOpen() {
		return elevatorOpen;
	}
	
	public boolean isFull() {
		return peopleCount == CAPACITY; 
	}
	
	public boolean isOnTopFloor() {
		return currentFloor == numberOfFloors - 1; 
	}
	
	public boolean isOnBottomFloor() {
		return currentFloor == 0;
	}
	
	
	@Override
	public void run() {
		while(true) {			
			//System.out.println("opening\n");
			open();
			elevatorWait();
			
			//System.out.println("closing\n");
			close();
			elevatorWait();	
			
			//System.out.println("moving\n");
			move();
			elevatorWait();	
			
			if(isOnTopFloor()) {
				goingUp = false;
			}
			
			else if(isOnBottomFloor()) {
				goingUp = true;
			}
		}
	}
}

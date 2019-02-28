package com.ru.usty.elevator;

import java.util.ArrayList;

public class Elevator implements Runnable {
	public static final int CAPACITY = 6;
	
	private ArrayList<Person> peopleList;	
	
	public int numberOfFloors;
	private int currentFloor;
	private int peopleCount;
	private int waitTime;
	
	private boolean goingUp; 
	
	public Elevator(int numberOfFloors, int waitTime) {
		this.peopleList = new ArrayList<Person>();
		this.numberOfFloors = numberOfFloors;
		this.waitTime = waitTime;
		
		this.currentFloor = 0;
		this.peopleCount = 0;
		
		this.goingUp = true;
	}
	
	public void addPerson(Person person) {
		peopleList.add(person);
		this.peopleCount++;
	}
	
	public void removePerson() {
		this.peopleCount--;
	}
	
	public boolean isAtCapacity() {
		return peopleCount == CAPACITY;
	}
	
	public void move() {
		if (goingUp) {
			currentFloor++;
		}
		else {
			currentFloor--;
		}
	}
	
	public boolean isFull() {
		return peopleCount == CAPACITY; 
	}
	
	public boolean isOnTopFloor() {
		return currentFloor == numberOfFloors; 
	}
	
	public void elevatorWait() {
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			//System.out.println("Elevator");
			System.out.println("opening\n");
			
			elevatorWait();
			
			System.out.println("closing\n");
			
			elevatorWait();	
			
			System.out.println("moving\n");
			
			elevatorWait();	
		}
	}
}

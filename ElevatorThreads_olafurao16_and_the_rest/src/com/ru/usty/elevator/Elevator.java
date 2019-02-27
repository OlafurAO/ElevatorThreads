package com.ru.usty.elevator;

import java.util.ArrayList;

public class Elevator implements Runnable {
	public static final int CAPACITY = 6;
	
	public int numberOfFloors;
	private int currentFloor;
	private int peopleCount;
	
	private ArrayList<Person> peopleList;	
	private boolean goingUp; 
	private int waitTime;
	
	public Elevator(int numberOfFloors) {
		peopleList = new ArrayList<Person>();
		this.numberOfFloors = numberOfFloors;
		this.peopleCount = 0;
		this.currentFloor = 0;
		
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
	
	@Override
	public void run() {
			
	}

}

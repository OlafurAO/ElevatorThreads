package com.ru.usty.elevator;

import java.util.ArrayList;

public class Elevator implements Runnable {
	public static final int CAPACITY = 6;
	
	public int numberOfFloors;
	private int currentFloor;
	private int peopleCount;
	
	ArrayList<Person> peopleList;	
	
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
	
	@Override
	public void run() {
		while(true) {
			System.out.println("Elevator");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

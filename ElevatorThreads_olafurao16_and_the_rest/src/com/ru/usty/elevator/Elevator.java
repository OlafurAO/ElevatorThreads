package com.ru.usty.elevator;

import java.util.ArrayList;

public class Elevator implements Runnable {
	public int numberOfFloors;
	public static final int CAPACITY = 6;
	private int peopleCount;
	
	ArrayList<Person> peopleList;	
	
	public Elevator() {
		peopleList = new ArrayList<Person>();
		this.peopleCount = 0;
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
			
	}

}

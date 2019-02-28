package com.ru.usty.elevator;

public class Person implements Runnable {
	int source, destination;
	
	public Person(int source, int destination) {
		this.destination = destination;
		this.source = source;
	}
	
	public int getSource() {
		return this.source;
	}
	
	public int getDestination() {
		return this.destination;
	}
	
	public boolean isElevatorOnDestFloor() {
		return false; 
	}
	
	public void enter() {
		return;
	}
	
	public void exit() {
		return;
	}
	
	@Override
	public void run() {
		//System.out.println("Person");
		
		try {
			ElevatorScene.sem.acquire();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}	
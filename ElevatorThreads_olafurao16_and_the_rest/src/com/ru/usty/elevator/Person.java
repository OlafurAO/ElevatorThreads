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

	@Override
	public void run() {
		try {
			ElevatorScene.sem.acquire();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Person Thread");
	}
	
}	

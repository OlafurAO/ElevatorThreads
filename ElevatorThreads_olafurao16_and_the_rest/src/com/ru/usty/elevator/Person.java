package com.ru.usty.elevator;

public class Person implements Runnable {
	ElevatorScene elevatorScene;
	
	int source, destination;
	
	public Person(ElevatorScene elevatorScene, int source, int destination) {
		this.elevatorScene = elevatorScene;
		this.destination = destination;
		this.source = source;
	}
	
	public void enter() {
		return;
	}
	
	public void exit() {
		return;
	}
	
	public void personWait() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	@Override
	public void run() {
		if(elevatorScene.elevators.get(0).isOpen()) {
			if(elevatorScene.elevators.get(0).isFull()) {
				personWait();
			} else {
				elevatorScene.elevators.get(0).addPerson(this);
			}
		}
	}
	
}	
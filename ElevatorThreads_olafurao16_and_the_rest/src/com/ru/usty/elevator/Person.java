package com.ru.usty.elevator;

public class Person implements Runnable {
	ElevatorScene elevatorScene;
	int source, destination;
	
	boolean inElevator;
	volatile boolean exitedElevator;
	
	public Person(ElevatorScene elevatorScene, int source, int destination) {
		this.elevatorScene = elevatorScene;
		this.destination = destination;
		this.source = source;
		this.inElevator = false;
		this.exitedElevator = false;
	}
	
	public void enter() {
		return;
	}
	
	public void exit() {
		return;
	}
	
	public void personWait() {
		try {
			Thread.sleep(100);
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
		return elevatorScene.elevators.get(0).getCurrentFloor() == destination; 
	}
	
	public boolean isPersonInElevator() {
		return inElevator;
	}
	
	@Override
	public void run() {
		int counter = 0;
		
		Elevator elevator = elevatorScene.elevators.get(0);
		
		while(exitedElevator == false) {
			if(!isPersonInElevator()) {
				if(elevator.getCurrentFloor() == source) {
					if(!elevator.isFull()) {						
						try {
							elevatorScene.sem.acquire();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						elevatorScene.decrementPeopleWaiting(source);
						elevator.addPerson(this);
						inElevator = true;
					} else {
						personWait();
					}
				}
			}
			
			else {
				if(elevator.getCurrentFloor() == destination) {
					elevatorScene.personExitsAtFloor(destination);
					elevatorScene.removePerson(this);
					exitedElevator = true;
					elevatorScene.sem.release();
					return;		
				} else {
					personWait();
				}
			}
		}
	}
}	
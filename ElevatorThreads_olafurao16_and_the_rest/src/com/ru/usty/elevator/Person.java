package com.ru.usty.elevator;

public class Person implements Runnable {
	ElevatorScene elevatorScene;
	int source, destination;
	
	boolean inElevator;
	
	public Person(ElevatorScene elevatorScene, int source, int destination) {
		this.elevatorScene = elevatorScene;
		this.destination = destination;
		this.source = source;
		this.inElevator = false;
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
		return elevatorScene.elevators.get(0).getCurrentFloor() == destination; 
	}
	
	public boolean isPersonInElevator() {
		return inElevator;
	}
	
	@Override
	public void run() {
		int counter = 0;
		
		Elevator elevator = elevatorScene.elevators.get(0);
		
		while(true) {
			if(!isPersonInElevator()) {
				if(elevator.getCurrentFloor() == source) {
					if(!elevator.isFull()) {
						elevator.addPerson(this);
						inElevator = true;
						
						elevatorScene.decrementPeopleWaiting(source);
					}
					
					else {
						personWait();
					}
				}
			}
			
			else {
				if(elevator.getCurrentFloor() == destination) {
					elevatorScene.personExitsAtFloor(destination);
					break;
				}
			}
			
			System.out.println(elevator.getPeopleCount());
		}
	}
}	
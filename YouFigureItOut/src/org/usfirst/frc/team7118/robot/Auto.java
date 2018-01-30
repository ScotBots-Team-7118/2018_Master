package org.usfirst.frc.team7118.robot;

import org.usfirst.frc.team7118.robot.Scotstants;

public class Auto{
	Drive drive;
	Sensors sensors;
	AutoPath step;
	public Auto(Drive drive) {
		this.drive = drive;
	}
	public enum AutoPath{
		// insert auto steps here
		FIRST_DISTANCE
	}
	
	public void reset() {
		step = AutoPath.FIRST_DISTANCE;
	}
	
	public void auto() {
		switch(step) {
		
		}
	}
}
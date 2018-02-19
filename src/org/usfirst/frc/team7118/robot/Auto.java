package org.usfirst.frc.team7118.robot;

import org.usfirst.frc.team7118.robot.Scotstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Auto implements Scotstants {
	Drive drive;
	Lifter lifter;
	Intake intake;
	char switchPos, scalePos;
	AutoState state;
	Timer timer;
	
	/**
	 * Constructs the autonomous class
	 * @param drive
	 */
	public Auto(Drive drive, Lifter lifter,Intake intake) {
		this.drive = drive;
		this.lifter = lifter;
		this.intake = intake;
		reset();
		
	}
	
	/**
	 * The different states of our robot during autonomous
	 */
	public enum AutoState {
		AUTO_FIRST_DIST,
		AUTO_FIRST_BREAK,
		AUTO_FIRST_TURN,
		AUTO_SECOND_BREAK,
		AUTO_SECOND_DIST,
		AUTO_THIRD_BREAK,
		AUTO_SECOND_TURN,
		AUTO_FOURTH_BREAK,
		AUTO_THIRD_DIST,
		AUTO_FIFTH_BREAK,
		AUTO_RAISE_SWITCH,
		AUTO_SIXTH_BREAK,
		AUTO_DELIVER_CUBE,
		AUTO_SEVENTH_BREAK;
		
	}
	
	/**
	 * Parses game data for autonomous in order to determine priorities for the robot
	 */
	public void parseGameData() {
		switchPos = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
		scalePos = DriverStation.getInstance().getGameSpecificMessage().charAt(1);
	}
	
	/**
	 * Runs the autonomous code for a given starting position
	 * @param position
	 */
	public void run(AutoPath position) {
		switch(position) {
		case CENTER:
			runCenter();
		case LEFT:
			runSide(AutoPath.LEFT);
		case RIGHT:
			runSide(AutoPath.RIGHT);
		}
	}
	
	/**
	 * Runs the autonomous code for a given side (left or right)
	 * @param position
	 */
	public void runSide(AutoPath position) {
		
	}
	
	/**
	 * Runs the autonomous code for the given  center location (left or right)
	 */
	
	public void runCenter() {
		
		switch (state) {
		
		/**
		 * Robot goes the first dist before switching to the next step
		 */
		case AUTO_FIRST_DIST:
			// actual dist = 10.4, give 10 instead to give leeway
			//take adverage encoder values and compares them to values we want 
			if( (drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 < AUTO_CENTER_DIST[0]*Scotstants.ROTATIONS_TO_FEET) { 
				drive.move(Scotstants.AUTO_SPEED);
			}
			// once the above statement is no longer switch to the next step
			else{
				nextStep(AutoState.AUTO_FIRST_BREAK);
			}
			break;
			
			/**
			 * Robot will break if it has reached the first dist 
			 */
		case AUTO_FIRST_BREAK:
			if(timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_FIRST_TURN);
			}
			break;

			/**
			 * Robot will turn 90 degress either left or right depending on which side of the switch is ours
			 * Note: needs testing to see which values is left and which values is right 
			 */
		case AUTO_FIRST_TURN:
			// 90 degrees (left side)/270 degrees (right side)
			if(String.valueOf(DriverStation.getInstance().getGameSpecificMessage().charAt(0)) == "L"){
					if (drive.turn(90, 0.3)) {
						nextStep(AutoState.AUTO_SECOND_BREAK);
					}
					
			}
			else {
				if(drive.turn(-90, 0.3)) {
					nextStep(AutoState.AUTO_SECOND_BREAK);
				}
			} 
			break;

			/**
			 * robot stops after it turns
			 */
		case AUTO_SECOND_BREAK:
			// note: add checks and balances if possible 
			if(timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_SECOND_DIST);
			}
				break;

			/**
			 * Robot goes forward until it reaches intended dist
			 * second dist = 4
			 */
		case AUTO_SECOND_DIST:
			if( (drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 < AUTO_CENTER_DIST[1]*Scotstants.ROTATIONS_TO_FEET) { 
				drive.move(Scotstants.AUTO_SPEED);}
			else{
				nextStep(AutoState.AUTO_THIRD_BREAK);

			}
			break;
		
	
			/**
			 * Code stops for 0.5 seconds and then continues 
			 */
		case AUTO_THIRD_BREAK:
			if(timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_SECOND_TURN);
			}
			break;
			
			/**
			 * Robot turns 90 degrees in a certain direction based on which side the switch is on 
			 */
		case AUTO_SECOND_TURN:
			// 90 degrees (left side)/270 degrees (right side)
			if(String.valueOf(DriverStation.getInstance().getGameSpecificMessage().charAt(0)) == "L"){
				if(drive.turn(90, 0.3)) {
					nextStep(AutoState.AUTO_FOURTH_BREAK);
					}
			}
			else {
				if(drive.turn(270, 0.3)) {
					nextStep(AutoState.AUTO_FOURTH_BREAK);
					}
			} 
			break;
			
		case AUTO_FOURTH_BREAK:
			// Note:add checks and balances if possible 
			if(timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_RAISE_SWITCH);
			}
			break;
			
			/**
			 * Raises the arm before going to the drop off point 
			 */
		case AUTO_RAISE_SWITCH:
			if(lifter.atSwitch()) {
				nextStep(AutoState.AUTO_FIFTH_BREAK);
			}
			else{
				lifter.operate(Scotstants.AUTO_LIFTING_SPEED);
			}
			break;
			
			
			/**
			 * Code stops for 0.5 seconds and them moves on 
			 */
		case AUTO_FIFTH_BREAK:
			if(timer.get() >= 0.5)
				nextStep(AutoState.AUTO_THIRD_DIST);
			break;
			
			/**
			 * Robot goes forward until it reaches intended dist
			 * third dist = 4
			 * Robot will be in position for switch
			 */
		case AUTO_THIRD_DIST:
			if( (drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 < AUTO_CENTER_DIST[2]*Scotstants.ROTATIONS_TO_FEET) { 
				drive.move(Scotstants.AUTO_SPEED);
			}
			else{
				nextStep(AutoState.AUTO_SIXTH_BREAK);
			}
			break;
			
			/**
			 * Code stops for 0.5 seconds and them moves on 
			 */
		case AUTO_SIXTH_BREAK:
			if(timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_DELIVER_CUBE);
			}
			break;
			
			/**
			 * Robot delivers cube 
			 */
		case AUTO_DELIVER_CUBE:
			// reverse intake motors to eject cube (go slow)
			if(timer.get() >= 2) {
				nextStep(AutoState.AUTO_SEVENTH_BREAK);
			}
			else {
				intake.run(Scotstants.AUTO_INTAKE_SPEED);
			}
			break;
			
			
			/**
			 * End of center auto code 
			 * robot stops everything resets 
			 */
		case AUTO_SEVENTH_BREAK:
			// break
			drive.move(0);
			timer.stop();
			break;
		}
	}
	
	/**
	 * Resets all necessary sensors and moves the autonomous progression to the given step
	 * @param step
	 */
	public void nextStep(AutoState step) {
		drive.resetGyro();
		drive.resetEncoders();
		state = step;
		drive.stop();
	}
	
	/**
	 * Resets the autonomous progression
	 */
	public void reset() {
		state = AutoState.AUTO_FIRST_DIST;
	}
};
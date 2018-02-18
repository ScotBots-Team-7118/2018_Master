package org.usfirst.frc.team7118.robot;

import org.usfirst.frc.team7118.robot.Scotstants;
import edu.wpi.first.wpilibj.DriverStation;

public class Auto implements Scotstants {
	Drive drive;
	char switchPos, scalePos;
	AutoState state;
	
	/**
	 * Constructs the autonomous class
	 * @param drive
	 */
	public Auto(Drive drive) {
		this.drive = drive;
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
		AUTO_SEVENTH_BREAK
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
	 * Runs the autonomous code for the given switch location (left or right)
	 */
	public void runCenter() {
		
		//path chooser for center
//		if (switchPos.equals("L")) {
//			side = 0;
//		} else {
//			side = 1;
//		}

		
		switch (state) {
		case AUTO_FIRST_DIST:
			// actual dist = 10.4, give 10 instead to give leeway
			if( (drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 <= AUTO_CENTER_DIST[0]*Scotstants.ROTATIONS_TO_FEET) { 
			drive.move(Scotstants.AUTO_SPEED);}
			else{
				nextStep(AutoState.AUTO_FIRST_BREAK);

			}
						break;
			
			
		case AUTO_FIRST_BREAK:
			// break

			
			
			
			nextStep(AutoState.AUTO_FIRST_TURN);
			break;

			
		case AUTO_FIRST_TURN:
			// 90 degrees (left side)/270 degrees (right side)
			
			nextStep(AutoState.AUTO_SECOND_BREAK);
			break;

			
		case AUTO_SECOND_BREAK:
			// break

			
			
			nextStep(AutoState.AUTO_SECOND_DIST);
			break;

			
		case AUTO_SECOND_DIST:
			// dist = 4
			
			nextStep(AutoState.AUTO_THIRD_BREAK);
			break;

			
		case AUTO_THIRD_BREAK:
			
			nextStep(AutoState.AUTO_SECOND_TURN);
			break;
			
		case AUTO_SECOND_TURN:
			// 90 degrees (left side)/270 degrees (right side)
			
			nextStep(AutoState.AUTO_FOURTH_BREAK);
			break;

			
		case AUTO_FOURTH_BREAK:
			
			nextStep(AutoState.AUTO_THIRD_DIST);
			break;
			
		case AUTO_THIRD_DIST:
			// dist = 4
			
			
			
			nextStep(AutoState.AUTO_FIFTH_BREAK);
			break;
			
			
		case AUTO_FIFTH_BREAK:
			// break
			
			drive.stop();
			
			nextStep(AutoState.AUTO_RAISE_SWITCH);
				break;
				
				
		case AUTO_RAISE_SWITCH:
			// raise arms to: switch fence hight + 1
			// (two feet?)
			
			
			
			nextStep(AutoState.AUTO_SIXTH_BREAK);
				break;
				
				
		case AUTO_SIXTH_BREAK:
			// break
			
			
			
			nextStep(AutoState.AUTO_DELIVER_CUBE);
				break;
				
				
		case AUTO_DELIVER_CUBE:
			// reverse intake motors to eject cube (go slow)
			
			
			
			nextStep(AutoState.AUTO_SEVENTH_BREAK);
				break;
				
				
		case AUTO_SEVENTH_BREAK:
			// break
			// end autonomous
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
}

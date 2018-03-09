package org.usfirst.frc.team7118.robot;

import org.usfirst.frc.team7118.robot.Scotstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Auto implements Scotstants {
	// Define Variables/Objects
	Drive drive;
	Intake intake;
	Lifter lifter;
	char switchPos, scalePos;
	AutoState state;
	Timer timer;
	
	/**
	 * Constructs the autonomous class
	 * @param drive
	 * @param intake
	 * @param lifter
	 */
	public Auto(Drive drive, Intake intake, Lifter lifter) {
		this.drive = drive;
		this.intake = intake;
		this.lifter = lifter;
		timer = new Timer();
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
		AUTO_RAISE_ARM,
		AUTO_FIFTH_BREAK,
		AUTO_THIRD_DIST,
		AUTO_SIXTH_BREAK,
		AUTO_DELIVER_CUBE,
		AUTO_SEVENTH_BREAK
	}
	
	/**
	 * Parses game data for autonomous in order to determine priorities for the robot
	 */
	public void parseGameData() {
		// Gets and stores the position of the allied switch from the Driver Station
		switchPos = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
		// Gets and stores the positions of the allied scale from the Driver Station
		scalePos = DriverStation.getInstance().getGameSpecificMessage().charAt(1);
	}
	
	/**
	 * Runs the autonomous code for a given starting position
	 * @param position
	 */
	public void run(AutoPath position) {
		// Creates a state engine for position and runs the program for the given position
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
		// Creates a state engine for the state of autonomous
		switch(state) {
		
		case AUTO_FIRST_DIST:
			// Checks if the average displacement of the two sides of the drive train is greater than the first autonomous distance
			if ((drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 > Scotstants.AUTO_SIDE_DIST[0]*Scotstants.ROTATIONS_TO_FEET) {
				// If so, go to the next step
				nextStep(AutoState.AUTO_FIRST_BREAK);
			}
			else {
				// Otherwise, drive the robot forward using the gyro
				drive.gyroDrive(Scotstants.AUTO_MOVE_SPEED);
			}
			break;
			
		case AUTO_FIRST_BREAK:
			// Checks if 0.5 seconds have passed since the last step ended
			if (timer.get() >= 0.5) {
				// If so, select the next step based on the position of the scale.
				// If the scale is on the same side, start the progression to deliver a cube to the scale
				// Otherwise, stop here.
				switch(position) {
				case LEFT:
					if (scalePos == 'L') {
						nextStep(AutoState.AUTO_SECOND_DIST);
					}
					else {
						nextStep(AutoState.AUTO_SIXTH_BREAK);
					}
					break;
					
				case RIGHT:
					if (scalePos == 'R') {
						nextStep(AutoState.AUTO_SECOND_DIST);
					}
					else {
						nextStep(AutoState.AUTO_SIXTH_BREAK);
					}
					break;
					
				case CENTER:
					// If something went wrong, print to the DS and stop the code
					System.out.println("ERROR: Incorrect autonomous method selected.");
					timer.stop();
					drive.stop();
					break;
				}
			}
			break;
			
		case AUTO_SECOND_DIST:
			// Checks if the average displacement of both sides of the drive train are greater than the second autonomous distance.
			if ((drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 > Scotstants.AUTO_SIDE_DIST[1]*Scotstants.ROTATIONS_TO_FEET) {
				// If so, go to the next step
				nextStep(AutoState.AUTO_SECOND_BREAK);
			}
			else {
				// Otherwise, drive the robot using the gyro.
				drive.gyroDrive(Scotstants.AUTO_MOVE_SPEED);
			}
			break;
			
		case AUTO_SECOND_BREAK:
			// If 0.5 seconds have passed since the last step ended, move to the next step
			if (timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_FIRST_TURN);
			}
			break;
			
		case AUTO_FIRST_TURN:
			// Begin to turn the robot the appropriate amount of degrees,
			// then if it's done turning, move to the next step
			switch(position) {
			case LEFT:
				if (drive.turn(Scotstants.AUTO_SIDE_TURN[0], Scotstants.AUTO_MOVE_SPEED)) {
					nextStep(AutoState.AUTO_THIRD_BREAK);
				}
				break;
			case RIGHT:
				if (drive.turn(Scotstants.AUTO_SIDE_TURN[1], Scotstants.AUTO_MOVE_SPEED)) {
					nextStep(AutoState.AUTO_THIRD_BREAK);
				}
				break;
			case CENTER:
				// If something went wrong, print to the DS and stop the code
				System.out.println("ERROR: Incorrect autonomous selected.");
				timer.stop();
				drive.stop();
				break;
			}
		case AUTO_THIRD_BREAK:
			// If 0.5 seconds have passed since the last step ended, move to the next step
			if (timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_RAISE_ARM);
			}
			break;
			
		case AUTO_RAISE_ARM:
			// If the lifter is at the scale position, move to the next step
			if (lifter.atScale()) {
				nextStep(AutoState.AUTO_FOURTH_BREAK);
			}
			else {
				// Otherwise, move the lifter up
				lifter.operate(Scotstants.AUTO_LIFTING_SPEED);
			}
			break;
			
		case AUTO_FOURTH_BREAK:
			// If 0.5 seconds have passed since the last step ended, move to the next step
			if (timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_THIRD_DIST);
			}
			break;
			
		case AUTO_THIRD_DIST:
			// Checks if the average displacement of the two sides of the drive train is greater than the third autonomous distance
			if ((drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 > Scotstants.AUTO_SIDE_DIST[2]*Scotstants.ROTATIONS_TO_FEET) {
				// If so, move to the next step
				nextStep(AutoState.AUTO_FIFTH_BREAK);
			}
			else {
				// Otherwise, drive the robot using the gyro
				drive.gyroDrive(Scotstants.AUTO_MOVE_SPEED);
			}
			break;
			
		case AUTO_FIFTH_BREAK:
			// If 0.5 seconds have passed since the end of the last step, move to the next step
			if (timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_DELIVER_CUBE);
			}
			break;
			
		case AUTO_DELIVER_CUBE:
			if (timer.get() >= 2) {
				// If two seconds have passed since this step started, move to the next step
				nextStep(AutoState.AUTO_SIXTH_BREAK);
				intake.stop();
			}
			else {
				// Otherwise, run the intake so that the cube is spit out
				intake.run(1);
			}
			break;
			
		case AUTO_SIXTH_BREAK:
			// Stop the timer and stop the robot from moving
			// This is the final step in the side autonomous sequence
			timer.stop();
			drive.stop();
			break;
			
		case AUTO_SECOND_TURN:
			// If there was an error and the code reached this step, print to the DS and stop the code
			System.out.println("ERROR: Out of given autonomous sequence.");
			drive.stop();
			timer.stop();
			break;
		case AUTO_SEVENTH_BREAK:
			// If there was an error and the code reached this step, print to the DS and stop the code
			System.out.println("ERROR: Out of given autonomous sequence.");
			drive.stop();
			timer.stop();
			break;
		}
	}
	
	/**
	 * Runs the auto code for the center
	 */
	public void runCenter() {
		
		switch (state) {
		
		/**
		 * Robot goes the first dist before switching to the next step
		 */
		case AUTO_FIRST_DIST:
			// actual dist = 10.4, give 10 instead to give leeway
			//take average encoder values and compares them to values we want 
			if( (drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 < AUTO_CENTER_DIST[0]*Scotstants.ROTATIONS_TO_FEET) { 
				drive.gyroDrive(Scotstants.AUTO_MOVE_SPEED);
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
			if(switchPos == 'L'){
					if (drive.turn(AUTO_CENTER_TURN[0], AUTO_MOVE_SPEED)) {
						nextStep(AutoState.AUTO_SECOND_BREAK);
					}
					
			}
			else {
				if(drive.turn(-AUTO_CENTER_TURN[0], AUTO_MOVE_SPEED)) {
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
				drive.gyroDrive(Scotstants.AUTO_MOVE_SPEED);}
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
			if(switchPos == 'L'){
				if (drive.turn(AUTO_CENTER_TURN[1], AUTO_MOVE_SPEED)) {
					nextStep(AutoState.AUTO_SECOND_BREAK);
				}
				
		}
		else {
			if(drive.turn(-AUTO_CENTER_TURN[1], AUTO_MOVE_SPEED)) {
				nextStep(AutoState.AUTO_SECOND_BREAK);
			}
		} 
		break;

		case AUTO_FOURTH_BREAK:
			// Note:add checks and balances if possible 
			if(timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_RAISE_ARM);
			}
			break;
			
			/**
			 * Raises the arm before going to the drop off point 
			 */
		case AUTO_RAISE_ARM:
			if(lifter.atSwitch()) {
				nextStep(AutoState.AUTO_FIFTH_BREAK);
			}
			else {
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
				drive.gyroDrive(Scotstants.AUTO_MOVE_SPEED);
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
				intake.stop();
			}
			else {
				intake.run(1);
			}
			/**
			 * End of center auto code 
			 * robot stops everything resets 
			 */
		case AUTO_SEVENTH_BREAK:
			// break
			drive.stop();
			timer.stop();
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
		timer.reset();
		timer.start();
	}
	
	/**
	 * Resets the autonomous progression
	 */
	public void reset() {
		state = AutoState.AUTO_FIRST_DIST;
	}
}

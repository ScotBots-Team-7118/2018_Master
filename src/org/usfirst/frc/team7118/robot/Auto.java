package org.usfirst.frc.team7118.robot;

import org.usfirst.frc.team7118.robot.Scotstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Auto implements Scotstants {
	// Define Variables/Objects
	Drive drive;
	Intake intake;
	Lifter lifter;
	char switchPos = 'L', scalePos;
	AutoState state;
	Timer timer;
	SidePath path;
	
	
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
	
	public enum SidePath{
		SC,
		SW,
		LN
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
			if (scalePos == 'L') {
				path = SidePath.SC;
			}
			else if (switchPos == 'L') {
				path = SidePath.SW;
			}
			else {
				path = SidePath.LN;
			}
		case RIGHT:
			runSide(AutoPath.RIGHT);
			if (scalePos == 'R') {
				path = SidePath.SC;
			}
			else if (switchPos == 'R') {
				path = SidePath.SW;
			}
			else {
				path = SidePath.LN;
			}
		}
	}
	
	/**
	 * Runs the autonomous code for a given side (left or right)
	 * @param position
	 */
	public void runSide(AutoPath position) {
		// Creates a state engine for the state of autonomous.
		switch(state) {
		case AUTO_FIRST_DIST:
			switch(path) {
			case SC:
				// If the robot has traveled the first distance (according to the encoders), go to the next step.
				if ((drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 > Scotstants.AUTO_SIDE_D1[0]*Scotstants.ROTATIONS_TO_FEET) {
					nextStep(AutoState.AUTO_FIRST_BREAK);
				}
				else {
					// Otherwise, drive the robot forward.
					drive.move(Scotstants.AUTO_MOVE_SPEED);
				}
				break;
				
			case SW:
				// If the robot has traveled the first distance (according to the encoders), go to the next step.
				if ((drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 > Scotstants.AUTO_SIDE_D1[1]*Scotstants.ROTATIONS_TO_FEET) {
					nextStep(AutoState.AUTO_FIRST_BREAK);
				}
				else {
					// Otherwise, drive the robot forward.
					drive.move(Scotstants.AUTO_MOVE_SPEED);
				}
				break;
				
			case LN:
				// If the robot has traveled the first distance (according to the encoders), go to the next step.
				if ((drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 > Scotstants.AUTO_SIDE_D1[2]*Scotstants.ROTATIONS_TO_FEET) {
					nextStep(AutoState.AUTO_FIRST_BREAK);
				}
				else {
					// Otherwise, drive the robot forward.
					drive.move(Scotstants.AUTO_MOVE_SPEED);
				}
				break;
			}
			
		case AUTO_FIRST_BREAK:
			// Checks if 0.5 seconds have passed since the last step ended.
			if (timer.get() >= 0.5) {
				switch(path) {
				// If the priority is on scale or switch, go to the next distance.
				case SC:
					nextStep(AutoState.AUTO_FIRST_TURN);
					break;
					
				case SW:
					nextStep(AutoState.AUTO_FIRST_TURN);
					break;
					
				// If the priority is on crossing the autonomous line, stop here.
				case LN:
					nextStep(AutoState.AUTO_FIFTH_BREAK);
					break;
				}
			}
			break;
			
		case AUTO_FIRST_TURN:
			// Begin to turn the robot the appropriate amount of degrees,
			// then if it's done turning, move to the next step.
			switch(position) {
			case LEFT:
				// Turns the robot, then checks if it has finished turning.
				if (drive.turn(Scotstants.AUTO_SIDE_TURN[0], Scotstants.AUTO_TURN_SPEED)) {
					nextStep(AutoState.AUTO_SECOND_BREAK);
				}
				break;
			case RIGHT:
				if (drive.turn(Scotstants.AUTO_SIDE_TURN[1], Scotstants.AUTO_TURN_SPEED)) {
					nextStep(AutoState.AUTO_SECOND_BREAK);
				}
				break;
			case CENTER:
				// If something went wrong, print to the DS and stop the code.
				System.out.println("ERROR: Incorrect autonomous selected.");
				timer.stop();
				drive.stop();
				break;
			}
			
		case AUTO_SECOND_BREAK:
			// If 0.5 seconds have passed since the last step ended, move to the next step.
			if (timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_RAISE_ARM);
			}
			break;
			
		case AUTO_RAISE_ARM:
			switch(path) {
			// If the priority is on the scale, raise the arm to the scale height and then go to the next step.
			case SC:
				if (lifter.atScale()) {
					nextStep(AutoState.AUTO_THIRD_BREAK);
				}
				else {
					lifter.operate(1);
				}
				break;
				
			// If the priority is on the switch, raise the arm to the switch height and then go the next step.
			case SW:
				if (lifter.atSwitch()) {
					nextStep(AutoState.AUTO_THIRD_BREAK);
				}
				else {
					lifter.operate(1);
				}
				break;
				
			case LN:
				// If the code somehow got to this point, print to the DS and stop the code.
				System.out.println("ERROR: Autonomous Sequence Failed.");
				nextStep(AutoState.AUTO_FIFTH_BREAK);
				break;
			}
			
		case AUTO_THIRD_BREAK:
			// If 0.5 seconds have passed since the last step ended, move to the next step
			if (timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_SECOND_DIST);
			}
			break;
			
		case AUTO_SECOND_DIST:
			switch(path) {
			case SC:
				// Goes to the next step if the robot has traveled the first distance according to the encoders.
				if ((drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 > Scotstants.AUTO_SIDE_D2[0]*Scotstants.ROTATIONS_TO_FEET) {
					nextStep(AutoState.AUTO_FOURTH_BREAK);
				}
				else {
					// Otherwise, drives the robot forward.
					drive.move(Scotstants.AUTO_MOVE_SPEED);
				}
				break;
				
			case SW:
				// Goes to the next step if the robot has traveled the first distance according to the encoders.
				if ((drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 > Scotstants.AUTO_SIDE_D2[1]*Scotstants.ROTATIONS_TO_FEET) {
					nextStep(AutoState.AUTO_FOURTH_BREAK);
				}
				else {
					// Otherwise, drive the robot forward.
					drive.move(Scotstants.AUTO_MOVE_SPEED);
				}
				break;
				
			case LN:
				System.out.println("ERROR: Autonomous Sequence Failed.");
				nextStep(AutoState.AUTO_FIFTH_BREAK);
				break;
			}
			
		case AUTO_FOURTH_BREAK:
			// If 0.5 seconds have passed since the last step ended, move to the next step.
			if (timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_DELIVER_CUBE);
			}
			break;
			
		case AUTO_DELIVER_CUBE:
//			if (timer.get() >= 2) {
//				// If two seconds have passed since this step started, move to the next step.
				nextStep(AutoState.AUTO_FIFTH_BREAK);
//				intake.stop();
//			}
//			else {
//				// Otherwise, run the intake so that the cube is spit out.
//				intake.run(1);
//			}
			break;
		
		case AUTO_FIFTH_BREAK:
			// Stop the timer and stop the robot from moving.
			// This is the final step in the side autonomous sequence!
			timer.stop();
			drive.stop();
			break;
		
		case AUTO_SIXTH_BREAK:
			// If there was an error and the code reached this step, print to the DS and stop the code.
			System.out.println("ERROR: Out of given autonomous sequence.");
			drive.stop();
			timer.stop();
			break;
			
		case AUTO_SECOND_TURN:
			// If there was an error and the code reached this step, print to the DS and stop the code.
			System.out.println("ERROR: Out of given autonomous sequence.");
			drive.stop();
			timer.stop();
			break;
			
		case AUTO_SEVENTH_BREAK:
			// If there was an error and the code reached this step, print to the DS and stop the code.
			System.out.println("ERROR: Out of given autonomous sequence.");
			drive.stop();
			timer.stop();
			break;
			
		case AUTO_THIRD_DIST:
			// If there was an error and the code reached this step, printo to the DS and stop the code.
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
				drive.move(Scotstants.AUTO_MOVE_SPEED);
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
					if (drive.turn(AUTO_CENTER_TURN[0], AUTO_TURN_SPEED)) {
						nextStep(AutoState.AUTO_SECOND_BREAK);
					}
					
			}
			else {
				if(drive.turn(-AUTO_CENTER_TURN[0], AUTO_TURN_SPEED)) {
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
				drive.move(Scotstants.AUTO_MOVE_SPEED);}
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
				if (drive.turn(AUTO_CENTER_TURN[1], AUTO_TURN_SPEED)) {
					nextStep(AutoState.AUTO_FOURTH_BREAK);
				}
				
		}
		else {
			if(drive.turn(-AUTO_CENTER_TURN[1], AUTO_TURN_SPEED)) {
				nextStep(AutoState.AUTO_FOURTH_BREAK);
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
				lifter.operate(1);
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
				drive.move(Scotstants.AUTO_MOVE_SPEED);
			}
			else{
				nextStep(AutoState.AUTO_SIXTH_BREAK);
			}
			break;
			
		// 
		case AUTO_SIXTH_BREAK:
			if(timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_DELIVER_CUBE);
			}
			break;
			
		// Runs the intake system for 2 seconds to deliver a cube
		case AUTO_DELIVER_CUBE:
//			if(timer.get() >= 2) {
				nextStep(AutoState.AUTO_SEVENTH_BREAK);
//				intake.stop();
//			}
//			else {
//				intake.run(1);
//			}
		
		case AUTO_SEVENTH_BREAK:
			// Stop the robot and stay where it is until teleop.
			// This is the final step in autonomous!
			drive.stop();
			timer.stop();
		}
	}
	
	/**
	 * Resets all necessary sensors and moves the autonomous progression to the given step.
	 * @param step
	 */
	public void nextStep(AutoState step) {
		drive.resetGyro();
		drive.resetEncoders();
		state = step;
		drive.stop();
		timer.reset();
		timer.start();
		lifter.stop();
		intake.stop();
	}
	
	/**
	 * Resets the autonomous progression.
	 */
	public void reset() {
		// Resets all necessary sensors for the first step in autnomous.
		nextStep(AutoState.AUTO_FIRST_DIST);
		// Sets the values for PIDf for driving.
		drive.pidControl(0.27, 0.0225, 0.0025, 0);
	}
	
}

package org.usfirst.frc.team7118.robot;

import org.usfirst.frc.team7118.robot.Scotstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Auto implements Scotstants {
	Drive drive;
	Intake intake;
	Lifter lifter;
	char switchPos, scalePos;
	AutoState state;
	Timer timer;
	
	/**
	 * Constructs the autonomous class
	 * @param drive
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
		AUTO_THIRD_DIST,
		AUTO_FIFTH_BREAK,
		AUTO_RAISE_ARM,
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
		switch(state) {
		case AUTO_FIRST_DIST:
			if ((drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 >= Scotstants.AUTO_SIDE_DIST[0]) {
				nextStep(AutoState.AUTO_FIRST_BREAK);
			}
			else {
				drive.move(Scotstants.AUTO_MOVE_SPEED);
			}
			break;
		case AUTO_FIRST_BREAK:
			if (timer.get() >= 0.5) {
				switch(position) {
				case LEFT:
					if (scalePos == 'L') {
						nextStep(AutoState.AUTO_SECOND_DIST);
					}
					break;
				case RIGHT:
					if (scalePos == 'R') {
						nextStep(AutoState.AUTO_SECOND_DIST);
					}
					break;
				case CENTER:
					break;
				}
			}
			break;
		case AUTO_SECOND_DIST:
			if ((drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 >= Scotstants.AUTO_SIDE_DIST[1]) {
				nextStep(AutoState.AUTO_SECOND_BREAK);
			}
			else {
				drive.move(Scotstants.AUTO_MOVE_SPEED);
			}
			break;
		case AUTO_SECOND_BREAK:
			if (timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_FIRST_TURN);
			}
			break;
		case AUTO_FIRST_TURN:
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
				break;
			}
		case AUTO_THIRD_BREAK:
			if (timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_RAISE_ARM);
			}
			break;
		case AUTO_RAISE_ARM:
			if (lifter.atScale()) {
				nextStep(AutoState.AUTO_FOURTH_BREAK);
			}
			else {
				lifter.operate(Scotstants.AUTO_LIFTING_SPEED);
			}
			break;
		case AUTO_FOURTH_BREAK:
			if (timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_THIRD_DIST);
			}
			break;
		case AUTO_THIRD_DIST:
			if ((drive.getNormalizedPositionL() + drive.getNormalizedPositionR())/2 >= Scotstants.AUTO_SIDE_DIST[2]) {
				nextStep(AutoState.AUTO_FIFTH_BREAK);
			}
			else {
				drive.move(Scotstants.AUTO_MOVE_SPEED);
			}
			break;
		case AUTO_FIFTH_BREAK:
			if (timer.get() >= 0.5) {
				nextStep(AutoState.AUTO_DELIVER_CUBE);
			}
			break;
		case AUTO_DELIVER_CUBE:
			if (timer.get() >= 2) {
				nextStep(AutoState.AUTO_SIXTH_BREAK);
			}
			else {
				intake.run(Scotstants.AUTO_INTAKE_SPEED);
			}
			break;
		case AUTO_SIXTH_BREAK:
			timer.stop();
			break;
		case AUTO_SECOND_TURN:
			System.out.println("ERROR: Out of given autonomous sequence");
			drive.move(0);
			break;
		case AUTO_SEVENTH_BREAK:
			System.out.println("ERROR: Out of given autonomous sequence");
			drive.move(0);
			break;
		}
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
			drive.move(Scotstants.AUTO_MOVE_SPEED);}
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
			
			nextStep(AutoState.AUTO_RAISE_ARM);
				break;
				
				
		case AUTO_RAISE_ARM:
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

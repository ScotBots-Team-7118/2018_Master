package org.usfirst.frc.team7118.robot;

import org.usfirst.frc.team7118.robot.Scotstants;
import edu.wpi.first.wpilibj.DriverStation;

public class Auto {
	Drive drive;
	
	int side = 0;
	boolean next = false;

	public enum Autostate {
		AUTO_FIRST_DIST,
		// actual dist = 10.4, give 10 instead to give leeway

		// dist = 10

		AUTO_FIRST_BREAK,
		// break

		AUTO_FIRST_TURN,
		// 90 degrees (left side)/270 degrees (right side)
		

		AUTO_SECOND_BREAK,
		// break

		AUTO_SECOND_DIST,
		// dist = 4

		AUTO_THIRD_BREAK,
		// break

		AUTO_SECOND_TURN,
		// 90 degrees (left side)/270 degrees (right side)
		

		AUTO_FOURTH_BREAK,
		// break

		AUTO_THIRD_DIST,
		// dist = 4

		AUTO_FIFTH_BREAK,
		// break

		AUTO_RAISE_SWITCH,
		// raise arms to: switch fence hight + 1
		// (two feet?)

		AUTO_SIXTH_BREAK,
		// break

		AUTO_DELIVER_CUBE,
		// reverse intake motors to eject cube (go slow)

		AUTO_SEVENTH_BREAK
		// break

		// end autonomous
	}

	//center auto state machine
	
	Autostate state = Autostate.AUTO_FIRST_DIST;

	public void runCenter() {
		
		//path chooser for center
		String arrangement = null;
		if (String.valueOf(arrangement.charAt(1)) == "L") {
			side = 0;
		} else {
			side = 1;
		}

		
		switch (state) {
		case AUTO_FIRST_DIST:
			// actual dist = 10.4, give 10 instead to give leeway
			
			//rough
			if(!drive.moveLength(124.8 ,.1)) {
				state = Autostate.AUTO_FIRST_BREAK;
			}
			break;
			
			
		case AUTO_FIRST_BREAK:
			// break

			drive.stop(true);
			
			
				state = Autostate.AUTO_FIRST_TURN;
			break;

			
		case AUTO_FIRST_TURN:
			// 90 degrees (left side)/270 degrees (right side)
			
			
			if (side == 0) {
				drive.turn(-90, .1);
				
			}else {
				drive.turn(90, .1);
			}
			
			state = Autostate.AUTO_SECOND_BREAK;
			break;

			
		case AUTO_SECOND_BREAK:
			// break

			drive.stop(true);
			
			state = Autostate.AUTO_SECOND_DIST;
			break;

			
		case AUTO_SECOND_DIST:
			// dist = 4

			drive.moveLength(drive.distIN(48), .1);
			
			state = Autostate.AUTO_THIRD_BREAK;
			break;

			
		case AUTO_THIRD_BREAK:
			// break

			drive.stop(true);
			
		
			state = Autostate.AUTO_SECOND_TURN;
			
			break;
			
			
		case AUTO_SECOND_TURN:
			// 90 degrees (left side)/270 degrees (right side)

			
			if (side == 0) {
				drive.turn(-90, 0.1);
			}else {
				drive.turn(90,  0.1);
			}
			
			state = Autostate.AUTO_FOURTH_BREAK;
			break;

			
		case AUTO_FOURTH_BREAK:
			// break
			
			drive.stop(true);
			
			state = Autostate.AUTO_THIRD_DIST;
			break;
			
			
		case AUTO_THIRD_DIST:
			// dist = 4
			
			drive.moveLength(48, 0.1);
			
			state = Autostate.AUTO_FIFTH_BREAK;
			break;
			
			
		case AUTO_FIFTH_BREAK:
			// break
			
			drive.stop(true);
			
			state= Autostate.AUTO_RAISE_SWITCH;
				break;
				
				
		case AUTO_RAISE_SWITCH:
			// raise arms to: switch fence hight + 1
			// (two feet?)
			
			
			
			state = Autostate.AUTO_SIXTH_BREAK;
				break;
				
				
		case AUTO_SIXTH_BREAK:
			// break
			
			
			
			state = Autostate.AUTO_DELIVER_CUBE;
				break;
				
				
		case AUTO_DELIVER_CUBE:
			// reverse intake motors to eject cube (go slow)
			
			
			
			state =  Autostate.AUTO_SEVENTH_BREAK;
				break;
				
				
		case AUTO_SEVENTH_BREAK:
			// break
			
			
			
			
			// end autonomous
		}
	}
}

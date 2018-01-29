package org.usfirst.frc.team7118.robot;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Joystick;

public interface Scotstants {
	public final int TALON_LM_PORT = 0;
	public final int TALON_LF_PORT = 1;
	public final int TALON_RM_PORT = 2;
	public final int TALON_RF_PORT = 3;
	public final int JOYSTICK_PORT = 0;
	public final int INTAL_R_PORT = //intake talon port
	public final int INTAL_L_PORT = //intake talon port
	final int PORT_L = //lifter port#
	final int PORT_RM = 2; // Right master CIM port
	final int PORT_RF = 5; // Right follower CIM port

	final int PORT_LM = 0; // Left master CIM port
	final int PORT_LF = 1; // Left follower CIM port

	final int PORT_ENC_R1 = 1; // Right encoder first port
	final int PORT_ENC_R2 = 2; // Right encoder second port

	final int PORT_ENC_L1 = 7; // Left encoder first port
	final int PORT_ENC_L2 = 8; // Left encoder second port

	final double STANDARD_SPEED = .3;
	final double WEAK_SPEED = -.5;
	final double STRONG_SPEED = .5;
	final int PORT_GYRO = 9;

	final int PORT_JOY_R = 1;// Joystick R's Port
	final int PORT_JOY_L = 2;// Joystick L's Port

	final double PI = 3.141; // Variable equal to pi
	final double DIS_TO_AUTO_LINE = 120; // Distance in inches to the auto line
	final double DIS_TO_SWITCH = 168; // Distance in inches to the middle of the switch final
	double DIS_TO_SCALE = 324; // Distance in inches to the middle of the scale
	final double WHEEL_DIAMETER = ; // Distance in inches of wheel diameter
	final double WHEEL_CIRCUM = WHEEL_DIAMETER * PI; // Distance in inches ofwheel circumference
	final double ROT_TO_AUTO_LINE = DIS_TO_AUTO_LINE / WHEEL_CIRCUM; // Number of rotations to the autoline
	final double ROT_TO_SWITCH = DIS_TO_SWITCH / WHEEL_CIRCUM; // Number of rotations to the middle of the switch
	final double ROT_TO_SCALE = DIS_TO_SCALE / WHEEL_CIRCUM; // Number of rotations to the middle of the scale }

}

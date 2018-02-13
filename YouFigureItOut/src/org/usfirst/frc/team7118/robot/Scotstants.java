package org.usfirst.frc.team7118.robot;

public interface Scotstants {
	// Drive Train Talon Ports
	public final int TALON_LM_PORT = 4;
	public final int TALON_LF_PORT = 5;
	public final int TALON_RM_PORT = 7;
	public final int TALON_RF_PORT = 8;
	// Intake Talon Ports
	public final int TALON_IR_PORT = 4;
	public final int TALON_IL_PORT = 5;
	// Lifter Talon Port
	public final int TALON_ML_PORT = 6;
	//Joystick Port
	public final int JOYSTICK_L_PORT = 0;
	public final int JOYSTICK_R_PORT = 1;
	// Right encoder first port
	public final int ENC_R1_PORT = 1;
	// Right encoder second port
	public final int ENC_R2_PORT = 2;
	// Left encoder first port
	public final int ENC_L1_PORT = 7;
	// Left encoder second port
	public final int ENC_L2_PORT = 8;
	// Gyroscope port
	public final int GYROSCOPE_PORT = 9;

	public final double STANDARD_SPEED = 0.3;
	public final double WEAK_SPEED = -0.5;
	public final double STRONG_SPEED = 0.5;

	// Distance in inches to the auto line
	public final double DIS_TO_AUTO_LINE = 120;
	// Distance in inches to the middle of the switch final
	public final double DIS_TO_SWITCH = 168;
	// Distance in inches to the middle of the scale
	public final double DIS_TO_SCALE = 324;
	
	public final int AUTO_DEFAULT = 0, AUTO_L = 1, AUTO_C = 2, AUTO_R = 3;
}

package org.usfirst.frc.team7118.robot;

public interface Scotstants {
	// Talon Port Numbers
	public final int TALON_LM_PORT = 7;
	public final int TALON_LF_PORT = 8;
	public final int TALON_RM_PORT = 4;
	public final int TALON_RF_PORT = 5;
	public final int TALON_A1_PORT = 0;
	public final int TALON_A2_PORT = 0;
	public final int TALON_IR_PORT = 0;
	public final int TALON_IL_PORT = 0;

	// Joystick Port Numbers
	public final int JOY_R_PORT = 1;
	public final int JOY_L_PORT = 0;
	
	// Constants for the turn method
	public final double MAX_DEGREES_FULL_SPEED = 5.0;
	public final double TURN_OFFSET = 3;
	public final double MIN_TURN_SPEED = 0.18;
	public final long ENCODER_ROTATION_DIFF = 0;
	
	// Constants for Autonomous
	public final int AUTO_CENTER_DIST[] = {10, 4, 4};
	public enum AutoPath {CENTER, LEFT, RIGHT};
	public final double AUTO_SPEED = 0.2;
	
	// The amount of rotations in a single foot
	public final double ROTATIONS_TO_FEET = 0;
}

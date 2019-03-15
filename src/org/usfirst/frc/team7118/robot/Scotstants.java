package org.usfirst.frc.team7118.robot;

public interface Scotstants {
	// Talon Port Numbers
	public final int TALON_LM_PORT = 7;
	public final int TALON_LF_PORT = 8;
	public final int TALON_RM_PORT = 4;
	public final int TALON_RF_PORT = 5;
	public final int TALON_A1_PORT = 1;
	public final int TALON_A2_PORT = 9;
	public final int TALON_IR_PORT = 3;
	public final int TALON_IL_PORT = 2;

	// Joystick Port Numbers
	public final int JOY_R_PORT = 1;
	public final int JOY_L_PORT = 0;
	
	// Constants for the turn method
	public final double MAX_DEGREES_FULL_SPEED = 5.0;
	public final double TURN_OFFSET = 3;
	public final double MIN_TURN_SPEED = 0.18;
	public final long ENCODER_ROTATION_DIFF = 0;
	
	// Constants for acceleration
	public final double HERTZ = 50;
	public final double ACCELERATION_TIME_SECONDS = 1.2; 
	public final double MAX_POWER_CHANGE = 1 / (ACCELERATION_TIME_SECONDS * HERTZ);
	
	// Constants for Autonomous
	public final int AUTO_CENTER_DIST[] = {7, 4, 4};
	public enum AutoPath {CENTER, LEFT, RIGHT};
	public final double AUTO_MOVE_SPEED = 0.3;
	public final double AUTO_SIDE_D1[] = {26.4, 17};
	public final double AUTO_SIDE_D2[] = {1,2};
	public final double AUTO_SIDE_TURN[] = {-85.5, 85.5};
	public final double AUTO_CENTER_TURN[] = {-85.5 , 85.5};
	
	// Automatic lifting speed for the telescoping arms
	public final double AUTO_LIFTING_SPEED = 0.8;
	
	// Automatic intake/outake speek for the intake system
	public final double AUTO_INTAKE_SPEED = 1.0;
	
	public final double AUTO_TURN_SPEED = 0.2;
	
	// Angular deadzone for drive.gyroDrive(v)
	public final double GYRO_DEAD_ZONE = 0.15;
	
	// Joystick deadzone constant
	public final double JOYSTICK_DEADZONE = 0.2;
	
	// The amount of rotations in a single foot
	public final double ROTATIONS_TO_FEET = 639.75;

	
}

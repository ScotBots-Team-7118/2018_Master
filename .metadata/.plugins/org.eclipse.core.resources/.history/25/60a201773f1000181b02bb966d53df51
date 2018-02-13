package org.usfirst.frc.team7118.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;

public class Sensors implements Scotstants {
	Encoder encL, encR, encU;
	AnalogGyro gyro;

	/**
	 * Constructor for sensors
	 */ 
	public Sensors() {
		// Initializing encoders
		encR = new Encoder(PORT_ENC_R1, PORT_ENC_R2, false); // Right encoder(Will upload with port #s when we can merge)
		encL = new Encoder(PORT_ ENC_L1, PORT_ENC_L2, false); // Left encoder(Will upload with port #s when we can merge)
		encU = new Encoder(0,2, false);// port #s are just random #'s on this one will create a constant soon

		// Initializing Gyros
		gyro = new AnalogGyro(PORT_GYRO);
	}
}
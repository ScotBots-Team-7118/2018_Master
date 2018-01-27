package org.usfirst.frc.team7118.robot;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Encoder;
public class Lifting {
	Lift_Path lift_path;
	Drive drive;
	Sensors sensors;
	TalonSRX talLift;
	boolean ground = false;
	boolean cwitch = false;
	boolean scale = false;
	
	public Lifting() {
		talLift = new TalonSRX(4);
		
	}
	public enum Lift_Path{
		Scale,Switch,Ground
		
	}
public void lift() {
	switch(lift_path) {
	case Scale:
		if(joyR.getRawButton(4) && ground) {
			talLift.set(ControlMode.PercentOutput,0.3);
			if(sensors.encU.get() >= 20) {
				talLift.set(ControlMode.PercentOutput,0);
				scale = true;
				}// when the arm gets to the number of rotations to the scale the motor and the arms will stop
			}
		if(joyR.getRawButton(4) && cwitch) {
			talLift.set(ControlMode.PercentOutput,0.3);
			if(sensors.encU.get() >= 10) {
				talLift.set(ControlMode.PercentOutput,0);
				scale = true;
				}// when the arm gets to the number of rotations to the scale the motor and the arms will stop
			}
		break;
	case Switch:
		if(joyR.getRawButton(5) && scale) {
			talLift.set(ControlMode.PercentOutput, -0.3);
			if(sensors.encU.get())
		}
		
		
	}
	
	
	
	}
}

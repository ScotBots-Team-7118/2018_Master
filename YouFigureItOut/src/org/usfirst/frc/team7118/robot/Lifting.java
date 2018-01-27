package org.usfirst.frc.team7118.robot;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Encoder;
public class Lifting {
	LiftState lift_path;
	Drive drive;
	Sensors sensors;
	TalonSRX talLift;
	boolean ground = false;
	boolean cwitch = false;
	boolean scale = false;
	boolean lastButtonS = false;
	boolean lastButtonSc = false;
	boolean lastButtonG = false;
	LiftState currentState;
	LiftState targetState;
	
	
	public Lifting() {
		talLift = new TalonSRX(4);
		
	}
	public enum LiftState{
		Scale,Switch,Ground,Moving
	};
	
	private boolean getButtonState(LiftState whichOne)
	{
		if (whichOne == LiftState.Scale) {
			return joyR.getRawButton(4);
		}else if (whichOne == LiftState.Switch) {
			return joyR.getRawButton(5);
		}else if (whichOne == LiftState.Ground) {
			return joyR.getRawButton(2);
		}
	}
	
	public void init() {
		lastButtonS = getButtonState(LiftState.Scale);
		lastButtonSc = getButtonState(LiftState.Switch);
		lastButtonG = getButtonState(LiftState.Ground);
	}
	
public void lift() {
	
	boolean currentButtonS = getButtonState(LiftState.Scale);
	boolean currentButtonsc = getButtonState(LiftState.Switch);
	if(currentButtonS != lastButtonS) {
		
		lastButtonS = currentButtonS;
	}
	
	
	
	lastButtonSc = getButtonState(LiftState.Switch);
	lastButtonG = getButtonState(LiftState.Ground);
	if (joyR.getRawButton(2) == true && lastButtonG ==false){
		;
	}
	

	
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
			if(sensors.encU.get()>= 10)
		}
		
		
	}
	
	
	
	}
}

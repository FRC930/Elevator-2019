
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
 
  Solenoid piston = new Solenoid(0);
  TalonSRX motor = new TalonSRX(0);
  Joystick stick = new Joystick(0);

  @Override
  public void robotInit() {
   
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  
  }

  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopPeriodic() {

    if (stick.getRawButton(1)) {
      piston.set(true);
    } else {
      piston.set(false);
    }
    
  }

  @Override
  public void testPeriodic() {
  }
}

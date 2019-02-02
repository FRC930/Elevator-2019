
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
 
  private final static Solenoid piston = new Solenoid(0);
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

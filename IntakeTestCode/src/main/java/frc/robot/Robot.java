
//-------- Imports --------\\

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
 
  //-------- Objects --------\\

  private final static Solenoid hatchPiston = new Solenoid(0);
  private Joystick coDriverStick = new Joystick(1);

  //-------- Methods --------\\

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

    //coDriverStick.getRawButton(6) will return a true or false value of the
    //co driver's right shoulder button. It is true when the shoulder button is down, and false if it is up.

    //hatchPiston.set sets the piston's state. If it is true, the piston will be pushed out. If it is false, 
    //the piston will be retracted. When the piston is pushed out, the piston will grab the hatch

    //overall, the piston will be set to the button's state. if the buttton is down, it is true, which then
    //sets the piston to true, which will push out the piston. when the piston is true, we will grab the hatch

    hatchPiston.set(coDriverStick.getRawButton(6));  
  }

  @Override
  public void testPeriodic() {
  }
}

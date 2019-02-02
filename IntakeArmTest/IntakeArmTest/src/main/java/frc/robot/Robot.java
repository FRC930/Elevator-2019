/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


/*

//===== OVERALL EXPLANATION =====||

GOAL(S):
  Arm is retracted on start.
  One button is used to extend and retract the arm.

BUTTON(S) USED:
  Button 5: Left Bumper (LB)

*/


//===== Imports =====||

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Solenoid;

public class Robot extends TimedRobot {

//===== Variables =====||

  public static Joystick CoDriverController = new Joystick(1); //Declaring to codriver and calling the controller.
  public static boolean armStatus = true; //Button control.
  public final static Solenoid armPiston = new Solenoid(0); //Declaring the arm piston.
  public static boolean armActivity = true; //Arm piston control.


  @Override
  public void robotInit() {
    armPiston.set(armActivity);
      /*
      On startup, the piston will extend, pulling the arm in.
      */
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

    
//===== Button Press Statement =====||

    if(CoDriverController.getRawButton(5) == true && armStatus == false)
    {
      armStatus = true;
      /*
      If LB is pressed and the button control is false, set button control true.
      */
    }
    if(CoDriverController.getRawButton(5) == false && armStatus == true)
    {
      armStatus = false;
      armActivity = !armActivity;
      /*
      If LB is pressed and the button control is true, set button control false and set armActivity opposite to itself.
      */
    }

//===== Piston Control =====||

    armPiston.set(armActivity);
      /*
      When armActivity = false, piston retracts, pushing the arm out.
      When armActivity = true, piston extends, pulling the arm in.
      */

  }

  @Override
  public void testPeriodic() {
  }
}

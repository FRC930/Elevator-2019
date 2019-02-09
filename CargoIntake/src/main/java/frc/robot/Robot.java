/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*

//===== OVERALL EXPLANATION =====||

GOAL(S):
  Hand is up on start.
  One button is used for intake and another for outtake of cargo.

BUTTON(S) USED:
  Button 1: A
  Button 2: B

*/

//===== Imports =====||

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {

//===== Variables =====||

  public static Joystick stick = new Joystick(1); //Codriver's controller.
    public static int Button_A = 1; //Button A on controller.
    public static int Button_B = 2; //Button B on controller.
  public final static Solenoid handPiston = new Solenoid(1); //Hand piston control.
    public static boolean handActivity = false; //Hand piston control.
  public static TalonSRX IntakeCargo = new TalonSRX(1); //Wheel control.
    public static double intakeSpeed = 1; //Wheel speed during intake.
    public static double outtakeSpeed = -1; //Wheel speed during outtake.
    public static double stopSpeed = 0; //Reset of wheel speed.

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

//===== Intake & Outtake =====||

    if(stick.getRawButton(Button_A) == true) //Motor control sets speed for inttake. Hand is out.
    {
      IntakeCargo.set(ControlMode.PercentOutput, intakeSpeed);
      handActivity = false;
    }
    else if(stick.getRawButton(Button_B) == true) //Motor control sets speed for outtake. Hand is out.
    {
      IntakeCargo.set(ControlMode.PercentOutput, outtakeSpeed);
      handActivity = false;
    }
    else //Motor control sets speed to stop. Hand is held up.
    {
      IntakeCargo.set(ControlMode.PercentOutput, stopSpeed);
      handActivity = true;
    }

//===== Piston Control =====||

    handPiston.set(handActivity);
      /*
      When handActivity = false, piston retracts, letting the hand hang.
      When handActivity = true, piston extends, holding the hand up.
      */

  }

  @Override
  public void testPeriodic() {

  }
}

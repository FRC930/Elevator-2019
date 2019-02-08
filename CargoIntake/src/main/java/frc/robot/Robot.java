/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//===== Imports =====||

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Counter.Mode;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Solenoid;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {

//===== Variables =====||

  public static Joystick stick = new Joystick(1); //Codriver.
  public static TalonSRX IntakeCargo = new TalonSRX(1);
  public final static Solenoid handPiston = new Solenoid(1);
  public static boolean handActivity = false; //Hand piston control.
  public static double intakeSpeed = 1;
  public static double outtakeSpeed = -1;
  public static double cargoStop = 0;

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

    if(stick.getRawButton(1) == true) //Intake
    {
      IntakeCargo.set(ControlMode.PercentOutput, intakeSpeed);
      handActivity = true;
    }
    else if(stick.getRawButton(2) == true) //Outtake
    {
      IntakeCargo.set(ControlMode.PercentOutput, outtakeSpeed);
      handActivity = true;
    }
    else //Reset
    {
      IntakeCargo.set(ControlMode.PercentOutput, cargoStop);
      handActivity = false;
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

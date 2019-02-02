/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.sun.tools.classfile.TypeAnnotation.Position;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
     public static TalonSRX lift1 = new TalonSRX(1);
     public static TalonSRX lift2 = new TalonSRX(2);
     public static double LevelOneCargo;
     public static double LevelTwoCargo;
     public static double LevelThreeCargo;
     public static double LevelOneHatch;
     public static double LevelTwoHatch;
     public static double LevelThreeHatch;
     public static double CargoShipCargo;
     public static double PlayerStation;

     public static Joystick stick = new Joystick(0);
     private static double TargetPostition;
    @Override
   public void robotInit(){
    lift2.follow(lift1);
    lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    lift1.configForwardSoftLimitThreshold(8300, 10);
    lift1.configReverseSoftLimitThreshold(10, 10);
    lift1.config_kF(0, 0, 10);
		lift1.config_kP(0, 2.0, 10);
		lift1.config_kI(0, 0, 10);
    lift1.config_kD(0, 0, 10);
    lift1.configMotionCruiseVelocity(1200, 10);
		lift1.configMotionAcceleration(1500, 10);
   }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //Rocket level 1 cargo
    if (stick.getRawButton(1) == true){
      TargetPostition = LevelOneCargo;

    }


    //Rocket level 2 cargo
    else if (stick.getRawButton(2) == true){
      TargetPostition = LevelTwoCargo;


    }

    //Rocket level 3 cargo
    else if (stick.getRawButton(3) == true){
      TargetPostition = LevelThreeCargo;

    }

    //Rocket level 1 hatch
    else if (stick.getRawButton(4) == true){
      TargetPostition = LevelOneHatch;


    }

    //Rocket level 2 hatch
    else if (stick.getPOV() == 0){
      TargetPostition = LevelTwoHatch;

    }

    //Rocket level 3 hatch
    else if (stick.getPOV() == 90){
      TargetPostition = LevelThreeHatch;

    }
    // Player Station Stuff
    else if (stick.getPOV() == 180){
      TargetPostition = PlayerStation;


    }
    //Cargo Ship Cargo
    else if(stick.getPOV() == 270) {
      TargetPostition = CargoShipCargo;


    }
  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}

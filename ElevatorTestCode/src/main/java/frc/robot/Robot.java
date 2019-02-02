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
import com.sun.tools.javac.jvm.Target;

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
     public static double LevelOneHatch_PlayerStation;
     public static double LevelTwoHatch;
     public static double LevelThreeHatch;
     public static double CargoShipCargo;
     public static double ElevatorReset;

     public static Joystick stick = new Joystick(0);
     public static double TargetPosition;
    @Override
   public void robotInit(){
    //lift2 motor follows lift1
    lift2.follow(lift1);
    //talon gets info from encoder
    lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
   //sets limit of where it should go
    lift1.configForwardSoftLimitThreshold(8300, 10);
    lift1.configReverseSoftLimitThreshold(10, 10);
    //sets up the  fpid for pid functions
    lift1.selectProfileSlot(0, 0);
    lift1.config_kF(0, 0, 10);
		lift1.config_kP(0, 0.1, 10);
		lift1.config_kI(0, 0, 10);
    lift1.config_kD(0, 0, 10);
    //CruiseVelocity is the no exceleration part of trapizoid / top Acceleration is getting to top
    lift1.configMotionCruiseVelocity(1200, 10);
    lift1.configMotionAcceleration(1500, 10);
    //Nominal out put is lowest limit and peak is highest    lift1.configNominalOutputForward(0, 10);
    lift1.configNominalOutputReverse(0, 10);
    lift1.configNominalOutputForward(0, 10);
		lift1.configPeakOutputForward(1, 10);
    lift1.configPeakOutputReverse(-1, 10);
    //sets the sensor to the bootom/0
    lift1.setSelectedSensorPosition(0, 0, 10);
    
    lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 10);
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);
    TargetPosition = 0;
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
    //Rocket level 1 cargo/button1 A
    if (stick.getRawButton(1) == true){
      TargetPosition = LevelOneCargo;
    }


    //Rocket level 2 cargo/button2 B
    else if (stick.getRawButton(2) == true){
      TargetPosition = LevelTwoCargo;


    }

    //Rocket level 3 cargo/button3 X
    else if (stick.getRawButton(3) == true){
      TargetPosition = LevelThreeCargo;

    }

    //Rocket level 1 hatch/button4 Y
    else if (stick.getRawButton(4) == true){
      TargetPosition = LevelOneHatch_PlayerStation;


    }

    //Rocket level 2 hatch/Dpad Up
    else if (stick.getPOV() == 0){
      TargetPosition = LevelTwoHatch;

     

    }

    //Rocket level 3 hatch/Dpad Right
    else if (stick.getPOV() == 90){
      TargetPosition = LevelThreeHatch;

    }
    // Cargo Ship Cargo/Dpad Down
    else if (stick.getPOV() == 180){
      TargetPosition = CargoShipCargo;


    }
    //Cargo Ship Cargo/Dpad Left
    else if(stick.getPOV() == 270) {
      TargetPosition = ElevatorReset;
   }
    lift1.set(ControlMode.MotionMagic, TargetPosition);
  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}

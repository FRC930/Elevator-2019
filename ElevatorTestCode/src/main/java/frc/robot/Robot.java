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
    //Sets up the  talons
    public static TalonSRX lift1 = new TalonSRX(1);
    public static TalonSRX lift2 = new TalonSRX(2);
    
    //Cargo level variable
    public static double LevelOneCargo;
    public static double LevelTwoCargo;
    public static double LevelThreeCargo;
    
    //Hatch levels variables 
    public static double LevelOneHatch_PlayerStation;
    public static double LevelTwoHatch;
    public static double LevelThreeHatch;
    
    //Cargo ship level variable
    public static double CargoShipCargo;
    
    //Resets the elevator variable
    public static double ElevatorReset;
    
    //Variable for the  RT LT pressing
    public static boolean RtPressed;
    public static boolean LtPressed;
    
    //Use CoDriver controller sets up a controller called stick
    public static Joystick stick = new Joystick(1);
    
    //Creates a target variable
    public static double TargetPosition;
    
    //kTimeout variable for a value used often
    public static int kTimeoutMs;
    
    //Variable to set actual position
    public static double ActualPosition;
    
    //Variable to check to see if the buttons are ready 
    public static boolean Lcheck;
    public static boolean Rcheck;
    
    //Variable to check to see if you can shoot
    public static boolean CanShoot;
    
    //Variable to get the axisY positon on the controller
    public static double AxisY;
    
    private static ElevatorStates stateEnum;

    @Override
   public void robotInit(){


    
    kTimeoutMs = 10;
    
    //lift2 motor follows lift1
    lift2.follow(lift1);
    
    //talon gets info from encoder
    lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
    
    //sets limit of where it should go
    lift1.configForwardSoftLimitThreshold(8300, kTimeoutMs);
    lift1.configReverseSoftLimitThreshold(10, kTimeoutMs);
    
    //sets up the  fpid for pid functions
    lift1.selectProfileSlot(0, 0);
    lift1.config_kF(0, 0, kTimeoutMs);
		lift1.config_kP(0, 0.1, kTimeoutMs);
		lift1.config_kI(0, 0, kTimeoutMs);
    lift1.config_kD(0, 0, kTimeoutMs);
    
    //CruiseVelocity is the no exceleration part of trapizoid / top Acceleration is getting to top
    lift1.configMotionCruiseVelocity(1200, 10);
    lift1.configMotionAcceleration(1500, 10);
    
    //Nominal out put is lowest limit and peak is highest    lift1.configNominalOutputForward(0, 10);
    lift1.configNominalOutputReverse(0, kTimeoutMs);
    lift1.configNominalOutputForward(0, kTimeoutMs);
		lift1.configPeakOutputForward(1, kTimeoutMs);
    lift1.configPeakOutputReverse(-1, kTimeoutMs);
    
    //sets the sensor to the bootom/0
    lift1.setSelectedSensorPosition(0, 0, kTimeoutMs);
    
    lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
    
    TargetPosition = 0;
    //setting booleans to false
    Lcheck = false;
    Rcheck = false;
    RtPressed = false;
    LtPressed = false;
    CanShoot = false;
    
    //setting levels for diffrent game pieces
    LevelOneCargo = 0;
    LevelOneHatch_PlayerStation = 0;
    LevelTwoCargo = 0;
    LevelTwoHatch= 0;
    LevelThreeCargo = 0;
    LevelThreeHatch = 0;
    CargoShipCargo = 0;
    ElevatorReset = 0;
    
    //sets actual position
    ActualPosition = lift1.getSelectedSensorPosition(0);
    
    //sets the value of axisY to the joystick value
    AxisY = stick.getRawAxis(1);
    

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
    if( Math.abs(AxisY) > 0.01){
      RunManual(AxisY);

    }
    //Button RT test to see if pressed and released
    if(stick.getRawAxis(3) >= 0.5 && RtPressed == false){
      RtPressed = true;
    }

    if(RtPressed == true && stick.getRawAxis(3) <= 0.5  ){
      RtPressed = false;
      Lcheck = false;
      Rcheck = !Rcheck;
    }

    //Button LT test to see if pressed and released 
    if(stick.getRawAxis(2) >= 0.5 && LtPressed == false){
      LtPressed = true;
    }

    if(LtPressed == true && stick.getRawAxis(2) <= 0.5  ){
      LtPressed = false;
      Rcheck = false;
      Lcheck = !Lcheck;
    }

    //check to see if it has been pressed the first time(true) or pressed a second time(false)
    if(Rcheck){
      
      //sends the enum rocklevelonecargo to the setTargetPos method when button 1(A) is pressed
      if (stick.getRawButton(1) == true){
        setTargetPos(ElevatorStates.RocketLevelOneCargo);
      }


      //sends the enum rockleveltwocargo to the setTargetPos method when button 2(B) is pressed
      else if (stick.getRawButton(2) == true){
        setTargetPos(ElevatorStates.RocketLevelTwoCargo);
      }

      //sends the enum rocketlevelthreecargo to the setTargetPos method when button 3(X) is  pressed
      else if (stick.getRawButton(3) == true){
        setTargetPos(ElevatorStates.RocketLevelThreeCargo);
      }

      //sends the enum rocklevelonehatch and player station to the setTargetPos method when button 4(Y) is pressed
      else if (stick.getRawButton(4) == true){
        setTargetPos(ElevatorStates.RocketLevelOneHatchAndPlayerStation);
      }
    }
    
    //check to see if it has been pressed the first time(true) or pressed a second time(false)
    else if(Lcheck){
      
      //sends the enum rocklevelonehatch to the setTargetPos method when button 1(A) is pressed
       if (stick.getRawButton(1) == true){
        setTargetPos(ElevatorStates.RocketLevelOneHatchAndPlayerStation);
      }

      //sends the enum rockleveltwohatch to the setTargetPos method when button 2(B) is pressed
      else if (stick.getRawButton(2) == true){
        setTargetPos(ElevatorStates.RocketLevelTwoHatch);
      }

      //sends the enum rocklevelthreehatch to the setTargetPos method whem button 3(X) is pressed
      else if (stick.getRawButton(3) == true){
        setTargetPos(ElevatorStates.RocketLevelThreeHatch);
      }
    }
   
    //sends the enum ResetElevator to the setTargetPos method when the right stick is pressed down
     if(stick.getRawButton(10) == true) {
      setTargetPos(ElevatorStates.ResetElevator);
   }
  }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  //makes sure the it is at the right position
  public static boolean AtPosition() {
    if(ActualPosition > (TargetPosition-10) && ActualPosition < (TargetPosition+10)){
      return true;
    }
    else{
      return false;
    }
  }

  //Making enums for elevator
  enum ElevatorStates {
      RocketLevelOneCargo,
      RocketLevelTwoCargo,
      RocketLevelThreeCargo,
      RocketLevelOneHatchAndPlayerStation,
      RocketLevelTwoHatch,
      RocketLevelThreeHatch,
      ResetElevator
  }

  //setting target position
  public static void setTargetPos(Enum pos1) {
    stateEnum = (ElevatorStates) pos1;

    //makes a swithc case to go to position
    switch(stateEnum){

      case RocketLevelOneCargo:
        TargetPosition = LevelOneCargo;
        break;
      case RocketLevelTwoCargo:
        TargetPosition = LevelTwoCargo;
        break;
      case RocketLevelThreeCargo:
        TargetPosition = LevelThreeCargo;
        break;
      case RocketLevelOneHatchAndPlayerStation:
        TargetPosition = LevelOneHatch_PlayerStation;
        break;
      case RocketLevelTwoHatch:
        TargetPosition = LevelTwoHatch;
        break;
      case RocketLevelThreeHatch:
        TargetPosition = LevelThreeHatch;
        break;
      case ResetElevator:
        TargetPosition = ElevatorReset;
        break;
      lift1.set(ControlMode.MotionMagic, TargetPosition);
    
    }


    }

    //runs manual motion magic
    public static void RunManual(double AxisY){
    AxisY = Math.pow(AxisY,3);
    TargetPosition += (AxisY * -400);
    
    if(TargetPosition > LevelThreeHatch ){
			TargetPosition = LevelThreeHatch;
    }
     else if (TargetPosition < ElevatorReset){
			TargetPosition = ElevatorReset;
		}

  }

  //checks to make sure the  encoder is working
  public static boolean CheckEncoder(){ 
    if(ActualPosition == 0 && TargetPosition != ElevatorReset){
      return false;
    }
    else{
      return true;
    }
  }
}
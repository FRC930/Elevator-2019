/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*

// --------- Hatch Intake General Goal(s) --------- \\

FUNCTION OF HATCH INTAKE:
---------------------------------------------------------------------------
The hatch intake system we are using is a row of multiple rubber wheels
that, once in position over a hatch, which spin inwards, consequently 
pulling the hatch inwards. The pressure between the rubber wheels and hatch
will hold the hatch plate snug under the holding wheels. The "hand" of
wheels holding the hatch will then rotate from the ground 90 degrees upwards 
so it is perpendicular to the ground and in position to be taken by the elevator
and placed at any of the three height positions.
bitboptasoiu
CONTROLLER BUTTONS USED:       | BUTTON NAME:   | BUTTON MAPPING:
------------------------       | ------------   | ---------------
All controls :                 | Left Bumper    | (Button 5)
*/

// --------- Imports --------- \\
package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Robot extends TimedRobot {

  // ---------- Constants --------- \\
  private static final int HATCH_FLOOR_SOLENOID = 0;
  private static final int HATCH_FLOOR_VICTOR = 8;
  public static final int ARM_SOLENOID_PORT = 0;
  public static final int HATCH_SOLENOID_PORT = 0;

  private static final int LEFT_BUMPER = 5;
  private static final double INTAKE_SPEED = 1.0;
  private static final double OUTTAKE_SPEED = -0.3;
  private static final double HATCH_CURRENT_LIMIT = 13.0;
  private static final int HATCH_PDP_VICTOR_CHANNEL = 8;
  private static final double HATCH_FLOOR_RAISE_WAITTIME = 1.0;

  // ------------ Objects ------------- \\
  private static final Solenoid hatchFloorIntakePistonController = new Solenoid(HATCH_FLOOR_SOLENOID);
  private static final VictorSPX hatchFloorIntakeVictorController = new VictorSPX(HATCH_FLOOR_VICTOR);

  private static final Joystick codriver = new Joystick(1);

  private static final Solenoid armPiston = new Solenoid(ARM_SOLENOID_PORT); // Beak arm for intake
  private static final Solenoid hatchPiston = new Solenoid(HATCH_SOLENOID_PORT); // piston that controls beak (open or closed)

  private static final PowerDistributionPanel PDP = new PowerDistributionPanel();   //power distribtion panel of robot

  private static Timer timeCount = new Timer(); //a timer, used to track time

  @Override
  public void robotInit() {
    timeCount.reset();
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

    // Checks to see if Left Bumper button is pressed by codriver controller
    if (codriver.getRawButton(LEFT_BUMPER)){

      // Checks to see if the elevator and beak intake is NOT at floor position
      if (!Elevator.atPosition(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation)) {
        // Moves the elevator to floor position to be ready to intake 
        Elevator.setTargetPos(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation);
      }

      //Checks to see if beak arm is NOT down 
      if (!armPiston.get()) {
        // Brings hatch beak arm down to position
        armPiston.set(true);
      }

      // Checks to see if beak is NOT closed
      if (hatchPiston.get()){
        // Closes beak so the hatch can be placed over the beak
        hatchPiston.set(false);
      }

      /*
      If all are set for intake, wheels intake hatch intohand
      */
      hatchFloorIntakeVictorController.set(ControlMode.PercentOutput, INTAKE_SPEED);

      /* Brings floor intake hand to ground using piston

      The '.set' method is what tells the Solenoid whether the piston should be set
      to true or false. When set to false, the piston is collapsed and not
      extended. When set to true, the piston is extended, meaning the hatch intake
      "hand" is set to a perpendicular position from the ground, which in theory
      should be in position to be picked up by the bird beak hatch holder on the
      elevator.
      
      Inside the () of the '.set' function is a boolean value, which we have set to
      the boolean value that is recieved from the controller button, which is
      either pressed (true) or unpressed (false).
      */
      hatchFloorIntakePistonController.set(true);

      //If the elevator is down, the arm on the elevator is down, the beak is closed, and there is a hatch in our floor hatch intake
      if (Elevator.atPosition(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation) && armPiston.get() && !hatchPiston.get() && (PDP.getCurrent(HATCH_PDP_VICTOR_CHANNEL) >= HATCH_CURRENT_LIMIT)) {

        //Raise the hatch floor intake to the beak
        hatchFloorIntakePistonController.set(false);

        //starts the timer
        timeCount.start();

        //Waits until the timer reaches a certain frame
        if (timeCount.get() >= HATCH_FLOOR_RAISE_WAITTIME) {
          // Opens Beak, which grabs hatch object
          hatchPiston.set(true);
          //Outtake the hatch floor intake rollers
          hatchFloorIntakeVictorController.set(ControlMode.PercentOutput, OUTTAKE_SPEED);
        }

        //if the beak is open and the floor intake arm is up
        if (hatchPiston.get() && !hatchFloorIntakePistonController.get()) {

          //move the floor hatch intake down t the ground as a reset for the next 
          // hatchFloorIntakePistonController.set(true);
          // Resets the timer to 0 to be ready for the next run of the hatch floor intake program
          timeCount.stop();
          timeCount.reset();
        }

      }

    }

  }

  @Override
  public void testPeriodic() {
  }
}

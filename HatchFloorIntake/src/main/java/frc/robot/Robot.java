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
Intake Wheels (Pull Hatch In): | X Button       | (Button 3)
Intake Wheels (Let Hatch Go):  | Y Button       | (Button 4)
From Ground to Elevator:       | Left Bumper    | (Button 5)
*/

// --------- Imports --------- \\
package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Robot extends TimedRobot {

  // ---------- Constants --------- \\
  private static final int HATCH_FLOOR_SOLENOID = 0;
  private static final int HATCH_FLOOR_VICTOR = 1;
  private static final int LEFT_BUMPER = 5;
  private static final double INTAKE_SPEED = 1.0;
  private static final double OUTTAKE_SPEED = -1.0;

  // ------------ Objects ------------- \\
  private static final Solenoid hatchFloorIntakePistonController = new Solenoid(HATCH_FLOOR_SOLENOID);
  private static final VictorSPX hatchFloorIntakeVictorController = new VictorSPX(HATCH_FLOOR_VICTOR);
  private static final Joystick codriver = new Joystick(1);
  private static final Solenoid armPiston = new Solenoid(0); // Beak arm for intake
  private static final Solenoid hatchPiston = new Solenoid(1); // piston that controls beak (open or closed)
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
    // Checks to see if Left Bumper is pressedd by codriver
    if(codriver.getRawButton(LEFT_BUMPER)){

      // Checks to see if the elevator and beak intake is NOT at floor position
      if(!Elevator.atPosition(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation)) {

        // Moves the elevator to floor position to be ready to intake 
        Elevator.setTargetPos(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation);

      }

      //Checks to see if beak arm is NOT down 
      if(!armPiston.get()) {

        // Brings hatch beak arm down to position
        armPiston.set(true);

      }

      // Checks to see if beak is NOT closed
      if(!hatchPiston.get()){

        // Closes beak 
        hatchPiston.set(true);
      }
       // If all are set for intake, wheels intake hatch intohand
        hatchFloorIntakeVictorController.set(ControlMode.PercentOutput, INTAKE_SPEED);
      // Brings floor intake hand to ground using piston
        hatchFloorIntakePistonController.set(true);

        if(Elevator.atPosition(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation) && armPiston.get() && hatchPiston.get() && ) {

        }

    }

    /*
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

    hatchFloorIntakePistonController.set(codriver.getRawButton(LEFT_BUMPER));

    /*
    The if statement checks for a true or false value, and runs when the value is true.
    We have set the boolean value to be the value of the Y Button, which is true when pressed,
    and false when unpressed. When the button is pressed, the victor motor controller spins
    the motors inward to take in the hatch panel. 

    Percent.Output is the standard control mode for motors, which uses 
    values for motor controlling from 0 to 1.

    The INTAKE_SPEED constant is the speed at which the intake motor will run 
    while the button is pressed. 
    */

    if (codriver.getRawButton(Y_BUTTON)) {
      hatchFloorIntakeVictorController.set(ControlMode.PercentOutput, INTAKE_SPEED);
    }

     /*
    Same as above, but opposite for outtake
    */

    if (codriver.getRawButton(X_BUTTON)) {
      hatchFloorIntakeVictorController.set(ControlMode.PercentOutput, OUTTAKE_SPEED);
    }
  }

  @Override
  public void testPeriodic() {
  }
}

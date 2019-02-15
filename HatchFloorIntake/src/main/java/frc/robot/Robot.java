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
  private static final int Y_BUTTON = 3;
  private static final int X_BUTTON = 4;
  private static final int LEFT_BUMPER = 5;
  private static final double INTAKE_SPEED = 1.0;
  private static final double OUTTAKE_SPEED = -1.0;

  // ------------ Objects ------------- \\
  private static final Solenoid hatchFloorIntakePistonController = new Solenoid(HATCH_FLOOR_SOLENOID);
  private static final VictorSPX hatchFloorIntakeVictorController = new VictorSPX(HATCH_FLOOR_VICTOR);
  private static final Joystick codriver = new Joystick(1);

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
    The if statement checks for a true or false value, and runs when the value is true.
    We have set the boolean value to be the value of the X Button, which is true when pressed,
    and false when unpressed. When the button is pressed, the victor motor controller spins
    the motors inward to take in the hatch panel. 

    Percent.Output is the standard control mode for motors, which uses 
    values for motor controlling from 0 to 1.

    The OUTTAKE_SPEED constant is the speed at which the outtake motor will run 
    while the button is pressed. 
    */

    if (codriver.getRawButton(X_BUTTON)) {
      hatchFloorIntakeVictorController.set(ControlMode.PercentOutput, OUTTAKE_SPEED);
    }
  }

  @Override
  public void testPeriodic() {
  }
}

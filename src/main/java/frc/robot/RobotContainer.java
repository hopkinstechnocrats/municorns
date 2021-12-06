// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.opencv.core.Point;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FourBarSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.DriveToWall;
import frc.robot.commands.RotateTo;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  public final FourBarSubsystem fourBarSubsystem = new FourBarSubsystem();
  private final ConveyorSubsystem conveyorSubsystem = new ConveyorSubsystem();
  private double speedMultiplyer = -.55;

  private final XboxController driveController = new XboxController(Constants.XboxControllerPort);
  private final XboxController operatorController = new XboxController(Constants.OperatorXboxControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    driveSubsystem.setSpeed(.45);

    driveSubsystem.setDefaultCommand(
            new RunCommand(
                    () -> {
                      driveSubsystem.drive(driveController.getY(GenericHID.Hand.kLeft), driveController.getY(GenericHID.Hand.kRight));
                    }
            , driveSubsystem)
    );
    conveyorSubsystem.setDefaultCommand(new RunCommand(() -> conveyorSubsystem.spin(0), conveyorSubsystem));
    fourBarSubsystem.setDefaultCommand(new RunCommand(() -> fourBarSubsystem.dontSpin(), fourBarSubsystem));

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton aButton = new JoystickButton(operatorController, 1);
    aButton.whileHeld(new RunCommand(() -> {conveyorSubsystem.spin(-0.4);}, conveyorSubsystem));
    JoystickButton bButton = new JoystickButton(operatorController, 2);
    bButton.whileHeld(new RunCommand(() -> {conveyorSubsystem.spin(1);}, conveyorSubsystem));
    JoystickButton yButton = new JoystickButton(operatorController, 4);
    yButton.whileHeld(new RunCommand(() -> {conveyorSubsystem.spin(.4);}, conveyorSubsystem));
    JoystickButton leftBumper = new JoystickButton(operatorController, 5);
    JoystickButton rightBumper = new JoystickButton(operatorController, 6);
    JoystickButton leftBumperD = new JoystickButton(driveController, 5);
    JoystickButton rightBumperD = new JoystickButton(driveController, 6);

    leftBumper.whenPressed(new RotateTo(fourBarSubsystem, -1*Constants.fourBarSpeed, 0));
    rightBumper.whenPressed(new RotateTo(fourBarSubsystem, Constants.fourBarSpeed, 4868));
    JoystickButton back = new JoystickButton(operatorController, 7);
    back.whenPressed(new InstantCommand(fourBarSubsystem::resetEncoder, fourBarSubsystem));
    POVButton povUp = new POVButton(driveController, 0);
    POVButton povRight = new POVButton(driveController, 90);
    POVButton povDown = new POVButton(driveController, 180);
    POVButton povLeft = new POVButton(driveController, 270);

    povUp.whenPressed(new InstantCommand(() -> driveSubsystem.setSpeed(.6), driveSubsystem));
    povRight.whenPressed(new InstantCommand(() -> driveSubsystem.setSpeed(.5), driveSubsystem));
    povDown.whenPressed(new InstantCommand(() -> driveSubsystem.setSpeed(.4), driveSubsystem));
    povLeft.whenPressed(new InstantCommand(() -> driveSubsystem.setSpeed(.8), driveSubsystem));
  }

  public DriveSubsystem getDriveSubsystem() {
    return driveSubsystem;
  }
  
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    
    return new DriveToWall(driveSubsystem).withTimeout(2);
    // return new SequentialCommandGroup(
    //   new DriveToWall(driveSubsystem).withTimeout(9),
    //   new RotateTo(fourBarSubsystem, Constants.fourBarSpeed, 4868).withTimeout(3),
    //   new RunCommand(
    //     () -> {
    //       conveyorSubsystem.spin(1);
    //     }
    //     , conveyorSubsystem).withTimeout(1),
    //     new RotateTo(fourBarSubsystem, Constants.fourBarSpeed, 0).withTimeout(3)
    // );
    
  }
}

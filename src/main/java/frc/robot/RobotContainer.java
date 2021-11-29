// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FourBarSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DriveToWall;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final FourBarSubsystem fourBarSubsystem = new FourBarSubsystem();
  private final ConveyorSubsystem conveyorSubsystem = new ConveyorSubsystem();

  private final XboxController driveController = new XboxController(Constants.XboxControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
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
    JoystickButton bButton = new JoystickButton(driveController, 0);
    bButton.whileHeld(new RunCommand(() -> {conveyorSubsystem.spin(0.6);}, conveyorSubsystem));
    JoystickButton leftBumper = new JoystickButton(driveController, 5);
    JoystickButton rightBumper = new JoystickButton(driveController, 6);
    leftBumper.whenPressed(new RunCommand(() -> {fourBarSubsystem.lower();}, fourBarSubsystem));
    rightBumper.whenPressed(new RunCommand(() -> {fourBarSubsystem.raise();}, fourBarSubsystem));
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
    return new SequentialCommandGroup(
      new DriveToWall(driveSubsystem),
      new RunCommand(
        () -> {
          conveyorSubsystem.spin(-0.4);
        }
        , conveyorSubsystem).withTimeout(1)
    );
  }
}

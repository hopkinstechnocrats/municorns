// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FourBarSubsystem;

public class RotateTo extends CommandBase {
  private FourBarSubsystem subsystem;
  private double speed;
  private double stopPoint;

  /** Creates a new RotateTo. */
  public RotateTo(FourBarSubsystem subsystem, double speed, double stopPoint) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.subsystem = subsystem;
    this.speed = speed;
    this.stopPoint = stopPoint;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    subsystem.spin(stopPoint);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.dontSpin();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    // if (speed >= 0) {
    //   return subsystem.getEncoderPosition() > stopPoint;
    // } else {
    //   return subsystem.getEncoderPosition() < stopPoint;
    // }
  }
}

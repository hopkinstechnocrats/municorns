// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyorSubsystem extends SubsystemBase {
  /** Creates a new HighShelfSubsystem. */
  WPI_VictorSPX motor;

  public ConveyorSubsystem() {
    motor = new WPI_VictorSPX(Constants.conveyorMotorPort);
    motor.setNeutralMode(NeutralMode.Brake);
  }

  public void spin(double speed) {
    motor.set(speed);
    System.out.println("motor enabled to "+ speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

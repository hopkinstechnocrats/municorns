// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FourBarSubsystem extends SubsystemBase {
  /** Creates a new fourBarSubsystem. */
  WPI_TalonSRX motor;
  CANCoder rotationCounter;
  double offSet;

  public FourBarSubsystem() {
    motor = new WPI_TalonSRX(Constants.fourBarMotorPort);
    motor.setNeutralMode(NeutralMode.Brake);
    rotationCounter = new CANCoder(Constants.encoderPort);
  }

  public void resetEncoder() {
    offSet = rotationCounter.getAbsolutePosition();
  }

  public double getEncoderPosition() {
    return rotationCounter.getAbsolutePosition() - offSet;
  }
  
  public void raise() {
    while(getEncoderPosition() > 0) {
      motor.set(Constants.fourBarSpeed);
    }
  }

  public void lower() {
    while(getEncoderPosition() < 2986.666666666667) {
      motor.set(Constants.fourBarSpeed * -1);
    }
  }

  

  public void dontSpin() {
    motor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

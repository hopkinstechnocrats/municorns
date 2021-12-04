// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FourBarSubsystem extends SubsystemBase {
  /** Creates a new fourBarSubsystem. */
  WPI_TalonSRX motor;
  double offSet;

  private final PIDController locPIDController =
  new PIDController(Constants.fourBarKP, Constants.fourBarKI, Constants.fourBarKD);

  public FourBarSubsystem() {
    motor = new WPI_TalonSRX(Constants.fourBarMotorPort);
    motor.setNeutralMode(NeutralMode.Brake);
    SmartDashboard.putNumber("kP Input", Constants.fourBarKP);
    SmartDashboard.putNumber("kI Input", Constants.fourBarKI);
    SmartDashboard.putNumber("kD Input", Constants.fourBarKD);
  }

  public void resetEncoder() {
    offSet = motor.getSelectedSensorPosition();
  }

  public double getEncoderPosition() {
    return motor.getSelectedSensorPosition() - offSet;
  }


  public void spin(double stopPos) {
    final double rotateSpeed = 
      locPIDController.calculate(getEncoderPosition(), stopPos);
    
    motor.setVoltage(Math.max(Math.min(rotateSpeed, 6), -3));
    SmartDashboard.putNumber("rotateSpeed", rotateSpeed);
  }

  public void dontSpin() {
    motor.set(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("FourBarEncoderPosition", getEncoderPosition());
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("kP", Constants.fourBarKP);
    SmartDashboard.putNumber("kI", Constants.fourBarKI);
    SmartDashboard.putNumber("kD", Constants.fourBarKD);

    Constants.fourBarKP = SmartDashboard.getNumber("kP Input", 0);
    Constants.fourBarKI = SmartDashboard.getNumber("kI Input", 0);
    Constants.fourBarKD = SmartDashboard.getNumber("kD Input", 0);

    locPIDController.setP(Constants.fourBarKP);
    locPIDController.setI(Constants.fourBarKI);
    locPIDController.setD(Constants.fourBarKD);
  }
}

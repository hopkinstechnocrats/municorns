// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  WPI_TalonFX leftMaster;
  WPI_TalonFX leftFollower;
  WPI_TalonFX rightMaster;
  WPI_TalonFX rightFollower;
  DifferentialDrive drive;
  public DigitalInput limitSwitch1;
  public DigitalInput limitSwitch2;
  private double maxSpeed;
  

  public DriveSubsystem() {
    leftMaster = new WPI_TalonFX(Constants.leftMasterCANID);
    leftFollower = new WPI_TalonFX(Constants.leftFollowerCANID);
    rightMaster = new WPI_TalonFX(Constants.rightMasterCANID);
    rightFollower = new WPI_TalonFX(Constants.rightFollowerCANID);
    leftMaster.setNeutralMode(NeutralMode.Brake);
    rightMaster.setNeutralMode(NeutralMode.Brake);
    leftFollower.setNeutralMode(NeutralMode.Brake);
    rightFollower.setNeutralMode(NeutralMode.Brake);
    maxSpeed = .45;

    drive = new DifferentialDrive(
      leftMaster,
      rightMaster
    );
    leftFollower.follow(leftMaster);
    rightFollower.follow(rightMaster);

    limitSwitch1 = new DigitalInput(Constants.limitSwitchPort1);
    limitSwitch2 = new DigitalInput(Constants.limitSwitchPort2);
    
  }

  public void drive(double left, double right) {
    drive.tankDrive(-maxSpeed*left, -maxSpeed*right);
    System.out.println("left: "+ left+ ", right: "+ right);
  }

  public Command setSpeed(double maxSpeed) {
    this.maxSpeed = maxSpeed;
    return null;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    System.out.println("Switch 1"+limitSwitch1.get());
    SmartDashboard.putBoolean("Limit Switch1 State", limitSwitch1.get());
    System.out.println("Switch 2"+limitSwitch2.get());
    SmartDashboard.putBoolean("Limit Switch2 State", limitSwitch2.get());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc2022.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc2022.Joysticks.Attack3;
import org.usfirst.frc2022.Utils;

/**
 *
 * @author Emma Sloan
 */
public class RotationPitchCommand extends CommandBase {

    Attack3 attack3;

    public RotationPitchCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooterPitch);
        requires(shooterRotation);
        attack3 = oi.getAttack3();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        shooterPitch.disable(); //Disables the PID loop for the pitch
        shooterRotation.disable(); //Disables the PID loop for the rotation
        shooterPitch.setPitch(0);
        shooterRotation.setRotation(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //manual command to set the pitch to the attack 3 y axis value        
        double ps, pd, rs, rd;

        ps = SmartDashboard.getNumber("Pitch Sensitivity", 0.9);
        pd = SmartDashboard.getNumber("Pitch Deadzone", 0.1);

        rs = SmartDashboard.getNumber("Rotation Sensitivity", 0.9);
        rd = SmartDashboard.getNumber("Rotation Deadzone", 0.1);

        shooterPitch.setPitch(Utils.controllerMath(attack3.GetY(), 0.75, pd, ps));
        SmartDashboard.putNumber("Pitch", attack3.GetY());

        //manual command to set the rotation to the attack 3 x axis value
        shooterRotation.setRotation(Utils.controllerMath(-attack3.GetX(), 0.75, rd, rs));

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        shooterPitch.setPitch(0);
        shooterRotation.setRotation(0);
    }
}

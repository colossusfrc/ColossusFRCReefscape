package frc.robot.autonomous;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.Swerve.DriveRobotOrientated;
import frc.robot.subsystems.SwerveMechanisms.SwerveSubsystem;

public class AlignTagSwerve extends Command{
    private final SwerveSubsystem swerveSubsystem;
    private final Pose2d pose2d;
    private final LimelightTagGetters limelightTagGetters;
    private final DoubleSupplier y;
    private final DoubleSupplier x;
    public AlignTagSwerve(DoubleSupplier x, DoubleSupplier y, Pose2d targHeading, SwerveSubsystem swerveSubsystem, LimelightTagGetters limelightTagGetters) {
        this.swerveSubsystem = swerveSubsystem;
        this.pose2d = targHeading;
        this.limelightTagGetters = limelightTagGetters;
        this.x = x;
        this.y = y;

        addRequirements(this.swerveSubsystem, this.limelightTagGetters);
    }
    @Override
    public void initialize() {
        
    }
    @Override
    public void execute() {
        new DriveRobotOrientated(
            swerveSubsystem,
            x,
            y,
            ()-> limelightTagGetters.getOutput(pose2d.getRotation().getDegrees())).execute(); 
    }
    @Override
    public void end(boolean interrupted) {
        
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}

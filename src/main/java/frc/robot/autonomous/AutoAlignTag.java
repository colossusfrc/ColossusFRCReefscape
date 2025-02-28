package frc.robot.autonomous;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DriveRobotOrientated;
import frc.robot.subsystems.SwerveSubsystem;

public class AutoAlignTag extends Command{
    private final SwerveSubsystem swerveSubsystem;
    private final Pose2d pose2d;
    private final LimelightTagGetters limelightTagGettersX;
    private final LimelightTagGetters limelightTagGettersY;
    private final LimelightTagGetters limelightTagGettersTheta;
    public AutoAlignTag(SwerveSubsystem swerveSubsystem, Pose2d pose2d,
     LimelightTagGetters limelightTagGettersX,
      LimelightTagGetters limelightTagGettersY,
       LimelightTagGetters limelightTagGettersTheta){
        this.swerveSubsystem = swerveSubsystem;
        this.pose2d = pose2d;
        this.limelightTagGettersX = limelightTagGettersX;
        this.limelightTagGettersY = limelightTagGettersY;
        this.limelightTagGettersTheta = limelightTagGettersTheta;
        addRequirements(swerveSubsystem);
    }
    @Override
    public void initialize() {
        swerveSubsystem.setMotorBrake(false);
    }
    @Override
    public void execute() {
        new DriveRobotOrientated(swerveSubsystem, 
        limelightTagGettersY.getOutput(pose2d.getY()), 
        limelightTagGettersX.getOutput(pose2d.getX()), 
        limelightTagGettersTheta.getOutput(pose2d.getRotation().getDegrees())).execute();
    }
    @Override
    public void end(boolean interrupted) {
        swerveSubsystem.setMotorBrake(true);
    }
    @Override
    public boolean isFinished() {
        return limelightTagGettersX.getPid().getAsBoolean();
    }
}

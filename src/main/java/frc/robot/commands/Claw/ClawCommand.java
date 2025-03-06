package frc.robot.commands.Claw;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMechanisms.GarraBase;

public class ClawCommand extends Command{
    private final GarraBase garra;
    private final double target;
    public ClawCommand(GarraBase garra, double target){
        this.garra = garra;
        this.target = target;
        addRequirements(this.garra);
    }

    @Override
    public void initialize() {
        garra.setBrake(false);
    }

    @Override
    public void execute() {
        garra.setAbsolutePosition(target, 0.0);
    }

    @Override
    public void end(boolean interrupted) {
        garra.setBrake(true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

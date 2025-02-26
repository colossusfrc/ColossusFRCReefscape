package frc.robot.commands.Claw;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMechanisms.Garra;

public class ClawCommand extends Command{
    private final Garra garra;
    private final double target;
    public ClawCommand(Garra garra, double target){
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
        garra.setReference(target);
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

package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMechanisms.Braco;
@Deprecated
public class SimpleArmCommand extends Command{
    Braco braco;
    double power;

    public SimpleArmCommand(Braco braco, double power){
        this.braco = braco;

        this.power = power;

        addRequirements(this.braco);
    }
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        braco.setArm(power);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

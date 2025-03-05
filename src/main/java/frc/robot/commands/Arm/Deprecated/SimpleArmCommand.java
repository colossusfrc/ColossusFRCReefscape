package frc.robot.commands.Arm.Deprecated;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Braco;
//@Deprecated
public class SimpleArmCommand extends Command{
    Braco braco;
    Supplier<Double> power;

    public SimpleArmCommand(Braco braco, Supplier<Double> power){
        this.braco = braco;

        this.power = power;

        addRequirements(this.braco);
    }
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        braco.setArm(power.get());
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

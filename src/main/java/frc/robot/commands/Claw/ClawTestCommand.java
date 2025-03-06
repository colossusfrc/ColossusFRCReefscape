package frc.robot.commands.Claw;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.GarraIntake;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Garra;

public class ClawTestCommand extends Command{
    Garra garra;
    Supplier<Double> power;
    boolean garraIntake;
    public ClawTestCommand(GarraBase garraBase, Supplier<Double> power){
        this.garra = garraBase;
        this.power = power;
        this.garraIntake = false;
        addRequirements(this.garra);
    }
    public ClawTestCommand(GarraIntake garraIntake, Supplier<Double> power){
        this.garra = garraIntake;
        this.power = power;
        this.garraIntake = true;
        addRequirements(this.garra);
    }
    @Override
    public void initialize() {
        
    }
    @Override
    public void execute() {
        garra.setPower(power.get());  
    }
    @Override
    public void end(boolean interrupted) {
        garra.setPower(0.0);
        garra.setBrake(true);
        if(garraIntake)garra.resetEncoder();
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}

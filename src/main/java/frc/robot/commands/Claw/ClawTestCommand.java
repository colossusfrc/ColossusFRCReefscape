package frc.robot.commands.Claw;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.GarraIntake;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Garra;

public class ClawTestCommand extends Command{
    Garra garra;
    double power;
    boolean garraIntake;
    public ClawTestCommand(GarraBase garraBase, double power){
        this.garra = garraBase;
        this.power = power;
        this.garraIntake = false;
        addRequirements(garraBase);
    }
    public ClawTestCommand(GarraIntake garraIntake, double power){
        this.garra = garraIntake;
        this.power = power;
        this.garraIntake = true;
        addRequirements(garraIntake);
    }
    @Override
    public void initialize() {
        
    }
    @Override
    public void execute() {
        garra.setPower(power);  
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

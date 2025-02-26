package frc.robot.commands.Claw;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMechanisms.Garra;

public class ClawTestCommand extends Command{
    Garra garra;
    double power;
    public ClawTestCommand(Garra garra, double power){
        this.garra = garra;
        addRequirements(garra);
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
        
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}

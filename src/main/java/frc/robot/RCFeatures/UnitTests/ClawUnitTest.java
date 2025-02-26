package frc.robot.RCFeatures.UnitTests;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RCFeatures.Interfaces.IOInterface;
import frc.robot.commands.Claw.ClawCommand;
import frc.robot.commands.Claw.ClawTestCommand;
import frc.robot.subsystems.ArmMechanisms.Garra;

public class ClawUnitTest {
    private final Garra garra;
    private final CommandXboxController contrle;

    public ClawUnitTest(Garra garra, CommandXboxController contrle){
        this.garra = garra;
        this.contrle = contrle;

        unitTestClaw();
    }

    private void unitTestClaw(){
        garra.setDefaultCommand(new ClawCommand(garra, 0.0));
    }
}

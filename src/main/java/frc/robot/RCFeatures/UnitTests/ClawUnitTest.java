package frc.robot.RCFeatures.UnitTests;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ArmUtility.ClawConstants;
import frc.robot.RCFeatures.Interfaces.IOInterface;
import frc.robot.commands.Claw.ClawCommand;
import frc.robot.commands.Claw.ClawTestCommand;
import frc.robot.subsystems.ArmMechanisms.Garra;
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.GarraIntake;

public class ClawUnitTest {
    private final GarraIntake garraIntake;
    private final GarraBase garraBase;
    private final CommandXboxController contrle;

    public ClawUnitTest(GarraIntake garraIntake, GarraBase garraBase, CommandXboxController contrle){
        this.garraIntake = garraIntake;
        this.garraBase = garraBase;
        this.contrle = contrle;

        unitTestClaw();
    }

    private void unitTestClaw(){
        garraBase.setDefaultCommand(new ClawCommand(garraBase, 0.0));
        garraIntake.setDefaultCommand(new ClawCommand(garraIntake, 0.0));
        contrle.leftBumper().toggleOnTrue(new ClawTestCommand(garraIntake, ClawConstants.clawReceive));
        contrle.rightBumper().whileTrue(new ClawTestCommand(garraIntake, ClawConstants.clawDrop));
    }
}

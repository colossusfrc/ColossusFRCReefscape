package frc.robot.RCFeatures.UnitTests;

import java.util.List;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.Constants.ArmUtility.ArmPositions;
import frc.robot.RCFeatures.ArmFeatures.ArmCommandFactory;
import frc.robot.RCFeatures.ArmFeatures.StateMachine;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.commands.Arm.Pidbraco;
import frc.robot.subsystems.ArmMechanisms.Braco;
import frc.robot.subsystems.ArmMechanisms.Garra;

public class ArmUnitTest{
    private final CommandXboxController controleXbox;
    private final List<Braco> bracos;
    private final StateMachine stateMachine;
    private final Garra garra;

    public ArmUnitTest(CommandXboxController controleXbox, List<Braco> bracos, StateMachine stateMachine, Garra garra){
        this.controleXbox = controleXbox;
        this.bracos = bracos;
        this.stateMachine = stateMachine;
        this.garra = garra;

        armRoutineManager();
    }
    private void armRoutineManager(){
      bracos.get(0).setDefaultCommand(
        new Pidbraco(bracos.get(0), Constants.ArmUtility.ArmPositions.armPositions.get(ArmStates.guarda)[0], 0.0)
      );
      bracos.get(1).setDefaultCommand(
        new Pidbraco(bracos.get(1), Constants.ArmUtility.ArmPositions.armPositions.get(ArmStates.guarda)[1], ArmPositions.armFeedForward)
      );
        treeUnitTest();
    }
  private void treeUnitTest(){
    controleXbox.a().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.l1, bracos, stateMachine, garra)
    );
    controleXbox.x().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.l2, bracos, stateMachine, garra)
    );
    controleXbox.y().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.pega, bracos, stateMachine, garra)
    );
    controleXbox.b().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.l3, bracos, stateMachine, garra)
    );
    controleXbox.start().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.guarda, bracos, stateMachine, garra)
    );
    controleXbox.back().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.pegaAlgeeL2, bracos, stateMachine, garra)
    );
    controleXbox.rightStick().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.pegaAlgeeL3, bracos, stateMachine, garra)
    );
    controleXbox.leftStick().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.pegaAlgeeChao, bracos, stateMachine, garra)
    );
  }
}
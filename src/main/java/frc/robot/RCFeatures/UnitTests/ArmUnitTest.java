package frc.robot.RCFeatures.UnitTests;

import java.util.List;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.RCFeatures.ArmFeatures.ArmCommandFactory;
import frc.robot.RCFeatures.ArmFeatures.StateMachine;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.commands.Arm.Pidbraco;
import frc.robot.commands.Arm.Deprecated.SimpleArmCommand;
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Braco;

public class ArmUnitTest{
    private final CommandXboxController controleXbox;
    private final List<Braco> bracos;
    private final StateMachine stateMachine;
    private final GarraBase garra;

    public ArmUnitTest(CommandXboxController controleXbox, List<Braco> bracos, StateMachine stateMachine, GarraBase garra){
        this.controleXbox = controleXbox;
        this.bracos = bracos;
        this.stateMachine = stateMachine;
        this.garra = garra;

        armRoutineManager();
    }
    @Deprecated
    private void armTestCrisis(){
      bracos.get(0).setDefaultCommand(
       new SimpleArmCommand(bracos.get(0),()->controleXbox.getLeftTriggerAxis()*0.5)
      );
      treeUnitTest();

    }
    private void armRoutineManager(){
      //estado padrão (inicia em guarda, mas sempre volta pra l1 quando tu destolggla os botões.)
      /*bracos.get(0).setDefaultCommand(
        new Pidbraco(bracos.get(0), ()->{
          return (stateMachine.getAtual()==ArmStates.guarda)?Constants.ArmUtility.ArmPositions.armPositions.get(ArmStates.guarda)[0]:
          Constants.ArmUtility.ArmPositions.armPositions.get(ArmStates.l1)[0];
        })
      );*/
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
    //marcar pra revisão (ids do joystick certo)
    controleXbox.b().and(()->controleXbox.axisGreaterThan(2, 0.1).getAsBoolean()).toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.l3, bracos, stateMachine, garra)
    );
    controleXbox.axisGreaterThan(2, 0.1).and(()->controleXbox.b().getAsBoolean()).toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.l3, bracos, stateMachine, garra)
    );
    //marcar prarevisã0 (não precisa, o gerente acredita que os botões sao conservados)
    controleXbox.b().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.startL3, bracos, stateMachine, garra)
    );
    controleXbox.start().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.guarda, bracos, stateMachine, garra)
    );
    controleXbox.back().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.cage, bracos, stateMachine, garra)
    );
    controleXbox.rightStick().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.pegaAlgeeL3, bracos, stateMachine, garra)
    );
    controleXbox.leftStick().toggleOnTrue(
      ArmCommandFactory.getArmCommand(ArmStates.pegaAlgeeL2, bracos, stateMachine, garra)
    );
  }
}
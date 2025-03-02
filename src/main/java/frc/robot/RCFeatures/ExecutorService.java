package frc.robot.RCFeatures;

import java.util.Arrays;
import java.util.List;

import frc.robot.RCFeatures.ArmFeatures.StateMachine;
import frc.robot.RCFeatures.Interfaces.ArmInterface;
import frc.robot.RCFeatures.Interfaces.ClawInterface;
import frc.robot.RCFeatures.Interfaces.SwerveInterface;
import frc.robot.RCFeatures.Interfaces.SuperInterfaces.EventRoutine;
import frc.robot.RCFeatures.Interfaces.UtilityInterfaces.AutonomousInterface;
import frc.robot.RCFeatures.Interfaces.UtilityInterfaces.IOInterface;
import frc.robot.RCFeatures.OfficialRoutines.ArmRoutines;
import frc.robot.RCFeatures.OfficialRoutines.ClawRoutines;
import frc.robot.RCFeatures.OfficialRoutines.SwerveRoutines;
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.GarraIntake;
import frc.robot.subsystems.SwerveMechanisms.SwerveSubsystem;

public abstract class ExecutorService implements
 IOInterface,
  AutonomousInterface{
    protected final List<EventRoutine> subsystems;
    public ExecutorService(){
        subsystems = Arrays.asList(
            getArmRoutine(),
            getClawRoutines(),
            getSwerveRoutines()
        );

        subsystems.forEach(this::mainEvent);
        
    }
    protected void mainEvent(EventRoutine executorService){ executorService.mainEvent(); }

    private ArmInterface getArmRoutine(){
        return ()-> ArmRoutines.armRoutineManager(
        controleXbox, 
            ArmInterface.bracos,
                StateMachine.getInstance(), GarraBase.getInstance());}
    private ClawInterface getClawRoutines(){ 
        return ()-> ClawRoutines.unitTestClaw(GarraBase.getInstance(),
            GarraIntake.getInstance(),
                controleXbox); }
    private SwerveInterface getSwerveRoutines(){    
        return ()-> SwerveRoutines.swerveUnitTestHeadingJoysticjInputs(
            SwerveSubsystem.getInstance(),
             controleXbox);
    }
}

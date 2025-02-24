package frc.robot.commands;

import java.util.List;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Dimensoes;
import frc.robot.Constants.SwerveConfigs;
import frc.robot.subsystems.SwerveSubsystem;
import swervelib.SwerveController;
import swervelib.math.SwerveMath;

public class DriveRobotOrientated extends Command {
    // Variáveis que guardam nossas funções do gamepad
    DoubleSupplier y;
    DoubleSupplier x;
    DoubleSupplier turn;

    // Objetos necessárias para acessar funções e variáveis
    SwerveSubsystem swerve;
    SwerveController controller;

    // Variáveis que guardam a translação e velocidade angular do swerve
    Translation2d translation;
    double angle;
    double omega;   

    public DriveRobotOrientated(SwerveSubsystem swerve, DoubleSupplier y, DoubleSupplier x, DoubleSupplier turn) {
      // Aqui atribuimos as funções e subsistema
      this.y = y;
      this.x = x;
      this.turn = turn;
      this.swerve = swerve;
      controller = swerve.getSwerveController(); // Obtemos o controlador do swerve
      // Adiciona a tração como requerimento
      addRequirements(swerve);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()
    {
   
    }

    // Abaixo calculamos os valores de saída a partir dos nossos inputs
    @Override
    public void execute() {
    double xVelocity   = y.getAsDouble() * SwerveConfigs.multiplicadorTranslacionalY;
    double yVelocity   = x.getAsDouble() * SwerveConfigs.multiplicadorTranslacionalX;
    double angVelocity = turn.getAsDouble() * SwerveConfigs.multiplicadorRotacional;
   
    translation = new Translation2d(xVelocity * Dimensoes.MAX_SPEED, yVelocity * Dimensoes.MAX_SPEED);

    omega = controller.config.maxAngularVelocity * angVelocity;
    
    // Caso essa função seja verdadeira a aceleração do robô será limitada
    if(SwerveConfigs.accelCorrection) {
        translation = SwerveMath.limitVelocity(translation, swerve.getFieldVelocity(), swerve.getPose(),
                                              Dimensoes.LOOP_TIME, Dimensoes.ROBOT_MASS, 
                                               List.of(Dimensoes.CHASSIS),
                                               swerve.getSwerveDriveConfiguration());
    }
    
    // Aqui temos nossa função definida dentro da classe de subsistema a qual comandara o swerve
    swerve.drive(translation, omega, false);
    }

    
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

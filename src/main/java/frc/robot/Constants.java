// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import swervelib.math.Matter;
import swervelib.math.SwerveMath;

/**
 * Classe de constantes
 */
public final class Constants {
  // Aqui temos várias constantes referentes as demais áreas do robô
    
  public static final class Dimensoes {
    // Tempo de loop (sparkMax + normal = 130ms)
    public static final double LOOP_TIME = 0.13;
    // Massa do robô *DEVE SER CONFIGURADO PARA O SEU ROBÔ*
    public static final double ROBOT_MASS = 38;
    //Velocidade máxima *DEVE SER CONFIGURADO PARA O SEU ROBÔ*
    public static final double MAX_SPEED = 4;
    //Posição do módulo mais longe *COLOQUE OS MESMOS VALORES DO JSON*
    private static final Translation2d FURTHEST_MODULE_POSE = new Translation2d(11.75, 11.75);
    public static final double MAX_ANGULAR_SPEED = SwerveMath.calculateMaxAngularVelocity(MAX_SPEED, FURTHEST_MODULE_POSE.getX(), FURTHEST_MODULE_POSE.getY());

    //Posições do centro de massa *DEVE SER CONFIGURADO PARA SEU ROBÔ*
    private static final double xMass = 0;
    private static final double yMass = 0;
    private static final double zMass = .08;

    // Centro de massa do chassi
    public static final Matter CHASSIS    = new Matter(new Translation3d(xMass, yMass, (zMass)), ROBOT_MASS);
   }

   public static final class AutonConstants{
    private static final String[] labels = {
      "bleu 1",
      "bleu 2",
      "bleu 3",
      "rouge 1",
      "rouge 2",
      "rouge 3"
    };
    private static final Pose2d[] initialPositions = {
      new Pose2d(7.6, 2, new Rotation2d(180)),
      new Pose2d(7.6, 4, new Rotation2d(180)),
      new Pose2d(7.6, 6, new Rotation2d(180)),
      new Pose2d(10, 2, new Rotation2d(0)),
      new Pose2d(10, 4, new Rotation2d(0)),
      new Pose2d(10, 6, new Rotation2d(0))
    };

    public static final HashMap<String, Pose2d> initialPositionsByLabels = new HashMap<>();
    static{
      for(int i = 0; i<6; i++)initialPositionsByLabels.put(labels[i], initialPositions[i]); 
    }
   }

    // Contem a porta em que o controle está
    public static final class Controle {
      public static final double limit = 0.8;
      // Porta do controle
      public static final int xboxControle = 0;
      
      // Deadband do controle
      public static final double DEADBAND = 0.2;
    }

    public static final class SwerveConfigs {
      // variável que ativa o PID para controlar a orientação do robô (PID tunado no json)
      public static final boolean headingCorrection = true;
      // true para correção de aceleração
      public static final boolean accelCorrection = false;
      // constante para diminuir o input do joystick (0 < multiplicadorRotacional <= 1)
      public static final double multiplicadorRotacional = 0.8;
      // constante para diminuir o input do joystick (y)
      public static final double multiplicadorTranslacionalY = 0.7;
      // constante para diminuir o input do joystick (x)
      public static final double multiplicadorTranslacionalX = 0.7;
      public static final double TURN_CONSTANT = 0.75;
      // variável que ativa a coração para resolver problema de "skew"
      public static final boolean usarCorrecaoDesvioVelocidadeAngular = false;
      // constante que corrije o skew (de -0.15 - 0.15) ESSA VARIÁVEL DEVE SER TUNADA PARA SEU ROBÔ
      public static final double coeficienteCorecaoAngVel = 4;
    }
    public static final class ArmUtility{
      public static final class ArmConstants {
        public static final double kP = 0.01;
        public static final double kI = 0.0;
        public static final double kD = 0.00027;
        public static final double kIz = 0.0;
        public static final double kFF = 0.0;
        public static final double kMaxOutput = 0.6;
        public static final double kMinOutput = -kMaxOutput;
      }
      public static final class HighArmConstants {
        public static final double kP = 0.01;
        public static final double kI = 0.0;
        public static final double kD = 0.00075;
        public static final double kIz = 0.0;
        public static final double kFF = 0.0;
        public static final double kMaxOutput = 0.6;
        public static final double kMinOutput = -kMaxOutput;
      }
      public static final class ClawConstants{
        public static final double kPositionAlgee = -4.4;
      }
      public static final class ArmPositions{
         @SuppressWarnings(value = { "rawtypes", "unchecked" })
          public static HashMap<ArmStates, Double[]> armPositions = new HashMap(7);
          static{
              armPositions.put(ArmStates.guarda, new Double[]{75.0, 29.0});
              armPositions.put(ArmStates.idle, new Double[]{0.0, 0.0});
              armPositions.put(ArmStates.l1, new Double[]{53.0, 34.0});
              armPositions.put(ArmStates.l2, new Double[]{13.0, 40.0});
              armPositions.put(ArmStates.pega, new Double[]{26.0, 29.0});
              armPositions.put(ArmStates.pegaChao, new Double[]{75.0, 29.0});
              armPositions.put(ArmStates.l3, new Double[]{55.0, 90.0});
              armPositions.put(ArmStates.algee, new Double[]{54.0, 52.0});
          }
        }
    }
}

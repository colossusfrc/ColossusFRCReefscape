package frc.robot.autonomous;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Camera extends SubsystemBase   {
    private final UsbCamera camera;
    public final CvSink cvSink;
    public final CvSource cvSource;
    public Camera(String name){
        this.camera = CameraServer.startAutomaticCapture();
        this.camera.setResolution(640, 480);

        cvSink = CameraServer.getVideo();
        cvSource = CameraServer.putVideo(name, 640, 480);

    }
}



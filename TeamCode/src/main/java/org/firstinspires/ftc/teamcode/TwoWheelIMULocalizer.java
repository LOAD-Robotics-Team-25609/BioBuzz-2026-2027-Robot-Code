package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class TwoWheelIMULocalizer {

    public class Odometry {
        private final DcMotor parEnc, perpEnc;
        private final IMU imu;

        // goBILDA odometry pod: 2000 ticks/rev, 48mm wheel
        static final double INCHES_PER_TICK = Math.PI * (48 / 25.4) / 2000.0;
        static final double PAR_OFFSET  = //need location of the odom pods; // inches the parallel pod is LEFT of center (negative if right)
        static final double PERP_OFFSET = //need location of the odom pods; // inches the perp pod is AHEAD of center (negative if behind)

        public double x, y, heading; // inches, inches, radians
        private int lastPar, lastPerp;
        private double lastHeading;

        public Odometry(HardwareMap hw) {
            parEnc  = hw.get(DcMotor.class, "parEnc");
            perpEnc = hw.get(DcMotor.class, "perpEnc");

            imu = hw.get(IMU.class, "imu");
            imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD))); // your hub is mounted
            imu.resetYaw();
        }

        public void update() {
            int par  = parEnc.getCurrentPosition();
            int perp = perpEnc.getCurrentPosition();
            double newHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double dPar  = (par  - lastPar)  * INCHES_PER_TICK;
            double dPerp = (perp - lastPerp) * INCHES_PER_TICK;
            double dTheta = AngleUnit.normalizeRadians(newHeading - lastHeading);
            lastPar = par; lastPerp = perp; lastHeading = newHeading;

            // remove the movement the pods "see" just from rotating
            double dForward = dPar  + PAR_OFFSET  * dTheta;
            double dStrafe  = dPerp - PERP_OFFSET * dTheta;

            double mid = heading + dTheta / 2.0;
            x += dForward * Math.cos(mid) - dStrafe * Math.sin(mid);
            y += dForward * Math.sin(mid) + dStrafe * Math.cos(mid);
            heading = newHeading;
        }
    }
}

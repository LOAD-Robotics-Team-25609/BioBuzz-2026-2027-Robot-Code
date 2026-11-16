package org.firstinspires.ftc.teamcode.mechanisms;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;


public class Shooter {
    private DcMotorEx Shooter;
    private static final double LAUNCHER_TICKS_PER_REV = 28; // Bare goBILDA motor, no gearbox
    private static final double LAUNCHER_TARGET_RPM = 3500; // TUNE HERE cuz u will forget, this number is in RPM
    private static final double LAUNCHER_TARGET_VELOCITY = LAUNCHER_TARGET_RPM * LAUNCHER_TICKS_PER_REV / 60.0; // encoder ticks/per sec, calculated from the RPM above
    private static final double LAUNCHER_VELOCITY_TOLERANCE = 50; // Ticks
    private static final double FLYWHEEL_GEAR_RATIO = 2.25; // Flywheel turns 3.75x slower than the motor

    private DcMotorEx ShooterMotor;

    public void init(HardwareMap hardwareMap) {
        ShooterMotor = hardwareMap.get(DcMotorEx.class, "Shooter");

        ShooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ShooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        ShooterMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));



    }
}

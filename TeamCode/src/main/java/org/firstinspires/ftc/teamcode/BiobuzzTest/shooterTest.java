package org.firstinspires.ftc.teamcode.BiobuzzTest;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "shooterTest", group = "Test")
public class shooterTest extends OpMode {

    private DcMotorEx Shooter;
    private static final double LAUNCHER_TARGET_VELOCITY = 2000; // TUNE HERE
    private static final double LAUNCHER_VELOCITY_TOLERANCE = 50; // Ticks


    @Override
    public void init() {

       Shooter = hardwareMap.get(DcMotorEx.class, "Shooter");

        Shooter.setDirection(DcMotor.Direction.REVERSE);
       Shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        if (gamepad1.y) {
            Shooter.setVelocity(LAUNCHER_TARGET_VELOCITY);
        } else {
            Shooter.setVelocity(0);
        }

        boolean readyToShoot = Math.abs(Shooter.getVelocity() - LAUNCHER_TARGET_VELOCITY) < LAUNCHER_VELOCITY_TOLERANCE;
    }
}

package org.firstinspires.ftc.teamcode.BiobuzzTest;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Mecanum Drive Test", group = "Test")
public class MecanumDriveTest extends OpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private DcMotor intake;
    private DcMotor transfer;
    private DcMotorEx launcher;
    private static final double LAUNCHER_TARGET_VELOCITY = 1800; // TUNE HERE
    private static final double LAUNCHER_VELOCITY_TOLERANCE = 50; // Ticks

    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");
        intake = hardwareMap.get(DcMotor.class, "Intake");
        transfer = hardwareMap.get(DcMotor.class, "Transfer");

        // Mecanum drives typically need one side reversed so both sides drive
        // the robot forward with the same joystick direction.
        // Start with this guess; we'll fix any wheel that spins backward below.
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        launcher.setDirection(DcMotorEx.Direction.FORWARD);
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

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
        double y = -gamepad1.left_stick_y;   // forward/backward
        double x = gamepad1.left_stick_x;    // strafe left/right
        double rx = gamepad1.right_stick_x;  // rotate

        double frontLeftPower = y + x + rx;
        double backLeftPower = y - x + rx;
        double frontRightPower = y - x - rx;
        double backRightPower = y + x - rx;

        // Normalize so no value exceeds 1.0
        double max = Math.max(1.0, Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(backLeftPower),
                Math.max(Math.abs(frontRightPower), Math.abs(backRightPower)))));

        // Drive is capped at 60% power unless the X button is held, which unlocks full speed.
        double speedLimiter = gamepad1.x ? 1.0 : 0.6; // pick any free button



        frontLeft.setPower((frontLeftPower / max) * speedLimiter);
        backLeft.setPower((backLeftPower / max) * speedLimiter);
        frontRight.setPower((frontRightPower / max) * speedLimiter);
        backRight.setPower((backRightPower / max) * speedLimiter);

        // Right trigger spins the intake forward, left trigger spins it backward.
        double intakePower = gamepad1.right_trigger - gamepad1.left_trigger;
        intake.setPower(intakePower);


        if (gamepad1.y) {
            launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
        } else {
            launcher.setVelocity(0);
        }

        boolean readyToShoot = Math.abs(launcher.getVelocity() - LAUNCHER_TARGET_VELOCITY) < LAUNCHER_VELOCITY_TOLERANCE;

        double transferPower = (gamepad1.left_bumper ? 1 : 0) - (gamepad1.right_bumper ? 1 : 0);

        if (transferPower > 0 && !readyToShoot) {
            transferPower = 0;
        }

        transfer.setPower(transferPower);



        telemetry.addData("Speed Mode", speedLimiter == 1.0 ? "Full (100%)" : "Limited (60%)");
        telemetry.addData("Front Left Power", (frontLeftPower / max) * speedLimiter);
        telemetry.addData("Front Right Power", (frontRightPower / max) * speedLimiter);
        telemetry.addData("Back Left Power", (backLeftPower / max) * speedLimiter);
        telemetry.addData("Back Right Power", (backRightPower / max) * speedLimiter);
        telemetry.addData("Intake Power", intakePower);
        telemetry.addData("Launcher Ready", readyToShoot);
        telemetry.addData("Launcher Velocity", launcher.getVelocity());
        telemetry.update();
    }

    @Override
    public void stop() {
    }
}
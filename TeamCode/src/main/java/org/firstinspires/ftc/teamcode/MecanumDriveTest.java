package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Mecanum Drive Test", group = "Test")
public class MecanumDriveTest extends OpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private DcMotor intake;

    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");
        intake = hardwareMap.get(DcMotor.class, "Intake");

        // Mecanum drives typically need one side reversed so both sides drive
        // the robot forward with the same joystick direction.
        // Start with this guess; we'll fix any wheel that spins backward below.
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

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

        // Drive is capped at 30% power unless the left bumper is held, which unlocks full speed.
        double speedLimiter = gamepad1.left_bumper ? 1.0 : 0.3;

        frontLeft.setPower((frontLeftPower / max) * speedLimiter);
        backLeft.setPower((backLeftPower / max) * speedLimiter);
        frontRight.setPower((frontRightPower / max) * speedLimiter);
        backRight.setPower((backRightPower / max) * speedLimiter);

        // Right trigger spins the intake forward, left trigger spins it backward.
        double intakePower = gamepad1.right_trigger - gamepad1.left_trigger;
        intake.setPower(intakePower);

        telemetry.addData("Speed Mode", gamepad1.left_bumper ? "Full (100%)" : "Limited (30%)");
        telemetry.addData("Front Left Power", (frontLeftPower / max) * speedLimiter);
        telemetry.addData("Front Right Power", (frontRightPower / max) * speedLimiter);
        telemetry.addData("Back Left Power", (backLeftPower / max) * speedLimiter);
        telemetry.addData("Back Right Power", (backRightPower / max) * speedLimiter);
        telemetry.addData("Intake Power", intakePower);
        telemetry.update();
    }

    @Override
    public void stop() {
    }
}
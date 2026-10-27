package org.firstinspires.ftc.teamcode.BiobuzzTest;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "JosephinesIntake", group = "Test")
public class IntakeTest extends OpMode {

    private DcMotor intake;
    private DcMotor transfer;

    private boolean intakeOn = false;
    private boolean transferOn = false;

    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class, "intake");
        transfer = hardwareMap.get(DcMotor.class, "Transfer");

        transfer.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Y toggles the intake
        if (gamepad1.yWasPressed()) {
            intakeOn = !intakeOn;
        }

        // A toggles the transfer
        if (gamepad1.aWasPressed()) {
            transferOn = !transferOn;
        }

        intake.setPower(intakeOn ? 1.0 : 0.0);
        transfer.setPower(transferOn ? 1.0 : 0.0);

        telemetry.addData("Intake", intakeOn ? "ON" : "OFF");
        telemetry.addData("Transfer", transferOn ? "ON" : "OFF");
        telemetry.update();
    }

    @Override
    public void stop() {
        intake.setPower(0);
        transfer.setPower(0);
    }
}





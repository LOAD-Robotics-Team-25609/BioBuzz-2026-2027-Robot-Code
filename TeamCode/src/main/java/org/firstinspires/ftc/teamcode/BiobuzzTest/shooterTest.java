package org.firstinspires.ftc.teamcode.BiobuzzTest;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "shooterTest", group = "Test")
public class shooterTest extends OpMode {

    private DcMotorEx Shooter;
    private static final double LAUNCHER_TARGET_VELOCITY = 3000; // TUNE HERE cuz u will forget
    private static final double LAUNCHER_VELOCITY_TOLERANCE = 50; // Ticks

    private boolean shooterOn = false;
    @Override
    public void init() {

       Shooter = hardwareMap.get(DcMotorEx.class, "Shooter");

        Shooter.setDirection(DcMotor.Direction.FORWARD);
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
        if (gamepad1.yWasPressed()) {
            shooterOn = !shooterOn;   // Each press toggles on/off hopefully
        }

        Shooter.setVelocity(shooterOn ? LAUNCHER_TARGET_VELOCITY : 0);

        boolean readyToShoot = shooterOn //Whatever happens, dont get rid of this
                && Math.abs(Shooter.getVelocity() - LAUNCHER_TARGET_VELOCITY) < LAUNCHER_VELOCITY_TOLERANCE;

        telemetry.addData("Shooter On", shooterOn);
        telemetry.addData("Shooter Ready", readyToShoot);
        telemetry.addData("Shooter Velocity", Shooter.getVelocity()); //future reference, always leave telemetry on the bottom
    }
}

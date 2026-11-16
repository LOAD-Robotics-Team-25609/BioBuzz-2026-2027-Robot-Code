package org.firstinspires.ftc.teamcode.BiobuzzTest;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.mechanisms.Shooter;

@TeleOp(name = "shooterTest", group = "Test")
public class shooterTest extends OpMode {

    private DcMotorEx Shooter;
    private static final double LAUNCHER_TICKS_PER_REV = 28; // Bare goBILDA motor, no gearbox
    private static final double LAUNCHER_START_RPM = 3350; // Motor RPM the shooter starts at each time the program runs
    private static final double RPM_STEP = 50; // How much one D-pad press changes the target RPM
    private static final double LAUNCHER_MIN_RPM = 0;
    private static final double LAUNCHER_MAX_RPM = 6000; // Top speed of a bare goBILDA motor
    private static final double LAUNCHER_VELOCITY_TOLERANCE = 50; // Ticks
    private static final double FLYWHEEL_GEAR_RATIO = 2.2; // Flywheel turns 2.2x slower than the motor

    private boolean shooterOn = false;
    private double targetRpm = LAUNCHER_START_RPM;

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
        if (gamepad1.yWasPressed()) {
            shooterOn = !shooterOn;   // Each press toggles on/off hopefully
        }

        if (gamepad1.dpadUpWasPressed()) {
            targetRpm += RPM_STEP;
        }
        if (gamepad1.dpadDownWasPressed()) {
            targetRpm -= RPM_STEP;
        }
        targetRpm = Math.max(LAUNCHER_MIN_RPM, Math.min(LAUNCHER_MAX_RPM, targetRpm));

        double targetVelocity = targetRpm * LAUNCHER_TICKS_PER_REV / 60.0; // encoder ticks/sec

        Shooter.setVelocity(shooterOn ? targetVelocity : 0);

        boolean readyToShoot = shooterOn //Whatever happens, dont get rid of this
                && Math.abs(Shooter.getVelocity() - targetVelocity) < LAUNCHER_VELOCITY_TOLERANCE;

        telemetry.addData("Controls", "Y = on/off, D-pad up/down = +/- 50 RPM");
        telemetry.addData("Shooter On", shooterOn);
        telemetry.addData("Shooter Ready", readyToShoot);
        telemetry.addData("Shooter Velocity", Shooter.getVelocity()); //future reference, always leave telemetry on the bottom
        telemetry.addData("Shooter RPM", "%.0f", Shooter.getVelocity() * 60.0 / LAUNCHER_TICKS_PER_REV);
        telemetry.addData("Target RPM", "%.0f", targetRpm);
        telemetry.addData("Target Flywheel RPM", "%.0f", targetRpm / FLYWHEEL_GEAR_RATIO);
        telemetry.addData("Flywheel RPM", "%.0f", Shooter.getVelocity() * 60.0 / LAUNCHER_TICKS_PER_REV / FLYWHEEL_GEAR_RATIO);
    }
}
package org.firstinspires.ftc.teamcode.mechanisms;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private DcMotor intakeMotor;

    public void init(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeMotor.setZeroPowerBehavior(BRAKE);

    }

    public void setPower(double power) {
        intakeMotor.setPower(power);

    }
    public void stop() {
        setPower(0);
    }

}

package krpcj.fsw.data;

public class Telemetry {

    private long timestamp; // unix epoch time; the time this telem packet was captured

    private long met; // in-game MET

    private double airspeed; // Airspeed indicator

    private double altitude; // Altimeter

    private double pitch; // Artificial horizon
    private double roll;

    private double heading; // Compass

    private double vSpeed; // VSI

    private double ilsHorizDiff; // ILS
    private double ilsVertDiff;

    // Control input monitoring
    private double throttleState;
    private double pitchSetting;
    private double rollSetting;
    private double yawSetting;

    public Telemetry() {

    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getMet() {
        return met;
    }

    public void setMet(long met) {
        this.met = met;
    }

    public double getAirspeed() {
        return airspeed;
    }

    public void setAirspeed(double airspeed) {
        this.airspeed = airspeed;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getRoll() {
        return roll;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getvSpeed() {
        return vSpeed;
    }

    public void setvSpeed(double vSpeed) {
        this.vSpeed = vSpeed;
    }

    public double getIlsHorizDiff() {
        return ilsHorizDiff;
    }

    public void setIlsHorizDiff(double ilsHorizDiff) {
        this.ilsHorizDiff = ilsHorizDiff;
    }

    public double getIlsVertDiff() {
        return ilsVertDiff;
    }

    public void setIlsVertDiff(double ilsVertDiff) {
        this.ilsVertDiff = ilsVertDiff;
    }

    public double getThrottleState() {
        return throttleState;
    }

    public void setThrottle(double throttle) {
        this.throttleState = throttle;
    }

    public double getPitchSetting() {
        return pitchSetting;
    }

    public void setPitchSetting(double pitchSetting) {
        this.pitchSetting = pitchSetting;
    }

    public double getRollSetting() {
        return rollSetting;
    }

    public void setRollSetting(double rollSetting) {
        this.rollSetting = rollSetting;
    }

    public double getYawSetting() {
        return yawSetting;
    }

    public void setYawSetting(double yawSetting) {
        this.yawSetting = yawSetting;
    }
}

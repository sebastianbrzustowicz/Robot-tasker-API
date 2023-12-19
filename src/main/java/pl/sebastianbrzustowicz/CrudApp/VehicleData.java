package pl.sebastianbrzustowicz.CrudApp;

public class VehicleData {

    private String vehicleId;
    private int mode;
    private int vtol;
    private int x;
    private int y;
    private int alt;
    private int yaw;
    private boolean camTrig;
    private boolean camTog;
    private int camPitch;
    private boolean clamp;

    // Getters and setters

    public VehicleData(String vehicleId, int mode, int vtol, int x, int y, int alt, int yaw,
                       boolean camTrig, boolean camTog, int camPitch, boolean clamp) {
        this.vehicleId = vehicleId;
        this.mode = mode;
        this.vtol = vtol;
        this.x = x;
        this.y = y;
        this.alt = alt;
        this.yaw = yaw;
        this.camTrig = camTrig;
        this.camTog = camTog;
        this.camPitch = camPitch;
        this.clamp = clamp;
    }
}
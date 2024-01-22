package pl.sebastianbrzustowicz.robotTaskerAPI.model;


public class VehicleData {

    private static VehicleData instance;

    // desired values
    private String vehicleId;
    private int mode;
    private int vtol;
    private int x;
    private int y;
    private int alt;
    private int yawd;
    private boolean camTrig;
    private boolean camTog;
    private int camPitch;
    private boolean clamp;
    // sensors values
    private double roll;
    private double pitch;
    private double yaw;
    private double altitude;
    private boolean isClamp;

    // Getters and setters

    private VehicleData() {
        this.vehicleId = "";
        this.mode = 1;
        this.vtol = 0;
        this.x = 0;
        this.y = 0;
        this.alt = 0;
        this.yawd = 0;
        this.camTrig = false;
        this.camTog = false;
        this.camPitch = 0;
        this.clamp = false;

        this.altitude = 0;
    }

    // Getter for the singleton instance
    public static VehicleData getInstance() {
        if (instance == null) {
            // Provide initial values as needed
            instance = new VehicleData();
        }
        return instance;
    }

    public void saveDesiredValues(String vehicleId, int mode, int vtol, int x, int y, int alt,
                                  int yaw, boolean camTrig, boolean camTog, int camPitch, boolean clamp) {
        this.vehicleId = vehicleId;
        this.mode = mode;
        this.vtol = vtol;
        this.x = x;
        this.y = y;
        this.alt = alt;
        this.yawd = yaw;
        this.camTrig = camTrig;
        this.camTog = camTog;
        this.camPitch = camPitch;
        this.clamp = clamp;
    }

    public void saveSensorsValues(double roll, double pitch, double yaw, double altitude, boolean isClamp) {
        this.roll = roll;
        this.pitch = pitch;
        this.yaw = yaw;
        this.altitude = altitude;
        this.isClamp = isClamp;
    }

    public String getSensorsFrame() {
        StringBuilder dataFrame = new StringBuilder();

        dataFrame.append("VEHICLE").append("\n");
        dataFrame.append(altitude).append("\n");
        dataFrame.append("END");

        return dataFrame.toString();
    }

    public String getDesiredFrame() {
        // there handshake should be established between server and client
        // data is sent in raw format but values stands for these variables:
        // CLIENT                                   <- fixed prefix for client message
        // 4436ed9a-5228-46c0-b825-6d0a3cd90437     <- vehicleId
        // 1                                        <- mode
        // 0                                        <- vtol
        // 0                                        <- x
        // 0                                        <- y
        // 0                                        <- alt
        // 0                                        <- yaw
        // false                                    <- camTrig
        // false                                    <- camTog
        // 0                                        <- camPitch
        // false                                    <- clamp
        // END                                      <- fixed ending statement of message

        StringBuilder dataFrame = new StringBuilder();

        dataFrame.append("CLIENT").append("\n");
        dataFrame.append(vehicleId).append("\n");
        dataFrame.append(mode).append("\n");
        dataFrame.append(vtol).append("\n");
        dataFrame.append(x).append("\n");
        dataFrame.append(y).append("\n");
        dataFrame.append(alt).append("\n");
        dataFrame.append(yawd).append("\n");
        dataFrame.append(camTrig).append("\n");
        dataFrame.append(camTog).append("\n");
        dataFrame.append(camPitch).append("\n");
        dataFrame.append(clamp).append("\n");
        dataFrame.append("END");

        return dataFrame.toString();
    }
}
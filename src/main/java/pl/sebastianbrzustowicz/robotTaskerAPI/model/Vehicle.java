package pl.sebastianbrzustowicz.robotTaskerAPI.model;

//import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private String userId;
    private String vehicleId;
    private String vehicleName;
    private String vehicleType;
    private String registrationTime;

    public Vehicle(String userId, String vehicleId, String vehicleName, String vehicleType, String registrationTime) {
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.registrationTime = registrationTime;
    }

    public String getUserId() {
        return userId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }
}

package pl.sebastianbrzustowicz.CrudApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quadcopter {
    private int id;
    private String mode;
    private int altitude;

    public String getMode() {
        return mode;
    }

    public int getAltitude() {
        return altitude;
    }
}

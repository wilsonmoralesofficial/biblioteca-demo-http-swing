package segurosgyt.wsoc;

import segurosgyt.wsoc.interfacesHealthCheck.HealthCheckComponent;

import java.util.HashMap;
import java.util.Map;

public class DataBaseHealthCheck implements HealthCheckComponent {

    private String details;

    @Override
    public HealthStatus check() {
        try {
            if (getTestConnection()) {
                return new HealthStatus("Database", true, "Successful connection" );
            } else {
                return new HealthStatus("Database", false, "Offline");
            }
        } catch (RuntimeException e) {
            Map<String, Object> details = new HashMap<>();
            details.put("error", e.getMessage());
            return new HealthStatus("database", false, "Error al conectar a DB");
        }
    }

    private boolean getTestConnection(){
        return true;
    }
}

package segurosgyt.wsoc;

import segurosgyt.wsoc.healthChecksProcessors.TestConnectionHealthCheck;
import segurosgyt.wsoc.interfacesHealthCheck.HealthCheckComponent;


public class DataBaseHealthCheck implements HealthCheckComponent {


    TestConnectionHealthCheck testConnectionHealthCheck = new TestConnectionHealthCheck();

    @Override
    public HealthStatus check() {
        try {
            if (getTestConnection()) {
                return new HealthStatus("Database", true, "Successful connection" );
            } else {
                return new HealthStatus("Database", false, "Offline");
            }
        } catch (RuntimeException e) {
            return new HealthStatus("database", false, "Error al conectar a DB => " + e);
        }
    }

    private boolean getTestConnection(){
        try{
            if (testConnectionHealthCheck.connectionAvailable())
            {
                return true;
            }else
            {
                return false;
            }
        }
        catch (RuntimeException exception)
        {
            throw new RuntimeException(exception);
        }
    }
}

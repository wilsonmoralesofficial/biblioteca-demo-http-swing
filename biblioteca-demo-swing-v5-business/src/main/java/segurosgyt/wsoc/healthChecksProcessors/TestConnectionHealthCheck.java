package segurosgyt.wsoc.healthChecksProcessors;

import segurosgyt.wsoc.healthCheck;
import segurosgyt.wsoc.interfaces.IHealthCheck;

public class TestConnectionHealthCheck {

    IHealthCheck healthCheck = new healthCheck();
    public boolean connectionAvailable() {
        try {
            if (healthCheck.testConnection() == 1){return true;}
            return false;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


}

package segurosgyt.wsoc.interfacesHealthCheck;

import java.util.Map;

public interface HealthCheckComponent {
    HealthStatus check();

    class HealthStatus {
        private final String name;
        private final boolean isUp;
        private final String message;
        //private final Map<String, Object> details;

        public HealthStatus(String name, boolean isUp, String message) { //Map<String, Object> details
            this.name = name;
            this.isUp = isUp;
            this.message = message;
            //this.details = details;
        }

        public String getName() {
            return name;
        }

        public boolean isUp() {
            return isUp;
        }

        public String getMessage() {
            return message;
        }

        /*public Map<String, Object> getDetails() {
            return details;
        }

         */
    }
}

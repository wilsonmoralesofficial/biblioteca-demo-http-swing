package segurosgyt.wsoc;

import io.javalin.Javalin;

public class HealthChecksController {

    public void createEndPointsHealthChecks(Javalin app){
        DataBaseHealthCheck dataBaseHealthCheck = new DataBaseHealthCheck();

        // Liveness Check y a la vez Readiness Check
        app.get("/api/healthcheck/testconnection", ctx -> {
                    ctx.json(dataBaseHealthCheck.check());
                    ctx.status(200);
        });
    }
}

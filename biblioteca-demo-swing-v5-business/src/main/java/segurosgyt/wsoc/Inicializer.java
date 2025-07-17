package segurosgyt.wsoc;

import io.javalin.Javalin;

public class Inicializer {


    public static void main(String[] args) {
        try{
            Javalin app = Javalin.create().start(8080);
            BookController bookController = new BookController();
            bookController.createBookEndPoints(app);
            HealthChecksController healthChecksController = new HealthChecksController();
            healthChecksController.createEndPointsHealthChecks(app);

        }catch (RuntimeException e)
        {
            throw new RuntimeException(e);
        }
    }

}

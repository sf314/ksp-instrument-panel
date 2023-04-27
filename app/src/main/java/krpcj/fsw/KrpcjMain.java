package krpcj.fsw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrpcjMain {

    private static final Logger logger = LoggerFactory.getLogger(KrpcjMain.class.getSimpleName());

    public static void main(String[] args) throws Exception {
        logger.info("Starting KRPCJ Main");
        logger.info("Environment FSW_ENV=" + System.getenv("FSW_ENV"));

        App app = new App();
        app.run();
    }
}

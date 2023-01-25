package krpcj.fsw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrpcjMain {

    private static final Logger logger = LoggerFactory.getLogger(KrpcjMain.class.getSimpleName());

    public static void main(String[] args) throws Exception {
        logger.info("Starting KRPCJ Main");

        App app = new App();
        app.run();
    }
}

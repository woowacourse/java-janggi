import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            Janggi janggi = new Janggi();
            janggi.run();
        } catch (Exception e) {
            logger.error("에러 발생", e);
        }
    }
}

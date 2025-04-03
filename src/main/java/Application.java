import controller.JanggiController;
import domain.dao.JanggiCoordinateDao;
import domain.dao.JanggiDBConnect;
import domain.dao.JanggiGameDao;
import domain.dao.JanggiPieceDao;
import view.InputView;
import view.OutputView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    public static void main(String[] args) {
        Logger.getLogger("org.flywaydb").setLevel(Level.WARNING);

        JanggiMigration flyMigration = new JanggiMigration(
                JanggiMigration.URL,
                JanggiMigration.USERNAME,
                JanggiMigration.PASSWORD
        );
        flyMigration.migrate();

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiGameDao gameDao = new JanggiGameDao(JanggiDBConnect.getConnection());
        JanggiPieceDao pieceDao = new JanggiPieceDao(JanggiDBConnect.getConnection());
        JanggiCoordinateDao coordinateDao = new JanggiCoordinateDao(JanggiDBConnect.getConnection());

        JanggiController controller = new JanggiController(
                inputView,
                outputView,
                gameDao,
                coordinateDao,
                pieceDao
        );

        controller.startJanggiGame();
    }
}

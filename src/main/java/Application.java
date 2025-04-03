import dao.*;
import domain.position.generator.DefaultPositionsGenerator;
import domain.position.generator.InitDefaultPositionsGenerator;
import service.JanggiPieceService;
import service.JanggiTurnService;

public class Application {
    public static void main(String[] args) {
        Janggi janggi = new Janggi(
                janggiPieceService(), janggiTurnService()
        );
        janggi.play();
    }

    private static JanggiPieceService janggiPieceService() {
        return new JanggiPieceService(defaultPositionsGenerator(), pieceDao());
    }

    private static DefaultPositionsGenerator defaultPositionsGenerator() {
        return new InitDefaultPositionsGenerator();
    }

    private static PieceDao pieceDao() {
        return new PieceDaoImpl(databaseConnector());
    }

    private static DatabaseConnector databaseConnector() {
        return new JanggiDatabaseConnector();
    }

    private static JanggiTurnService janggiTurnService() {
        return new JanggiTurnService(turnDao());
    }

    private static TurnDao turnDao() {
        return new TurnDaoImpl(databaseConnector());
    }
}

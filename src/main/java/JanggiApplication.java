import java.sql.Connection;

import board.Position;
import dao.DaoService;
import dao.PieceDao;
import dao.TurnConverter;
import dao.TurnDao;
import dao.connector.DBConnector;
import dao.connector.MySQLDBConnector;
import game.JanggiGame;
import game.Turn;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        DBConnector dbConnector = new MySQLDBConnector();
        Connection connection = dbConnector.getConnection();
        DaoService daoService = new DaoService(new PieceDao(connection), new TurnDao(connection));
        JanggiGame janggiGame = new JanggiGame(daoService.findBoard(), daoService.findTurn());

        outputView.printBoard(janggiGame.getPieces());
        outputView.printTeamScore(janggiGame.calculateTotalScore());
        playGame(janggiGame, daoService);
    }

    private static void playGame(final JanggiGame janggiGame, final DaoService daoService) {
        retry(() -> movePosition(janggiGame, daoService));
        outputView.printBoard(janggiGame.getPieces());
        outputView.printTeamScore(janggiGame.calculateTotalScore());
        if (janggiGame.isFinish()) {
            outputView.printWinner(janggiGame.findWinnerTeam());
            daoService.removeAllGameData();
            return;
        }
        playGame(janggiGame, daoService);
    }

    private static void movePosition(final JanggiGame janggiGame, final DaoService daoService) {
        Turn turn = janggiGame.getTurn();
        Position startPosition = inputView.readStartPosition(turn);
        Position destinationPosition = inputView.readDestinationPosition();

        janggiGame.move(startPosition, destinationPosition);

        daoService.removeAndUpdatePosition(startPosition, destinationPosition);
        daoService.updateTurn(TurnConverter.toEntity(janggiGame.getTurn()));
    }

    private static void retry(final Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

}

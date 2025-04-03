package janggi;

import janggi.board.Board;
import janggi.coordinate.JanggiPosition;
import janggi.database.JanggiDatabase;
import janggi.piece.Country;
import janggi.piece.Piece;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Map;

public class JanggiApplication {
    private static final int MOVE_SOURCE = 0;
    private static final int MOVE_DESTINATION = 1;
    private static final JanggiDatabase JANGGI_DATABASE = new JanggiDatabase();

    public static void main(String[] args) {
        OutputView.printIntroduce();
        final Board board = readTablesIfExistsBeforeGame();
        final Country type = readCurrentTurnIfExistsBeforeGame();
        startJanggi(board, type);
    }

    private static Board readTablesIfExistsBeforeGame() {
        if (JANGGI_DATABASE.existsJanggiRows()) {
            return JANGGI_DATABASE.readBoard();
        }
        final Board board = Board.createInitializedJanggiBoard();
        JANGGI_DATABASE.saveBoard(board);
        return board;
    }

    private static Country readCurrentTurnIfExistsBeforeGame() {
        if(JANGGI_DATABASE.existsTurn()){
            return JANGGI_DATABASE.readCurrentTurn();
        }
        final Country firstTurnCountry = Country.getFirstTurnCountry();
        JANGGI_DATABASE.saveTurn(firstTurnCountry);
        return firstTurnCountry;
    }

    private static void startJanggi(final Board board, final Country country) {
        while (board.isAliveAllGenerals()) {
            OutputView.printBoard(board, country);
            final List<JanggiPosition> janggiPositions = InputView.readPositions();
            board.updatePosition(janggiPositions.get(MOVE_SOURCE), janggiPositions.get(MOVE_DESTINATION), country);
            final Country countryOfEnemy = country.toggleCountry();
            updateMoveForDatabase(janggiPositions.get(MOVE_SOURCE), janggiPositions.get(MOVE_DESTINATION), board);
            JANGGI_DATABASE.updateTurn(countryOfEnemy);
        }

        JANGGI_DATABASE.removeAllJanggiRows();
        JANGGI_DATABASE.removeTurn();

        OutputView.printJanggiWinner(board);
    }

    private static void updateMoveForDatabase(final JanggiPosition source, final JanggiPosition destination,
                                              final Board board) {
        final Map<JanggiPosition, Piece> janggiBoard = board.getJanggiBoard();
        if (janggiBoard.containsKey(source)) {
            return;
        }
        JANGGI_DATABASE.removeJanggiRowByPosition(source);
        JANGGI_DATABASE.updateJanggiRowByPosition(destination, janggiBoard.get(destination));
    }
}

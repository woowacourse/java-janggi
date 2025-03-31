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
    private static final JanggiDatabase janggiDatabase = new JanggiDatabase();

    public static void main(String[] args) {
        OutputView.printIntroduce();
        createTablesIfNotExistsJanggiTables();
        final Board board = readTablesIfExistsJanggiTable();
        final Country type = readCurrentTurnIfExistsTurnTable();
        startJanggi(board, type);
    }

    private static Country readCurrentTurnIfExistsTurnTable() {
        if(janggiDatabase.existsJanggiTable()){
            return janggiDatabase.readCurrentTurn();
        }
        return Country.getFirstTurnCountry();
    }

    private static Board readTablesIfExistsJanggiTable() {
        if (janggiDatabase.existsJanggiRows()) {
            return janggiDatabase.readBoard();
        }
        final Board board = Board.createInitializedJanggiBoard();
        janggiDatabase.saveBoard(board);
        return board;
    }

    private static void createTablesIfNotExistsJanggiTables() {
        if (!janggiDatabase.existsJanggiTable()) {
            janggiDatabase.createJanggiTables();
        }
    }

    private static void startJanggi(final Board board, Country type) {
        while (board.isAliveAllGenerals()) {
            OutputView.printBoard(board, type);
            final List<JanggiPosition> janggiPositions = InputView.readPositions();
            board.updatePosition(janggiPositions.get(MOVE_SOURCE), janggiPositions.get(MOVE_DESTINATION), type);
            type = type.toggleCountry();
            updateMoveForDatabase(janggiPositions.get(MOVE_SOURCE), janggiPositions.get(MOVE_DESTINATION), board);
            janggiDatabase.updateTurn(type);
        }

        OutputView.printJanggiWinner(board);
    }

    private static void updateMoveForDatabase(final JanggiPosition source, final JanggiPosition destination,
                                              final Board board) {
        final Map<JanggiPosition, Piece> janggiBoard = board.getJanggiBoard();
        if (janggiBoard.containsKey(source)) {
            return;
        }
        janggiDatabase.removeJanggiRowByPosition(source);
        janggiDatabase.updateJanggiRowByPosition(destination, janggiBoard.get(destination));
    }
}

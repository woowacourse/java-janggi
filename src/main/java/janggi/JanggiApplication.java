package janggi;

import janggi.board.Board;
import janggi.coordinate.JanggiPosition;
import janggi.database.JanggiDatabase;
import janggi.piece.Country;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiApplication {
    private static final int MOVE_SOURCE = 0;
    private static final int MOVE_DESTINATION = 1;

    public static void main(String[] args) {
        OutputView.printIntroduce();
        final JanggiDatabase janggiDatabase = new JanggiDatabase();
        createTablesIfNotExistsJanggiTables(janggiDatabase);
        final Board board = Board.createInitializedJanggiBoard();
        startJanggi(board);
    }

    private static void createTablesIfNotExistsJanggiTables(final JanggiDatabase janggiDatabase) {
        if(!janggiDatabase.existsJanggiTable()){
            janggiDatabase.createJanggiTables();
        }
    }

    private static void startJanggi(final Board board) {
        Country type = Country.getFirstTurnCountry();

        while (board.isAliveAllGenerals()) {
            OutputView.printBoard(board, type);
            final List<JanggiPosition> janggiPositions = InputView.readPositions();
            board.updatePosition(janggiPositions.get(MOVE_SOURCE), janggiPositions.get(MOVE_DESTINATION), type);
            type = type.toggleCountry();
        }

        OutputView.printJanggiWinner(board);
    }
}

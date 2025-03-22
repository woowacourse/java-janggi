package domain.board;

import domain.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Zzu;
import java.util.HashMap;
import java.util.Map;

public class InitialBoardFixture {

    public static Map<BoardPosition, Piece> rawInitialBoard = new HashMap<>();

    static {
        rawInitialBoard.put(new BoardPosition(0, 3), new Zzu(Team.GREEN));
        rawInitialBoard.put(new BoardPosition(2, 3), new Zzu(Team.GREEN));
        rawInitialBoard.put(new BoardPosition(4, 3), new Zzu(Team.GREEN));
        rawInitialBoard.put(new BoardPosition(6, 3), new Zzu(Team.GREEN));
        rawInitialBoard.put(new BoardPosition(8, 3), new Zzu(Team.GREEN));

        rawInitialBoard.put(new BoardPosition(0, 6), new Zzu(Team.RED));
        rawInitialBoard.put(new BoardPosition(2, 6), new Zzu(Team.RED));
        rawInitialBoard.put(new BoardPosition(4, 6), new Zzu(Team.RED));
        rawInitialBoard.put(new BoardPosition(6, 6), new Zzu(Team.RED));
        rawInitialBoard.put(new BoardPosition(8, 6), new Zzu(Team.RED));

        rawInitialBoard.put(new BoardPosition(0, 0), new Chariot(Team.GREEN));
        rawInitialBoard.put(new BoardPosition(8, 0), new Chariot(Team.GREEN));

        rawInitialBoard.put(new BoardPosition(0, 9), new Chariot(Team.RED));
        rawInitialBoard.put(new BoardPosition(8, 9), new Chariot(Team.RED));

        rawInitialBoard.put(new BoardPosition(2, 0), new Horse(Team.GREEN));
        rawInitialBoard.put(new BoardPosition(6, 0), new Horse(Team.GREEN));

        rawInitialBoard.put(new BoardPosition(2, 9), new Horse(Team.RED));
        rawInitialBoard.put(new BoardPosition(6, 9), new Horse(Team.RED));

        rawInitialBoard.put(new BoardPosition(1, 0), new Elephant(Team.GREEN));
        rawInitialBoard.put(new BoardPosition(7, 0), new Elephant(Team.GREEN));

        rawInitialBoard.put(new BoardPosition(1, 9), new Elephant(Team.RED));
        rawInitialBoard.put(new BoardPosition(7, 9), new Elephant(Team.RED));

        rawInitialBoard.put(new BoardPosition(1, 2), new Cannon(Team.GREEN));
        rawInitialBoard.put(new BoardPosition(7, 2), new Cannon(Team.GREEN));

        rawInitialBoard.put(new BoardPosition(1, 7), new Cannon(Team.RED));
        rawInitialBoard.put(new BoardPosition(7, 7), new Cannon(Team.RED));

        rawInitialBoard.put(new BoardPosition(4, 1), new General(Team.GREEN));
        rawInitialBoard.put(new BoardPosition(4, 8), new General(Team.RED));

        rawInitialBoard.put(new BoardPosition(3, 0), new Guard(Team.GREEN));
        rawInitialBoard.put(new BoardPosition(5, 0), new Guard(Team.GREEN));

        rawInitialBoard.put(new BoardPosition(3, 9), new Guard(Team.RED));
        rawInitialBoard.put(new BoardPosition(5, 9), new Guard(Team.RED));
    }
}

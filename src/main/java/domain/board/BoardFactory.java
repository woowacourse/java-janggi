package domain.board;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import domain.piece.BasicPiece;
import domain.piece.Cha;
import domain.piece.Jang;
import domain.piece.Jol;
import domain.piece.None;
import domain.piece.Po;
import domain.piece.Sa;
import domain.player.Team;
import domain.position.Position;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public static Board createWithFormation(Formation choFormation, Formation hanFormation) {
        Map<Position, BasicPiece> board = createInitialBoard();
        choFormation.putFormation(board, Team.CHO);
        hanFormation.putFormation(board, Team.HAN);
        return new Board(board);
    }

    private static Map<Position, BasicPiece> createInitialBoard() {
        Map<Position, BasicPiece> board = new HashMap<>();
        initializeAllPositions(board);
        addChoPieces(board);
        addHanPieces(board);

        return board;
    }

    private static void initializeAllPositions(Map<Position, BasicPiece> board) {
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                board.put(new Position(row, column), None.getInstance());
            }
        }
    }

    private static void addHanPieces(Map<Position, BasicPiece> board) {
        board.put(new Position(0, 0), new Cha(Team.HAN));
        board.put(new Position(0, 3), new Sa(Team.HAN));
        board.put(new Position(0, 5), new Sa(Team.HAN));
        board.put(new Position(0, 8), new Cha(Team.HAN));

        board.put(new Position(1, 4), new Jang(Team.HAN));

        board.put(new Position(2, 1), new Po(Team.HAN));
        board.put(new Position(2, 7), new Po(Team.HAN));

        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column += 2) {
            board.put(new Position(3, column), new Jol(Team.HAN));
        }
    }

    private static void addChoPieces(Map<Position, BasicPiece> board) {
        board.put(new Position(9, 0), new Cha(Team.CHO));
        board.put(new Position(9, 3), new Sa(Team.CHO));
        board.put(new Position(9, 5), new Sa(Team.CHO));
        board.put(new Position(9, 8), new Cha(Team.CHO));

        board.put(new Position(8, 4), new Jang(Team.CHO));

        board.put(new Position(7, 1), new Po(Team.CHO));
        board.put(new Position(7, 7), new Po(Team.CHO));

        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column += 2) {
            board.put(new Position(6, column), new Jol(Team.CHO));
        }
    }
}

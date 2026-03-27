package domain.board;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import domain.piece.Cha;
import domain.piece.Jang;
import domain.piece.Jol;
import domain.piece.Ma;
import domain.piece.None;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.player.Team;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {

    public static Board createWithFormation(Formation choFormation, Formation hanFormation) {
        Map<Position, Piece> board = createInitialBoard();
        putFormation(board, choFormation, Team.CHO);
        putFormation(board, hanFormation, Team.HAN);
        return new Board(board);
    }

    private static void putFormation(Map<Position, Piece> board, Formation inputFormation, Team team) {
        List<PieceType> formation = inputFormation.getFormation();
        List<Integer> columnPositions = List.of(1, 2, 6, 7);

        for (int i = 0; i < formation.size(); i++) {
            PieceType pieceType = formation.get(i);
            int column = columnPositions.get(i);
            Piece piece = PieceFactory.createPiece(team, pieceType);
            Position position = new Position(team.getColumn(), column);
            board.put(position, piece);
        }
    }

    private static Map<Position, Piece> createInitialBoard() {
        Map<Position, Piece> board = new HashMap<>();

        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                board.put(new Position(row, column), new None());
            }
        }
        addChoPieces(board);
        addHanPieces(board);

        return board;
    }

    private static void addHanPieces(Map<Position, Piece> board) {
        board.put(new Position(0, 0), new Cha(Team.HAN));
        board.put(new Position(0, 1), new Sang(Team.HAN));
        board.put(new Position(0, 2), new Ma(Team.HAN));
        board.put(new Position(0, 3), new Sa(Team.HAN));
        board.put(new Position(0, 4), new None());
        board.put(new Position(0, 5), new Sa(Team.HAN));
        board.put(new Position(0, 6), new Ma(Team.HAN));
        board.put(new Position(0, 7), new Sang(Team.HAN));
        board.put(new Position(0, 8), new Cha(Team.HAN));

        board.put(new Position(1, 4), new Jang(Team.HAN));

        board.put(new Position(2, 1), new Po(Team.HAN));
        board.put(new Position(2, 7), new Po(Team.HAN));

        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column += 2) {
            board.put(new Position(3, column), new Jol(Team.HAN));
        }
    }

    private static void addChoPieces(Map<Position, Piece> board) {
        board.put(new Position(9, 0), new Cha(Team.CHO));
        board.put(new Position(9, 1), new Sang(Team.CHO));
        board.put(new Position(9, 2), new Ma(Team.CHO));
        board.put(new Position(9, 3), new Sa(Team.CHO));
        board.put(new Position(9, 4), new None());
        board.put(new Position(9, 5), new Sa(Team.CHO));
        board.put(new Position(9, 6), new Ma(Team.CHO));
        board.put(new Position(9, 7), new Sang(Team.CHO));
        board.put(new Position(9, 8), new Cha(Team.CHO));

        board.put(new Position(8, 4), new Jang(Team.CHO));

        board.put(new Position(7, 1), new Po(Team.CHO));
        board.put(new Position(7, 7), new Po(Team.CHO));

        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column += 2) {
            board.put(new Position(6, column), new Jol(Team.CHO));
        }
    }
}

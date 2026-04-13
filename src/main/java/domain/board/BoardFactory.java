package domain.board;

import static domain.board.Board.MAX_COLUMN;
import static domain.board.Board.MAX_ROW;
import static domain.board.Board.MIN_COLUMN;
import static domain.board.Board.MIN_ROW;

import domain.piece.Cha;
import domain.piece.Jang;
import domain.piece.Jol;
import domain.piece.None;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Po;
import domain.piece.Sa;
import domain.player.Team;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {
    private static final Map<Team, Integer> NORMAL_PIECE_ROWS = Map.of(
            Team.CHO, 9,
            Team.HAN, 0
    );
    private static final Map<Team, Integer> JANG_ROWS = Map.of(
            Team.CHO, 8,
            Team.HAN, 1
    );
    private static final Map<Team, Integer> PO_ROWS = Map.of(
            Team.CHO, 7,
            Team.HAN, 2
    );
    private static final Map<Team, Integer> JOL_ROWS = Map.of(
            Team.CHO, 6,
            Team.HAN, 3
    );

    public static Board createWithFormation(Formation choFormation, Formation hanFormation) {
        Map<Position, Piece> board = createEmptyBoard();
        putFixedPieces(board, Team.CHO);
        putFixedPieces(board, Team.HAN);
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
            Piece piece = pieceType.create(team);
            Position position = new Position(NORMAL_PIECE_ROWS.get(team), column);
            board.put(position, piece);
        }
    }

    private static Map<Position, Piece> createEmptyBoard() {
        Map<Position, Piece> board = new HashMap<>();

        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                board.put(new Position(row, column), new None());
            }
        }
        return board;
    }

    private static void putFixedPieces(Map<Position, Piece> board, Team team) {
        int normalPieceRow = NORMAL_PIECE_ROWS.get(team);

        board.put(new Position(normalPieceRow, 0), new Cha(team));
        board.put(new Position(normalPieceRow, 3), new Sa(team));
        board.put(new Position(normalPieceRow, 4), new None());
        board.put(new Position(normalPieceRow, 5), new Sa(team));
        board.put(new Position(normalPieceRow, 8), new Cha(team));

        board.put(new Position(JANG_ROWS.get(team), 4), new Jang(team));

        board.put(new Position(PO_ROWS.get(team), 1), new Po(team));
        board.put(new Position(PO_ROWS.get(team), 7), new Po(team));

        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column += 2) {
            board.put(new Position(JOL_ROWS.get(team), column), new Jol(team));
        }
    }
}

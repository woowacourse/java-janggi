package domain.board;

import static domain.board.Board.MAX_COLUMN_RANGE;
import static domain.board.Board.MAX_ROW_RANGE;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.player.Team;
import java.util.HashMap;
import java.util.Map;

public class BoardInitializer {

    public static Board initialize(ElephantSetup choSetup, ElephantSetup hanSetup) {
        Map<Position, Piece> pieces = new HashMap<>();

        putDefaultPiece(pieces, Team.HAN);
        putDefaultPiece(pieces, Team.CHO);

        setupElephant(pieces, hanSetup, Team.HAN);
        setupElephant(pieces, choSetup, Team.CHO);

        return Board.of(pieces);
    }


    private static void putDefaultPiece(Map<Position, Piece> pieces, Team team) {
        put(pieces, Position.of(1, 1), PieceType.CHARIOT, team);
        put(pieces, Position.of(1, 9), PieceType.CHARIOT, team);

        put(pieces, Position.of(1, 4), PieceType.GUARD, team);
        put(pieces, Position.of(1, 6), PieceType.GUARD, team);

        put(pieces, Position.of(2, 5), PieceType.GENERAL, team);

        put(pieces, Position.of(3, 2), PieceType.CANNON, team);
        put(pieces, Position.of(3, 8), PieceType.CANNON, team);

        put(pieces, Position.of(4, 1), PieceType.SOLDIER, team);
        put(pieces, Position.of(4, 3), PieceType.SOLDIER, team);
        put(pieces, Position.of(4, 5), PieceType.SOLDIER, team);
        put(pieces, Position.of(4, 7), PieceType.SOLDIER, team);
        put(pieces, Position.of(4, 9), PieceType.SOLDIER, team);
    }

    private static void put(Map<Position, Piece> pieces, Position pos, PieceType type, Team team) {
        pieces.put(transform(pos, team), Piece.of(type, team));
    }

    private static Position transform(Position pos, Team team) {
        if (team == Team.CHO) {
            return mirror(pos);
        }
        return pos;
    }

    private static Position mirror(Position pos) {
        int mirroredRow = MAX_ROW_RANGE - pos.row() + 1;
        int mirroredColumn = MAX_COLUMN_RANGE - pos.column() + 1;
        return Position.of(mirroredRow, mirroredColumn);
    }


    private static void setupElephant(Map<Position, Piece> pieces, ElephantSetup setup, Team team) {
        for (Map.Entry<Position, PieceType> entry : setup.getPiecePositions().entrySet()) {
            put(pieces, entry.getKey(), entry.getValue(), team);
        }
    }
}

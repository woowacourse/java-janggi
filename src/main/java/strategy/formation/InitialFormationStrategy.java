package strategy.formation;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.board.Position;
import domain.piece.TeamColor;
import java.util.HashMap;
import java.util.Map;

public abstract class InitialFormationStrategy {
    protected static final int HAN_BACK_RANK_ROW = 0;
    protected static final int CHO_BACK_RANK_ROW = 9;
    private static final int KING_DISTANCE_FROM_BACK_RANK = 1;
    private static final int CANNON_DISTANCE_FROM_BACK_RANK = 2;
    private static final int PAWN_DISTANCE_FROM_BACK_RANK = 3;
    private static final int LEFT_EDGE_COLUMN = 0;
    private static final int LEFT_CANNON_COLUMN = 1;
    private static final int PALACE_LEFT_GUARD_COLUMN = 3;
    private static final int PALACE_CENTER_COLUMN = 4;
    private static final int PALACE_RIGHT_GUARD_COLUMN = 5;
    private static final int RIGHT_CANNON_COLUMN = 7;
    private static final int RIGHT_EDGE_COLUMN = 8;
    private static final int PAWN_COLUMN_INTERVAL = 2;

    public final Map<Position, Piece> createInitialPieces(TeamColor teamColor) {
        final Map<Position, Piece> formationPieces = createFormationPieces(teamColor);
        final Map<Position, Piece> fixedPieces = placeFixedPieces(teamColor);
        final Map<Position, Piece> allPieces = new HashMap<>();

        allPieces.putAll(formationPieces);
        allPieces.putAll(fixedPieces);

        return allPieces;
    }

    protected abstract Map<Position, Piece> createFormationPieces(TeamColor teamColor);

    protected final int findBackRankRow(TeamColor teamColor) {
        if (teamColor == TeamColor.CHO) {
            return CHO_BACK_RANK_ROW;
        }
        return HAN_BACK_RANK_ROW;
    }

    protected final int findRowFromBackRank(TeamColor teamColor, int distanceFromBackRank) {
        final int backRankRow = findBackRankRow(teamColor);
        if (teamColor == TeamColor.CHO) {
            return backRankRow - distanceFromBackRank;
        }
        return backRankRow + distanceFromBackRank;
    }

    private Map<Position, Piece> placeFixedPieces(TeamColor teamColor) {
        final int backRankRow = findBackRankRow(teamColor);
        final int kingRow = findRowFromBackRank(teamColor, KING_DISTANCE_FROM_BACK_RANK);
        final int cannonRow = findRowFromBackRank(teamColor, CANNON_DISTANCE_FROM_BACK_RANK);
        final int pawnRow = findRowFromBackRank(teamColor, PAWN_DISTANCE_FROM_BACK_RANK);

        return createFixedMap(teamColor, backRankRow, kingRow, cannonRow, pawnRow);
    }

    private Map<Position, Piece> createFixedMap(TeamColor teamColor, int backRankRow, int kingRow, int cannonRow, int pawnRow) {
        final Map<Position, Piece> map = new HashMap<>();

        map.put(Position.of(backRankRow, LEFT_EDGE_COLUMN), Piece.of(teamColor, PieceType.ROOK));
        map.put(Position.of(backRankRow, RIGHT_EDGE_COLUMN), Piece.of(teamColor, PieceType.ROOK));

        map.put(Position.of(backRankRow, PALACE_LEFT_GUARD_COLUMN), Piece.of(teamColor, PieceType.GUARD));
        map.put(Position.of(backRankRow, PALACE_RIGHT_GUARD_COLUMN), Piece.of(teamColor, PieceType.GUARD));

        map.put(Position.of(kingRow, PALACE_CENTER_COLUMN), Piece.of(teamColor, PieceType.KING));

        map.put(Position.of(cannonRow, LEFT_CANNON_COLUMN), Piece.of(teamColor, PieceType.CANNON));
        map.put(Position.of(cannonRow, RIGHT_CANNON_COLUMN), Piece.of(teamColor, PieceType.CANNON));

        for (int column = LEFT_EDGE_COLUMN; column <= RIGHT_EDGE_COLUMN; column += PAWN_COLUMN_INTERVAL) {
            map.put(Position.of(pawnRow, column), Piece.of(teamColor, PieceType.PAWN));
        }

        return map;
    }
}


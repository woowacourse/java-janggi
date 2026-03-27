package strategy.formation;

import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;
import java.util.HashMap;
import java.util.Map;

public abstract class InitialFormationStrategy {

    public final Map<Position, Piece> setUpPieces(TeamColor teamColor) {

        Map<Position, Piece> formationPieces = setupFormation(teamColor);
        Map<Position, Piece> fixedPieces = placeFixedPieces(teamColor);
        Map<Position, Piece> allPieces = new HashMap<>();

        allPieces.putAll(formationPieces);
        allPieces.putAll(fixedPieces);

        return allPieces;
    }

    protected abstract Map<Position, Piece> setupFormation(TeamColor teamColor);

    private Map<Position, Piece> placeFixedPieces(TeamColor teamColor) {
        if (teamColor == TeamColor.HAN) {
            return createFixedMap(teamColor, 0, 1, 2, 3);
        }
        return createFixedMap(teamColor, 9, 8, 7, 6);
    }

    private Map<Position, Piece> createFixedMap(TeamColor teamColor, int backRankRow, int kingRow, int cannonRow, int pawnRow) {
        Map<Position, Piece> map = new HashMap<>();

        // 1. 차 (ROOK) - 양쪽 맨 끝 (column = 0, 8)
        map.put(Position.of(backRankRow, 0), Piece.of(teamColor, PieceType.ROOK));
        map.put(Position.of(backRankRow, 8), Piece.of(teamColor, PieceType.ROOK));

        // 2. 사 (GUARD) - 궁성 안쪽 귀 (column = 3, 5)
        map.put(Position.of(backRankRow, 3), Piece.of(teamColor, PieceType.GUARD));
        map.put(Position.of(backRankRow, 5), Piece.of(teamColor, PieceType.GUARD));

        // 3. 왕 (KING) - 궁성 중앙 (column = 4)
        map.put(Position.of(kingRow, 4), Piece.of(teamColor, PieceType.KING));

        // 4. 포 (CANNON) - (column = 1, 7)
        map.put(Position.of(cannonRow, 1), Piece.of(teamColor, PieceType.CANNON));
        map.put(Position.of(cannonRow, 7), Piece.of(teamColor, PieceType.CANNON));

        // 5. 졸/병 (PAWN) - 5개 (column = 0, 2, 4, 6, 8)
        for (int column = 0; column <= 8; column += 2) {
            map.put(Position.of(pawnRow, column), Piece.of(teamColor, PieceType.PAWN));
        }

        return map;
    }
}

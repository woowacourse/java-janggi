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

    private Map<Position, Piece> createFixedMap(TeamColor teamColor, int backRankY, int kingY, int cannonY, int pawnY) {
        Map<Position, Piece> map = new HashMap<>();

        // 1. 차 (ROOK) - 양쪽 맨 끝 (x = 0, 8)
        map.put(Position.of(0, backRankY), Piece.of(teamColor, PieceType.ROOK));
        map.put(Position.of(8, backRankY), Piece.of(teamColor, PieceType.ROOK));

        // 2. 사 (GUARD) - 궁성 안쪽 귀 (x = 3, 5)
        map.put(Position.of(3, backRankY), Piece.of(teamColor, PieceType.GUARD));
        map.put(Position.of(5, backRankY), Piece.of(teamColor, PieceType.GUARD));

        // 3. 왕 (KING) - 궁성 중앙 (x = 4)
        map.put(Position.of(4, kingY), Piece.of(teamColor, PieceType.KING));

        // 4. 포 (CANNON) - (x = 1, 7)
        map.put(Position.of(1, cannonY), Piece.of(teamColor, PieceType.CANNON));
        map.put(Position.of(7, cannonY), Piece.of(teamColor, PieceType.CANNON));

        // 5. 졸/병 (PAWN) - 5개 (x = 0, 2, 4, 6, 8)
        for (int x = 0; x <= 8; x += 2) {
            map.put(Position.of(x, pawnY), Piece.of(teamColor, PieceType.PAWN));
        }

        return map;
    }
}

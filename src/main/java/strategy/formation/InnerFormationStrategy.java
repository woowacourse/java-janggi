package strategy.formation;

import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;
import java.util.HashMap;
import java.util.Map;

public class InnerFormationStrategy implements  InitialFormationStrategy{

    public Map<Position, Piece> setupFormation(TeamColor teamColor) {
        Map<Position, Piece> formation = new HashMap<>();
        int y;
        if (teamColor.equals(TeamColor.CHO)) {
            y =9;
            // 왼쪽 마 (x=1)
            formation.put(Position.of(1, y), Piece.of(teamColor, PieceType.HORSE));
            // 왼쪽 상 (x=2)
            formation.put(Position.of(2, y), Piece.of(teamColor, PieceType.ELEPHANT));
            // 오른쪽 상 (x=6)
            formation.put(Position.of(6, y), Piece.of(teamColor, PieceType.ELEPHANT));
            // 오른쪽 마 (x=7)
            formation.put(Position.of(7, y), Piece.of(teamColor, PieceType.HORSE));
            return formation;
        }
        y=0;
        // 왼쪽 마 (x=1)
        formation.put(Position.of(1, y), Piece.of(teamColor, PieceType.HORSE));
        // 왼쪽 상 (x=2)
        formation.put(Position.of(2, y), Piece.of(teamColor, PieceType.ELEPHANT));
        // 오른쪽 상 (x=6)
        formation.put(Position.of(6, y), Piece.of(teamColor, PieceType.ELEPHANT));
        // 오른쪽 마 (x=7)
        formation.put(Position.of(7, y), Piece.of(teamColor, PieceType.HORSE));
        return formation;

    }
}

package strategy.formation;

import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;
import java.util.HashMap;
import java.util.Map;

public class OuterFormationStrategy implements InitialFormationStrategy {

    @Override
    public Map<Position, Piece> setupFormation(TeamColor teamColor) {
        Map<Position, Piece> formation = new HashMap<>();
        int y = teamColor == TeamColor.CHO ? 9 : 0;

        formation.put(Position.of(1, y), Piece.of(teamColor, PieceType.ELEPHANT));
        formation.put(Position.of(2, y), Piece.of(teamColor, PieceType.HORSE));
        formation.put(Position.of(6, y), Piece.of(teamColor, PieceType.HORSE));
        formation.put(Position.of(7, y), Piece.of(teamColor, PieceType.ELEPHANT));
        return formation;
    }
}

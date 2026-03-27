package strategy.formation;

import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;
import java.util.HashMap;
import java.util.Map;

public class OuterFormationStrategy extends InitialFormationStrategy {

    @Override
    protected Map<Position, Piece> setupFormation(TeamColor teamColor) {
        Map<Position, Piece> formation = new HashMap<>();
        int row = teamColor == TeamColor.CHO ? 9 : 0;

        formation.put(Position.of(row, 1), Piece.of(teamColor, PieceType.ELEPHANT));
        formation.put(Position.of(row, 2), Piece.of(teamColor, PieceType.HORSE));
        formation.put(Position.of(row, 6), Piece.of(teamColor, PieceType.HORSE));
        formation.put(Position.of(row, 7), Piece.of(teamColor, PieceType.ELEPHANT));
        return formation;
    }
}

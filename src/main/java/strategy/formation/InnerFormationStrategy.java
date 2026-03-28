package strategy.formation;

import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;
import java.util.HashMap;
import java.util.Map;

public class InnerFormationStrategy extends InitialFormationStrategy {
    private static final int LEFT_HORSE_COLUMN = 1;
    private static final int LEFT_ELEPHANT_COLUMN = 2;
    private static final int RIGHT_ELEPHANT_COLUMN = 6;
    private static final int RIGHT_HORSE_COLUMN = 7;

    @Override
    protected Map<Position, Piece> setupFormation(TeamColor teamColor) {
        if (teamColor.equals(TeamColor.CHO)) {
            return createFormation(teamColor, CHO_BACK_RANK_ROW);
        }
        return createFormation(teamColor, HAN_BACK_RANK_ROW);
    }

    private Map<Position, Piece> createFormation(TeamColor teamColor, int row) {
        final Map<Position, Piece> formation = new HashMap<>();
        formation.put(Position.of(row, LEFT_HORSE_COLUMN), Piece.of(teamColor, PieceType.HORSE));
        formation.put(Position.of(row, LEFT_ELEPHANT_COLUMN), Piece.of(teamColor, PieceType.ELEPHANT));
        formation.put(Position.of(row, RIGHT_ELEPHANT_COLUMN), Piece.of(teamColor, PieceType.ELEPHANT));
        formation.put(Position.of(row, RIGHT_HORSE_COLUMN), Piece.of(teamColor, PieceType.HORSE));
        return formation;
    }
}

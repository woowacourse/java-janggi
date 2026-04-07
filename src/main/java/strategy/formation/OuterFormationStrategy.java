package strategy.formation;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.board.Position;
import domain.piece.TeamColor;
import java.util.HashMap;
import java.util.Map;

public class OuterFormationStrategy extends InitialFormationStrategy {
    private static final int LEFT_ELEPHANT_COLUMN = 1;
    private static final int LEFT_HORSE_COLUMN = 2;
    private static final int RIGHT_HORSE_COLUMN = 6;
    private static final int RIGHT_ELEPHANT_COLUMN = 7;

    @Override
    protected Map<Position, Piece> createFormationPieces(TeamColor teamColor) {
        final Map<Position, Piece> formation = new HashMap<>();
        final int row = findBackRankRow(teamColor);

        formation.put(Position.of(row, LEFT_ELEPHANT_COLUMN), Piece.of(teamColor, PieceType.ELEPHANT));
        formation.put(Position.of(row, LEFT_HORSE_COLUMN), Piece.of(teamColor, PieceType.HORSE));
        formation.put(Position.of(row, RIGHT_HORSE_COLUMN), Piece.of(teamColor, PieceType.HORSE));
        formation.put(Position.of(row, RIGHT_ELEPHANT_COLUMN), Piece.of(teamColor, PieceType.ELEPHANT));
        return formation;
    }
}



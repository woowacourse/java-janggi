package domain.board;

import domain.game.Team;
import domain.piece.Piece;
import domain.piece.PieceDefinition;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class WonangmaFactory extends BoardFactory {

    private static final List<PieceDefinition> FORMATION = List.of(
            PieceDefinition.SANG, PieceDefinition.MA, PieceDefinition.MA, PieceDefinition.SANG
    );

    @Override
    protected void setVariablePieces(Map<Position, Piece> pieces, Team team) {
        placeVariablePieces(pieces, team, FORMATION);
    }
}

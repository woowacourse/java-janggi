package domain.board;

import domain.game.Team;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class WonangmaFactory extends AbstractBoardFactory {

    private static final List<PieceType> FORMATION = List.of(
            PieceType.SANG, PieceType.MA, PieceType.MA, PieceType.SANG
    );

    @Override
    protected void setVariablePieces(Map<Position, Piece> pieces, Team team) {
        placeVariablePieces(pieces, team, FORMATION);
    }
}

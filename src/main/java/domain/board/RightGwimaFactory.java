package domain.board;

import domain.position.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.game.Team;
import java.util.Map;

public class RightGwimaFactory extends AbstractFormationFactory {
    @Override
    protected void setVariablePieces(Map<Position, Piece> pieces, Team team) {
        pieces.put(new Position(TeamLayout.of(team).backRow(), PieceType.MA.getInitialColumns().get(0)),
                PieceType.MA.createPiece(team));
        pieces.put(new Position(TeamLayout.of(team).backRow(), PieceType.SANG.getInitialColumns().get(1)),
                PieceType.SANG.createPiece(team));
        pieces.put(new Position(TeamLayout.of(team).backRow(), PieceType.MA.getInitialColumns().get(2)),
                PieceType.MA.createPiece(team));
        pieces.put(new Position(TeamLayout.of(team).backRow(), PieceType.SANG.getInitialColumns().get(3)),
                PieceType.SANG.createPiece(team));
    }
}



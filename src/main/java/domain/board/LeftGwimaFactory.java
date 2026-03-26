package domain.board;

import domain.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.Map;

public class LeftGwimaFactory extends AbstractBoardFactory {
    @Override
    protected void setVariablePieces(Map<Position, Piece> pieces, Team team) {
        pieces.put(new Position(team.getBackRow(), PieceType.SANG.getInitialColumns().get(0)),
                PieceType.SANG.createPiece(team));
        pieces.put(new Position(team.getBackRow(), PieceType.MA.getInitialColumns().get(1)),
                PieceType.MA.createPiece(team));
        pieces.put(new Position(team.getBackRow(), PieceType.SANG.getInitialColumns().get(2)),
                PieceType.SANG.createPiece(team));
        pieces.put(new Position(team.getBackRow(), PieceType.MA.getInitialColumns().get(3)),
                PieceType.MA.createPiece(team));
    }
}

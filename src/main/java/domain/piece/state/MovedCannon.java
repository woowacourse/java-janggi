package domain.piece.state;

import domain.piece.Piece;
import domain.piece.PieceSymbol;
import java.util.List;

public class MovedCannon extends ContinuousPiece {
    @Override
    public void validateMove(List<Piece> hurdlePieces) {
        if (hurdlePieces.isEmpty()) {
            throw new IllegalArgumentException("경로에 장애물이 1개 있어야 움직일 수 있습니다.");
        }
        if (hurdlePieces.size() > 1) {
            throw new IllegalArgumentException("경로에 장애물이 2개 이상 있어서 움직일 수 없습니다.");
        }
        if (hurdlePieces.getFirst().getPieceSymbol().equals(PieceSymbol.CANNON)) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }

    @Override
    public PieceState updateState() {
        return new MovedCannon();
    }
}

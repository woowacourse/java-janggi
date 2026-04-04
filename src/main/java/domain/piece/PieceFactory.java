package domain.piece;

import domain.piece.strategy.EmptyMoveStrategy;
import domain.piece.strategy.ByeongMoveStrategy;
import domain.piece.strategy.JolMoveStrategy;
import domain.piece.strategy.MaMoveStrategy;
import domain.piece.strategy.SangMoveStrategy;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.piece.strategy.SlidingMoveStrategy;
import domain.piece.strategy.component.PalaceMoveRule;

public final class PieceFactory {

    private PieceFactory() {
    }

    public static Piece create(PieceType pieceType, Team team) {
        PalaceMoveRule palaceMoveRule = new PalaceMoveRule();
        return switch (pieceType) {
            case PO -> new Po(new SlidingMoveStrategy(palaceMoveRule), team);
            case MA -> new Ma(new MaMoveStrategy(), team);
            case SANG -> new Sang(new SangMoveStrategy(), team);
            case SA -> new Sa(new SingleStepMoveStrategy(palaceMoveRule), team);
            case JANG -> new Jang(new SingleStepMoveStrategy(palaceMoveRule), team);
            case CHA -> new Cha(new SlidingMoveStrategy(palaceMoveRule), team);
            case JOL -> new Jol(new JolMoveStrategy(palaceMoveRule), team);
            case BYEONG -> new Byeong(new ByeongMoveStrategy(palaceMoveRule), team);
            case EMPTY -> new EmptyPiece(new EmptyMoveStrategy(), Team.UNDEFINED);
        };
    }
}

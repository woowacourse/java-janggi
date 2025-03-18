package strategy;

import java.util.Map;
import piece.PieceType;

public class MoveStrategyMapper {

    private static final Map<PieceType,MoveStrategy> moveStrategyMapper;

    static {
        moveStrategyMapper = Map.of(
                PieceType.CHA,new ChaMoveStrategy(),
                PieceType.SANG,new SangMoveStrategy(),
                PieceType.MA,new MaMoveStrategy(),
                PieceType.SA,new SaMoveStrategy(),
                PieceType.GUNG,new GungMoveStrategy(),
                PieceType.JOL,new JolMoveStrategy());
    }
    public static MoveStrategy from(PieceType pieceType) {
        return moveStrategyMapper.get(pieceType);
    }
}

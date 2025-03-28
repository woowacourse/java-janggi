package domain.position.generator;

import domain.janggiPiece.JanggiChessPiece;
import domain.position.JanggiPosition;

import java.util.Map;

public class EmptyPositionsGenerator implements JanggiPiecePositionsGenerator {

    @Override
    public Map<JanggiPosition, JanggiChessPiece> generate() {
        return Map.of();
    }
}

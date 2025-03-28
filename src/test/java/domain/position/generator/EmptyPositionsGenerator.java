package domain.position.generator;

import domain.janggiPiece.JanggiPiece;
import domain.position.JanggiPosition;

import java.util.Map;

public class EmptyPositionsGenerator implements JanggiPiecePositionsGenerator {

    @Override
    public Map<JanggiPosition, JanggiPiece> generate() {
        return Map.of();
    }
}

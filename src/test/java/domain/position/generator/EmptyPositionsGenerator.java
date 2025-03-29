package domain.position.generator;

import domain.janggiPiece.JanggiChessPiece;
import domain.position.JanggiPosition;

import java.util.Map;

public class EmptyPositionsGenerator implements DefaultPositionsGenerator {

    @Override
    public Map<JanggiPosition, JanggiChessPiece> generate() {
        return Map.of();
    }
}

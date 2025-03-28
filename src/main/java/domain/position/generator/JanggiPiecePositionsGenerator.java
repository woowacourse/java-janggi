package domain.position.generator;

import domain.janggiPiece.JanggiChessPiece;
import domain.position.JanggiPosition;

import java.util.Map;

public interface JanggiPiecePositionsGenerator {
    Map<JanggiPosition, JanggiChessPiece> generate();
}

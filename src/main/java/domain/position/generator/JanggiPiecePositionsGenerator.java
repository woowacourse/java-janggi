package domain.position.generator;

import domain.janggiPiece.JanggiPiece;
import domain.position.JanggiPosition;

import java.util.Map;

public interface JanggiPiecePositionsGenerator {
    Map<JanggiPosition, JanggiPiece> generate();
}

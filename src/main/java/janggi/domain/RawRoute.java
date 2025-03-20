package janggi.domain;

import janggi.domain.piece.RawPosition;
import java.util.List;

public record RawRoute(List<RawPosition> rawPositions) {
}

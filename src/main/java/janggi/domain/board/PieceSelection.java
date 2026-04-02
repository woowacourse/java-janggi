package janggi.domain.board;

import java.util.List;

public record PieceSelection(Position selected, List<Position> destinations) {
}

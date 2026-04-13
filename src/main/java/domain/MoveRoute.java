package domain;

import java.util.List;

public record MoveRoute(Position destination, List<Position> route) {
}

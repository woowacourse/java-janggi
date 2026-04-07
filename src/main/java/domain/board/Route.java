package domain.board;

import java.util.List;

public record Route(Position startPos, Position endPos, List<Position> intermediatePositions) {}




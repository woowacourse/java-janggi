package domain;

import java.util.List;

public record Route(Position startPos, Position endPos, List<Position> intermeidateNodes) {}

package janggi.dto;

import janggi.domain.Position;
import java.util.Map;

public record BoardSpots(
    Map<Position, BoardSpot> value
) {

}

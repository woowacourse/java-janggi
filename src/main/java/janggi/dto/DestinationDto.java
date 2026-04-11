package janggi.dto;

import janggi.domain.space.Destinations;
import java.util.List;

public record DestinationDto(List<PositionDto> positions) {

    public static DestinationDto from(Destinations destinations) {
        return new DestinationDto(
                destinations.getPositions().stream()
                        .map(PositionDto::from)
                        .toList()
        );
    }
}

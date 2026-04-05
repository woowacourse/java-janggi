package dto;

import domain.Destinations;
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

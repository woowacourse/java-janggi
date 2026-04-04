package dto;

import domain.MoveCandidate;
import java.util.List;

public record DestinationDto(List<PositionDto> positions) {

    public static DestinationDto from(MoveCandidate moveCandidate) {
        return new DestinationDto(
                moveCandidate.destinations()
                        .getPositions()
                        .stream()
                        .map(PositionDto::from)
                        .toList()
        );
    }
}

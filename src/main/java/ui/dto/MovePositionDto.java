package ui.dto;

public class MovePositionDto {
    private final PositionDto start;
    private final PositionDto destination;

    public MovePositionDto(PositionDto start, PositionDto destination) {
        this.start = start;
        this.destination = destination;
    }

    public PositionDto getStart() {
        return start;
    }

    public PositionDto getDestination() {
        return destination;
    }
}

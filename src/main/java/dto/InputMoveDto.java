package dto;

public class InputMoveDto {
    private final InputPointDto from;
    private final InputPointDto to;

    public InputMoveDto(InputPointDto from, InputPointDto to) {
        this.from = from;
        this.to = to;
    }

    public InputPointDto getFrom() {
        return from;
    }

    public InputPointDto getTo() {
        return to;
    }
}

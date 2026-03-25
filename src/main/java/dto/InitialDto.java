package dto;

import domain.board.Placement;

public record InitialDto(
        Placement choPlacement,
        Placement hanPlacement
) {
    public static InitialDto of(Placement choPlacement, Placement hanPlacement) {
        return new InitialDto(choPlacement, hanPlacement);
    }
}

package dto;

import domain.JanggiStatus;
import domain.Turn;

public record JanggiDto(
        int id,
        String title,
        Turn turn,
        JanggiStatus status
) {
}

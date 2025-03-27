package dto;

import domain.janggi.JanggiStatus;
import domain.janggi.Turn;

public record JanggiDto(
        int id,
        String title,
        Turn turn,
        JanggiStatus status
) {
}

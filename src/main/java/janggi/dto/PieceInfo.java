package janggi.dto;

import janggi.domain.status.Team;

public record PieceInfo(
        String name,
        Team team
) {
}

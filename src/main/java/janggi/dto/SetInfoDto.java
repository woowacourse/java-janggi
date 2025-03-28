package janggi.dto;

import janggi.domain.JanggiGame;

public record SetInfoDto(
        JanggiGame janggiGame,
        int roomId
) {
}

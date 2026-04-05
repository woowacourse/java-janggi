package janggi.application;

import janggi.domain.game.Game;

public record GameDto(
        Long id,
        Game game
){
}

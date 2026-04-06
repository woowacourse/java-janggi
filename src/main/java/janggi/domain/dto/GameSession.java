package janggi.domain.dto;

import janggi.domain.Board;

public record GameSession(
    long gameId,
    Board board
) {

}

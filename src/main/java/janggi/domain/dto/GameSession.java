package janggi.domain.dto;

import janggi.domain.Board;
import janggi.domain.Team;

public record GameSession(
    long gameId,
    Board board,
    Team turn
) {

}

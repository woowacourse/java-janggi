package janggi.domain;

public record GameSession(
    long gameId,
    Board board,
    Team turn
) {

}

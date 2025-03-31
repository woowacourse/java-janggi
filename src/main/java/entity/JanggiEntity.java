package entity;

import domain.board.Board;
import domain.janggi.Janggi;
import domain.janggi.JanggiStatus;
import domain.janggi.Team;
import domain.janggi.Turn;

public record JanggiEntity(
        int id,
        String title,
        Team team,
        JanggiStatus status
) {

    public Janggi toJanggi(final Board board) {
        return new Janggi(
                id, title, board, new Turn(team)
        );
    }
}

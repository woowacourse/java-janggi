package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Team;
import janggi.domain.position.Position;
import janggi.exception.business.InvalidTurnException;

public class JanggiGame {
    private final Board board;
    private Team currentTeam;

    public JanggiGame(Board board) {
        this.board = board;
        this.currentTeam = Team.CHO;
    }

    public void move(Position from, Position to) {
        if (board.getPieceAt(from).getTeam() != currentTeam) {
            throw new InvalidTurnException(currentTeam);
        }
        board.move(from, to);

        this.currentTeam = currentTeam.switchTeam();
    }

    public Board getBoard() {
        return board;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }
}

package domain;

import domain.board.Board;
import domain.board.Point;
import domain.piece.Team;

public class JanggiGame {

    private final Board board;
    private Team turn;

    public JanggiGame(Board board, Team startTeam) {
        this.board = board;
        this.turn = startTeam;
    }

    public boolean isPlaying() {
        return board.isPlaying();
    }

    public void move(Point source, Point destination) {
        validateMove(source, destination);

        board.movePiece(source, destination);
        turn = turn.inverse();
    }

    private void validateMove(Point source, Point destination) {
        if (!board.existsPiece(source)) {
            throw new IllegalArgumentException(source + ": 해당 위치에 움직일 기물이 존재하지 않습니다.");
        }
        if (!board.matchTeam(source, turn)) {
            throw new IllegalArgumentException("다른 팀의 기물이 놓여있습니다.");
        }
        if (!board.canMove(source, destination)) {
            throw new IllegalArgumentException(source + "->" + destination + "으로 이동할 수 없습니다.");
        }
    }

    public double calculateScore(Team team) {
        return board.calculateScore(team);
    }

    public Team findWinTeam() {
        return board.findWinTeam();
    }

    public Team currentTurn() {
        return turn;
    }

    public Board board() {
        return board;
    }
}

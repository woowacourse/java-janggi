package domain;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.Map;

public class Game {

    private final Board board;
    private Team turn;

    public Game(Board board) {
        this(board, Team.CHO);
    }

    public Game(Board board, Team turn) {
        this.board = board;
        this.turn = turn;
    }

    public void move(Position from, Position to) {
        board.move(from, to);
        nextTurn();
    }

    private void nextTurn() {
        this.turn = turn.opposite();
    }

    public void validateMoveAblePiece(Position from) {
        Piece piece = board.getRequiredPiece(from);

        if (!piece.isSameTeam(turn)) {
            throw new IllegalStateException("본인 기물이 아닙니다.");
        }
    }

    public double getCurrentScore(Team team) {
        return board.calculateScore(team);
    }

    public Team getTurn() {
        return turn;
    }

    public boolean isGameEnd() {
        return !(board.isAliveGeneral(Team.CHO) && board.isAliveGeneral(Team.HAN));
    }

    public Map<Position, Piece> getPieces() {
        return board.getPieces();
    }

    public Team getWinnerTeam() {
        if (!isGameEnd()) {
            throw new IllegalStateException("게임이 아직 끝나지 않았습니다");
        }
        if (board.isAliveGeneral(Team.CHO)) {
            return Team.CHO;
        }
        return Team.HAN;
    }
}

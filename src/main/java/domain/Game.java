package domain;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.Optional;

public class Game {

    private final Board board;
    private Team turn = Team.CHO;

    public Board getBoard() {
        return board;
    }

    public Optional<Piece> getPiece(Position position) {
        return board.getPiece(position);
    }

    public Game(Board board) {
        this.board = board;
    }

    public void move(Position from, Position to) {
        try {
            board.move(from, to);
            nextTurn();
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private void nextTurn() {
        if (turn == Team.CHO) {
            this.turn = Team.HAN;
            return;
        }
        this.turn = Team.CHO;
    }

    public void validateMoveAblePiece(Position from) {
        Piece piece = board.getRequiredPiece(from);

        if (!this.turn.equals(piece.getTeam())) {
            throw new IllegalStateException("본인 차례가 아닙니다.");
        }
    }

}

package model.game;

import model.board.Board;
import model.board.Country;
import model.move.Move;
import model.pieces.Piece;

public class JanggiGame {
    private final Board board;
    private Country turn;
    private GameStatus status;

    public JanggiGame(Board board) {
        this(board, Country.CHO, GameStatus.playing());
    }

    private JanggiGame(Board board, Country turn, GameStatus status) {
        this.board = board;
        this.turn = turn;
        this.status = status;
    }

    public static JanggiGame restore(Board board, Country turn, boolean finished, Country winner) {
        return new JanggiGame(
                board,
                turn,
                GameStatus.restore(finished, winner)
        );
    }

    public void move(Move move) {
        status.validateNotFinished();
        validateTurn(move);

        Piece capturedPiece = board.move(move);
        status = status.update(capturedPiece, turn);

        if (status.isFinished()) {
            return;
        }
        changeTurn();
    }

    private void validateTurn(Move move) {
        Piece piece = board.findPiece(move.from());

        if (piece == null) {
            throw new IllegalArgumentException("[ERROR] 기물이 없습니다.");
        }

        if (piece.country() != turn) {
            throw new IllegalArgumentException("[ERROR] 자기 나라의 기물만 이동할 수 있습니다.");
        }
    }

    private void changeTurn() {
        if (turn == Country.CHO) {
            turn = Country.HAN;
            return;
        }
        turn = Country.CHO;
    }

    public Country turn() {
        return turn;
    }

    public Country winner() {
        return status.winner();
    }

    public boolean isFinished() {
        return status.isFinished();
    }
}

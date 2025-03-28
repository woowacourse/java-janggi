package janggi;

import static janggi.piece.Side.BLUE;
import static janggi.piece.Side.RED;

import janggi.board.Board;
import janggi.board.Position;
import janggi.piece.Piece;
import janggi.piece.Side;
import java.util.Collections;
import java.util.Map;

public class JanggiGame {

    private final Board board;
    private final Turn turn;

    public JanggiGame(final Board board, final Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public void movePiece(final Position start, final Position end) {
        board.move(start, end, turn);
        turn.nextTurn();
    }

    public Side calculateWinner() {
        Score redSideScore = scoreBySide(RED);
        Score blueSideScore = scoreBySide(BLUE);
        if (redSideScore.isGreaterThan(blueSideScore)) {
            return RED;
        }
        return BLUE;
    }

    public Score scoreBySide(final Side side) {
        Score scoreBySide = board.calculatePiecesScoreBySide(side);
        return Score.initBySide(side).plus(scoreBySide);
    }

    public boolean continueGame() {
        return scoreBySide(RED).isGreaterThanZero()
                && scoreBySide(BLUE).isGreaterThanZero();
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board.getBoard());
    }

    public Turn getTurn() {
        return turn;
    }
}

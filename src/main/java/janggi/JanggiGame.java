package janggi;

import janggi.board.Board;
import janggi.board.Position;
import janggi.piece.Piece;
import java.util.Collections;
import java.util.Map;

public class JanggiGame {

    private final Board board;
    private final Turn turn;
    private Score redScore;
    private Score blueScore;

    public JanggiGame(final Board board, final Score redScore, final Score blueScore, final Turn turn) {
        this.board = board;
        this.redScore = redScore;
        this.blueScore = blueScore;
        this.turn = turn;
    }

    public void movePiece(final Position start, final Position end) {
        Score score = board.move(start, end, turn);
        if (turn.isBlueTurn()) {
            redScore = redScore.minusScore(score);
        }
        if (turn.isRedTurn()) {
            blueScore = blueScore.minusScore(score);
        }
        turn.nextTurn();
    }

    public boolean continueGame() {
        return !(redScore.isEnd() && blueScore.isEnd());
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board.getBoard());
    }

    public Score getRedScore() {
        return redScore;
    }

    public Score getBlueScore() {
        return blueScore;
    }

    public Turn getTurn() {
        return turn;
    }
}

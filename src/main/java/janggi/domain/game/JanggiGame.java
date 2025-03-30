package janggi.domain.game;

import static janggi.domain.piece.Side.BLUE;
import static janggi.domain.piece.Side.RED;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import java.util.Collections;
import java.util.List;
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
        List<Piece> pieces = board.piecesBySide(side);
        Score scoreBySide = pieces.stream()
                .map(Piece::getScore)
                .reduce(Score::plus)
                .orElse(Score.zero());
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

package domain.game;

import domain.board.Board;
import domain.board.Intersection;
import domain.exception.UnexpectedException;
import domain.movement.Move;
import domain.piece.Piece;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class JanggiGame {

    private static final Side FIRST_TURN = Side.CHO;

    private final Board board;
    private final Side currentTurn;
    private final Move lastMove;

    public JanggiGame(Board board) {
        this(board, FIRST_TURN);
    }

    public JanggiGame(
            Board board,
            Side currentTurn
    ) {
        this(board, currentTurn, null);
    }

    public JanggiGame(
            Board board,
            Side currentTurn,
            Move lastMove
    ) {
        this.board = board;
        this.currentTurn = currentTurn;
        this.lastMove = lastMove;
    }

    public List<Intersection> getMovableIntersections(Intersection startIntersection) {
        validatePlaying();

        return board.getMovableIntersections(startIntersection, currentTurn);
    }

    public JanggiGame movePiece(Move move) {
        validatePlaying();

        Board nextBoard = board.movePiece(move, currentTurn);
        Side nextTurn = currentTurn.nextTurn();

        return new JanggiGame(nextBoard, nextTurn, move);
    }

    public boolean isPlaying() {
        return Arrays.stream(Side.values())
                .allMatch(board::hasRoyalPiece);
    }

    public Side getWinner() {
        if (isPlaying()) {
            throw new IllegalStateException("승자는 게임 종료 이후에 조회할 수 있습니다.");
        }

        return Arrays.stream(Side.values())
                .filter(board::hasRoyalPiece)
                .findAny()
                .orElseThrow(() -> new UnexpectedException("승자를 조회할 수 없습니다."));
    }

    public Side getCurrentTurn() {
        return currentTurn;
    }

    public Move getLastMove() {
        if (lastMove == null) {
            throw new IllegalStateException("아직 기물을 움직이지 않은 게임입니다.");
        }

        return lastMove;
    }

    public double getTotalScore(Side side) {
        return side.calculateTotalScore(board.getTotalScore(side));
    }

    public Map<Intersection, Piece> getPieces() {
        return board.getPieces();
    }

    public JanggiGame copyOf() {
        return new JanggiGame(board, currentTurn, lastMove);
    }

    private void validatePlaying() {
        if (isEnded()) {
            throw new IllegalStateException("게임이 이미 종료되었습니다.");
        }
    }

    private boolean isEnded() {
        return !isPlaying();
    }
}

package domain.game;

import domain.board.Board;
import domain.board.Intersection;
import domain.exception.UnexpectedException;
import domain.piece.Piece;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class JanggiGame {

    private static final Side FIRST_TURN = Side.CHO;

    private final Board board;
    private Side currentTurn;

    public JanggiGame(Board board) {
        this(board, FIRST_TURN);
    }

    public JanggiGame(
            Board board,
            Side currentTurn
    ) {
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public List<Intersection> getMovableIntersections(Intersection startIntersection) {
        validatePlaying();

        return board.getMovableIntersections(startIntersection, currentTurn);
    }

    public void movePiece(
            Intersection startIntersection,
            Intersection destination
    ) {
        validatePlaying();

        board.movePiece(startIntersection, destination, currentTurn);

        currentTurn = currentTurn.nextTurn();
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

    public double getTotalScore(Side side) {
        return side.calculateTotalScore(board.getTotalScore(side));
    }

    public Map<Intersection, Piece> getBoard() {
        return board.getPieces();
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

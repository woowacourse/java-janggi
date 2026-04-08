package janggi.domain.game;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;

import janggi.domain.board.Board;
import janggi.domain.board.DefaultBoardDesignPolicy;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;

public class Game {

    private static final Dynasty FIRST_DYNASTY = CHO;
    private static final double DEOM = 1.5;
    private static final double POINTS_COMPARISON_THRESHOLD = 30;

    private final Board board;
    private final CurrentTurn currentTurn;

    private Game(Board board, CurrentTurn currentTurn) {
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public static Game initGame(Map<Dynasty, HorseElephantPosition> horseElephantPositions) {
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(horseElephantPositions);
        return new Game(Board.from(policy), new CurrentTurn(FIRST_DYNASTY));
    }

    public static Game restore(Board board, CurrentTurn currentTurn) {
        return new Game(board, currentTurn);
    }

    public List<Position> placeablePositions(Position from) {
        List<Position> positions = board.placeablePositions(from, currentTurn.currentDynasty());
        if (positions.isEmpty()) {
            throw new IllegalStateException("선택된 기물이 이동할 수 있는 위치가 없습니다.");
        }
        return positions;
    }

    public GameState movePiece(Position from, Position to) {
        Dynasty dynasty = currentTurn.currentDynasty();
        Piece catchedPiece = board.movePiece(from, to, dynasty);
        return judgeGameState(catchedPiece, dynasty);
    }

    private GameState judgeGameState(Piece catchedPiece, Dynasty dynasty) {
        if (catchedPiece != null && catchedPiece.isSame(PieceType.GENERAL)) {
            return GameState.from(dynasty);
        }
        if (isPossibleToCompareUsingPoints()) {
            return GameState.from(findWinnerUsingPoints());
        }
        currentTurn.changeTurn();
        return GameState.PLAYING;
    }

    public Map<Position, Piece> pieces() {
        return board.pieces();
    }

    public Dynasty currentDynasty() {
        return currentTurn.currentDynasty();
    }

    private boolean isPossibleToCompareUsingPoints() {
        return pointsOf(HAN) < POINTS_COMPARISON_THRESHOLD
                && pointsOf(CHO) < POINTS_COMPARISON_THRESHOLD;
    }

    private Dynasty findWinnerUsingPoints() {
        if (pointsOf(HAN) < pointsOf(CHO)) {
            return CHO;
        }
        return HAN;
    }

    private double pointsOf(Dynasty dynasty) {
        double points = board.sumPointsOf(dynasty);
        if (dynasty == HAN) {
            return points + DEOM;
        }
        return points;
    }

}

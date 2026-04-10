package domain.game;

import domain.board.Board;
import domain.board.Intersection;
import domain.piece.Piece;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JanggiGame {

    private static final Side FIRST_TURN = Side.CHO;
    private static final int MINIMUM_POINT_FOR_CONTINUE = 30;
    private static final Map<Side, Double> bonusPointBySide = Map.of(
            Side.CHO, 0.0,
            Side.HAN, 1.5
    );

    private final Board board;
    private Side currentTurn;

    public JanggiGame(Board board, Side currentTurn) {
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public JanggiGame(Board board) {
        this(board, FIRST_TURN);
    }

    public void movePiece(
            Intersection startIntersection,
            Intersection destination,
            Side requestingSide
    ) {
        validateTurn(requestingSide);

        board.movePiece(startIntersection, destination, requestingSide);

        currentTurn = currentTurn.nextTurn();
    }

    private void validateTurn(Side requestingSide) {
        if (requestingSide != currentTurn) {
            throw new IllegalArgumentException("지금은 " + currentTurn + "의 차례입니다.");
        }
    }

    public Side currentTurn() {
        return currentTurn;
    }

    private Side previousTurn() {
        return currentTurn.nextTurn();
    }

    public List<Intersection> getMovableIntersections(
            Intersection selectedIntersection,
            Side side
    ) {
        return board.getMovableIntersections(selectedIntersection, side);
    }

    public boolean isFinished() {
        return isGeneralCaptured() || !hasEnoughPointsToContinue();
    }

    private boolean isGeneralCaptured() {
        return board.isGeneralCaptured(currentTurn) || board.isGeneralCaptured(previousTurn());
    }

    private boolean hasEnoughPointsToContinue() {
        double pointOfCho = board.calculatePiecePointOf(Side.CHO);
        double pointOfHan = board.calculatePiecePointOf(Side.HAN);

        return pointOfCho >= MINIMUM_POINT_FOR_CONTINUE
                || pointOfHan >= MINIMUM_POINT_FOR_CONTINUE;
    }

    public double calculateScoreOf(Side side) {
        double sideBonusPoint = bonusPointBySide.getOrDefault(side, 0.0);

        return sideBonusPoint + board.calculatePiecePointOf(side);
    }

    public GameResult determineResult() {
        if (!isFinished()) {
            throw new IllegalStateException("승자는 게임이 종료되었을 때 판단할 수 있습니다.");
        }

        if (isGeneralCaptured()) {
            return new GameResult(
                    previousTurn(),
                    true,
                    calculateTotalPointBySide()
            );
        }

        return new GameResult(
                findSideWithHigherScore(),
                false,
                calculateTotalPointBySide()
        );
    }

    private Side findSideWithHigherScore() {
        double choPoint = calculateScoreOf(Side.CHO);
        double hanPoint = calculateScoreOf(Side.HAN);

        if (choPoint > hanPoint) {
            return Side.CHO;
        }

        return Side.HAN;
    }

    private Map<Side, Double> calculateTotalPointBySide() {
        Map<Side, Double> totalPointBySide = new LinkedHashMap<>();
        final Side cho = Side.CHO;
        final Side han = Side.HAN;

        totalPointBySide.put(cho, calculateScoreOf(cho));
        totalPointBySide.put(han, calculateScoreOf(han));

        return totalPointBySide;
    }

    public Map<Intersection, Piece> toMap() {
        return board.toAlivePieces()
                .toMap();
    }

    public Board getBoard() {
        return board;
    }
}

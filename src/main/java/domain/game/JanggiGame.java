package domain.game;

import domain.board.Board;
import domain.board.Intersection;
import domain.piece.Piece;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JanggiGame {

    private final GameRule gameRule;
    private final Board board;
    private Side currentTurn;

    private JanggiGame(GameRule gameRule, Board board, Side currentTurn) {
        this.gameRule = gameRule;
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public static JanggiGame create(Board board) {
        GameRule gameRule = new GameRule();

        return new JanggiGame(gameRule, board, gameRule.firstTurn());
    }

    public static JanggiGame load(Board board, Side currentTurn) {
        return new JanggiGame(new GameRule(), board, currentTurn);
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
        return gameRule.isFinished(
                isGeneralCaptured(),
                board.calculatePiecePointOf(Side.CHO),
                board.calculatePiecePointOf(Side.HAN)
        );
    }

    private boolean isGeneralCaptured() {
        return board.isGeneralCaptured(currentTurn) || board.isGeneralCaptured(previousTurn());
    }

    public double calculateScoreOf(Side side) {
        return gameRule.bonusPointOf(side) + board.calculatePiecePointOf(side);
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

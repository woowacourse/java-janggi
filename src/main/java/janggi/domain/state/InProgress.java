package janggi.domain.state;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class InProgress implements GameState {

    private static final double GAME_END_SCORE_THRESHOLD = 30;
    private final Map<Side, Score> scores;
    private Side currentSide;

    public InProgress(Side currentSide, List<Piece> alivePieces) {
        this.currentSide = currentSide;
        scores = initScores(alivePieces);
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public void update(GameContext context, Piece piece) {
        if (piece.isNotEmpty()) {
            scores.get(piece.getSide()).subtract(piece);
        }

        if (piece.isSame(PieceType.GUNG)) {
            context.changeState(new End(currentSide));
            return;
        }

        if (isAllSideBelowThreshold()) {
            Side winner = getHighestScoreSide();
            context.changeState(new End(winner));
            return;
        }

        currentSide = currentSide.switchTurn();
    }

    private Side getHighestScoreSide() {
        return scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalStateException("점수를 비교할 수 없습니다."));
    }

    private boolean isAllSideBelowThreshold() {
        return scores.values().stream()
                .allMatch(score -> score.isBelow(GAME_END_SCORE_THRESHOLD));
    }

    @Override
    public Side getCurrentSide() {
        return currentSide;
    }

    @Override
    public Side getWinner() {
        throw new IllegalStateException("게임이 진행중인 상태에는 승자가 존재하지 않습니다.");
    }

    private Map<Side, Score> initScores(List<Piece> alivePieces) {
        Map<Side, Score> finalScores = new EnumMap<>(Side.class);
        for (Side side : Side.values()) {
            finalScores.put(side, new Score(side));
        }

        for (Piece piece : alivePieces) {
            finalScores.get(piece.getSide()).add(piece);
        }

        return finalScores;
    }
}

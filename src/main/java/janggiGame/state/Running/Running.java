package janggiGame.state.Running;

import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.Type;
import janggiGame.position.Position;
import janggiGame.state.GameResult;
import janggiGame.state.GameScore;
import janggiGame.state.State;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Running implements State {
    private static final double DEOM_FOR_HAN = 1.5;
    protected final Map<Position, Piece> pieces;
    protected final boolean wasLastTurnPassed;

    public Running(Map<Position, Piece> pieces, boolean wasLastTurnPassed) {
        this.pieces = pieces;
        this.wasLastTurnPassed = wasLastTurnPassed;
    }

    @Override
    public Map<Position, Piece> getPieces() {
        return new HashMap<>(pieces);
    }

    @Override
    public State arrangePieces(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy) {
        throw new IllegalStateException("[ERROR] 이미 배치 초기화가 이루어진 상태입니다.");
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public GameResult getGameResult() {
        throw new IllegalStateException("[ERROR] 아직 게임 결과를 알 수 없습니다.");
    }

    @Override
    public GameScore getGameScore() {
        double hanScore = calculateScore(Dynasty.HAN) + DEOM_FOR_HAN;
        double choScore = calculateScore(Dynasty.CHO);

        return new GameScore(hanScore, choScore);
    }

    private double calculateScore(Dynasty dynasty) {
        return pieces.values().stream()
                .filter(piece -> piece.getDynasty() == dynasty)
                .filter(piece -> piece.getType() != Type.KING)
                .mapToInt(Piece::getPoint)
                .sum();
    }

    protected void validateOrigin(Position origin, Dynasty dynasty) {
        validateEmptySpace(origin);
        validatePieceDynasty(origin, dynasty);
    }

    private void validateEmptySpace(Position origin) {
        if (!pieces.containsKey(origin)) {
            throw new IllegalArgumentException("[ERROR] 입력 받은 위치에 기물이 존재하지 않습니다.");
        }
    }

    private void validatePieceDynasty(Position origin, Dynasty dynasty) {
        if (pieces.get(origin).getDynasty() != dynasty) {
            throw new IllegalArgumentException("[ERROR] 입력 받은 위치의 기물이 현 사용자 소유의 기물이 아닙니다.");
        }
    }

    protected Map<Position, Piece> getPiecesOn(List<Position> route) {
        Map<Position, Piece> routeWithPiece = new HashMap<>();

        for (Position position : route) {
            routeWithPiece.put(position, pieces.getOrDefault(position, null));
        }
        return routeWithPiece;
    }

    protected Map<Position, Piece> movePiece(Position origin, Position destination, Piece originPiece) {
        Map<Position, Piece> nextTurnPieces = new HashMap<>(pieces);
        nextTurnPieces.remove(origin);
        nextTurnPieces.put(destination, originPiece);

        return nextTurnPieces;
    }

    @Override
    public boolean wasLastTurnPassed() {
        return wasLastTurnPassed;
    }
}

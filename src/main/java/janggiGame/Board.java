package janggiGame;

import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.piece.EmptyPiece;
import janggiGame.piece.Piece;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.character.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Board {
    private static final double BONUS_POINT = 1.5;
    private Map<Position, Piece> survivedPieces = new HashMap<>();

    public Board(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy) {
        arrangeHanPieces(Objects.requireNonNull(hanStrategy));
        arrangeChoPieces(Objects.requireNonNull(choStrategy));
    }

    public Board(final Map<Position, Piece> survivedPieces) {
        this.survivedPieces = Objects.requireNonNull(survivedPieces);
    }

    public void arrangeHanPieces(ArrangementStrategy strategy) {
        Map<Position, Piece> pieces = strategy.arrange(Dynasty.HAN);
        Map<Position, Piece> reversePieces = new HashMap<>();
        pieces.keySet()
                .forEach(dot -> reversePieces.put(dot.getReverse(), pieces.get(dot)));

        this.survivedPieces.putAll(reversePieces);
    }

    public void arrangeChoPieces(ArrangementStrategy strategy) {
        survivedPieces.putAll(strategy.arrange(Dynasty.CHO));
    }

    public void processTurn(Dynasty dynasty, Position origin, Position destination) {
        validateOrigin(dynasty, origin);

        Piece originPiece = survivedPieces.get(origin);

        List<Position> intermediatePoints = originPiece.getIntermediatePoints(origin, destination);

        Map<Position, Piece> intermediatePointsWithPiece = getPiecesOn(intermediatePoints);

        originPiece.validateMove(intermediatePointsWithPiece,
                survivedPieces.getOrDefault(destination, new EmptyPiece()));

        movePiece(origin, destination, originPiece);
    }

    private void validateOrigin(Dynasty dynasty, Position origin) {
        validateEmptySpace(origin);
        validatePieceDynasty(dynasty, origin);
    }

    private void validateEmptySpace(Position origin) {
        if (!survivedPieces.containsKey(origin)) {
            throw new IllegalArgumentException("[ERROR] 입력 받은 위치에 기물이 존재하지 않습니다.");
        }
    }

    private void validatePieceDynasty(Dynasty dynasty, Position origin) {
        if (!survivedPieces.get(origin).hasDynasty(dynasty)) {
            throw new IllegalArgumentException("[ERROR] 입력 받은 위치의 기물이 현 사용자 소유의 기물이 아닙니다.");
        }
    }

    private Map<Position, Piece> getPiecesOn(List<Position> intermediatePoints) {
        Map<Position, Piece> intermediatePointsWithPiece = new HashMap<>();

        for (Position position : intermediatePoints) {
            intermediatePointsWithPiece.put(position, survivedPieces.getOrDefault(position, new EmptyPiece()));
        }
        return intermediatePointsWithPiece;
    }

    private void movePiece(Position origin, Position destination, Piece originPiece) {
        survivedPieces.remove(origin);
        survivedPieces.put(destination, originPiece);
    }

    public double calculateTotalPoints(final Dynasty dynasty) {
        double totalPoints = 0;

        totalPoints += survivedPieces.values().stream()
                .filter(piece -> piece.hasDynasty(dynasty))
                .mapToInt(piece -> piece.getType().getPoint())
                .sum();

        if (dynasty == Dynasty.HAN) {
            totalPoints += BONUS_POINT;
        }

        return totalPoints;
    }

    public boolean isKingDead(final Dynasty dynasty) {
        boolean live = survivedPieces.values().stream()
                .anyMatch(piece -> piece.hasDynasty(dynasty)
                        && piece.getType() == PieceType.KING);
        return !live;
    }


    public Map<Position, Piece> getSurvivedPieces() {
        return Map.copyOf(survivedPieces);
    }
}

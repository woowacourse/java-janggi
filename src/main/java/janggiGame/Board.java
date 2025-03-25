package janggiGame;

import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Dot, Piece> survivedPieces = new HashMap<>();

    public void arrangeHanPieces(ArrangementStrategy strategy) {
        Map<Dot, Piece> pieces = strategy.arrange(Dynasty.HAN);
        Map<Dot, Piece> reversePieces = new HashMap<>();
        pieces.keySet()
                .forEach(dot -> reversePieces.put(dot.getReverse(), pieces.get(dot)));

        this.survivedPieces.putAll(reversePieces);

    }

    public void arrangeChoPieces(ArrangementStrategy strategy) {
        survivedPieces.putAll(strategy.arrange(Dynasty.CHO));
    }

    public void processTurn(Dynasty dynasty, Dot origin, Dot destination) {
        validateOrigin(dynasty, origin);

        Piece originPiece = survivedPieces.get(origin);

        List<Dot> route = originPiece.getIntermediatePoints(origin, destination);

        Map<Dot, Piece> intermediatePointsWithPiece = getPiecesOn(route);

        originPiece.validateMove(intermediatePointsWithPiece, survivedPieces.getOrDefault(destination, null));

        movePiece(origin, destination, originPiece);
    }

    private void validateOrigin(Dynasty dynasty, Dot origin) {
        validateEmptySpace(origin);
        validatePieceDynasty(dynasty, origin);
    }

    private void validateEmptySpace(Dot origin) {
        if (!survivedPieces.containsKey(origin)) {
            throw new IllegalArgumentException("[ERROR] 입력 받은 위치에 기물이 존재하지 않습니다.");
        }
    }

    private void validatePieceDynasty(Dynasty dynasty, Dot origin) {
        if (survivedPieces.get(origin).getDynasty() != dynasty) {
            throw new IllegalArgumentException("[ERROR] 입력 받은 위치의 기물이 현 사용자 소유의 기물이 아닙니다.");
        }
    }

    private Map<Dot, Piece> getPiecesOn(List<Dot> route) {
        Map<Dot, Piece> intermediatePointsWithPiece = new HashMap<>();

        for (Dot dot : route) {
            intermediatePointsWithPiece.put(dot, survivedPieces.getOrDefault(dot, null));
        }
        return intermediatePointsWithPiece;
    }

    private void movePiece(Dot origin, Dot destination, Piece originPiece) {
        survivedPieces.remove(origin);
        survivedPieces.put(destination, originPiece);
    }

    public Map<Dot, Piece> getSurvivedPieces() {
        return Map.copyOf(survivedPieces);
    }
}

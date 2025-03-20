package janggiGame;

import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.board.Board;
import janggiGame.board.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiGame {
    private Map<Dot, Piece> pieces = new HashMap<>();

    public void arrangeHanPieces(ArrangementStrategy strategy) {
        Map<Dot, Piece> pieces = strategy.arrange(Dynasty.HAN);
        Map<Dot, Piece> reversePieces = new HashMap<>();
        pieces.keySet()
                .forEach(dot -> reversePieces.put(Board.getReverse(dot), pieces.get(dot))
                );

        this.pieces.putAll(reversePieces);

    }

    public void arrangeChoPieces(ArrangementStrategy strategy) {
        pieces.putAll(strategy.arrange(Dynasty.CHO));
    }

    public Map<Dot, Piece> getPieces() {
        return Map.copyOf(pieces);
    }

    public void processTurn(Dynasty dynasty, Dot origin, Dot destination) {
        validateOrigin(dynasty, origin);

        Piece originPiece = pieces.get(origin);

        List<Dot> route = originPiece.getRoute(origin, destination);

        Map<Dot, Piece> routeWithPiece = getPiecesOn(route);

        originPiece.validateMove(routeWithPiece, pieces.getOrDefault(destination, null));

        movePiece(origin, destination, originPiece);
    }

    private Map<Dot, Piece> getPiecesOn(List<Dot> route) {
        Map<Dot, Piece> routeWithPiece = new HashMap<>();

        for (Dot dot : route) {
            routeWithPiece.put(dot, pieces.getOrDefault(dot, null));
        }
        return routeWithPiece;
    }

    private void movePiece(Dot origin, Dot destination, Piece originPiece) {
        pieces.remove(origin);
        pieces.put(destination, originPiece);
    }

    private void validateOrigin(Dynasty dynasty, Dot origin) {
        validateEmptySpace(origin);
        validatePieceDynasty(dynasty, origin);
    }

    private void validatePieceDynasty(Dynasty dynasty, Dot origin) {
        if (pieces.get(origin).getDynasty() != dynasty) {
            throw new IllegalArgumentException("[ERROR] 입력 받은 위치의 기물이 현 사용자 소유의 기물이 아닙니다.");
        }
    }

    private void validateEmptySpace(Dot origin) {
        if (!pieces.containsKey(origin)) {
            throw new IllegalArgumentException("[ERROR] 입력 받은 위치에 기물이 존재하지 않습니다.");
        }
    }
}

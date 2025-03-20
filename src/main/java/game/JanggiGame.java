package game;

import game.arrangement.ArrangementStrategy;
import piece.Dynasty;
import piece.Piece;

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
        return pieces; //todo 방어적 복사로 바꾸기
    }

    public void movePiece(Dynasty dynasty, Dot origin, Dot destination) {
        if (!pieces.containsKey(origin)) {
            throw new IllegalArgumentException("[ERROR] 입력 받은 위치에 기물이 존재하지 않습니다.");
        }
        if (pieces.get(origin).getDynasty() != dynasty) {
            throw new IllegalArgumentException("[ERROR] 입력 받은 위치의 기물이 현 사용자 소유의 기물이 아닙니다.");
        }

        Piece originPiece = pieces.get(origin);

        List<Dot> route = originPiece.getRoute(origin, destination);

        Map<Dot, Piece> routeWithPiece = new HashMap<>();

        for (Dot dot : route) {
            routeWithPiece.put(dot, pieces.getOrDefault(dot, null));
        }

        boolean canMove = originPiece.canMove(routeWithPiece, pieces.getOrDefault(destination, null));

        if (canMove) {
            pieces.remove(origin);
            pieces.put(destination, originPiece);
        }
    }
}

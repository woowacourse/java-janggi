import java.util.HashMap;
import java.util.Map;

public class JanggiGame {
    private Map<Dot, Piece> pieces = new HashMap<>();

    public void arrangeHanPieces(ArrangementStrategy strategy) {
        Map<Dot, Piece> pieces = strategy.arrange(Dynasty.HAN);
        Map<Dot, Piece> reversePieces = new HashMap<>();
        pieces.keySet()
                .forEach(dot -> reversePieces.put(dot.reverse(), pieces.get(dot))
                );

        this.pieces.putAll(reversePieces);

    }

    public void arrangeChoPieces(ArrangementStrategy strategy) {
        pieces.putAll(strategy.arrange(Dynasty.CHO));
    }

    public Map<Dot, Piece> getPieces() {
        return pieces; //todo 방어적 복사로 바꾸기
    }
}

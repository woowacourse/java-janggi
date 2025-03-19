import java.util.HashMap;
import java.util.Map;

public class JanggiGame {
    private Map<Dot, Piece> pieces = new HashMap<>();

    public void arrangeHanPieces(ArrangementStrategy strategy) {
        Map<Dot, Piece> tempPieces = strategy.arrange(Dynasty.HAN);
        pieces.keySet()
                .forEach(dot -> {
                    pieces.put(dot.reverse(), tempPieces.get(dot));
                });
    }

    public void arrangeChoPieces(ArrangementStrategy strategy) {
        pieces = strategy.arrange(Dynasty.CHO);
    }
}

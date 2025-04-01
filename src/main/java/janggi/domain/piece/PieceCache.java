package janggi.domain.piece;

import janggi.domain.piece.impl.Cannon;
import janggi.domain.piece.impl.Chariot;
import janggi.domain.piece.impl.Elephant;
import janggi.domain.piece.impl.General;
import janggi.domain.piece.impl.Guard;
import janggi.domain.piece.impl.Horse;
import janggi.domain.piece.impl.None;
import janggi.domain.piece.impl.Soldier;
import java.util.HashMap;
import java.util.Map;

public class PieceCache {
    private static final Map<String, Piece> pieceCache = new HashMap<>();

    static {
        for (Team team : Team.values()) {
            pieceCache.put(generateKey("궁", team), new General(team));
            pieceCache.put(generateKey("사", team), new Guard(team));
            pieceCache.put(generateKey("차", team), new Chariot(team));
            pieceCache.put(generateKey("포", team), new Cannon(team));
            pieceCache.put(generateKey("졸", team), new Soldier(team));
            pieceCache.put(generateKey("마", team), new Horse(team));
            pieceCache.put(generateKey("상", team), new Elephant(team));
            pieceCache.put(generateKey("ㅁ", team), new None());
        }
    }

    private static String generateKey(final String pieceName, final Team team) {
        return team + "_" + pieceName;
    }

    public static Piece getPiece(final String pieceName, final Team team) {
        return pieceCache.get(generateKey(pieceName, team));
    }
}
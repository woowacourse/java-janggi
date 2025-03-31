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
            pieceCache.put(generateKey("General", team), new General(team));
            pieceCache.put(generateKey("Guard", team), new Guard(team));
            pieceCache.put(generateKey("Chariot", team), new Chariot(team));
            pieceCache.put(generateKey("Cannon", team), new Cannon(team));
            pieceCache.put(generateKey("Soldier", team), new Soldier(team));
            pieceCache.put(generateKey("Horse", team), new Horse(team));
            pieceCache.put(generateKey("Elephant", team), new Elephant(team));
            pieceCache.put(generateKey("None", team), new None());
        }
    }

    private static String generateKey(final String pieceName, final Team team) {
        return team.name() + "_" + pieceName;
    }

    public static Piece getPiece(final String pieceName, final Team team) {
        return pieceCache.get(generateKey(pieceName, team));
    }
}
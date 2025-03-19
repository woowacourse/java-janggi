package janggi;

import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;

import java.util.*;

public class Board {

    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;

    private final Map<Position, Piece> positionToPiece;

    private Board(final Map<Position, Piece> positionToPiece) {
        this.positionToPiece = positionToPiece;
    }

    public static Board initialize(List<Piece> pieces) {
        HashMap<Position, Piece> positionToPiece = new HashMap<>();

        // TODO 외부 주입 예정
//        for (Team team : Team.values()) {
//            pieces.add(General.Default(team));
//            pieces.addAll(Cannon.Default(team));
//            pieces.addAll(Chariot.Default(team));
//            pieces.addAll(Elephant.Default(team));
//            pieces.addAll(Guard.Default(team));
//            pieces.addAll(Horse.Default(team));
//            pieces.addAll(Soldier.Default(team));
//        }

        pieces.forEach(piece ->
                positionToPiece.put(piece.getPosition(), piece));

        return new Board(positionToPiece);
    }

    public boolean isExists(Position position) {
        return positionToPiece.containsKey(position);
    }

    public void move(Player player, Position departure, Position destination) {
        Piece target = getPiece(departure);
        Piece moved = target.move(this, destination);

        positionToPiece.remove(departure);
        positionToPiece.put(destination, moved);
    }

    public boolean isAlly(Position position, Team team) {
        return getPiece(position).isAlly(team);
    }

    public Piece getPiece(final Position departure) {
        if (isExists(departure)) {
            return positionToPiece.get(departure);
        }
        throw new IllegalArgumentException("장기말이 존재하지 않는 지점입니다.");
    }

    public Map<Position, Piece> getPositionToPiece() {
        return Collections.unmodifiableMap(positionToPiece);
    }
}

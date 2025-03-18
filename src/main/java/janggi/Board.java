package janggi;

import janggi.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Board {

    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;

    private final Map<Position, Piece> positionToPiece;

    private Board(final Map<Position, Piece> positionToPiece) {
        this.positionToPiece = positionToPiece;
    }

    public static Board createBoard() {
        return new Board(new HashMap<>());
    }

    public boolean isExists(Position position) {
        return positionToPiece.containsKey(position);
    }

    public void move(Player player, Position departure, Position destination) {
        Piece target = getPiece(departure)
                .orElseThrow(() -> new IllegalArgumentException("장기말이 존재하지 않는 지점입니다."));
        Piece moved = target.move(this, destination);
        positionToPiece.remove(departure);
        positionToPiece.put(destination, moved);
    }

    public boolean isAlly(Team team, Position position) {
        Piece piece = getPiece(position)
                .orElseThrow(() -> new IllegalArgumentException("장기말이 존재하지 않는 지점입니다."));
        return piece.isAlly(team);
    }

    private Optional<Piece> getPiece(final Position departure) {
        return Optional.ofNullable(positionToPiece.get(departure));
    }
}

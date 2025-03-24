package janggi;

import janggi.coordinate.Position;
import janggi.piece.Piece;
import janggi.piece.Pieces;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> positionToPiece;

    private Board(final Map<Position, Piece> positionToPiece) {
        this.positionToPiece = positionToPiece;
    }

    public static Board from(final Pieces pieces) {
        HashMap<Position, Piece> positionToPiece = new HashMap<>();

        pieces.getPieces().forEach(piece ->
                positionToPiece.put(piece.getPosition(), piece));

        return new Board(positionToPiece);
    }

    public boolean isExists(final Position position) {
        return positionToPiece.containsKey(position);
    }

    public boolean isAlly(final Position position, final Team team) {
        return isExists(position) && getPiece(position).isAlly(team);
    }

    public Piece getPiece(final Position position) {
        if (isExists(position)) {
            return positionToPiece.get(position);
        }
        throw new IllegalArgumentException("장기말이 존재하지 않는 지점입니다.");
    }

    public void movePiece(final Player player, final Position departure, final Position destination) {
        Piece allyPiece = getPiece(departure);
        Piece movedPiece = allyPiece.move(this, destination);

        updateScore(player, destination);
        updateBoard(departure, destination, movedPiece);
    }

    private void updateScore(final Player player, final Position destination) {
        if (isExists(destination)) {
            Score score = positionToPiece.remove(destination).die();
            player.addScore(score);
        }
    }

    private void updateBoard(final Position departure, final Position destination, final Piece movedPiece) {
        positionToPiece.remove(departure);
        positionToPiece.put(destination, movedPiece);
    }

    public Map<Position, Piece> getPositionToPiece() {
        return Collections.unmodifiableMap(positionToPiece);
    }
}

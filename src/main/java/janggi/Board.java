package janggi;

import janggi.piece.Piece;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> positionToPiece;

    private Board(final Map<Position, Piece> positionToPiece) {
        this.positionToPiece = positionToPiece;
    }

    public static Board initialize(final List<Piece> pieces) {
        HashMap<Position, Piece> positionToPiece = new HashMap<>();

        pieces.forEach(piece -> positionToPiece.put(piece.getPosition(), piece));

        return new Board(positionToPiece);
    }

    public boolean isExists(final Position position) {
        return positionToPiece.containsKey(position);
    }

    public void movePiece(final Position departure, final Position destination) {
        Piece allyPiece = getPiece(departure);
        Piece movedPiece = allyPiece.move(this, destination);

        Score score = positionToPiece.remove(departure).die();
        // TODO 2.1 단계(점수 계산) 요구사항 추가 예정
        updateBoard(destination, movedPiece);
    }

    private void updateBoard(final Position destination, final Piece movedPiece) {
        positionToPiece.put(destination, movedPiece);
    }

    public boolean isAlly(final Position position, final Team team) {
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

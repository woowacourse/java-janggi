package board;

import board.create.strategy.TableSettingStrategy;
import coordinate.Coordinate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import piece.Piece;
import team.Team;

public class Board {

    private final Map<Coordinate, Piece> pieces;

    private Board(Map<Coordinate, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board create(TableSettingStrategy hanStrategy, TableSettingStrategy choStrategy) {
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.putAll(hanStrategy.create(Team.HAN));
        pieces.putAll(choStrategy.create(Team.CHO));
        return new Board(pieces);
    }

    public void move(Coordinate departure, Coordinate arrival) {
        Piece piece = getPiece(departure);

        if (piece == null) {
            throw new IllegalArgumentException("해당 좌표에는 기물이 없습니다.");
        }
        if (!piece.canMove(this, departure, arrival)) {
            throw new IllegalArgumentException("해당 기물이 이동할 수 없는 좌표입니다.");
        }
        if (hasPiece(arrival) && getPiece(arrival).isSameTeam(piece)) {
            throw new IllegalArgumentException("도착 좌표에 같은 팀 말이 있습니다.");
        }

        movePiece(piece, departure, arrival);
    }

    public boolean hasPiece(Coordinate coordinate) {
        return pieces.containsKey(coordinate);
    }

    public Piece getPiece(Coordinate coordinate) {
        return pieces.get(coordinate);
    }

    private void movePiece(Piece piece, Coordinate departure, Coordinate arrival) {
        pieces.remove(departure);
        pieces.put(arrival, piece);
    }

    public Map<Coordinate, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}

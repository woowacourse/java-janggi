package domain;

import domain.piece.Piece;
import java.util.Map;
import java.util.Optional;

public class Board {

    private final Map<Coordinate, Piece> pieces;

    public Board(Map<Coordinate, Piece> pieces) {
        this.pieces = pieces;
    }

    public void move(Coordinate departure, Coordinate arrival) {
        Piece piece = pieces.get(departure);
        if (piece == null) {
            throw new IllegalArgumentException("해당 좌표에는 기물이 없습니다.");
        }

        if (!piece.canMove(this, departure, arrival)) {
            throw new IllegalArgumentException("해당 기물이 이동할 수 없는 좌표입니다.");
        }

        if (isExistence(arrival)) {
            if (findPiece(arrival).get().isSameTeam(piece)) {
                throw new IllegalArgumentException("도착 좌표에 같은 팀 말이 있습니다.");
            }
            // TODO: Step2에서 상대 팀 말을 제거하고 점수를 먹는 메서드로 변경
            replace(departure, arrival, piece);
            return;
        }
        put(departure, arrival, piece);
    }

    public boolean isExistence(Coordinate coordinate) {
        return pieces.containsKey(coordinate);
    }

    public Optional<Piece> findPiece(Coordinate coordinate) {
        return Optional.ofNullable(pieces.get(coordinate));
    }

    private void replace(Coordinate departure, Coordinate arrival, Piece piece) {
        pieces.remove(departure);
        pieces.replace(arrival, piece);
    }

    private void put(Coordinate departure, Coordinate arrival, Piece piece) {
        pieces.remove(departure);
        pieces.put(arrival, piece);
    }
}

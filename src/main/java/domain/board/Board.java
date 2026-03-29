package domain.board;

import domain.piece.ActivePiece;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void move(Position src, Position dest) {
        Piece piece = pieces.get(src);
        validateCanMove(piece, src, dest);
        List<Position> route = ((ActivePiece) piece).searchRoute(src, dest);
        if (piece.isCannon()) {
            validateCannonRoute(route, dest);
        } else {
            validateIntermediateRoute(route);
        }
        validateDestination(dest, piece);
        applyMove(src, dest, piece);
    }

    private void validateCannonRoute(List<Position> route, Position dest) {
        int count = 0;
        for (Position position : route) {
            if (pieceAt(position).isCannon()) {
                throw new IllegalArgumentException("포는 포를 넘지 못합니다.");
            }

            if (pieceAt(position).isNotEmpty()) {
                count++;
            }
        }

        if (count != 1) {
            throw new IllegalArgumentException("포가 넘을 수 있는 기물의 개수는 하나입니다.");
        }

        if (pieceAt(dest).isCannon()) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    private void validateCanMove(Piece piece, Position src, Position dest) {
        if (!piece.canMove(src, dest)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    private void validateIntermediateRoute(List<Position> route) {
        for (Position position : route) {
            if (pieceAt(position).isNotEmpty()) {
                throw new IllegalArgumentException("이동 경로에 기물이 있습니다.");
            }
        }
    }

    private void validateDestination(Position dest, Piece movingPiece) {
        if (pieceAt(dest).isAlly(movingPiece)) {
            throw new IllegalArgumentException("아군 기물이 있는 위치로 이동할 수 없습니다.");
        }
    }

    private void applyMove(Position src, Position dest, Piece piece) {
        pieces.put(dest, piece);
        pieces.put(src, new EmptyPiece());
    }

    public Piece pieceAt(Position position) {
        return pieces.getOrDefault(position, new EmptyPiece());
    }
}

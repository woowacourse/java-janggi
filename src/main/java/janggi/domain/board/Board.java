package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> piecesByPosition;

    public Board(Map<Position, Piece> base) {
        this.piecesByPosition = base;
    }

    public Map<Position, Piece> move(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 출발 좌표와 도착 좌표는 같을 수 없습니다.");
        }

        Piece fromPiece = piecesByPosition.get(from);
        List<Position> path = fromPiece.getPath(from, to);
        List<Piece> pieceOnPath = new ArrayList<>();
        for (Position position : path) {
            pieceOnPath.add(piecesByPosition.get(position));
        }
        if (fromPiece.canMove(pieceOnPath, piecesByPosition.get(to))) {
            piecesByPosition.put(from, new EmptyPiece());
            piecesByPosition.put(to, fromPiece);
        }
        return showBoard();
    }

    public Map<Position, Piece> showBoard() {
        return Map.copyOf(piecesByPosition);
    }
}

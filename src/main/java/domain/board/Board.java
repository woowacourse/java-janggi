package domain.board;

import domain.Path;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece getPiece(Position position) {
        return pieces.get(position);
    }

    public void move(Position from, Position to) {
        // 해당 위치(from)에 기물이 위치하고 있는지
        // (1) piece 가 해당 도착지(to)로 도착하는 경로 반환  : Piece의 getPathPositions 호출
        // 상대편 위치, 지금할껀지 or 있다가 할껀지, 초/한
        // (2) 1에서 받은 값으로 getPathWithPiece 호출하여 Piece의 canMove 호출
    }

    public List<Path> getPath(List<Position> positions) {
        List<Path> paths = new ArrayList<>();
        for (Position position : positions) {
            if (pieces.containsKey(position)) {
                paths.add(new Path(position, pieces.get(position)));
            }
        }

        return paths;
    }
}

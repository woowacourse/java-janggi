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
        Piece fromPiece = pieces.get(from);
        Piece toPiece = pieces.get(to);
        List<Position> pathPositions = fromPiece.getPathPositions(from, to);
        List<Path> path = getPath(pathPositions);
        fromPiece.canMove(path, toPiece);
        
        if (fromPiece.isSameTeam(toPiece)) {
            throw new IllegalStateException("같은 팀의 기물을 잡을 수 없습니다.");
        }
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

package domain.board;

import domain.Offset;
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

        List<Offset> pathPositions = fromPiece.getPathPositions(Offset.of(from, to));
        List<Path> path = getPath(from,pathPositions);
        fromPiece.canMove(path, toPiece);

        if (toPiece != null && fromPiece.isSameTeam(toPiece)) {
            throw new IllegalStateException("같은 팀의 기물을 잡을 수 없습니다.");
        }

        Piece remove = pieces.remove(from);
        pieces.put(to, remove);
    }

    public List<Path> getPath(Position from, List<Offset> offsets) {
        List<Path> paths = new ArrayList<>();
        for (Offset offset : offsets) {
            Position position = offset.applyTo(from);
            if (pieces.containsKey(position)) {
                paths.add(new Path(position, pieces.get(position)));
            }
        }
        return paths;
    }
}

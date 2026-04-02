package domain.board;

import domain.path.PathInfo;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {
    Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public boolean isExistPieceAt(Position position) {
        return pieces.containsKey(position);
    }

    public Piece pieceAt(Position position) {
        if (!isExistPieceAt(position)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }

        return pieces.get(position);
    }

    public void move(Position departure, Position destination) {
        Piece departurePiece = pieceAt(departure);

        List<PathInfo> path = new ArrayList<>();
        for (Position position : departurePiece.getPath(departure, destination)) {
            path.add(new PathInfo(position, pieceAt(position)));
        }

        departurePiece.validateBlockingPiece(path, departure, destination);

        if (isExistPieceAt(destination)) {
            validateCapture(departurePiece, pieceAt(destination));
        }

        pieces.remove(departure);
        pieces.put(destination, departurePiece);
    }

    private void validateCapture(Piece departurePiece, Piece destinationPiece) {
        if (departurePiece.isSameCampe(destinationPiece)) {
            throw new IllegalArgumentException("같은 팀끼리는 잡을 수 없습니다.");
        }
    }
}

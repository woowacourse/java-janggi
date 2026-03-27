package domain;

import domain.pieces.Piece;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board = new HashMap<>();

    public void locatePiece(Position position, Piece piece) {
        if (!isExist(position)) {
            board.put(position, piece);
            return;
        }

        if (getPieceFrom(position).isSameCamp(piece)) {
            throw new IllegalArgumentException("[ERROR] 같은 팀은 잡을 수 없습니다!");
        }

        board.put(position, piece);
    }

    public Piece getPieceFrom(Position position) {
        return board.get(position);
    }

    public boolean isExist(Position position) {
        return board.containsKey(position);
    }

    public void move(Position fromPosition, Position toPosition) {
        Piece piece = board.get(fromPosition);
        board.remove(fromPosition);
        locatePiece(toPosition, piece);
    }
}

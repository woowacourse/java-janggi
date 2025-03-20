package janggi.domain;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.PositionSide;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.TeamType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void putPiece(final Position position, final Piece piece) {
        pieces.put(position, piece);
    }

    public Piece getPiece(final Position position) {
        return pieces.getOrDefault(position, Piece.createEmpty());
    }

    public Map<Position, Piece> getPieces() {
        Map<Position, Piece> copy = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                copy.put(new Position(i, j), getPiece(new Position(i, j)));
            }
        }
        return copy;
    }

}

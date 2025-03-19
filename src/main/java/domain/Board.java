package domain;

import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Piece> alivePieces;
    private final List<Piece> deadPieces;

    public Board(List<Piece> pieces) {
        this.alivePieces = new ArrayList<>(pieces);
        this.deadPieces = new ArrayList<>();
    }
}

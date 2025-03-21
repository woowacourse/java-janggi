package domain;

import domain.position.ChessPiecePositions;

public class Board {

    private final ChessPiecePositions chessPiecePositions;

    public Board(final ChessPiecePositions chessPiecePositions) {
        this.chessPiecePositions = chessPiecePositions;
    }

    public void initialize() {
        chessPiecePositions.initialize();
    }

}

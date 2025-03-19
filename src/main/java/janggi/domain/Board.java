package janggi.domain;

public class Board {
    private final Pieces pieces;
    private final Turn turn;

    public Board() {
        this.pieces = new Pieces(PiecesInitializer.initializePieces());
        this.turn = Turn.initialize();
    }
}

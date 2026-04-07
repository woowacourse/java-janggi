package domain.board;

import domain.board.wing.WingPieces;
import domain.board.wing.Wings;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;

public final class BoardFixture {

    private static final Wings DEFAULT_HAN_WINGS = new Wings(
            Side.HAN,
            new WingPieces(Piece.of(PieceType.HORSE, Side.HAN), Piece.of(PieceType.ELEPHANT, Side.HAN)),
            new WingPieces(Piece.of(PieceType.HORSE, Side.HAN), Piece.of(PieceType.ELEPHANT, Side.HAN))
    );
    private static final Wings DEFAULT_CHO_WINGS = new Wings(
            Side.CHO,
            new WingPieces(Piece.of(PieceType.HORSE, Side.CHO), Piece.of(PieceType.ELEPHANT, Side.CHO)),
            new WingPieces(Piece.of(PieceType.HORSE, Side.CHO), Piece.of(PieceType.ELEPHANT, Side.CHO))
    );

    private BoardFixture() {
    }

    public static Board create() {
        InitialPieces initialPieces = new InitialPieces(DEFAULT_HAN_WINGS, DEFAULT_CHO_WINGS);

        return new Board(initialPieces.toAlivePieces());
    }
}

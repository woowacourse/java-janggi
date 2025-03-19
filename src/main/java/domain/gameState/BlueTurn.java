package domain.gameState;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;

public class BlueTurn implements State {

    private final Board board;
    private final PieceColor pieceColor;

    public BlueTurn(Board board) {
        this.board = board;
        this.pieceColor = PieceColor.BLUE;
    }

    @Override
    public State movePiece(Piece piece, Position source, Position destination) {
        validateIsMyPiece(piece);
        board.move(piece, source, destination);

        return new RedTurn(board);
    }

    @Override
    public PieceColor getColor() {
        return this.pieceColor;
    }

    private void validateIsMyPiece(Piece piece) {
        if (piece.getColor() != PieceColor.BLUE) {
            throw new IllegalArgumentException("움직이려는 기물이 파란색이 아닙니다.");
        }
    }
}

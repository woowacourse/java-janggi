package domain.gameState;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

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

        Piece destinationPiece = board.getPieceBy(destination);
        board.move(piece, source, destination);
        boolean isGeneral = destinationPiece.isSamePiece(PieceType.GENERAL);

        if (isGeneral) {
            return new Finished(pieceColor);
        }
        return new RedTurn(board);
    }

    @Override
    public PieceColor getColor() {
        return this.pieceColor;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private void validateIsMyPiece(Piece piece) {
        if (piece.isOtherTeam(pieceColor)) {
            throw new IllegalArgumentException("움직이려는 기물이 파란색이 아닙니다.");
        }
    }
}

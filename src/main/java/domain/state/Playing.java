package domain.state;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

public abstract class Playing extends Started {

    protected final PieceColor turnColor;

    protected Playing(Board board, PieceColor turnColor) {
        super(board);
        this.turnColor = turnColor;
    }

    @Override
    public State movePiece(PieceType pieceType, Position source, Position destination) {
        Piece sourcePiece = board.getPieceByPosition(source);
        Piece destinationPiece = board.getPieceByPosition(destination);
        validateIsMyPieceColor(sourcePiece);

        board.movePiece(pieceType, source, destination);
        boolean isGeneral = destinationPiece.isSamePieceType(PieceType.GENERAL);

        if (isGeneral) {
            return new Finished(board);
        }
        return nextTurn(board);
    }

    @Override
    public PieceColor getTurnColor() {
        return this.turnColor;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private void validateIsMyPieceColor(Piece sourcePiece) {
        if (sourcePiece.isOtherTeam(turnColor)) {
            throw new IllegalArgumentException("움직이려는 기물이 본인팀이 아닙니다.");
        }
    }

    @Override
    public PieceColor determineWinner() {
        throw new UnsupportedOperationException("게임이 종료되지 않아 승자를 판별할 수 없습니다.");
    }

    protected abstract State nextTurn(Board board);
}

package janggi.domain.gameState;

import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.TeamColor;
import janggi.domain.piece.PieceType;

public abstract class InProgress implements State {
    final PlayingBoard playingBoard;
    final TeamColor turnColor;

    public InProgress(PlayingBoard playingBoard, TeamColor turnColor) {
        this.playingBoard = playingBoard;
        this.turnColor = turnColor;
    }

    @Override
    public final State movePiece(PieceType pieceType, Position source, Position destination) {
        Piece sourcePiece = playingBoard.getPieceBy(source);
        validateIsMyPiece(sourcePiece);

        Piece destinationPiece = playingBoard.getPieceBy(destination);
        playingBoard.move(pieceType, source, destination);

        if (destinationPiece.isPieceType(PieceType.GENERAL)) {
            return new Finished(turnColor);
        }
        return getNextTurn();
    }

    @Override
    public final int getDestinationPieceScore(Position destination) {
        Piece piece = playingBoard.getPieceBy(destination);
        return piece.getScore();
    }

    protected void validateIsMyPiece(Piece piece) {
        if (piece.getColor() != turnColor) {
            throw new IllegalArgumentException("움직이려는 기물이 " + turnColor + "색이 아닙니다.");
        }
    }

    protected abstract State getNextTurn();

    @Override
    public TeamColor getColor() {
        return turnColor;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public PlayingBoard getPlayingBoard() {
        return playingBoard;
    }
}

package domain.piece;

import domain.board.Position;

import java.util.List;

public record Chariot(PieceType pieceType) implements Piece {
    @Override
    public List<Position> getPathPositions(Position from, Position to) {
        return List.of();
    }

    @Override
    public void canMove(List<Piece> pathWithPiece) {

    }
}

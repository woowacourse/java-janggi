package domain.movement;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;

interface MovementRule {
    boolean isValid(Piece piece, Paths candidatePaths, Position to, Board board);
}
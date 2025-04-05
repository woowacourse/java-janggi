//package janggi.piece;
//
//import janggi.direction.PathValidator;
//import janggi.direction.PieceType;
//import janggi.piece.board.Board;
//import janggi.position.Position;
//
//public class Piece {
//
//    private final PathValidator pathValidator;
//
//    public Piece(final PathValidator pathValidator) {
//        this.pathValidator = pathValidator;
//    }
//
//    public void validateMovement(final Position currentPosition, final Position arrivalPosition,
//                                 final Board board) {
//        pathValidator.validatePath(currentPosition, arrivalPosition, board);
//    }
//
//    public boolean isObstacleJumping() {
//        return pathValidator.isObstacleJumping();
//    }
//
//    public boolean matchPieceMovement(final PieceType givenPieceType) {
//        return getPieceType() == givenPieceType;
//    }
//
//    public PieceType getPieceType() {
//        return pathValidator.getPieceType();
//    }
//}

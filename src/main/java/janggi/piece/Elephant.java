package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Elephant implements Piece{

    private final Movement movement = Movement.ELEPHANT;
    private final Position position;

    public Elephant(Position position) {
        validatePositionRange(position);
        this.position = position;
    }

    @Override
    public void move() {

    }

    @Override
    public List<Position> checkPossibleMoves() {
        return movement.getDirections()
                .stream()
                .map(direction ->
                        {
                            Position position = direction.plusOffsetToPosition(this.position);
                            return makePositionWithOptional(position);
                        }
                )
                .flatMap(Optional::stream)
                .toList();
    }

    @Override
    public Map<Position, Boolean> isAlreadyLocatedWithinOneSpaceOrAtThatSpace(Piece selectedPiece) {
        List<Position> temp = new ArrayList<>();
        List<Position> possibleMoves = selectedPiece.checkPossibleMoves();
        System.out.println(possibleMoves);
        if(canMoveFirstStep(selectedPiece)) {
            System.out.println("여기");
            for (Position possibleMove : possibleMoves) {
                System.out.println(possibleMove);
                if (!(Math.abs(possibleMove.getX()) - Math.abs(this.position.getX()) == 2)) {
                    temp.add(possibleMove);
                }
            }
        };
        System.out.println(temp);
        return Map.of();
    }

    @Override
    public boolean canMoveFirstStep(Piece seletedPiece) {
        if(position.getX() + 1 == seletedPiece.getPosition().getX() && seletedPiece.getPosition().getY() == position.getY()) {
            return false;
        }
        if(position.getX() - 1 == seletedPiece.getPosition().getX() && seletedPiece.getPosition().getY() == position.getY()) {
            return false;
        }
        if(seletedPiece.getPosition().getX() == position.getX() && position.getY() + 1 == seletedPiece.getPosition().getY()) {
            return false;
        }
        return !(seletedPiece.getPosition().getX() == position.getX() && position.getY() - 1 == seletedPiece.getPosition().getY());
    }

    /*@Override
    public boolean canMoveFirstStep(Piece seletedPiece) {
        if(seletedPiece.getPosition().getX() + 1 == position.getX() && seletedPiece.getPosition().getY() == position.getY()) {
            return false;
        }
        if(seletedPiece.getPosition().getX() - 1 == position.getX() && seletedPiece.getPosition().getY() == position.getY()) {
            return false;
        }
        if(seletedPiece.getPosition().getX() == position.getX() && seletedPiece.getPosition().getY() + 1 == position.getY()) {
            return false;
        }
        return !(seletedPiece.getPosition().getX() == position.getX() && seletedPiece.getPosition().getY() - 1 == position.getY());
    }*/

    @Override
    public boolean isOneSpaceAway(Position piecePosition, Position possiblePosition) { //piecePosition
//        if (piecePosition.getX() == possiblePosition)
//        this.position.getX() + 1
        return false;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isAtThatSpace(Position piecePosition, Position possiblePosition) {
        return Piece.super.isAtThatSpace(piecePosition, possiblePosition);
    }

//    public List<Position> removeUnMoveableFirstStep(Elephant selectPiece) {
//        List<Position> possibleMoves = selectPiece.checkPossibleMoves();
//        if (selectPiece.position.getX() + 1 == this.position.getX()) {
//            possibleMoves.stream()
//                    .filter(
//                            position -> {
//                                return !(position.getX() - selectPiece.position.getX() == 2);
//                            }
//
//                    )
//        }
//        return null;
//    }
}

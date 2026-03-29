package domain.pieces;

import domain.Camp;
import domain.ExistBoard;
import domain.Position;
import java.util.HashSet;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, ExistBoard existBoard) {
        Set<Position> movablePositions = new HashSet<>();

        movablePositions.addAll(moveUp(from, existBoard));
        movablePositions.addAll(moveLeft(from, existBoard));
        movablePositions.addAll(moveRight(from, existBoard));
        movablePositions.addAll(moveDown(from, existBoard));

        return movablePositions.contains(to);
    }

    private Set<Position> moveRight(Position position, ExistBoard existBoard) {
        Set<Position> movablePositions = new HashSet<>();
        boolean jumping = false;

        try {
            while (!jumping) {
                position = right(position);
                jumping = checkCannonJumping(position, existBoard);
            }
        } catch (IllegalArgumentException e) {

        }

        try {
            do {
                position = right(position);
                if (existBoard.isDifferentPieceType(position, this)) {
                    movablePositions.add(position);
                }
            } while (!existBoard.isExist(position));
        } catch (IllegalArgumentException e) {

        }

        return movablePositions;
    }

    private Set<Position> moveLeft(Position position, ExistBoard existBoard) {
        Set<Position> movablePositions = new HashSet<>();
        boolean jumping = false;

        try {
            while (!jumping) {
                position = left(position);
                jumping = checkCannonJumping(position, existBoard);
            }
        } catch (IllegalArgumentException e) {

        }

        try {
            do {
                position = left(position);
                if (existBoard.isDifferentPieceType(position, this)) {
                    movablePositions.add(position);
                }
            } while (!existBoard.isExist(position));
        } catch (IllegalArgumentException e) {

        }

        return movablePositions;
    }

    private Set<Position> moveUp(Position position, ExistBoard existBoard) {
        Set<Position> movablePositions = new HashSet<>();
        boolean jumping = false;

        try {
            while (!jumping) {
                position = up(position);
                jumping = checkCannonJumping(position, existBoard);
            }
        } catch (IllegalArgumentException e) {

        }

        try {
            do {
                position = up(position);
                if (existBoard.isDifferentPieceType(position, this)) {
                    movablePositions.add(position);
                }
            } while (!existBoard.isExist(position));
        } catch (IllegalArgumentException e) {

        }

        return movablePositions;
    }

    private Set<Position> moveDown(Position position, ExistBoard existBoard) {
        Set<Position> movablePositions = new HashSet<>();
        boolean jumping = false;

        try {
            while (!jumping) {
                position = down(position);
                jumping = checkCannonJumping(position, existBoard);
            }
        } catch (IllegalArgumentException e) {

        }

        try {
            do {
                position = down(position);
                if (existBoard.isDifferentPieceType(position, this)) {
                    movablePositions.add(position);
                }
            } while (!existBoard.isExist(position));
        } catch (IllegalArgumentException e) {

        }

        return movablePositions;
    }

    private boolean checkCannonJumping(Position position, ExistBoard existBoard) {
        return existBoard.isExist(position) && existBoard.isDifferentPieceType(position, this);
    }
}

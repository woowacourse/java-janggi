package domain.pieces;

import domain.Camp;
import domain.BoardReader;
import domain.PieceType;
import domain.Position;
import java.util.HashSet;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(Camp camp) {
        super(camp, PieceType.CANNON);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        Set<Position> movablePositions = new HashSet<>();

        movablePositions.addAll(moveUp(from, boardReader));
        movablePositions.addAll(moveLeft(from, boardReader));
        movablePositions.addAll(moveRight(from, boardReader));
        movablePositions.addAll(moveDown(from, boardReader));

        return movablePositions.contains(to);
    }

    private Set<Position> moveRight(Position position, BoardReader boardReader) {
        Set<Position> movablePositions = new HashSet<>();
        boolean jumping = false;

        try {
            while (!jumping) {
                position = right(position);
                jumping = checkCannonJumping(position, boardReader);
            }
        } catch (IllegalArgumentException e) {

        }

        try {
            do {
                position = right(position);
                if (boardReader.isDifferentPieceType(position, this)) {
                    movablePositions.add(position);
                }
            } while (!boardReader.isExist(position));
        } catch (IllegalArgumentException e) {

        }

        return movablePositions;
    }

    private Set<Position> moveLeft(Position position, BoardReader boardReader) {
        Set<Position> movablePositions = new HashSet<>();
        boolean jumping = false;

        try {
            while (!jumping) {
                position = left(position);
                jumping = checkCannonJumping(position, boardReader);
            }
        } catch (IllegalArgumentException e) {

        }

        try {
            do {
                position = left(position);
                if (boardReader.isDifferentPieceType(position, this)) {
                    movablePositions.add(position);
                }
            } while (!boardReader.isExist(position));
        } catch (IllegalArgumentException e) {

        }

        return movablePositions;
    }

    private Set<Position> moveUp(Position position, BoardReader boardReader) {
        Set<Position> movablePositions = new HashSet<>();
        boolean jumping = false;

        try {
            while (!jumping) {
                position = up(position);
                jumping = checkCannonJumping(position, boardReader);
            }
        } catch (IllegalArgumentException e) {

        }

        try {
            do {
                position = up(position);
                if (boardReader.isDifferentPieceType(position, this)) {
                    movablePositions.add(position);
                }
            } while (!boardReader.isExist(position));
        } catch (IllegalArgumentException e) {

        }

        return movablePositions;
    }

    private Set<Position> moveDown(Position position, BoardReader boardReader) {
        Set<Position> movablePositions = new HashSet<>();
        boolean jumping = false;

        try {
            while (!jumping) {
                position = down(position);
                jumping = checkCannonJumping(position, boardReader);
            }
        } catch (IllegalArgumentException e) {

        }

        try {
            do {
                position = down(position);
                if (boardReader.isDifferentPieceType(position, this)) {
                    movablePositions.add(position);
                }
            } while (!boardReader.isExist(position));
        } catch (IllegalArgumentException e) {

        }

        return movablePositions;
    }

    private boolean checkCannonJumping(Position position, BoardReader boardReader) {
        return boardReader.isExist(position) && boardReader.isDifferentPieceType(position, this);
    }
}

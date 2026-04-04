package domain.pieces;

import domain.Camp;
import domain.ExistBoard;
import domain.PieceType;
import domain.Position;
import java.util.HashSet;
import java.util.Set;

public class Chariot extends Piece {

    public Chariot(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, ExistBoard existBoard) {
        Set<Position> movablePositions = new HashSet<>();

        movablePositions.addAll(moveUp(from, existBoard));
        movablePositions.addAll(moveDown(from, existBoard));
        movablePositions.addAll(moveLeft(from, existBoard));
        movablePositions.addAll(moveRight(from, existBoard));

        return movablePositions.contains(to);
    }

    private Set<Position> moveUp(Position position, ExistBoard existBoard) {
        Set<Position> movablePositions = new HashSet<>();
        try {
            do {
                position = up(position);
                movablePositions.add(position);
            } while (!existBoard.isExist(position));
        } catch (IllegalArgumentException e) {

        }
        return movablePositions;
    }

    private Set<Position> moveLeft(Position position, ExistBoard existBoard) {
        Set<Position> movablePositions = new HashSet<>();
        try {
            do {
                position = left(position);
                movablePositions.add(position);
            } while (!existBoard.isExist(position));
        } catch (IllegalArgumentException e) {

        }
        return movablePositions;
    }

    private Set<Position> moveRight(Position position, ExistBoard existBoard) {
        Set<Position> movablePositions = new HashSet<>();
        try {
            do {
                position = right(position);
                movablePositions.add(position);
            } while (!existBoard.isExist(position));
        } catch (IllegalArgumentException e) {

        }
        return movablePositions;
    }

    private Set<Position> moveDown(Position position, ExistBoard existBoard) {
        Set<Position> movablePositions = new HashSet<>();
        try {
            do {
                position = down(position);
                movablePositions.add(position);
            } while (!existBoard.isExist(position));
        } catch (IllegalArgumentException e) {

        }
        return movablePositions;
    }

    @Override
    public PieceType getPieceType() {
        return this.pieceType;
    }
}

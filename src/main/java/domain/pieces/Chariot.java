package domain.pieces;

import domain.Camp;
import domain.BoardReader;
import domain.PieceType;
import domain.Position;
import java.util.HashSet;
import java.util.Set;

public class Chariot extends Piece {

    public Chariot(Camp camp) {
        super(camp, PieceType.CHARIOT);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        Set<Position> movablePositions = new HashSet<>();

        movablePositions.addAll(moveUp(from, boardReader));
        movablePositions.addAll(moveDown(from, boardReader));
        movablePositions.addAll(moveLeft(from, boardReader));
        movablePositions.addAll(moveRight(from, boardReader));

        return movablePositions.contains(to);
    }

    private Set<Position> moveUp(Position position, BoardReader boardReader) {
        Set<Position> movablePositions = new HashSet<>();
        try {
            do {
                position = up(position);
                movablePositions.add(position);
            } while (!boardReader.isExist(position));
        } catch (IllegalArgumentException e) {

        }
        return movablePositions;
    }

    private Set<Position> moveLeft(Position position, BoardReader boardReader) {
        Set<Position> movablePositions = new HashSet<>();
        try {
            do {
                position = left(position);
                movablePositions.add(position);
            } while (!boardReader.isExist(position));
        } catch (IllegalArgumentException e) {

        }
        return movablePositions;
    }

    private Set<Position> moveRight(Position position, BoardReader boardReader) {
        Set<Position> movablePositions = new HashSet<>();
        try {
            do {
                position = right(position);
                movablePositions.add(position);
            } while (!boardReader.isExist(position));
        } catch (IllegalArgumentException e) {

        }
        return movablePositions;
    }

    private Set<Position> moveDown(Position position, BoardReader boardReader) {
        Set<Position> movablePositions = new HashSet<>();
        try {
            do {
                position = down(position);
                movablePositions.add(position);
            } while (!boardReader.isExist(position));
        } catch (IllegalArgumentException e) {

        }
        return movablePositions;
    }
}

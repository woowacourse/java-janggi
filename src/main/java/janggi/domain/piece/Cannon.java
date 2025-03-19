package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {

    protected Cannon(Side side, Position position) {
        super(side, position);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        if (getPosition().hasSameX(destination)) {
            return !getPosition().hasSameY(destination);
        }
        return getPosition().hasSameY(destination);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {

        List<Piece> 이동하고자_하는_위치_내_기물들 = 이동하고자_하는_위치_내에_몇_가지_기물들(existingPieces, destination);

        if (이동하고자_하는_위치_내_기물들.size() == 1 && 이동하고자_하는_위치_내_기물들.stream()
                .noneMatch(piece -> piece.getClass().equals(this.getClass()))) {

            if (!배열_내에_특정_포지션을_갖는_말이_있는지(existingPieces, destination)) {
                return true;
            }
            Piece piece = 배열_내에_특정_포지션을_갖는_말(existingPieces, destination);

            return piece.getSide() != getSide() && !piece.getClass().equals(this.getClass());
        }
        return false;
    }

    private boolean 배열_내에_특정_포지션을_갖는_말이_있는지(List<Piece> existingPieces, Position position) {
        return existingPieces.stream()
                .anyMatch(existingPiece -> existingPiece.isSamePosition(position));
    }


    private Piece 배열_내에_특정_포지션을_갖는_말(List<Piece> existingPieces, Position position) {
        return existingPieces.stream()
                .filter(existingPiece -> existingPiece.isSamePosition(position))
                .findAny()
                .get();
    }

    private List<Piece> 이동하고자_하는_위치_내에_몇_가지_기물들(List<Piece> existingPieces, Position destination) {
        List<Piece> result = new ArrayList<>();
        int currentX = getXPosition();
        int currentY = getYPosition();
        int destinationX = destination.getX();
        int destinationY = destination.getY();

        if (getPosition().hasSameX(destination)) {
            if (currentY < destinationY) {
                for (int y = currentY + 1; y < destinationY; y++) {
                    if (배열_내에_특정_포지션을_갖는_말이_있는지(existingPieces, new Position(currentX, y))) {
                        result.add(배열_내에_특정_포지션을_갖는_말(existingPieces, new Position(currentX, y)));
                    }
                }
                return result;
            }
            for (int y = destinationY + 1; y < currentY; y++) {
                if (배열_내에_특정_포지션을_갖는_말이_있는지(existingPieces, new Position(currentX, y))) {
                    result.add(배열_내에_특정_포지션을_갖는_말(existingPieces, new Position(currentX, y)));
                }
            }
            return result;
        }
        if (currentX < destinationX) {
            for (int x = currentX + 1; x < destinationX; x++) {
                if (배열_내에_특정_포지션을_갖는_말이_있는지(existingPieces, new Position(x, currentY))) {
                    result.add(배열_내에_특정_포지션을_갖는_말(existingPieces, new Position(x, currentY)));
                }
            }
            return result;
        }
        for (int x = destinationX + 1; x < currentX; x++) {
            if (배열_내에_특정_포지션을_갖는_말이_있는지(existingPieces, new Position(x, currentY))) {
                result.add(배열_내에_특정_포지션을_갖는_말(existingPieces, new Position(x, currentY)));
            }
        }
        return result;
    }
}

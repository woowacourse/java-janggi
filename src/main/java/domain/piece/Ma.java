package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.MaMovement;
import java.util.ArrayList;
import java.util.List;

public class Ma implements Piece {
    private final Country country;

    public Ma(Country country) {
        this.country = country;
    }

    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for (MaMovement maMovement : MaMovement.values()) {
            if (!janggiBoard.hasPiece(movePosition(currCoordinate, maMovement.getDirection()))) {
                for (JanggiCoordinate destination : maMovement.getDestination()) {
                    JanggiCoordinate next = movePosition(currCoordinate, destination);
                    if (janggiBoard.isOutOfBoundary(next)) {
                        continue;
                    }
                    if (!janggiBoard.hasPiece(next) && !janggiBoard.isMyTeam(currCoordinate, next)) {
                        availablePositions.add(next);
                    }
                }
            }
        }
        return availablePositions;
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }

    @Override
    public boolean isPho() {
        return false;
    }

    @Override
    public Country getTeam() {
        return country;
    }
}

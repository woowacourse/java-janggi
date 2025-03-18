package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;

import java.util.ArrayList;
import java.util.List;

public class Ma implements Piece {
    private static final List<JanggiCoordinate> OFFSET_ROUTE = List.of(
            new JanggiCoordinate(1,0),
            new JanggiCoordinate(0,1),
            new JanggiCoordinate(-1,0),
            new JanggiCoordinate(0,-1)
    );

    private static final List<JanggiCoordinate> OFFSET_MOVEMENT = List.of(
            new JanggiCoordinate(-1, -2),
            new JanggiCoordinate(1, -2),

            new JanggiCoordinate(2,-1),
            new JanggiCoordinate(2,1),

            new JanggiCoordinate(1,2),
            new JanggiCoordinate(1,-2),

            new JanggiCoordinate(-2,1),
            new JanggiCoordinate(-2,-1)
    );

    private final JanggiCoordinate currCoordinate;
    private final Team team;

    public Ma(JanggiCoordinate currCoordinate, Team team) {
        this.currCoordinate = currCoordinate;
        this.team = team;
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        if(!janggiBoard.hasPiece(movePosition(OFFSET_ROUTE.get(0)))){
            JanggiCoordinate next;

            next = movePosition(OFFSET_MOVEMENT.get(0));
            if(!janggiBoard.hasPiece(next) || !janggiBoard.isMyTeam(currCoordinate,next)) {
                availablePositions.add(next);
            }

            next = movePosition(OFFSET_MOVEMENT.get(1));
            if(!janggiBoard.hasPiece(next) || !janggiBoard.isMyTeam(currCoordinate,next)) {
                availablePositions.add(next);
            }
        }
        if(!janggiBoard.hasPiece(movePosition(OFFSET_ROUTE.get(1)))){
            JanggiCoordinate next;
            next = movePosition(OFFSET_MOVEMENT.get(2));
            if(!janggiBoard.hasPiece(next) || !janggiBoard.isMyTeam(currCoordinate,next)) {
                availablePositions.add(next);
            }

            next = movePosition(OFFSET_MOVEMENT.get(3));
            if(!janggiBoard.hasPiece(next) || !janggiBoard.isMyTeam(currCoordinate,next)) {
                availablePositions.add(next);
            }
        }
        if(!janggiBoard.hasPiece(movePosition(OFFSET_ROUTE.get(2)))){
            JanggiCoordinate next;
            next = movePosition(OFFSET_MOVEMENT.get(4));
            if(!janggiBoard.hasPiece(next) || !janggiBoard.isMyTeam(currCoordinate,next)) {
                availablePositions.add(next);
            }

            next = movePosition(OFFSET_MOVEMENT.get(5));
            if(!janggiBoard.hasPiece(next) || !janggiBoard.isMyTeam(currCoordinate,next)) {
                availablePositions.add(next);
            }
        }
        if (!janggiBoard.hasPiece(movePosition(OFFSET_ROUTE.get(3)))) {
            JanggiCoordinate next;
            next = movePosition(OFFSET_MOVEMENT.get(6));
            if(!janggiBoard.hasPiece(next) || !janggiBoard.isMyTeam(currCoordinate,next)) {
                availablePositions.add(next);
            }

            next = movePosition(OFFSET_MOVEMENT.get(7));
            if(!janggiBoard.hasPiece(next) || !janggiBoard.isMyTeam(currCoordinate,next)) {
                availablePositions.add(next);
            }
        }

        return availablePositions;
    }

    public JanggiCoordinate movePosition(JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }

    @Override
    public Team getTeam() {
        return team;
    }
}

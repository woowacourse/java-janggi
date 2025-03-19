package domain.piece.movement;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;

import java.util.ArrayList;
import java.util.List;

public enum ChaMovement {
    UP(new JanggiCoordinate(0, -1)),
    DOWN(new JanggiCoordinate(0, 1)),
    RIGHT(new JanggiCoordinate(1, 0)),
    LEFT(new JanggiCoordinate(-1, 0));

    private final JanggiCoordinate direction;

    public static List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate, JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for(ChaMovement direction : values()){
            JanggiCoordinate next = movePosition(currCoordinate,direction.getDirection());
            while(true){
                if(janggiBoard.isOutOfBoundary(next)){
                    break;
                }
                if(janggiBoard.hasPiece(next) && !janggiBoard.isMyTeam(currCoordinate,next)){
                    availablePositions.add(next);
                    break;
                }
                if(janggiBoard.hasPiece(next) && janggiBoard.isMyTeam(currCoordinate,next)){
                    break;
                }

                availablePositions.add(next);
                next = movePosition(next,direction.getDirection());
            }
        }

        return availablePositions;
    }

    ChaMovement(JanggiCoordinate direction) {
        this.direction = direction;
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }

    public JanggiCoordinate getDirection() {
        return direction;
    }
}

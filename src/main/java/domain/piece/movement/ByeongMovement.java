package domain.piece.movement;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.List;

public enum ByeongMovement {
    UP(new JanggiCoordinate(0, -1)),
    DOWN(new JanggiCoordinate(0, 1)),
    RIGHT(new JanggiCoordinate(1, 0)),
    LEFT(new JanggiCoordinate(-1, 0));

    private final JanggiCoordinate direction;

    ByeongMovement(JanggiCoordinate direction) {
        this.direction = direction;
    }

    public static List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                                JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        Team team = janggiBoard.findTeamByCoordinate(currCoordinate);

        if (team == Team.Cho) {
            for (ByeongMovement byeongMovement : values()) {
                if (byeongMovement == DOWN) {
                    continue;
                }
                JanggiCoordinate next = movePosition(currCoordinate, byeongMovement.direction);
                if (!janggiBoard.isOutOfBoundary(next) && !janggiBoard.isMyTeam(currCoordinate, next)) {
                    availablePositions.add(next);
                }
            }
            return availablePositions;
        }

        for (ByeongMovement byeongMovement : values()) {
            if (byeongMovement == UP) {
                continue;
            }
            JanggiCoordinate next = movePosition(currCoordinate, byeongMovement.direction);
            if (!janggiBoard.isOutOfBoundary(next) && !janggiBoard.isMyTeam(currCoordinate, next)) {
                availablePositions.add(next);
            }
        }
        return availablePositions;
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }

}

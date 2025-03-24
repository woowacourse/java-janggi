package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.List;
import java.util.Objects;

public class Elephant implements Piece {

    private final Team team;
    private final Position position;
    private final List<List<Movement>> movements = List.of(
            List.of(Movement.UP_UP_LEFT, Movement.LEFT_UP),
            List.of(Movement.UP_UP_RIGHT, Movement.RIGHT_UP),
            List.of(Movement.DOWN_DOWN_RIGHT, Movement.RIGHT_DOWN),
            List.of(Movement.DOWN_DOWN_LEFT, Movement.LEFT_DOWN),
            List.of(Movement.RIGHT_RIGHT_UP, Movement.RIGHT_UP),
            List.of(Movement.RIGHT_RIGHT_DOWN, Movement.RIGHT_DOWN),
            List.of(Movement.LEFT_LEFT_UP, Movement.LEFT_UP),
            List.of(Movement.LEFT_LEFT_DOWN, Movement.LEFT_DOWN)
    );

    private final List<Movement> availablePathMovement = List.of(
            Movement.UP,
            Movement.DOWN,
            Movement.LEFT,
            Movement.RIGHT,
            Movement.UP_UP_LEFT,
            Movement.UP_UP_RIGHT,
            Movement.DOWN_DOWN_RIGHT,
            Movement.DOWN_DOWN_LEFT,
            Movement.RIGHT_RIGHT_UP,
            Movement.RIGHT_RIGHT_DOWN,
            Movement.LEFT_LEFT_UP,
            Movement.LEFT_LEFT_DOWN
    );

    public Elephant(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    // 입력한 도착 위치가 갈 수 있는 곳인 경우
/*    public boolean isAvailableArrivePosition(Position arrivedPosition) {
        return movements.stream()
                .anyMatch(movement -> !arrivedPosition.isOutOfBoards() && move(movement).equals(arrivedPosition));
    }*/

    // 현 위치에서 도착 위치까지 도달할 때 장애물이 있는 경우 확인
    // 도착 경로까지 이동가능한 모든 Movement 상대로 해당 위치에 장애물이 존재하는지 확인

    public Position move(List<Movement> movements) {
        Position arrivedPosition = position;

        for (Movement movement : movements) {
            arrivedPosition = movement.move(arrivedPosition);
        }

        return arrivedPosition;
    }

    @Override
    public void move(Position arrivedPosition, List<Piece> positioningPiece) {

    }

    @Override
    public boolean isSameTeam(Team team) {
        return false;
    }

    @Override
    public boolean matchesPosition(Position position) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Elephant elephant = (Elephant) o;
        return team == elephant.team && Objects.equals(position, elephant.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }

    @Override
    public String toString() {
        return "[" + team +
                ": " + position;
    }
}

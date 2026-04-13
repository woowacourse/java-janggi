package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StepMoveRuleTest {

    @Test
    @DisplayName("경로와 목적지에 장애물이 없다")
    void execute_1() {
        // given
        Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 6), new Elephant(TeamType.RED)
        );
        Board board = new Board(positionPieceMap);
        List<Movement> movementOrder = List.of(
                new Movement(Direction.RIGHT),
                new Movement(Direction.UP_RIGHT),
                new Movement(Direction.UP_RIGHT)
        );
        MoveRule stepMoveRule = new StepMoveRule(movementOrder);
        Position from = Position.valueOf(5, 6);

        // when
        List<Position> actual = stepMoveRule.execute(from, TeamType.RED, board);

        // then
        List<Position> expected = List.of(Position.valueOf(3, 9));
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("경로에 장애물이 없지만 목적지에 장애물이 있다")
    void execute_2() {
        // given
        Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 3), new Elephant(TeamType.RED),
                Position.valueOf(3, 6), new Soldier(TeamType.RED)
        );
        Board board = new Board(positionPieceMap);
        List<Movement> movementOrder = List.of(
                new Movement(Direction.RIGHT),
                new Movement(Direction.UP_RIGHT),
                new Movement(Direction.UP_RIGHT)
        );
        MoveRule stepMoveRule = new StepMoveRule(movementOrder);
        Position from = Position.valueOf(5, 3);

        // when
        List<Position> actual = stepMoveRule.execute(from, TeamType.RED, board);

        // then
        List<Position> expected = List.of();
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("경로에 장애물이 없고 목적지에 적 기물이 있다")
    void execute_3() {
        // given
        Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 3), new Elephant(TeamType.RED),
                Position.valueOf(3, 6), new Soldier(TeamType.BLUE)
        );
        Board board = new Board(positionPieceMap);
        List<Movement> movementOrder = List.of(
                new Movement(Direction.RIGHT),
                new Movement(Direction.UP_RIGHT),
                new Movement(Direction.UP_RIGHT)
        );
        MoveRule stepMoveRule = new StepMoveRule(movementOrder);
        Position from = Position.valueOf(5, 3);

        // when
        List<Position> actual = stepMoveRule.execute(from, TeamType.RED, board);

        // then
        List<Position> expected = List.of(Position.valueOf(3, 6));
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("경로에 장애물이 있고 목적지에 적 기물이 있다")
    void execute_4() {
        // given
        Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 3), new Elephant(TeamType.RED),
                Position.valueOf(5, 4), new Soldier(TeamType.BLUE),
                Position.valueOf(6, 3), new Soldier(TeamType.BLUE)
        );
        Board board = new Board(positionPieceMap);
        List<Movement> movementOrder = List.of(
                new Movement(Direction.RIGHT),
                new Movement(Direction.UP_RIGHT),
                new Movement(Direction.UP_RIGHT)
        );
        MoveRule stepMoveRule = new StepMoveRule(movementOrder);
        Position from = Position.valueOf(5, 3);

        // when
        List<Position> actual = stepMoveRule.execute(from, TeamType.RED, board);

        // then
        List<Position> expected = List.of();
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("경로에 장애물이 있고 목적지에 적 기물이 있다")
    void execute_5() {
        // given
        Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 3), new Elephant(TeamType.RED),
                Position.valueOf(4, 5), new Soldier(TeamType.BLUE),
                Position.valueOf(6, 3), new Soldier(TeamType.BLUE)
        );
        Board board = new Board(positionPieceMap);
        List<Movement> movementOrder = List.of(
                new Movement(Direction.RIGHT),
                new Movement(Direction.UP_RIGHT),
                new Movement(Direction.UP_RIGHT)
        );
        MoveRule stepMoveRule = new StepMoveRule(movementOrder);
        Position from = Position.valueOf(5, 3);

        // when
        List<Position> actual = stepMoveRule.execute(from, TeamType.RED, board);

        // then
        List<Position> expected = List.of();
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("보드판의 경계를 나가게 되어 목적지로 이동할 수 없다")
    void execute_6() {
        // given
        Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 7), new Elephant(TeamType.RED)
        );
        Board board = new Board(positionPieceMap);
        List<Movement> movementOrder = List.of(
                new Movement(Direction.RIGHT),
                new Movement(Direction.UP_RIGHT),
                new Movement(Direction.UP_RIGHT)
        );
        MoveRule stepMoveRule = new StepMoveRule(movementOrder);
        Position from = Position.valueOf(5, 7);

        // when
        List<Position> actual = stepMoveRule.execute(from, TeamType.RED, board);

        // then
        List<Position> expected = List.of();
        assertThat(actual).hasSameElementsAs(expected);
    }
}

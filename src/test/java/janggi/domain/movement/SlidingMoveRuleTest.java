package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SlidingMoveRuleTest {

    @Test
    @DisplayName("적이 있는 곳 까지 이동할 수 있다")
    public void success1() {
        // given
        Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 3), new Chariot(TeamType.RED),
                Position.valueOf(8, 3), new Elephant(TeamType.BLUE)
        );
        Board board = new Board(positionPieceMap);
        Direction direction = Direction.DOWN;
        MoveRule moveRuleWithTraces = new SlidingMoveRule(new Movement(direction));
        Position from = Position.valueOf(5, 3);

        // when
        List<Position> actual = moveRuleWithTraces.execute(from, TeamType.RED, board);

        // then
        List<Position> expected = List.of(
                Position.valueOf(6, 3),
                Position.valueOf(7, 3),
                Position.valueOf(8, 3)
        );
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("아군이 있는 곳 까지 이동할 수 있다")
    public void success2() {
        // given
        Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 3), new Chariot(TeamType.RED),
                Position.valueOf(8, 3), new Soldier(TeamType.RED)
        );
        Board board = new Board(positionPieceMap);
        Direction direction = Direction.DOWN;
        MoveRule moveRuleWithTraces = new SlidingMoveRule(new Movement(direction));
        Position from = Position.valueOf(5, 3);

        // when
        List<Position> actual = moveRuleWithTraces.execute(from, TeamType.RED, board);

        // then
        List<Position> expected = List.of(
                Position.valueOf(6, 3),
                Position.valueOf(7, 3)
        );
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("장기판 경계 까지 이동할 수 있다")
    public void success3() {
        // given
        Map<Position, Piece> positionPieceMap = Map.of(Position.valueOf(5, 3), new Chariot(TeamType.RED));
        Board board = new Board(positionPieceMap);
        Direction direction = Direction.UP;
        MoveRule moveRuleWithTraces = new SlidingMoveRule(new Movement(direction));
        Position from = Position.valueOf(5, 3);

        // when
        List<Position> actual = moveRuleWithTraces.execute(from, TeamType.RED, board);

        // then
        List<Position> expected = List.of(
                Position.valueOf(1, 3),
                Position.valueOf(2, 3),
                Position.valueOf(3, 3),
                Position.valueOf(4, 3)
        );
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("장기판 경계에선 더 이동할 수 없다")
    public void success4() {
        // given
        Map<Position, Piece> positionPieceMap = Map.of(Position.valueOf(1, 3), new Chariot(TeamType.RED));
        Board board = new Board(positionPieceMap);
        Direction direction = Direction.UP;
        MoveRule moveRuleWithTraces = new SlidingMoveRule(new Movement(direction));
        Position from = Position.valueOf(1, 3);

        // when
        List<Position> actual = moveRuleWithTraces.execute(from, TeamType.RED, board);

        // then
        List<Position> expected = List.of();
        assertThat(actual).hasSameElementsAs(expected);
    }
}

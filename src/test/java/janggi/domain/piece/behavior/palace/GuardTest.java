package janggi.domain.piece.behavior.palace;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.BoardPositionInfo;
import janggi.domain.piece.Piece;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GuardTest {

    @DisplayName("사가 움직일 수 있는 포지션들을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"9,5,8", "2,5,8", "1,4,3", "1,6,3", "3,4,3", "2,4,3"})
    void test1(int row, int column, int expected) {
        // given
        Position position = Position.of(row, column);
        Guard guard = new Guard();
        Piece piece = new Piece(Team.HAN, guard);
        Board board = new Board(Map.of(position, piece));
        BoardPositionInfo boardPositionInfo = new BoardPositionInfo(board, position, Team.HAN);
        // when
        Set<Position> actual = guard.generateAvailableMovePositions(boardPositionInfo);

        // then
        assertThat(actual).hasSize(expected);
    }

    @DisplayName("궁성 밖으로는 나갈 수 없다.")
    @Test
    void test2() {

        // given
        Position position = Position.of(1, 4);
        Guard guard = new Guard();
        Piece piece = new Piece(Team.HAN, guard);

        Board board = new Board(Map.of(position, piece));
        BoardPositionInfo boardPositionInfo = new BoardPositionInfo(board, position, Team.HAN);

        // when
        Set<Position> result = guard.generateAvailableMovePositions(boardPositionInfo);

        assertAll(() -> assertThat(result).doesNotContain(Position.of(1, 3)),
                () -> assertThat(result).containsExactlyInAnyOrder(
                        Position.of(2, 4),
                        Position.of(2, 5),
                        Position.of(1, 5))
        );
    }
}

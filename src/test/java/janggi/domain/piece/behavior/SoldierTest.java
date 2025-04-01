package janggi.domain.piece.behavior;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.BoardPositionInfo;
import janggi.domain.piece.Piece;
import janggi.factory.PieceInitFactory;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {

    private static Stream<Arguments> moveableArguments() {
        return Stream.of(
                Arguments.of(Position.of(7, 3), Team.HAN,
                        List.of(Position.of(7, 2), Position.of(7, 4), Position.of(8, 3))),
                Arguments.of(Position.of(7, 1), Team.HAN,
                        List.of(Position.of(8, 1), Position.of(7, 2))),
                Arguments.of(Position.of(7, 1), Team.CHO,
                        List.of(Position.of(6, 1), Position.of(7, 2))),
                Arguments.of(Position.of(1, 1), Team.CHO, List.of(Position.of(1, 2)))
        );
    }

    @DisplayName("좌표를 입력하면 이동 가능한 좌표들을 반환한다.")
    @ParameterizedTest
    @MethodSource("moveableArguments")
    void test1(Position startingPosition, Team team, List<Position> expected) {
        // given
        Board board = new Board(PieceInitFactory.initialize());
        Soldier soldier = new Soldier();
        BoardPositionInfo boardPositionInfo = new BoardPositionInfo(board, startingPosition, team);

        // when
        Set<Position> actual = soldier.generateAvailableMovePositions(boardPositionInfo);

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("한나라 병은 대각선 방향을 뒤로 갈 수 없다.")
    @Test
    void test2() {
        Position position = Position.of(3, 6);
        Soldier soldier = new Soldier();

        Piece piece = new Piece(Team.HAN, soldier);

        Board board = new Board(Map.of(position, piece));
        BoardPositionInfo boardPositionInfo = new BoardPositionInfo(board, position, Team.HAN);

        Set<Position> positions = soldier.generateAvailableMovePositions(boardPositionInfo);

        assertThat(positions).doesNotContain(Position.of(2, 5), Position.of(2, 7));
    }

    @DisplayName("한나라 병은 궁성 내에서 대각선으로 움직일 수 있다.")
    @Test
    void test3() {
        Position position = Position.of(9, 5);

        Soldier soldier = new Soldier();

        Piece piece = new Piece(Team.HAN, soldier);

        Board board = new Board(Map.of(position, piece));
        BoardPositionInfo boardPositionInfo = new BoardPositionInfo(board, position, Team.HAN);

        Set<Position> positions = soldier.generateAvailableMovePositions(boardPositionInfo);

        assertThat(positions).contains(Position.of(10, 4), Position.of(10, 6)).hasSize(5);
    }
}

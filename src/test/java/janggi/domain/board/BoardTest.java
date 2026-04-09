package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Position;
import janggi.domain.movement.Direction;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

public class BoardTest {

    @Nested
    @DisplayName("빈칸 여부 테스트")
    class isBlank {

        @Test
        @DisplayName("빈칸인 경우")
        void success_1() {
            Board board = new Board(new LinkedHashMap<>());
            Position position = Position.valueOf(1, 1);
            boolean expected = true;

            boolean actual = board.isBlank(position);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("빈칸이 아닌 경우")
        void success_2() {
            Position position = Position.valueOf(1, 1);
            Map<Position, Piece> positionPieceMap = Map.of(
                position, new Cannon(TeamType.RED));
            Board board = new Board(positionPieceMap);
            boolean expected = false;

            boolean actual = board.isBlank(position);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("위치에 기반한 기물 획득 테스트")
    class FindPieceByPosition {

        @Test
        @DisplayName("정상 테스트")
        void success() {
            Position position = Position.valueOf(1, 1);
            Piece expected = new Cannon(TeamType.RED);
            Map<Position, Piece> positionPieceMap = Map.of(
                position, expected);
            Board board = new Board(positionPieceMap);

            Piece actual = board.findPieceByPosition(position);

            assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @Test
        @DisplayName("빈칸인 경우 예외가 발생한다.")
        void failure() {
            Position position = Position.valueOf(1, 1);
            Board board = new Board(new LinkedHashMap<>());

            assertThatIllegalStateException()
                .isThrownBy(() -> board.findPieceByPosition(position));
        }
    }

    @Nested
    @DisplayName("기물 이동 테스트")
    class MovePiece {

        @Test
        @DisplayName("이동 대상 위치에 기물이 존재하지 않는 경우")
        void success_1() {
            Position from = Position.valueOf(1, 1);
            Position to = Position.valueOf(1, 3);
            Piece me = new Cannon(TeamType.RED);
            Map<Position, Piece> positionPieceMap = Map.of(
                from, me);
            Board board = new Board(positionPieceMap);
            Optional<Piece> expected = Optional.empty();

            Optional<Piece> actual = board.movePiece(from, to);

            assertAll(
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(board).extracting("positionPieceMap")
                    .asInstanceOf(InstanceOfAssertFactories.MAP)
                    .containsEntry(to, me)
            );
        }

        @Test
        @DisplayName("이동 대상 위치에 기물이 존재하는 경우")
        void success_2() {
            Position from = Position.valueOf(1, 1);
            Position to = Position.valueOf(1, 3);
            Piece me = new Chariot(TeamType.RED);
            Piece target = new Soldier(TeamType.BLUE);
            Map<Position, Piece> positionPieceMap = Map.of(
                from, me,
                to, target);
            Board board = new Board(positionPieceMap);
            Optional<Piece> expected = Optional.of(target);

            Optional<Piece> actual = board.movePiece(from, to);

            assertAll(
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(board).extracting("positionPieceMap")
                    .asInstanceOf(InstanceOfAssertFactories.MAP)
                    .containsEntry(to, me)
            );
        }
    }

    @Nested
    @DisplayName("승리 팀 계산 테스트")
    class CalculateWinnerTeam {

        @Test
        @DisplayName("정상 테스트")
        void success() {
            Piece redGeneral = new General(TeamType.RED);
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(2, 5), redGeneral);
            Board board = new Board(positionPieceMap);
            TeamType expected = TeamType.RED;

            TeamType actual = board.calculateWinnerTeam();

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("게임이 끝나지 않은 경우 예외가 발생한다.")
        void failure() {
            Piece redGeneral = new General(TeamType.RED);
            Piece blueGeneral = new General(TeamType.BLUE);
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(2, 5), redGeneral,
                Position.valueOf(9, 5), blueGeneral);
            Board board = new Board(positionPieceMap);

            assertThatIllegalStateException()
                .isThrownBy(board::calculateWinnerTeam);

        }
    }

    @Nested
    @DisplayName("이동 가능 여부 테스트")
    class CanMove {

        @ParameterizedTest
        @EnumSource(value = Direction.class, names = {"NORTH", "EAST", "SOUTH", "WEST"})
        @DisplayName("모든 위치에서는 상하좌우로 이동이 가능하다.")
        void success_1(Direction direction) {
            Position position = Position.valueOf(3, 3);
            Board board = new Board(new LinkedHashMap<>());
            boolean expected = true;

            boolean actual = board.canMove(position, direction);

            assertThat(actual).isEqualTo(expected);
        }

        @ParameterizedTest
        @MethodSource("palaceCases")
        @DisplayName("궁성에서는 추가로 특정 방향의 이동이 가능하다.")
        void success_2(Position position, List<Direction> directions) {
            Board board = new Board(new LinkedHashMap<>());
            boolean expected = true;

            List<Boolean> actuals = directions.stream()
                .map(direction -> board.canMove(position, direction))
                .toList();

            assertAll(actuals.stream()
                .map(actual ->
                    () -> assertThat(actual).isEqualTo(expected)));
        }

        static private Stream<Arguments> palaceCases() {
            return Stream.of(
                // RED Team
                Arguments.of(Position.valueOf(1, 4), List.of(Direction.SOUTH_EAST)),
                Arguments.of(Position.valueOf(1, 6), List.of(Direction.SOUTH_WEST)),
                Arguments.of(Position.valueOf(2, 5), List.of(
                    Direction.NORTH_WEST, Direction.NORTH_EAST, Direction.SOUTH_EAST, Direction.SOUTH_WEST)),
                Arguments.of(Position.valueOf(3, 4), List.of(Direction.NORTH_EAST)),
                Arguments.of(Position.valueOf(3, 6), List.of(Direction.NORTH_WEST)),

                // BLUE Team
                Arguments.of(Position.valueOf(8, 4), List.of(Direction.SOUTH_EAST)),
                Arguments.of(Position.valueOf(8, 6), List.of(Direction.SOUTH_WEST)),
                Arguments.of(Position.valueOf(9, 5), List.of(
                    Direction.NORTH_WEST, Direction.NORTH_EAST, Direction.SOUTH_EAST, Direction.SOUTH_WEST)),
                Arguments.of(Position.valueOf(10, 4), List.of(Direction.NORTH_EAST)),
                Arguments.of(Position.valueOf(10, 6), List.of(Direction.NORTH_WEST)));
        }
    }

    @Nested
    @DisplayName("특정 팀의 남은 기물 점수 합 계산 테스트")
    class CalculateScoreByTeam {

        @Test
        @DisplayName("초나라의 남은 기물 점수 합을 계산한다.")
        void success_1() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(1, 1), new Soldier(TeamType.BLUE),
                Position.valueOf(1, 4), new Cannon(TeamType.BLUE),
                Position.valueOf(1, 5), new Guard(TeamType.BLUE),
                Position.valueOf(2, 5), new General(TeamType.RED),
                Position.valueOf(10, 9), new Chariot(TeamType.RED),
                Position.valueOf(3, 3), new Chariot(TeamType.BLUE),
                Position.valueOf(8, 5), new General(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            TeamType teamType = TeamType.BLUE;
            double expected = 2 + 7 + 3 + 13;

            double actual = board.calculateScoreByTeam(teamType);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("한나라의 경우 1.5점을 추가로 획득한다.")
        void success_2() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(1, 1), new Soldier(TeamType.RED),
                Position.valueOf(1, 4), new Cannon(TeamType.RED),
                Position.valueOf(1, 5), new Guard(TeamType.RED),
                Position.valueOf(2, 5), new General(TeamType.RED),
                Position.valueOf(3, 3), new Chariot(TeamType.RED),
                Position.valueOf(8, 5), new General(TeamType.BLUE),
                Position.valueOf(10, 9), new Chariot(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            TeamType teamType = TeamType.RED;
            double expected = 2 + 7 + 3 + 13 + 1.5;

            double actual = board.calculateScoreByTeam(teamType);

            assertThat(actual).isEqualTo(expected);
        }
    }
}

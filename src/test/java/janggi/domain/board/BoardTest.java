package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Position;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
    @DisplayName("기물 획득 테스트")
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
}

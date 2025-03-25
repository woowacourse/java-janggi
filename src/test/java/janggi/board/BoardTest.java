package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.coordinate.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.General;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Country;

public class BoardTest {

    @Nested
    @DisplayName("보드 생성")
    class ConstructBoard {

        @DisplayName("보드를 생성했을 때 사이즈가 총 90이어야 한다.")
        @Test
        void construct1() {
            //given
            //when
            final var board = new Board(new HashMap<>());

            // then
            assertThat(board.getJanggiBoard()).isNotNull();
        }

        @DisplayName("보드 사이즈가 올바르게 생성된다.")
        @Test
        void boardFactory() {
            // given
            final int expected = 32;

            // when
            final Board board = Board.createInitializedJanggiBoard();

            // then
            Assertions.assertThat(board.getJanggiBoard()).hasSize(expected);
        }

    }

    @Nested
    @DisplayName("보드의 특정 위치에 피스가 존재하는지 검사")
    class ContainsPiece {

        @DisplayName("보드의 특정 위치에 피스가 존재한다면 true, 아니라면 false를 반환한다.")
        @Test
        void existPieceByPosition() {
            // given
            final Map<Position, Piece> map = Map.of(new Position(1, 1), new General(Country.HAN));
            final Board board = new Board(map);
            final Position existPosition = new Position(1, 1);
            final Position notExistPosition = new Position(1, 2);

            // when
            final boolean actual1 = board.existPieceByPosition(existPosition);
            final boolean actual2 = board.existPieceByPosition(notExistPosition);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(actual1).isTrue(),
                    () -> Assertions.assertThat(actual2).isFalse()
            );
        }

        @DisplayName("보드의 특정 위치가 포라면 true를 반환한다.")
        @Test
        void isCannonByPosition() {
            // given
            final Map<Position, Piece> map = Map.of(
                    new Position(1, 1), new Cannon(Country.HAN),
                    new Position(1, 2), new Soldier(Country.HAN)
            );
            final Board board = new Board(map);
            final Position cannonPosition = new Position(1, 1);

            // when
            final boolean actualCannon = board.isCannonByPosition(cannonPosition);

            // then
            assertThat(actualCannon).isTrue();
        }

        @DisplayName("Positions 내부에 포가 존재한다면 true를 반환한다.")
        @Test
        void containsCannonByPositions() {
            // given
            final Map<Position, Piece> map = Map.of(
                    new Position(1, 1), new Cannon(Country.HAN),
                    new Position(1, 2), new Soldier(Country.HAN)
            );
            final Board board = new Board(map);
            final List<Position> positions = List.of(
                    new Position(1, 1), new Position(1, 2)
            );

            // when
            final boolean actual = board.containsCannonByPositions(positions);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("보드의 특정 위치의 기물이 주어진 팀과 같다면 true를 반환한다.")
        void equalsTeamTypeByPosition() {
            // given
            final Map<Position, Piece> map = Map.of(
                    new Position(1, 1), new Cannon(Country.HAN)
            );
            final Board board = new Board(map);

            final Position position = new Position(1, 1);
            final Country equalsCountry = Country.HAN;
            final Country notEqualsCountry = Country.CHO;

            // when
            final boolean actualEquals = board.equalsTeamTypeByPosition(position, equalsCountry);
            final boolean actualNotEquals = board.equalsTeamTypeByPosition(position, notEqualsCountry);

            // then
            assertThat(actualEquals).isTrue();
            assertThat(actualNotEquals).isFalse();
        }

        @DisplayName("보드에 주어진 범위에 존재하는 기물의 수를 반환")
        @Test
        void calculatePieceCountByPositions() {
            // given
            final Map<Position, Piece> map = Map.of(
                    new Position(1, 1), new Cannon(Country.HAN),
                    new Position(1, 2), new Cannon(Country.HAN),
                    new Position(1, 3), new Cannon(Country.HAN)
            );
            final Board board = new Board(map);
            final List<Position> positions = List.of(
                    new Position(1, 1), new Position(1, 2)
            );
            final int expected = 2;

            // when
            final int actual = board.calculatePieceCountByPositions(positions);

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("기물 위치 이동")
    class UpdatePosition {
        @Test
        @DisplayName("src 위치에 있는 기물이 dest로 옮겨질 수 있다면 이동시킨다.")
        void updatePosition3() {
            // given
            final Map<Position, Piece> map = Map.of(
                    new Position(1, 1), new Chariot(Country.HAN)
            );
            final Board board = new Board(map);

            final Position src = new Position(1, 1);
            final Position dest = new Position(1, 5);
            final Country country = Country.HAN;

            // when
            board.updatePosition(src, dest, country);

            // then
            Assertions.assertThat(board.getJanggiBoard())
                    .containsKey(dest)
                    .doesNotContainKey(src);

        }


        @Test
        @DisplayName("src 위치에 기물이 존재하지 않으면 예외가 발생한다")
        void updatePosition() {
            // given
            final Board board = new Board(new HashMap<>());

            final Position src = new Position(1, 1);
            final Position dest = new Position(1, 2);
            final Country country = Country.HAN;

            // when & then
            Assertions.assertThatThrownBy(() -> board.updatePosition(src, dest, country))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("src 위치에 기물이 현재 턴의 팀이 아니라면 예외가 발생한다")
        void updatePosition1() {
            // given
            final Map<Position, Piece> map = Map.of(
                    new Position(1, 1), new Cannon(Country.CHO)
            );
            final Board board = new Board(map);

            final Position src = new Position(1, 1);
            final Position dest = new Position(1, 2);
            final Country country = Country.HAN;

            // when & then
            Assertions.assertThatThrownBy(() -> board.updatePosition(src, dest, country))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("src 위치에 기물이 dest로 갈 수 없다면 예외가 발생한다")
        void updatePosition2() {
            // given
            final Map<Position, Piece> map = Map.of(
                    new Position(1, 1), new Cannon(Country.HAN)
            );
            final Board board = new Board(map);

            final Position src = new Position(1, 1);
            final Position dest = new Position(1, 2);
            final Country country = Country.HAN;

            // when & then
            Assertions.assertThatThrownBy(() -> board.updatePosition(src, dest, country))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}

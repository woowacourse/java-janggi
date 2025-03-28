package board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import piece.Cannon;
import piece.Country;
import piece.General;
import piece.Piece;
import position.Position;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    @Nested
    @DisplayName("보드 생성")
    class ConstructBoard {

        @DisplayName("보드를 생성되어야 한다.")
        @Test
        void newBoard() {
            //given
            //when
            final var board = new Board(new HashMap<>());

            // then
            assertThat(board.getPieces()).isNotNull();
        }
    }

    @Nested
    @DisplayName("보드의 특정 위치에 피스가 존재하는지 검사")
    class ContainsPiece {

        @DisplayName("보드의 특정 위치에 피스가 존재한다면 true, 아니라면 false를 반환한다.")
        @Test
        void existPieceByPosition() {
            // given
            final Position position = new Position(1, 1);
            final Map<Position, Piece> map = Map.of(position, new General(position, Country.CHO));
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

        @Test
        @DisplayName("보드의 특정 위치의 기물이 주어진 팀과 같다면 true를 반환한다.")
        void equalsTeamTypeByPosition() {
            // given

            Position position = new Position(1, 1);
            Country country = Country.CHO;
            final Map<Position, Piece> map = Map.of(
                    position, new Cannon(position, country)
            );
            final Board board = new Board(map);

            final Country oppositeCountry = country.opposite();

            // when
            final boolean actualEquals = board.equalsTeamTypeByPosition(position, country);
            final boolean actualNotEquals = board.equalsTeamTypeByPosition(position, oppositeCountry);

            // then
            assertThat(actualEquals).isTrue();
            assertThat(actualNotEquals).isFalse();
        }

        @Test
        @DisplayName("src 위치에 기물이 존재하지 않으면 예외가 발생한다")
        void updatePositionFailureByNonPieceInSrcPosition() {
            // given
            final Board board = new Board(new HashMap<>());

            final Position src = new Position(1, 1);

            // when & then
            Assertions.assertThatThrownBy(() -> board.getPieceBy(src))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("src 위치에 기물이 현재 턴의 팀이 아니라면 예외가 발생한다")
        void updatePositionFailureBySameTeamType() {
            // given
            Position position = new Position(1, 1);
            final Country country = Country.CHO;
            final Map<Position, Piece> map = Map.of(
                    position, new Cannon(position, country)
            );
            final Board board = new Board(map);

            final Position src = new Position(1, 1);
            final Position dest = new Position(1, 2);


            // when & then
            Assertions.assertThatThrownBy(() -> board.updatePosition(src, dest, country))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}

package board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coordinate.Coordinate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import piece.Cha;
import piece.Jol;
import piece.Piece;
import team.Team;

public class BoardTest {

    @Nested
    @DisplayName("기물의 움직임 테스트")
    class MoveTest {

        @Test
        @DisplayName("출발 좌표에 기물이 없으면 예외가 발생한다.")
        void test1() {
            // given
            Board board = new BoardFixture()
                    .build();

            // when & then
            assertThatThrownBy(() -> board.move(new Coordinate(5, 5), new Coordinate(5, 6)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 좌표에는 기물이 없습니다.");
        }

        @Test
        @DisplayName("도착 좌표에 같은 팀 기물이 있으면 예외가 발생한다.")
        void test2() {
            // given
            Board board = new BoardFixture()
                    .addPiece(5, 5, new Cha(Team.HAN))
                    .addPiece(5, 6, new Jol(Team.HAN))
                    .build();

            // when & then
            assertThatThrownBy(() -> board.move(new Coordinate(5, 5), new Coordinate(5, 6)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("도착 좌표에 같은 팀 말이 있습니다.");
        }

        @Test
        @DisplayName("도착 좌표에 다른 팀 기물이 있고 정상적으로 이동됐을 경우, 출발 좌표는 비어있고 도착 좌표의 기물을 대체한다.")
        void test3() {
            // given
            Cha cha = new Cha(Team.HAN);
            Board board = new BoardFixture()
                    .addPiece(5, 5, cha)
                    .addPiece(5, 6, new Jol(Team.CHO))
                    .build();
            Coordinate departure = new Coordinate(5, 5);
            Coordinate arrival = new Coordinate(5, 6);

            // when
            board.move(departure, arrival);

            // then
            assertAll(
                    () -> assertThat(board.isExistence(departure)).isFalse(),
                    () -> assertThat(board.findPiece(arrival)).hasValue(cha)
            );
        }

        @Test
        @DisplayName("도착 좌표에 다른 팀 기물이 없고 정상적으로 이동됐을 경우, 출발 좌표는 비어있고 도착 좌표에 이동한 기물이 위치한다.")
        void test4() {
            // given
            Cha cha = new Cha(Team.HAN);
            Board board = new BoardFixture()
                    .addPiece(5, 5, cha)
                    .build();
            Coordinate departure = new Coordinate(5, 5);
            Coordinate arrival = new Coordinate(5, 6);

            // when
            board.move(departure, arrival);

            // then
            assertAll(
                    () -> assertThat(board.isExistence(departure)).isFalse(),
                    () -> assertThat(board.findPiece(arrival)).hasValue(cha)
            );
        }
    }

    @Nested
    @DisplayName("기물의 존재 여부 테스트")
    class IsExistenceTest {

        @Test
        @DisplayName("해당 좌표에 기물이 있으면 true를 반환한다.")
        void test1() {
            // given
            Board board = new BoardFixture()
                    .addPiece(5, 5, new Cha(Team.HAN))
                    .build();

            // when
            boolean isExistence = board.isExistence(new Coordinate(5, 5));

            // then
            assertThat(isExistence).isTrue();
        }

        @Test
        @DisplayName("해당 좌표에 기물이 없으면 false를 반환한다.")
        void test2() {
            // given
            Board board = new BoardFixture()
                    .build();

            // when
            boolean isExistence = board.isExistence(new Coordinate(5, 5));

            // then
            assertThat(isExistence).isFalse();
        }
    }

    @Nested
    @DisplayName("기물을 찾아 반환하는 테스트")
    class FindPieceTest {

        @Test
        @DisplayName("기물이 없는 경우 빈 Optional을 반환한다.")
        void test1() {
            // given
            Board board = new BoardFixture().build();

            // when
            Optional<Piece> piece = board.findPiece(new Coordinate(5, 5));

            // then
            assertThat(piece).isEmpty();
        }

        @Test
        @DisplayName("기물이 있는 경우 기물을 찾아 반환한다.")
        void test2() {
            // given
            Board board = new BoardFixture()
                    .addPiece(5, 5, new Jol(Team.HAN))
                    .build();

            // when
            Optional<Piece> piece = board.findPiece(new Coordinate(5, 5));

            // then
            assertThat(piece).isPresent();
        }
    }
}

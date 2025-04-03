package object.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import object.coordinate.Coordinate;
import object.piece.Piece;
import object.piece.PieceType;
import object.team.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
            Coordinate departure = new Coordinate(5, 5);
            Coordinate arrival = new Coordinate(5, 6);

            assertThatThrownBy(() -> board.move(Country.HAN, departure, arrival))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("해당 좌표에 기물이 없습니다.");
        }

        @Test
        @DisplayName("기물이 이동할 수 없는 움직임이면 예외가 발생한다.")
        void test2() {
            // given
            Board board = new BoardFixture()
                    .addPiece(5, 5, new Piece(Country.CHO, PieceType.JOL))
                    .build();

            // when & then
            Coordinate departure = new Coordinate(5, 5);
            Coordinate arrival = new Coordinate(9, 9);

            assertThatThrownBy(() -> board.move(Country.HAN, departure, arrival))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("이동할 수 없는 좌표입니다.");
        }

        @Test
        @DisplayName("도착 좌표에 같은 팀 기물이 있으면 예외가 발생한다.")
        void test3() {
            // given
            Board board = new BoardFixture()
                    .addPiece(5, 5, new Piece(Country.HAN, PieceType.CHA))
                    .addPiece(5, 6, new Piece(Country.HAN, PieceType.MA))
                    .build();

            // when & then
            Coordinate departure = new Coordinate(5, 5);
            Coordinate arrival = new Coordinate(5, 6);

            assertThatThrownBy(() -> board.move(Country.HAN, departure, arrival))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("도착 좌표에 같은 팀 말이 있습니다.");
        }

        @Test
        @DisplayName("도착 좌표에 다른 팀 기물이 있고 정상적으로 이동됐을 경우, 출발 좌표는 비어있고 도착 좌표의 기물을 대체한다.")
        void test4() {
            // given
            Piece cha = new Piece(Country.HAN, PieceType.CHA);
            Board board = new BoardFixture()
                    .addPiece(5, 5, cha)
                    .addPiece(5, 6, new Piece(Country.CHO, PieceType.JOL))
                    .build();
            Coordinate departure = new Coordinate(5, 5);
            Coordinate arrival = new Coordinate(5, 6);

            // when
            board.move(Country.CHO, departure, arrival);

            // then
            assertAll(
                    () -> assertThat(board.hasPiece(departure)).isFalse(),
                    () -> assertThat(board.findPiece(arrival)).isEqualTo(cha)
            );
        }

        @Test
        @DisplayName("도착 좌표에 다른 팀 기물이 없고 정상적으로 이동됐을 경우, 출발 좌표는 비어있고 도착 좌표에 이동한 기물이 위치한다.")
        void test5() {
            // given
            Piece cha = new Piece(Country.HAN, PieceType.CHA);
            Board board = new BoardFixture()
                    .addPiece(5, 5, cha)
                    .build();
            Coordinate departure = new Coordinate(5, 5);
            Coordinate arrival = new Coordinate(5, 6);

            // when
            board.move(Country.HAN, departure, arrival);

            // then
            assertAll(
                    () -> assertThat(board.hasPiece(departure)).isFalse(),
                    () -> assertThat(board.findPiece(arrival)).isEqualTo(cha)
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
                    .addPiece(5, 5, new Piece(Country.HAN, PieceType.CHA))
                    .build();

            // when
            boolean isExistence = board.hasPiece(new Coordinate(5, 5));

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
            boolean isExistence = board.hasPiece(new Coordinate(5, 5));

            // then
            assertThat(isExistence).isFalse();
        }
    }

    @Nested
    @DisplayName("기물을 찾아 반환하는 테스트")
    class FindPieceTest {

        @Test
        @DisplayName("기물이 없는 경우 null을 반환한다.")
        void test1() {
            // given
            Board board = new BoardFixture().build();

            // when & then
            assertThatThrownBy(() -> board.findPiece(new Coordinate(5, 5)))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("해당 좌표에 기물이 없습니다.");
        }

        @Test
        @DisplayName("기물이 있는 경우 기물을 찾아 반환한다.")
        void test2() {
            // given
            Piece ma = new Piece(Country.HAN, PieceType.MA);
            Board board = new BoardFixture()
                    .addPiece(5, 5, ma)
                    .build();

            // when
            Piece piece = board.findPiece(new Coordinate(5, 5));

            // then
            assertThat(piece).isEqualTo(ma);
        }
    }
}

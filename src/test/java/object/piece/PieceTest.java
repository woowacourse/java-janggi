package object.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import object.board.Board;
import object.board.BoardFixture;
import object.coordinate.Coordinate;
import object.team.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Nested
    @DisplayName("궁 움직일 수 있는지 여부를 판단하는 테스트")
    class GoongTest {

        @Test
        @DisplayName("궁의 출발 좌표가 (5,2)일 때 이동 가능한지 여부를 판단한다.")
        void test1() {
            // given
            Piece goong = new Piece(Country.HAN, PieceType.GOONG);
            Board board = new BoardFixture()
                    .addPiece(5, 2, goong)
                    .build();

            // when
            boolean result1 = goong.canMove(board, new Coordinate(5, 2), new Coordinate(5, 1));
            boolean result2 = goong.canMove(board, new Coordinate(5, 2), new Coordinate(5, 3));
            boolean result3 = goong.canMove(board, new Coordinate(5, 2), new Coordinate(4, 2));
            boolean result4 = goong.canMove(board, new Coordinate(5, 2), new Coordinate(6, 2));
            boolean result5 = goong.canMove(board, new Coordinate(5, 2), new Coordinate(4, 1));
            boolean result6 = goong.canMove(board, new Coordinate(5, 2), new Coordinate(4, 3));
            boolean result7 = goong.canMove(board, new Coordinate(5, 2), new Coordinate(6, 1));
            boolean result8 = goong.canMove(board, new Coordinate(5, 2), new Coordinate(6, 3));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue(),
                    () -> assertThat(result4).isTrue(),
                    () -> assertThat(result5).isTrue(),
                    () -> assertThat(result6).isTrue(),
                    () -> assertThat(result7).isTrue(),
                    () -> assertThat(result8).isTrue()
            );
        }

        @Test
        @DisplayName("궁의 출발 좌표가 (4,1)일 때 보드판이나 궁성을 벗어난 좌표는 이동할 수 없다.")
        void test2() {
            // given
            Piece goong = new Piece(Country.HAN, PieceType.GOONG);
            Board board = new BoardFixture()
                    .addPiece(4, 1, goong)
                    .build();
            // when
            boolean result1 = goong.canMove(board, new Coordinate(4, 1), new Coordinate(5, 1));
            boolean result2 = goong.canMove(board, new Coordinate(4, 1), new Coordinate(4, 2));
            boolean result3 = goong.canMove(board, new Coordinate(4, 1), new Coordinate(5, 2));

            boolean result4 = goong.canMove(board, new Coordinate(4, 1), new Coordinate(3, 1));
            boolean result5 = goong.canMove(board, new Coordinate(4, 1), new Coordinate(3, 2));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue(),

                    () -> assertThat(result4).isFalse(),
                    () -> assertThat(result5).isFalse()
            );
        }

        @Test
        @DisplayName("궁은 장애물을 고려하지 않아도 된다.")
        void test3() {
            // given
            Piece goong = new Piece(Country.HAN, PieceType.GOONG);
            Board board = new BoardFixture()
                    .addPiece(4, 1, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(5, 1, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(6, 1, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(4, 2, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(6, 2, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(4, 3, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(5, 3, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(6, 3, new Piece(Country.HAN, PieceType.SANG))
                    .build();

            // when
            boolean result = goong.canMove(board, new Coordinate(5, 2), new Coordinate(5, 1));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("졸 움직일 수 있는지 여부를 판단하는 테스트")
    class JolTest {

        @Test
        @DisplayName("졸의 출발 좌표가 (5,5)일 때 이동 가능한지 여부를 판단한다.")
        void test1() {
            // given
            Piece jol = new Piece(Country.CHO, PieceType.JOL);
            Board board = new BoardFixture()
                    .addPiece(5, 5, jol)
                    .build();

            // when
            boolean result1 = jol.canMove(board, new Coordinate(5, 5), new Coordinate(5, 4));
            boolean result2 = jol.canMove(board, new Coordinate(5, 5), new Coordinate(6, 5));
            boolean result3 = jol.canMove(board, new Coordinate(5, 5), new Coordinate(4, 5));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue()
            );
        }

        @Test
        @DisplayName("졸의 출발 좌표가 (1,7)일 때 보드판을 벗어난 좌표는 이동할 수 없다.")
        void test2() {
            // given
            Piece jol = new Piece(Country.CHO, PieceType.JOL);
            Board board = new BoardFixture()
                    .addPiece(1, 7, jol)
                    .build();

            // when
            boolean result1 = jol.canMove(board, new Coordinate(1, 7), new Coordinate(2, 7));
            boolean result2 = jol.canMove(board, new Coordinate(1, 7), new Coordinate(1, 6));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue()
            );
        }

        @Test
        @DisplayName("졸의 좌표가 (4,3)으로 궁성으로 진입했을 때 대각선 좌표까지 이동할 수 있다.")
        void test3() {
            // given
            Piece jol = new Piece(Country.CHO, PieceType.JOL);
            Board board = new BoardFixture()
                    .addPiece(4, 3, jol)
                    .build();

            // when
            boolean result1 = jol.canMove(board, new Coordinate(4, 3), new Coordinate(3, 3));
            boolean result2 = jol.canMove(board, new Coordinate(4, 3), new Coordinate(5, 3));
            boolean result3 = jol.canMove(board, new Coordinate(4, 3), new Coordinate(4, 2));
            boolean result4 = jol.canMove(board, new Coordinate(4, 3), new Coordinate(5, 2));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue(),
                    () -> assertThat(result4).isTrue()
            );
        }

        @Test
        @DisplayName("졸은 장애물을 고려하지 않아도 된다.")
        void test4() {
            // given
            Piece jol = new Piece(Country.CHO, PieceType.JOL);
            Board board = new BoardFixture()
                    .addPiece(5, 5, jol)
                    .addPiece(4, 5, new Piece(Country.CHO, PieceType.SANG))
                    .addPiece(6, 5, new Piece(Country.CHO, PieceType.SANG))
                    .addPiece(5, 4, new Piece(Country.CHO, PieceType.SANG))
                    .build();

            // when
            boolean result = jol.canMove(board, new Coordinate(5, 5), new Coordinate(5, 4));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("병 움직일 수 있는지 여부를 판단하는 테스트")
    class ByeongTest {

        @Test
        @DisplayName("병의 출발 좌표가 (5,5)일 때 이동 가능한지 여부를 판단한다.")
        void test1() {
            // given
            Piece byeong = new Piece(Country.HAN, PieceType.BYEONG);
            Board board = new BoardFixture()
                    .addPiece(5, 5, byeong)
                    .build();

            // when
            boolean result1 = byeong.canMove(board, new Coordinate(5, 5), new Coordinate(5, 6));
            boolean result2 = byeong.canMove(board, new Coordinate(5, 5), new Coordinate(6, 5));
            boolean result3 = byeong.canMove(board, new Coordinate(5, 5), new Coordinate(4, 5));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue()
            );
        }

        @Test
        @DisplayName("병의 출발 좌표가 (1,4)일 때 보드판을 벗어난 좌표는 이동할 수 없다.")
        void test2() {
            // given
            Piece byeong = new Piece(Country.HAN, PieceType.BYEONG);
            Board board = new BoardFixture()
                    .addPiece(1, 4, byeong)
                    .build();
            // when
            boolean result1 = byeong.canMove(board, new Coordinate(1, 4), new Coordinate(2, 4));
            boolean result2 = byeong.canMove(board, new Coordinate(1, 4), new Coordinate(1, 5));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue()
            );
        }

        @Test
        @DisplayName("병의 좌표가 (4,8)으로 궁성으로 진입했을 때 대각선 좌표까지 이동할 수 있다.")
        void test3() {
            // given
            Piece byeong = new Piece(Country.HAN, PieceType.BYEONG);
            Board board = new BoardFixture()
                    .addPiece(4, 8, byeong)
                    .build();

            // when
            boolean result1 = byeong.canMove(board, new Coordinate(4, 8), new Coordinate(3, 8));
            boolean result2 = byeong.canMove(board, new Coordinate(4, 8), new Coordinate(5, 8));
            boolean result3 = byeong.canMove(board, new Coordinate(4, 8), new Coordinate(4, 9));
            boolean result4 = byeong.canMove(board, new Coordinate(4, 8), new Coordinate(5, 9));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue(),
                    () -> assertThat(result4).isTrue()
            );
        }

        @Test
        @DisplayName("병은 장애물을 고려하지 않아도 된다.")
        void test4() {
            // given
            Piece byeong = new Piece(Country.HAN, PieceType.BYEONG);
            Board board = new BoardFixture()
                    .addPiece(5, 5, byeong)
                    .addPiece(4, 5, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(6, 5, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(5, 6, new Piece(Country.HAN, PieceType.SANG))
                    .build();

            // when
            boolean result = byeong.canMove(board, new Coordinate(5, 5), new Coordinate(5, 6));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("사 움직일 수 있는지 여부를 판단하는 테스트")
    class SaTest {

        @Test
        @DisplayName("사의 출발 좌표가 (5,2)일 때 이동 가능한지 여부를 판단한다.")
        void test1() {
            // given
            Piece sa = new Piece(Country.HAN, PieceType.SA);
            Board board = new BoardFixture()
                    .addPiece(5, 2, sa)
                    .build();

            // when
            boolean result1 = sa.canMove(board, new Coordinate(5, 2), new Coordinate(4, 1));
            boolean result2 = sa.canMove(board, new Coordinate(5, 2), new Coordinate(5, 1));
            boolean result3 = sa.canMove(board, new Coordinate(5, 2), new Coordinate(6, 1));
            boolean result4 = sa.canMove(board, new Coordinate(5, 2), new Coordinate(4, 2));
            boolean result5 = sa.canMove(board, new Coordinate(5, 2), new Coordinate(6, 2));
            boolean result6 = sa.canMove(board, new Coordinate(5, 2), new Coordinate(4, 3));
            boolean result7 = sa.canMove(board, new Coordinate(5, 2), new Coordinate(5, 3));
            boolean result8 = sa.canMove(board, new Coordinate(5, 2), new Coordinate(6, 3));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue(),
                    () -> assertThat(result4).isTrue(),
                    () -> assertThat(result5).isTrue(),
                    () -> assertThat(result6).isTrue(),
                    () -> assertThat(result7).isTrue(),
                    () -> assertThat(result8).isTrue()
            );
        }

        @Test
        @DisplayName("사의 출발 좌표가 (4,1)일 때 보드판이나 궁성을 벗어난 좌표는 이동할 수 없다.")
        void test2() {
            // given
            Piece sa = new Piece(Country.HAN, PieceType.SA);
            Board board = new BoardFixture()
                    .addPiece(4, 1, sa)
                    .build();

            // when
            boolean result1 = sa.canMove(board, new Coordinate(4, 1), new Coordinate(5, 1));
            boolean result2 = sa.canMove(board, new Coordinate(4, 1), new Coordinate(4, 2));
            boolean result3 = sa.canMove(board, new Coordinate(4, 1), new Coordinate(5, 2));

            boolean result4 = sa.canMove(board, new Coordinate(4, 1), new Coordinate(3, 1));
            boolean result5 = sa.canMove(board, new Coordinate(4, 1), new Coordinate(3, 2));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue(),

                    () -> assertThat(result4).isFalse(),
                    () -> assertThat(result5).isFalse()
            );
        }

        @Test
        @DisplayName("사는 장애물을 고려하지 않아도 된다.")
        void test3() {
            // given
            Piece sa = new Piece(Country.CHO, PieceType.SA);
            Board board = new BoardFixture()
                    .addPiece(4, 1, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(5, 1, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(6, 1, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(4, 2, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(6, 2, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(4, 3, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(5, 3, new Piece(Country.HAN, PieceType.SANG))
                    .addPiece(6, 3, new Piece(Country.HAN, PieceType.SANG))
                    .build();

            // when
            boolean result = sa.canMove(board, new Coordinate(5, 2), new Coordinate(5, 1));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("상 움직일 수 있는지 여부를 판단하는 테스트")
    class SangTest {

        @Test
        @DisplayName("상의 출발 좌표가 (5,5)일 때 이동 가능한지 여부를 판단한다.")
        void test1() {
            // given
            Piece sang = new Piece(Country.HAN, PieceType.SANG);
            Board board = new BoardFixture()
                    .addPiece(5, 5, sang)
                    .build();

            // when
            boolean result1 = sang.canMove(board, new Coordinate(5, 5), new Coordinate(2, 3));
            boolean result2 = sang.canMove(board, new Coordinate(5, 5), new Coordinate(3, 2));
            boolean result3 = sang.canMove(board, new Coordinate(5, 5), new Coordinate(7, 2));
            boolean result4 = sang.canMove(board, new Coordinate(5, 5), new Coordinate(8, 3));
            boolean result5 = sang.canMove(board, new Coordinate(5, 5), new Coordinate(2, 7));
            boolean result6 = sang.canMove(board, new Coordinate(5, 5), new Coordinate(3, 8));
            boolean result7 = sang.canMove(board, new Coordinate(5, 5), new Coordinate(7, 8));
            boolean result8 = sang.canMove(board, new Coordinate(5, 5), new Coordinate(8, 7));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue(),
                    () -> assertThat(result4).isTrue(),
                    () -> assertThat(result5).isTrue(),
                    () -> assertThat(result6).isTrue(),
                    () -> assertThat(result7).isTrue(),
                    () -> assertThat(result8).isTrue()
            );
        }

        @Test
        @DisplayName("상의 출발 좌표가 (3,1)일 때 보드판을 벗어난 좌표는 이동할 수 없다.")
        void test2() {
            // given
            Piece sang = new Piece(Country.HAN, PieceType.SANG);
            Board board = new BoardFixture()
                    .addPiece(3, 1, sang)
                    .build();

            // when
            boolean result1 = sang.canMove(board, new Coordinate(3, 1), new Coordinate(1, 4));
            boolean result2 = sang.canMove(board, new Coordinate(3, 1), new Coordinate(5, 4));
            boolean result3 = sang.canMove(board, new Coordinate(3, 1), new Coordinate(6, 3));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue()
            );
        }

        @Test
        @DisplayName("상이 (5,5) -> (3,2) 으로 이동할 때 (5,4)를 거치기 때문에 이동할 수 없다.")
        void test3() {
            // given
            Piece sang = new Piece(Country.HAN, PieceType.SANG);
            Board board = new BoardFixture()
                    .addPiece(5, 5, sang)
                    .addPiece(5, 4, new Piece(Country.CHO, PieceType.SANG))
                    .build();

            // when
            boolean result = sang.canMove(board, new Coordinate(5, 5), new Coordinate(3, 2));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("상이 (5,5) -> (3,2) 으로 이동할 때 장애물이 하나도 없을 경우 이동할 수 있다.")
        void test4() {
            // given
            Piece sang = new Piece(Country.HAN, PieceType.SANG);
            Board board = new BoardFixture()
                    .addPiece(5, 5, sang)
                    .build();

            // when
            boolean result = sang.canMove(board, new Coordinate(5, 5), new Coordinate(3, 2));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("마 움직일 수 있는지 여부를 판단하는 테스트")
    class MaTest {

        @Test
        @DisplayName("마의 출발 좌표가 (5,5)일 때 이동 가능한지 여부를 판단한다.")
        void test1() {
            // given
            Piece ma = new Piece(Country.HAN, PieceType.MA);
            Board board = new BoardFixture()
                    .addPiece(5, 5, ma)
                    .build();

            // when
            boolean result1 = ma.canMove(board, new Coordinate(5, 5), new Coordinate(3, 4));
            boolean result2 = ma.canMove(board, new Coordinate(5, 5), new Coordinate(3, 6));
            boolean result3 = ma.canMove(board, new Coordinate(5, 5), new Coordinate(4, 3));
            boolean result4 = ma.canMove(board, new Coordinate(5, 5), new Coordinate(4, 7));
            boolean result5 = ma.canMove(board, new Coordinate(5, 5), new Coordinate(6, 3));
            boolean result6 = ma.canMove(board, new Coordinate(5, 5), new Coordinate(6, 7));
            boolean result7 = ma.canMove(board, new Coordinate(5, 5), new Coordinate(7, 4));
            boolean result8 = ma.canMove(board, new Coordinate(5, 5), new Coordinate(7, 6));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue(),
                    () -> assertThat(result4).isTrue(),
                    () -> assertThat(result5).isTrue(),
                    () -> assertThat(result6).isTrue(),
                    () -> assertThat(result7).isTrue(),
                    () -> assertThat(result8).isTrue()
            );
        }

        @Test
        @DisplayName("마의 출발 좌표가 (2,1)일 때 보드판을 벗어난 좌표는 이동할 수 없다.")
        void test2() {
            // given
            Piece ma = new Piece(Country.HAN, PieceType.MA);
            Board board = new BoardFixture()
                    .addPiece(2, 1, ma)
                    .build();

            // when
            boolean result1 = ma.canMove(board, new Coordinate(2, 1), new Coordinate(1, 3));
            boolean result2 = ma.canMove(board, new Coordinate(2, 1), new Coordinate(3, 3));
            boolean result3 = ma.canMove(board, new Coordinate(2, 1), new Coordinate(4, 2));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue()
            );
        }

        @Test
        @DisplayName("마가 (5,5) -> (4,3) 으로 이동할 때 (5,4)를 거치기 때문에 이동할 수 없다.")
        void test3() {
            // given
            Piece ma = new Piece(Country.HAN, PieceType.MA);
            Board board = new BoardFixture()
                    .addPiece(5, 5, ma)
                    .addPiece(5, 4, new Piece(Country.CHO, PieceType.SANG))
                    .build();

            // when
            boolean result = ma.canMove(board, new Coordinate(5, 5), new Coordinate(4, 3));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("마가 (5,5) -> (4,3) 으로 이동할 때 장애물이 하나도 없을 경우 이동할 수 있다.")
        void test4() {
            // given
            Piece ma = new Piece(Country.CHO, PieceType.MA);
            Board board = new BoardFixture()
                    .addPiece(5, 5, ma)
                    .build();

            // when
            boolean result = ma.canMove(board, new Coordinate(5, 5), new Coordinate(4, 3));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("포 움직일 수 있는지 여부를 판단하는 테스트")
    class PoTest {

        @Test
        @DisplayName("포의 출발 좌표가 (5,5)일 때 이동 가능한지 여부를 판단한다.")
        void test1() {
            // given
            Piece po = new Piece(Country.HAN, PieceType.PO);
            Board board = new BoardFixture()
                    .addPiece(5, 5, po)
                    .addPiece(4, 5, new Piece(Country.HAN, PieceType.MA))
                    .addPiece(6, 5, new Piece(Country.HAN, PieceType.MA))
                    .addPiece(5, 4, new Piece(Country.HAN, PieceType.MA))
                    .addPiece(5, 6, new Piece(Country.HAN, PieceType.MA))
                    .build();

            // when
            boolean result1 = po.canMove(board, new Coordinate(5, 5), new Coordinate(1, 5));
            boolean result2 = po.canMove(board, new Coordinate(5, 5), new Coordinate(2, 5));
            boolean result3 = po.canMove(board, new Coordinate(5, 5), new Coordinate(3, 5));
            boolean result4 = po.canMove(board, new Coordinate(5, 5), new Coordinate(7, 5));
            boolean result5 = po.canMove(board, new Coordinate(5, 5), new Coordinate(8, 5));
            boolean result6 = po.canMove(board, new Coordinate(5, 5), new Coordinate(9, 5));
            boolean result7 = po.canMove(board, new Coordinate(5, 5), new Coordinate(5, 1));
            boolean result8 = po.canMove(board, new Coordinate(5, 5), new Coordinate(5, 2));
            boolean result9 = po.canMove(board, new Coordinate(5, 5), new Coordinate(5, 3));
            boolean result10 = po.canMove(board, new Coordinate(5, 5), new Coordinate(5, 7));
            boolean result11 = po.canMove(board, new Coordinate(5, 5), new Coordinate(5, 8));
            boolean result12 = po.canMove(board, new Coordinate(5, 5), new Coordinate(5, 9));
            boolean result13 = po.canMove(board, new Coordinate(5, 5), new Coordinate(5, 10));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue(),
                    () -> assertThat(result4).isTrue(),
                    () -> assertThat(result5).isTrue(),
                    () -> assertThat(result6).isTrue(),
                    () -> assertThat(result7).isTrue(),
                    () -> assertThat(result8).isTrue(),
                    () -> assertThat(result9).isTrue(),
                    () -> assertThat(result10).isTrue(),
                    () -> assertThat(result11).isTrue(),
                    () -> assertThat(result12).isTrue(),
                    () -> assertThat(result13).isTrue()
            );
        }

        @Test
        @DisplayName("포가 (5,5) -> (6,5) 으로 이동할 때 장애물이 없을 경우 이동할 수 없다.")
        void test2() {
            // given
            Piece po = new Piece(Country.HAN, PieceType.PO);
            Board board = new BoardFixture()
                    .addPiece(5, 5, po)
                    .build();

            // when
            boolean result = po.canMove(board, new Coordinate(5, 5), new Coordinate(6, 5));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나가 아닐 경우 이동할 수 없다.")
        void test3() {
            // given
            Piece po = new Piece(Country.HAN, PieceType.PO);
            Board board = new BoardFixture()
                    .addPiece(5, 5, po)
                    .addPiece(6, 5, new Piece(Country.CHO, PieceType.MA))
                    .addPiece(7, 5, new Piece(Country.CHO, PieceType.SANG))
                    .build();

            // when
            boolean result = po.canMove(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포인 경우 이동할 수 없다.")
        void test4() {
            // given
            Piece po = new Piece(Country.HAN, PieceType.PO);
            Board board = new BoardFixture()
                    .addPiece(5, 5, po)
                    .addPiece(6, 5, new Piece(Country.CHO, PieceType.PO))
                    .build();

            // when
            boolean result = po.canMove(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포가 아니면서 도착 좌표에 포가 있으면 이동할 수 없다.")
        void test5() {
            // given
            Piece po = new Piece(Country.HAN, PieceType.PO);
            Board board = new BoardFixture()
                    .addPiece(5, 5, po)
                    .addPiece(6, 5, new Piece(Country.CHO, PieceType.MA))
                    .addPiece(8, 5, new Piece(Country.CHO, PieceType.PO))
                    .build();

            // when
            boolean result = po.canMove(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포가 아니면서 도착 좌표에 포가 없을 경우 이동할 수 있다.")
        void test6() {
            // given
            Piece po = new Piece(Country.HAN, PieceType.PO);
            Board board = new BoardFixture()
                    .addPiece(5, 5, po)
                    .addPiece(6, 5, new Piece(Country.CHO, PieceType.MA))
                    .addPiece(8, 5, new Piece(Country.CHO, PieceType.SANG))
                    .build();

            // when
            boolean result = po.canMove(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("포가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나이면서 그 장애물이 포가 아니면서 도착 좌표에 피스가 없을 경우 이동할 수 있다.")
        void test7() {
            // given
            Piece po = new Piece(Country.HAN, PieceType.PO);
            Board board = new BoardFixture()
                    .addPiece(5, 5, po)
                    .addPiece(6, 5, new Piece(Country.CHO, PieceType.SANG))
                    .build();

            // when
            boolean result = po.canMove(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("차 움직일 수 있는지 여부를 판단하는 테스트")
    class ChaTest {

        @Test
        @DisplayName("차의 출발 좌표가 (5,5)일 때 이동 가능한지 여부를 판단한다.")
        void test1() {
            // given
            Piece cha = new Piece(Country.HAN, PieceType.CHA);
            Board board = new BoardFixture()
                    .addPiece(5, 5, cha)
                    .build();

            // when
            boolean result1 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(1, 5));
            boolean result2 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(2, 5));
            boolean result3 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(3, 5));
            boolean result4 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(4, 5));
            boolean result5 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(6, 5));
            boolean result6 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(7, 5));
            boolean result7 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(8, 5));
            boolean result8 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(9, 5));
            boolean result9 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(5, 1));
            boolean result10 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(5, 2));
            boolean result11 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(5, 3));
            boolean result12 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(5, 4));
            boolean result13 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(5, 6));
            boolean result14 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(5, 7));
            boolean result15 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(5, 8));
            boolean result16 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(5, 9));
            boolean result17 = cha.canMove(board, new Coordinate(5, 5), new Coordinate(5, 10));

            // then
            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue(),
                    () -> assertThat(result3).isTrue(),
                    () -> assertThat(result4).isTrue(),
                    () -> assertThat(result5).isTrue(),
                    () -> assertThat(result6).isTrue(),
                    () -> assertThat(result7).isTrue(),
                    () -> assertThat(result8).isTrue(),
                    () -> assertThat(result9).isTrue(),
                    () -> assertThat(result10).isTrue(),
                    () -> assertThat(result11).isTrue(),
                    () -> assertThat(result12).isTrue(),
                    () -> assertThat(result13).isTrue(),
                    () -> assertThat(result14).isTrue(),
                    () -> assertThat(result15).isTrue(),
                    () -> assertThat(result16).isTrue(),
                    () -> assertThat(result17).isTrue()
            );
        }

        @Test
        @DisplayName("차가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나 이상 있을 경우 이동할 수 없다.")
        void test2() {
            // given
            Piece cha = new Piece(Country.HAN, PieceType.CHA);
            Board board = new BoardFixture()
                    .addPiece(5, 5, cha)
                    .addPiece(6, 5, new Piece(Country.CHO, PieceType.PO))
                    .build();

            // when
            boolean result = cha.canMove(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("차가 (5,5) -> (8,5) 으로 이동할 때 장애물이 하나도 없을 경우 이동할 수 있다.")
        void test3() {
            // given
            Piece cha = new Piece(Country.HAN, PieceType.CHA);
            Board board = new BoardFixture()
                    .addPiece(5, 5, cha)
                    .build();

            // when
            boolean result = cha.canMove(board, new Coordinate(5, 5), new Coordinate(8, 5));

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("포인지 확인하는 테스트")
    class IsPoTest {

        @Test
        @DisplayName("기물이 포라면 true를 반환한다.")
        void test1() {
            // given
            Piece po = new Piece(Country.CHO, PieceType.PO);

            // when
            boolean result = po.isPo();

            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("기물이 포가 아니라면 false를 반환한다.")
        void test2() {
            // given
            Piece sa = new Piece(Country.CHO, PieceType.SA);

            // when
            boolean result = sa.isPo();

            // then
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("궁인지 확인하는 테스트")
    class IsGoongTest {

        @Test
        @DisplayName("기물이 궁이라면 true를 반환한다.")
        void test1() {
            // given
            Piece goong = new Piece(Country.CHO, PieceType.GOONG);

            // when
            boolean result = goong.isGoong();

            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("기물이 궁이 아니라면 false를 반환한다.")
        void test2() {
            // given
            Piece sa = new Piece(Country.CHO, PieceType.SA);

            // when
            boolean result = sa.isPo();

            // then
            assertThat(result).isFalse();
        }
    }
}

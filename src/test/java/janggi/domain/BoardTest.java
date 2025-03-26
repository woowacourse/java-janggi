package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.board.Board;
import janggi.domain.board.PieceSearcher;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("각 팀의 점수를 합산할 수 있다.")
    void test1() {
        //given
        Board board = new BoardFixture()
            .addPiece(1, 1, PieceType.CHA, Team.HAN) // 차 13점
            .addPiece(2, 1, PieceType.SANG, Team.CHO) // 상 3점
            .addPiece(3, 10, PieceType.MA, Team.CHO) // 초팀 마 5점
            .build();

        //when
        final var hanTeamScore = board.sumScore(Team.HAN);
        final var choTeamScore = board.sumScore(Team.CHO);

        //then
        assertAll(
            () -> assertThat(hanTeamScore).isEqualTo(13),
            () -> assertThat(choTeamScore).isEqualTo(8)
        );
    }


    @Test
    @DisplayName("궁이 잡혔는 지 알 수 있다.")
    void test2() {
        //given
        Board board = new BoardFixture()
            .addPiece(5, 2, PieceType.GOONG, Team.HAN)  // ....궁. <-한팀 궁
            .addPiece(5, 3, PieceType.CHA, Team.CHO)    // ....차. <-초팀 차
            .addPiece(5, 9, PieceType.GOONG, Team.CHO)  // ......
            .build();

        //when
        board.move(new Coordinate(5, 3), new Coordinate(5, 2));

        //then
        assertThat(board.isAnyGoongDead()).isTrue();
    }

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
                .addPiece(5, 5, PieceType.CHA, Team.HAN)
                .addPiece(5, 6, PieceType.MA, Team.HAN)
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
            Piece cha = new Piece(Team.HAN, new Coordinate(5, 5), PieceType.CHA);
            Board board = new BoardFixture()
                .addPiece(5, 5, cha)
                .anyPieceNotPo(5, 6, Team.CHO)
                .build();

            Coordinate departure = new Coordinate(5, 5);
            Coordinate arrival = new Coordinate(5, 6);

            // when
            board.move(departure, arrival);

            // then
            final var movedCha = new Piece(Team.HAN, new Coordinate(5, 6), PieceType.CHA);
            assertAll(
                () -> assertThat(board.findAt(departure)).isEmpty(),
                () -> assertThat(board.findAt(arrival)).hasValue(movedCha)
            );
        }

        @Test
        @DisplayName("도착 좌표에 다른 팀 기물이 없고 정상적으로 이동됐을 경우, 출발 좌표는 비어있고 도착 좌표에 이동한 기물이 위치한다.")
        void test4() {
            // given
            Board board = new BoardFixture()
                .addPiece(5, 5, PieceType.CHA, Team.HAN)
                .build();
            Coordinate departure = new Coordinate(5, 5);
            Coordinate arrival = new Coordinate(5, 6);

            // when
            board.move(departure, arrival);

            // then
            final var movedCha = new Piece(Team.HAN, new Coordinate(5, 6), PieceType.CHA);
            assertAll(
                () -> assertThat(board.findAt(departure)).isEmpty(),
                () -> assertThat(board.findAt(arrival)).hasValue(movedCha)
            );
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
            Optional<Piece> piece = board.findAt(new Coordinate(5, 5));

            // then
            assertThat(piece).isEmpty();
        }

        @Test
        @DisplayName("기물이 있는 경우 기물을 찾아 반환한다.")
        void test2() {
            // given
            Board board = new BoardFixture()
                .addPiece(5, 5, PieceType.MA, Team.HAN)
                .build();

            // when
            Optional<Piece> piece = board.findAt(new Coordinate(5, 5));

            // then
            assertThat(piece).isPresent();
        }
    }

    @Nested
    @DisplayName("경로를 조회하는 테스트")
    class SearchPathTest {

        @Test
        @DisplayName("좌표 리스트를 받아 해당 좌표들에 기물들이 하나도 없는 지 알 수 있다.")
        void test1() {
            //given
            PieceSearcher searcher = new BoardFixture()
                .anyPieceNotPo(3, 1)
                .build();

            final var path = List.of(
                new Coordinate(2, 1), new Coordinate(3, 1), new Coordinate(4, 1)
            );

            //when & then
            assertThat(searcher.nonePiecesIn(path)).isFalse();
        }

        @Test
        @DisplayName("좌표 리스트를 받아 해당 좌표들에 존재하는 기물들을 반환한다.")
        void test2() {
            //given
            PieceSearcher searcher = new BoardFixture()
                .anyPieceNotPo(3, 1)
                .anyPieceNotPo(4, 1)
                .build();

            final var path = List.of(
                new Coordinate(2, 1), new Coordinate(3, 1), new Coordinate(4, 1)
            );

            //when & then
            assertThat(searcher.findPiecesIn(path)).hasSize(2);
        }
    }
}

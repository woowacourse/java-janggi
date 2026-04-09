package domain.move;

import domain.board.JanggiBoard;
import domain.intersection.exception.IntersectionException;
import domain.intersection.palace.NormalIntersection;
import fixture.JanggiBoardFixture;
import fixture.TestIntersectionGenerator;
import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.intersection.exception.IntersectionError.ORIGIN_INTERSECTION_IS_EMPTY;
import static domain.intersection.exception.IntersectionError.ORIGIN_INTERSECTION_IS_NOT_OPPONENT;

// THINK 클래스명과 테스트 의도에 대해서 고민해볼 것.
class MoveTest {

    @Test
    @DisplayName("이동이 끝난 뒤 출발지는 비어있고, 도착지는 기물이 존재한다.")
    void shouldMovePieceToDestinationAndLeaveSourceEmpty() {
        // given
        Team currentTurn = Team.CHO;
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Piece chariot = new Piece(currentTurn, PieceType.CHARIOT);
        Piece soldier = new Piece(Team.HAN, PieceType.SOLDIER);

        Intersection origin = new NormalIntersection(start, chariot);
        Intersection destination = new NormalIntersection(end, soldier);
        Intersection expectedEmpty = NormalIntersection.empty(start);
        Intersection expectedChariot = new NormalIntersection(end, chariot);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(origin, destination)));

        // when
        janggiBoard.processTurn(start, end);
        Intersection actualOrigin = janggiBoard.findIntersection(start);
        Intersection actualDestination = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actualOrigin)
                .isEqualTo(expectedEmpty);

        Assertions.assertThat(actualDestination)
                .isEqualTo(expectedChariot);
    }

    @Test
    @DisplayName("상대 칸을 출발 좌표로 지정하면, 예외가 발생한다.")
    void shouldThrowExceptionWhenOriginIsOpponent() {
        // given
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team team = Team.CHO;
        Team opponentTeam = Team.HAN;
        Piece choPiece = new Piece(team, PieceType.SOLDIER);

        Intersection opponentIntersection = new NormalIntersection(start, choPiece);
        Intersection destination = NormalIntersection.empty(end);

        // when
        JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                opponentTeam,
                opponentIntersection, destination
        );

        // then
        Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(start, end))
                .isInstanceOf(IntersectionException.class)
                .hasMessage(ORIGIN_INTERSECTION_IS_NOT_OPPONENT.getMessage());
    }

    @Test
    @DisplayName("빈 칸을 출발 좌표로 지정하면, 예외가 발생한다.")
    void shouldThrowExceptionWhenOriginIsEmpty() {
        // given
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Intersection emptyIntersection = NormalIntersection.empty(start);
        Intersection destination = NormalIntersection.empty(end);

        // when
        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                emptyIntersection,
                destination)
        ));

        // then
        Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(start, end))
                .isInstanceOf(IntersectionException.class)
                .hasMessage(ORIGIN_INTERSECTION_IS_EMPTY.getMessage());
    }

}

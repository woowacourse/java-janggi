package domain.move.rule;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.intersection.palace.NormalIntersection;
import domain.move.directions.exception.DirectionException;
import domain.move.path.Path;
import domain.move.path.exception.PathException;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.move.directions.Vector;
import domain.point.Point;
import fixture.TestIntersectionGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.move.directions.exception.DirectionError.INVALID_DIRECTION;
import static domain.move.path.exception.PathError.CANNOT_MOVE_DESTINATION_IS_SAME_TEAM;

class SoliderMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        // given
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team sameTeam = Team.HAN;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection origin = new Intersection(start, soldier);
        Intersection sameTeamDestination = new Intersection(end, sameTeamPiece);

        SoliderMoveRule soliderMoveRule = new SoliderMoveRule();

        // when
        Path path = new Path(List.of(
                origin,
                sameTeamDestination)
        );

        // then
        Assertions.assertThatThrownBy(() -> soliderMoveRule.validateMoveRule(path))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
    }

    @Test
    @DisplayName("한 진영인 졸의 직진 방향은 내려감이다.")
    void straightOfHanSoliderIsDown() {
        // given
        Point start = new Point(0, 0);
        Point end = start.next(Vector.DOWN);

        Team sameTeam = Team.HAN;
        Team anotherTeam = Team.CHO;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);
        Piece anotherTeamPiece = new Piece(anotherTeam, PieceType.SOLDIER);

        Intersection origin = new Intersection(start, soldier);
        Intersection destination = new Intersection(end, anotherTeamPiece);
        Intersection expected = new Intersection(end, soldier);
        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(origin, destination)));

        // when
        janggiBoard.tryToMove(start, end, Team.HAN);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("초 진영인 졸의 직진 방향은 올라감이다.")
    void straightOfCHOSoliderIsUp() {
        // given
        Point start = new Point(1, 0);
        Point end = start.next(Vector.UP);

        Team sameTeam = Team.CHO;
        Team anotherTeam = Team.HAN;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);
        Piece anotherTeamPiece = new Piece(anotherTeam, PieceType.SOLDIER);

        Intersection origin = new Intersection(start, soldier);
        Intersection destination = new Intersection(end, anotherTeamPiece);
        Intersection expected = new Intersection(end, soldier);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(origin, destination)));

        // when
        janggiBoard.tryToMove(start, end, Team.CHO);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("초 진영 졸이 내려가려고 하면 예외가 발생한다.")
    void shouldThrowExceptionWhenSoliderOfChoMoveDown() {
        // given
        Point start = new Point(1, 0);
        Point end = start.next(Vector.DOWN);

        Team sameTeam = Team.CHO;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection origin = new NormalIntersection(start, soldier);
        Intersection destination = NormalIntersection.empty(end);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                destination))
        );

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(start, end, sameTeam))
                .isInstanceOf(DirectionException.class)
                .hasMessage(INVALID_DIRECTION.getMessage());
    }

    @Test
    @DisplayName("한 진영 졸이 올라가려고 하면 예외가 발생한다.")
    void shouldThrowExceptionWhenSoliderOfHanMoveUp() {
        // given
        Point start = new Point(1, 0);
        Point end = start.next(Vector.UP);

        Team sameTeam = Team.HAN;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection origin = new NormalIntersection(start, soldier);
        Intersection destination = NormalIntersection.empty(end);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                destination))
        );

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(start, end, sameTeam))
                .isInstanceOf(DirectionException.class)
                .hasMessage(INVALID_DIRECTION.getMessage());
    }


}

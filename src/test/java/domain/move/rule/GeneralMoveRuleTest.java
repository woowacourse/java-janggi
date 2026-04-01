package domain.move.rule;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.move.path.Path;
import domain.move.path.exception.PathException;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import fixture.TestIntersectionGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.move.path.exception.PathError.CANNOT_MOVE_DESTINATION_IS_SAME_TEAM;

class GeneralMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        // given
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team sameTeam = Team.HAN;
        Piece general = new Piece(sameTeam, PieceType.GENERAL);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection origin = new Intersection(start, general);
        Intersection sameTeamDestination = new Intersection(end, sameTeamPiece);

        // when
        GeneralMoveRule generalMoveRule = new GeneralMoveRule();
        Path sameTeamPath = new Path(List.of(
                origin,
                sameTeamDestination)
        );

        // then
        Assertions.assertThatThrownBy(() -> generalMoveRule.validateMoveRule(sameTeamPath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
    }

    @Test
    @DisplayName("장군은 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGeneralWhenDestinationIsEmpty() {
        // given
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.HAN;
        Piece general = new Piece(sameTeam, PieceType.GENERAL);

        Intersection origin = new Intersection(start, general);
        Intersection emptyDestination = Intersection.empty(end);
        Intersection expected = new Intersection(end, general);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                emptyDestination
        )));

        // when
        janggiBoard.tryToMove(start, end, sameTeam);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("장군은 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGeneralWhenDestinationIsOpponent() {
        // given
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.HAN;
        Team anotherTeam = Team.CHO;
        Piece general = new Piece(sameTeam, PieceType.GENERAL);
        Piece opponent = new Piece(anotherTeam, PieceType.SOLDIER);

        Intersection origin = new Intersection(start, general);
        Intersection opponentDestination = new Intersection(end, opponent);
        Intersection expected = new Intersection(end, general);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                opponentDestination
        )));

        // when
        janggiBoard.tryToMove(start, end, sameTeam);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}

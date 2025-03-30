package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Position;
import janggi.domain.position.Route;
import janggi.domain.position.Routes;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesTest {

    @DisplayName("마 기물의 이동 가능한 목적지를 계산한다.")
    @Test
    void getPossibleRoutesTest() {

        // given
        Piece horse = new Horse(new Position(4, 4), RED);
        Piece soldier1 = new Soldier(new Position(5, 4), RED);
        Piece soldier2 = new Soldier(new Position(3, 6), RED);
        Piece soldier3 = new Soldier(new Position(2, 5), BLUE);

        Pieces pieces = new Pieces(List.of(horse, soldier1, soldier2, soldier3));

        // when
        Set<Route> possibleRoutes = pieces.classifyPossibleRoutes(horse);
        Routes routes = new Routes(possibleRoutes);

        Set<Position> answers = Set.of(
                new Position(2, 3),
                new Position(2, 5),
                new Position(3, 2),
                new Position(5, 2),
                new Position(5, 6)
        );
        // then
        assertThat(routes.getDestinations()).isEqualTo(answers);
    }

    @DisplayName("차 기물의 가능한 경로를 계산한다.")
    @Test
    void getPossibleRoutesForChariotTest() {

        // given
        Piece chariot = new Chariot(new Position(4, 4), RED);
        Piece soldier1 = new Soldier(new Position(5, 4), RED);
        Piece soldier2 = new Soldier(new Position(1, 4), RED);
        Piece soldier3 = new Soldier(new Position(4, 8), BLUE);
        Piece soldier4 = new Soldier(new Position(4, 3), BLUE);

        Pieces pieces = new Pieces(List.of(chariot, soldier1, soldier2, soldier3, soldier4));

        // when
        Set<Route> possibleRoutes = pieces.classifyPossibleRoutes(chariot);

        // then
        assertThat(possibleRoutes.size()).isEqualTo(7);
    }

    @DisplayName("차 기물이 궁성에서 이동 가능한 경로를 계산한다.")
    @Test
    void getPossibleRoutesInPalaceForChariotTest() {

        // given
        Piece chariot = new Chariot(new Position(3, 0), BLUE);
        Piece soldier1 = new Soldier(new Position(3, 4), RED);
        Piece general = new General(new Position(5, 2), BLUE);

        Pieces pieces = new Pieces(List.of(chariot, soldier1, general));

        // when
        Set<Route> possibleRoutes = pieces.classifyPossibleRoutes(chariot);
        Routes routes = new Routes(possibleRoutes);
        Set<Position> possibleDestinations = routes.getDestinations();
        Set<Position> expected = Set.of(
                new Position(2, 0),
                new Position(1, 0),
                new Position(0, 0),
                new Position(4, 0),
                new Position(5, 0),
                new Position(6, 0),
                new Position(7, 0),
                new Position(8, 0),
                new Position(4, 1),
                new Position(3, 1),
                new Position(3, 2),
                new Position(3, 3),
                new Position(3, 4)
        );

        // then
        assertThat(possibleDestinations).isEqualTo(expected);
    }

    @DisplayName("포 기물의 가능한 경로를 계산한다.")
    @Test
    void getPossibleRoutesForCannonTest() {

        // given
        Piece cannon = new Cannon(new Position(4, 4), RED);
        Piece soldier1 = new Soldier(new Position(5, 4), RED);
        Piece cannon1 = new Cannon(new Position(1, 4), BLUE);
        Piece soldier3 = new Soldier(new Position(2, 4), RED);
        Piece cannon2 = new Cannon(new Position(4, 2), RED);
        Piece soldier2 = new Soldier(new Position(4, 6), BLUE);
        Piece soldier4 = new Soldier(new Position(7, 4), BLUE);

        Pieces pieces = new Pieces(List.of(cannon, soldier1, cannon1, soldier3, cannon2, soldier2, soldier4));

        // when
        Set<Route> possibleRoutes = pieces.classifyPossibleRoutes(cannon);

        // then
        assertThat(possibleRoutes.size()).isEqualTo(5);
    }

    @DisplayName("포 기물이 궁성에서 이동 가능한 경로를 계산한다.")
    @Test
    void getPossibleRoutesInPalaceForCannonTest() {

        // given
        Piece cannon1 = new Cannon(new Position(3, 0), BLUE);
        Piece soldier1 = new Soldier(new Position(3, 4), RED);
        Piece cannon2 = new Cannon(new Position(3, 7), RED);
        Piece general = new General(new Position(4, 1), BLUE);

        Pieces pieces = new Pieces(List.of(cannon1, cannon2, soldier1, general));

        // when
        Set<Route> possibleRoutes = pieces.classifyPossibleRoutes(cannon1);
        Routes routes = new Routes(possibleRoutes);
        Set<Position> possibleDestinations = routes.getDestinations();
        Set<Position> expected = Set.of(
                new Position(5, 2),
                new Position(3, 5),
                new Position(3, 6)
        );

        // then
        assertThat(possibleDestinations).isEqualTo(expected);
    }

    @DisplayName("왕 기물의 가능한 경로를 계산한다.")
    @Test
    void getPossibleRoutesForGeneralTest() {

        // given
        Piece general = new General(new Position(4, 1), BLUE);
        Piece soldier1 = new Soldier(new Position(5, 4), RED);

        Pieces pieces = new Pieces(List.of(general, soldier1));

        // when
        Set<Route> possibleRoutes = pieces.classifyPossibleRoutes(general);
        Set<Position> possibleDestinations = possibleRoutes.stream().map(Route::getDestination).collect(
                Collectors.toSet());

        Set<Position> expected = Set.of(
                new Position(4, 2),
                new Position(3, 2),
                new Position(5, 2),
                new Position(3, 1),
                new Position(3, 0),
                new Position(4, 0),
                new Position(5, 0),
                new Position(5, 1)
        );

        // then
        assertThat(possibleDestinations).isEqualTo(expected);
    }

    @DisplayName("사 기물의 가능한 경로를 계산한다.")
    @Test
    void getPossibleRoutesForGuardTest() {

        // given
        Piece guard = new Guard(new Position(4, 1), BLUE);
        Piece soldier1 = new Soldier(new Position(5, 4), RED);

        Pieces pieces = new Pieces(List.of(guard, soldier1));

        // when
        Set<Route> possibleRoutes = pieces.classifyPossibleRoutes(guard);
        Set<Position> possibleDestinations = possibleRoutes.stream().map(Route::getDestination).collect(
                Collectors.toSet());

        Set<Position> expected = Set.of(
                new Position(4, 2),
                new Position(3, 2),
                new Position(5, 2),
                new Position(3, 1),
                new Position(3, 0),
                new Position(4, 0),
                new Position(5, 0),
                new Position(5, 1)
        );

        // then
        assertThat(possibleDestinations).isEqualTo(expected);
    }


    @DisplayName("위치와 팀으로 기물을 찾는다.")
    @Test
    void findPieceByPositionAndTeamTest() {

        // given
        Piece soldier = new Soldier(new Position(1, 1), RED);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        assertThat(pieces.findPieceByPositionAndTeam(new Position(1, 1), RED)).isEqualTo(soldier);
    }

    @DisplayName("기물을 움직인다.")
    @Test
    void moveTest() {

        // given
        Piece soldier = new Soldier(new Position(1, 1), RED);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        pieces.move(new Position(1, 2), soldier);

        // then
        assertThat(pieces.findPieceByPositionAndTeam(new Position(1, 2), RED)).isEqualTo(soldier);
    }

    @DisplayName("기물을 움직이며 해당 자리에 기물이 있을 경우 삭제한다.")
    @Test
    void moveAndKillTest() {
        Piece soldier1 = new Soldier(new Position(1, 1), RED);
        Piece soldier2 = new Soldier(new Position(1, 2), BLUE);
        Pieces pieces = new Pieces(List.of(soldier1, soldier2));

        pieces.move(new Position(1, 2), soldier1);

        assertThatThrownBy(() -> pieces.findPieceByPositionAndTeam(new Position(1, 2), BLUE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 우리팀 기물이 없습니다.");
    }

    @DisplayName("왕이 죽었을 경우 참을 반환한다.")
    @Test
    void isGeneralDeadTest() {
        Piece soldier1 = new Soldier(new Position(1, 1), RED);
        Piece soldier2 = new Soldier(new Position(1, 2), BLUE);
        Piece general = new General(new Position(1, 3), BLUE);
        Pieces pieces = new Pieces(List.of(soldier1, soldier2, general));
        assertAll(
                () -> assertThat(pieces.isGeneralDead(RED)).isTrue(),
                () -> assertThat(pieces.isGeneralDead(BLUE)).isFalse()
        );
    }
}

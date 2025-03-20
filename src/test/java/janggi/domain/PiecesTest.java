package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.Soldier;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesTest {

    @DisplayName("차와 포가 아닌 기물의 가능한 경로를 계산한다.")
    @Test
    void getPossibleRoutesTest() {

        // given
        Piece horse = new Horse(new Position(4, 4), RED);
        Piece soldier1 = new Soldier(new Position(5, 4), RED);
        Piece soldier2 = new Soldier(new Position(3, 6), RED);
        Piece soldier3 = new Soldier(new Position(2, 5), BLUE);

        Pieces pieces = new Pieces(List.of(horse, soldier1, soldier2, soldier3));

        // when
        Set<Route> possibleRoutes = pieces.getPossibleRoutes(horse);

        // then
        assertThat(possibleRoutes.size()).isEqualTo(5);
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
        Set<Route> possibleRoutes = pieces.getPossibleRoutes(chariot);

        // then
        assertThat(possibleRoutes.size()).isEqualTo(7);
    }

    @DisplayName("포 기물의 가능한 경로를 계산한다.")
    @Test
    void getPossibleRoutesForCannonTest() {

        // given
        Piece cannon = new Cannon(new Position(4, 4), RED);
        Piece soldier1 = new Soldier(new Position(5, 4), RED);
        Piece soldier2 = new Soldier(new Position(1, 4), BLUE);
        Piece soldier3 = new Soldier(new Position(2, 4), RED);
        Piece cannon2 = new Cannon(new Position(4, 2), RED);
        Piece cannon3 = new Cannon(new Position(4, 6), BLUE);

        Pieces pieces = new Pieces(List.of(cannon, soldier1, soldier2, soldier3, cannon2, cannon3));

        // when
        Set<Route> possibleRoutes = pieces.getPossibleRoutes(cannon);

        // then
        assertThat(possibleRoutes.size()).isEqualTo(4);
    }

    @DisplayName("위치와 팀으로 기물을 찾는다.")
    @Test
    void findPieceByPositionAndTeamTest() {

        // given
        Piece soldier = new Soldier(new Position(1, 1), RED);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        assertThat(pieces.findPieceByPositionAndTeam(1, 1, RED)).isEqualTo(soldier);
    }

    @DisplayName("기물을 움직인다.")
    @Test
    void moveTest() {

        // given
        Piece soldier = new Soldier(new Position(1, 1), RED);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        pieces.move(soldier, 1, 2);

        // then
        assertThat(pieces.findPieceByPositionAndTeam(1, 2, RED)).isEqualTo(soldier);
    }

    @DisplayName("기물을 움직이며 해당 자리에 기물이 있을 경우 삭제한다.")
    @Test
    void moveAndKillTest() {
        Piece soldier1 = new Soldier(new Position(1, 1), RED);
        Piece soldier2 = new Soldier(new Position(1, 2), BLUE);
        Pieces pieces = new Pieces(List.of(soldier1, soldier2));

        pieces.move(soldier1, 1, 2);

        assertThatThrownBy(() -> pieces.findPieceByPositionAndTeam(1, 2, BLUE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 우리팀 기물이 없습니다.");
    }
}

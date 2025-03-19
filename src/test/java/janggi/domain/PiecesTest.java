package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

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
        Piece cannon2 = new Soldier(new Position(4, 2), RED);
        Piece cannon3 = new Soldier(new Position(4, 6), BLUE);

        Pieces pieces = new Pieces(List.of(cannon, soldier1, soldier2, soldier3, cannon2, cannon3));

        // when
        Set<Route> possibleRoutes = pieces.getPossibleRoutes(cannon);

        // then
        assertThat(possibleRoutes.size()).isEqualTo(4);
    }
}

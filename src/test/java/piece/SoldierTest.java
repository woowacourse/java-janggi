package piece;

import domain.board.Board;
import domain.board.Palace;
import domain.piece.Country;
import domain.piece.Piece;
import domain.piece.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.position.LineDirection;
import domain.position.Position;
import domain.position.PositionFactory;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SoldierTest {

    @DisplayName("Soldier는 뒷 방향을 제외하고 한 간선을 이동할 수 있다.")
    @Test
    void validateMove() {
        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();

        final Country country = Country.HAN;
        Country.assignDirection(country, LineDirection.UP);
        final Position dumyPosition = new Position(2, 3);
        Position src = dumyPosition;
        final Piece soldier = new Soldier(src, country);
        final Board board = new Board(Map.of(
                src, soldier
        ));

        // when & then: 1 : failure
        final Position ableDest1 = new Position(2, 2);
        assertThatThrownBy(
                () -> soldier.validateMove(src, ableDest1, board)
        ).isInstanceOf(IllegalArgumentException.class);

        // when & then : 2 : success
        final Position ableDest2 = new Position(2, 4);
        assertThatCode(
                () -> soldier.validateMove(src, ableDest2, board)
        ).doesNotThrowAnyException();

        // when & then: 3 : success
        final Position ableDest3 = new Position(3, 3);
        assertThatCode(
                () -> soldier.validateMove(src, ableDest3, board)
        ).doesNotThrowAnyException();

        // when & then: 4 : success
        final Position ableDest4 = new Position(1, 3);
        assertThatCode(
                () -> soldier.validateMove(src, ableDest4, board)
        ).doesNotThrowAnyException();
    }

    @DisplayName("Soldier는 뒷 방향을 제외하고 한 간선을 이동할 수 있다. : 대각선 간선")
    @Test
    void validateMoveWithDiagonalSetting() {
        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();
        positionFactory.diagonalSettingGraph(Palace.getAllPositions());

        final Country country = Country.HAN;
        Country.assignDirection(country, LineDirection.UP);
        final Position dumyPosition = new Position(5, 2);
        Position src = dumyPosition;
        final Piece soldier = new Soldier(src, country);
        final Board board = new Board(Map.of(
                src, soldier
        ));

        // when & then: 1 : failure
        final Position ableDest1 = new Position(4, 1);
        assertThatThrownBy(
                () -> soldier.validateMove(src, ableDest1, board)
        ).isInstanceOf(IllegalArgumentException.class);

        // when & then : 2 : success
        final Position ableDest2 = new Position(4, 3);
        assertThatCode(
                () -> soldier.validateMove(src, ableDest2, board)
        ).doesNotThrowAnyException();

        // when & then: 3 : success
        final Position ableDest3 = new Position(6, 3);
        assertThatCode(
                () -> soldier.validateMove(src, ableDest3, board)
        ).doesNotThrowAnyException();
    }
}

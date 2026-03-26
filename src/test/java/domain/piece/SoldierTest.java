package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import testDouble.TestBoard;
import testDouble.TestBoardFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SoldierTest {

    @ParameterizedTest
    @CsvSource(value = {"3:7", "1:8", "2:6"}, delimiterString = ":")
    public void 이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {

        TestBoard board = TestBoardFactory.create("1", "1");

        Soldier soldier = new Soldier(Team.CHO);
        Coordination from = Coordination.of(1, 7);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> soldier.validateMovable(from, to, board.getBoard())).isInstanceOf(PieceException.class);
    }

}
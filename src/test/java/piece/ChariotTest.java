package piece;

import domain.board.Board;
import domain.board.Palace;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Country;
import domain.piece.Piece;
import org.assertj.core.api.Assertions;
import domain.position.LineDirection;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.position.PositionFactory;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ChariotTest {

    @DisplayName("sigNum 작동")
    @Test
    void sigNum() {
        // given
        int e1 = 2;
        int e2 = 0;
        int e3 = -2;

        // when
        int sig1 = (int) Math.signum(e1);
        int sig2 = (int) Math.signum(e2);
        int sig3 = (int) Math.signum(e3);

        // then
        Assertions.assertThat(sig1).isEqualTo(1);
        Assertions.assertThat(sig2).isEqualTo(0);
        Assertions.assertThat(sig3).isEqualTo(-1);
    }

    @DisplayName("Chariot은 같은 라인에 있는 모든 곳을 이동할 수 있다.")
    @Test
    void validateMove() {
        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();

        Country dumyCountry = Country.HAN;
        Country.assignDirection(dumyCountry, LineDirection.UP);
        Position dumyPosition = new Position(1, 1);
        final Piece chariot = new Chariot(dumyPosition, dumyCountry);

        final Position src = dumyPosition;
        final Board board = new Board(new HashMap<>());

        // when & then: 1 : success
        final Position ableDest = new Position(1, 5);
        assertThatCode(
                () -> chariot.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : success
        final Position notAbleDest = new Position(2, 3);
        assertThatThrownBy(
                () -> chariot.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Chariot 은 목적지까지 어떠한 기물도 존재해서는 안된다")
    @Test
    void validateMoveWithObstruction() {

        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();

        Country turnCountry = Country.HAN;
        Country.assignDirection(turnCountry, LineDirection.UP);
        Position dumyPosition = new Position(2, 3);
        final Position src = dumyPosition;
        final Piece chariot = new Chariot(src, turnCountry);

        final Position obstructionPosition = new Position(2, 2);
        final Piece obstructionPiece = new Cannon(obstructionPosition, turnCountry);
        final Board board = new Board(Map.of(
                obstructionPosition, obstructionPiece
        ));

        // when & then: 1 : success
        final Position ableDest = new Position(1, 3);
        assertThatCode(
                () -> chariot.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(2, 1);
        assertThatThrownBy(
                () -> chariot.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Chariot은 같은 대각선에 있는 모든 곳을 이동할 수 있다.")
    @Test
    void validateMoveWithDiagonalSetting() {
        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();
        positionFactory.diagonalSettingGraph(Palace.getAllPositions());

        Country dumyCountry = Country.HAN;
        Country.assignDirection(dumyCountry, LineDirection.UP);
        Position dumyPosition = new Position(4, 8);
        final Piece chariot = new Chariot(dumyPosition, dumyCountry);

        final Position src = dumyPosition;
        final Board board = new Board(new HashMap<>());

        // when & then: 1 : success
        final Position ableDest = new Position(6, 10);
        assertThatCode(
                () -> chariot.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(3, 7);
        assertThatThrownBy(
                () -> chariot.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}

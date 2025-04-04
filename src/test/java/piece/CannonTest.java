package piece;

import domain.board.Board;
import domain.board.Palace;
import domain.piece.Cannon;
import domain.piece.Country;
import domain.piece.General;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.position.LineDirection;
import domain.position.Position;
import domain.position.PositionFactory;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CannonTest {

    @DisplayName("Cannon은 같은 라인에 있는 모든 곳을 이동할 수 있다.")
    @Test
    void validateMove() {
        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();

        Country turnCountry = Country.HAN;
        Country.assignDirection(turnCountry, LineDirection.UP);
        Position dumyPosition = new Position(1, 1);
        final Position src = dumyPosition;
        final Piece cannon = new Cannon(src, turnCountry);


        final Position obstructionPosition = new Position(1, 2);
        final Piece obstructionPiece = new General(obstructionPosition, turnCountry);
        final Board board = new Board(Map.of(
                obstructionPosition, obstructionPiece
        ));

        // when & then: 1 : success
        final Position ableDest = new Position(1, 5);
        assertThatCode(
                () -> cannon.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(2, 4);
        assertThatThrownBy(
                () -> cannon.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Cannon은 목적지까지의 라인에서 하나의 장애물이 존재해야 한다.")
    @Test
    void validateMoveWithObstruction() {
        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();

        Country turnCountry = Country.HAN;
        Country.assignDirection(turnCountry, LineDirection.UP);
        Position dumyPosition = new Position(1, 1);
        final Position src = dumyPosition;
        final Piece cannon = new Cannon(src, turnCountry);


        final Position obstructionPosition = new Position(1, 2);
        final Position ableDest = new Position(1, 5);
        final General destPiece = new General(ableDest, turnCountry.opposite());
        final Piece obstructionPiece = new General(obstructionPosition, turnCountry);
        final Board board = new Board(Map.of(
                obstructionPosition, obstructionPiece,
                ableDest, destPiece
        ));

        // when & then: 1 : success
        assertThatCode(
                () -> cannon.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(2, 3);
        assertThatThrownBy(
                () -> cannon.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("Cannon은 Cannon을 뛰어 넘을 수 없다.")
    @Test
    void validateMoveWithObstructionLikeCannon() {
        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();

        Country turnCountry = Country.HAN;
        Country.assignDirection(turnCountry, LineDirection.UP);
        Position dumyPosition = new Position(1, 1);
        final Position src = dumyPosition;
        final Piece cannon = new Cannon(src, turnCountry);


        final Position obstructionPosition = new Position(1, 2);
        final Piece obstructionPiece = new Cannon(obstructionPosition, turnCountry);
        final Board board = new Board(Map.of(
                obstructionPosition, obstructionPiece
        ));

        // when & then : 1 : failure
        final Position notAbleDest = new Position(1, 5);
        assertThatThrownBy(
                () -> cannon.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Cannon은 같은 라인에 있는 모든 곳을 이동할 수 있다. : 대각선 간선")
    @Test
    void validateMoveWithDiagonalSetting() {
        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();
        positionFactory.diagonalSettingGraph(Palace.getAllPositions());

        Country turnCountry = Country.HAN;
        Country.assignDirection(turnCountry, LineDirection.UP);
        Position dumyPosition = new Position(4, 1);
        final Position src = dumyPosition;
        final Piece cannon = new Cannon(src, turnCountry);


        final Position obstructionPosition = new Position(5, 2);
        final Piece obstructionPiece = new General(obstructionPosition, turnCountry);
        final Board board = new Board(Map.of(
                obstructionPosition, obstructionPiece
        ));

        // when & then: 1 : success
        final Position ableDest = new Position(6, 3);
        assertThatCode(
                () -> cannon.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(3, 2);
        assertThatThrownBy(
                () -> cannon.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}

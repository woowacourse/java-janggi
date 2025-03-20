package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PieceTest {

    private static Stream<Arguments> provideAllyPiece() {
        Position position = Position.of(5, 5);
        Team team = Team.RED;

        return Stream.of(
                Arguments.of(Guard.of(position, team), Soldier.of(position.adjust(1, 0), team), 1, 0),
                Arguments.of(General.of(position, team), Soldier.of(position.adjust(1, 0), team), 1, 0),
                Arguments.of(Soldier.of(position, team), Soldier.of(position.adjust(1, 0), team), 1, 0),
                Arguments.of(Horse.of(position, team), Soldier.of(position.adjust(1, 2), team), 1, 2),
                Arguments.of(Elephant.of(position, team), Soldier.of(position.adjust(2, 3), team), 2, 3),
                Arguments.of(Chariot.of(position, team), Soldier.of(position.adjust(3, 0), team), 3, 0),
                Arguments.of(Cannon.of(position, team), Soldier.of(position.adjust(3, 0), team), 3, 0));
    }

    private static Stream<Arguments> provideEnemyPiece() {
        Position position = Position.of(5, 5);
        Team team = Team.RED;
        Team ohterTeam = Team.GREEN;

        return Stream.of(
                Arguments.of(Guard.of(position, team), Soldier.of(position.adjust(1, 0), ohterTeam), 1, 0),
                Arguments.of(General.of(position, team), Soldier.of(position.adjust(1, 0), ohterTeam), 1, 0),
                Arguments.of(Soldier.of(position, team), Soldier.of(position.adjust(1, 0), ohterTeam), 1, 0),
                Arguments.of(Horse.of(position, team), Soldier.of(position.adjust(1, 2), ohterTeam), 1, 2),
                Arguments.of(Elephant.of(position, team), Soldier.of(position.adjust(2, 3), ohterTeam), 2, 3),
                Arguments.of(Chariot.of(position, team), Soldier.of(position.adjust(3, 0), ohterTeam), 3, 0),
                Arguments.of(Cannon.of(position, team), Soldier.of(position.adjust(3, 0), ohterTeam), 3, 0));
    }

    @ParameterizedTest
    @MethodSource("provideAllyPiece")
    @DisplayName("모든 기물은 목적지에 아군이 존재할 경우 이동할 수 없다")
    void cannotMoveWhenExistAllyPieceInDestination(Piece piece,
                                                   Piece allyPiece,
                                                   int rowDirection,
                                                   int columnDirection) {
        // given
        Board board = Board.initialize(List.of(piece, allyPiece));
        Position movedPosition = piece.getPosition().adjust(rowDirection, columnDirection);

        // when
        // then
        assertThatThrownBy(() -> piece.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("목적지에 아군이 존재합니다.");
    }

    @ParameterizedTest
    @MethodSource("provideEnemyPiece")
    @DisplayName("모든 기물은 목적지에 적군이 존재할 경우 이동할 수 있다")
    void canMoveWhenExistEnemyPieceInDestination(Piece piece,
                                                 Piece enemyPiece,
                                                 int rowDirection,
                                                 int columnDirection) {
        // given
        List<Piece> pieces = new ArrayList<>(List.of(piece, enemyPiece));
        Position movedPosition = piece.getPosition().adjust(rowDirection, columnDirection);

        if (piece.getType().isCannon()) {
            pieces.add(Soldier.of(movedPosition.adjust(-1, 0), Team.RED));
        }

        Board board = Board.initialize(pieces);

        // when
        // then
        assertThatCode(() -> piece.move(board, movedPosition))
                .doesNotThrowAnyException();

    }
}

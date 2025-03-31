package janggi.piece;

import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import janggi.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PieceTest {

    private static Stream<Arguments> provideAllyPiece() {
        final Team team = Team.HAN;
        final Position position = Position.of(9, 5);

        return Stream.of(
                Arguments.of(Guard.of(position, team), Soldier.of(position.add(new Vector(1, 0)), team), 1, 0),
                Arguments.of(General.of(position, team), Soldier.of(position.add(new Vector(1, 0)), team), 1, 0),
                Arguments.of(Soldier.of(position, team), Soldier.of(position.add(new Vector(-1, 0)), team), -1, 0),
                Arguments.of(Horse.of(position, team), Soldier.of(position.add(new Vector(1, 2)), team), 1, 2),
                Arguments.of(Elephant.of(position, team), Soldier.of(position.add(new Vector(-2, 3)), team), -2, 3),
                Arguments.of(Chariot.of(position, team), Soldier.of(position.add(new Vector(0, 3)), team), 0, 3),
                Arguments.of(Cannon.of(position, team), Soldier.of(position.add(new Vector(0, 3)), team), 0, 3));
    }

    private static Stream<Arguments> provideEnemyPiece() {
        final Team team = Team.CHO;
        final Position position = Position.of(9, 5);
        final Team ohterTeam = Team.HAN;

        return Stream.of(
                Arguments.of(Guard.of(position, team), Soldier.of(position.add(new Vector(1, 0)), ohterTeam), 1, 0),
                Arguments.of(General.of(position, team), Soldier.of(position.add(new Vector(1, 0)), ohterTeam), 1, 0),
                Arguments.of(Soldier.of(position, team), Soldier.of(position.add(new Vector(-1, 0)), ohterTeam), -1, 0),
                Arguments.of(Horse.of(position, team), Soldier.of(position.add(new Vector(1, 2)), ohterTeam), 1, 2),
                Arguments.of(Elephant.of(position, team), Soldier.of(position.add(new Vector(-2, 3)), ohterTeam), -2, 3),
                Arguments.of(Chariot.of(position, team), Soldier.of(position.add(new Vector(-3, 0)), ohterTeam), -3, 0),
                Arguments.of(Cannon.of(position, team), Soldier.of(position.add(new Vector(-3, 0)), ohterTeam), -3, 0));
    }

    @ParameterizedTest
    @MethodSource("provideAllyPiece")
    @DisplayName("모든 기물은 목적지에 아군이 존재할 경우 이동할 수 없다")
    void cannotMoveWhenExistAllyPieceInDestination(final Piece piece,
                                                   final Piece allyPiece,
                                                   final int rowDirection,
                                                   final int columnDirection) {
        // given
        final Board board = Board.from(Pieces.empty().addAll(List.of(piece, allyPiece)));
        final Position movedPosition = piece.getPosition().add(new Vector(rowDirection, columnDirection));

        // when
        // then
        assertThatThrownBy(() -> piece.move(board, movedPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("목적지에 아군이 존재합니다.");
    }

    @ParameterizedTest
    @MethodSource("provideEnemyPiece")
    @DisplayName("모든 기물은 목적지에 적군이 존재할 경우 이동할 수 있다")
    void canMoveWhenExistEnemyPieceInDestination(final Piece piece,
                                                 final Piece enemyPiece,
                                                 final int rowDirection,
                                                 final int columnDirection) {
        // given
        final List<Piece> pieces = new ArrayList<>(List.of(piece, enemyPiece));
        final Position movedPosition = piece.getPosition().add(new Vector(rowDirection, columnDirection));

        if (piece.getType().isCannon()) {
            pieces.add(Soldier.of(movedPosition.add(new Vector(1, 0)), Team.HAN));
        }

        final Board board = Board.from(Pieces.empty().addAll(pieces));

        // when
        // then
        assertThatCode(() -> piece.move(board, movedPosition))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("원시 타입의 정보들로 기물을 생성할 수 있다")
    void of() {
        // given
        // when
        final Piece piece = Piece.of(1, 1, "SOLDIER", "CHO");

        // then
        assertAll(() -> {
            assertThat(piece.getPosition()).isEqualTo(Position.of(1, 1));
            assertThat(piece.getType()).isEqualTo(PieceType.SOLDIER);
            assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        });
    }
}

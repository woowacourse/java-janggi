package janggi.piece;

import janggi.Board;
import janggi.Score;
import janggi.Team;
import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class PieceTest {

    private static Stream<Arguments> provideAllyPiece() {
        Position position = Position.of(5, 5);
        Team team = Team.HAN;

        return Stream.of(
                Arguments.of(Guard.of(position, team), Soldier.of(position.add(new Vector(1, 0)), team), 1, 0),
                Arguments.of(General.of(position, team), Soldier.of(position.add(new Vector(1, 0)), team), 1, 0),
                Arguments.of(Soldier.of(position, team), Soldier.of(position.add(new Vector(1, 0)), team), 1, 0),
                Arguments.of(Horse.of(position, team), Soldier.of(position.add(new Vector(1, 2)), team), 1, 2),
                Arguments.of(Elephant.of(position, team), Soldier.of(position.add(new Vector(2, 3)), team), 2, 3),
                Arguments.of(Chariot.of(position, team), Soldier.of(position.add(new Vector(3, 0)), team), 3, 0),
                Arguments.of(Cannon.of(position, team), Soldier.of(position.add(new Vector(3, 0)), team), 3, 0));
    }

    private static Stream<Arguments> provideEnemyPiece() {
        Position position = Position.of(5, 5);
        Team team = Team.HAN;
        Team ohterTeam = Team.CHO;

        return Stream.of(
                Arguments.of(Guard.of(position, team), Soldier.of(position.add(new Vector(1, 0)), ohterTeam), 1, 0),
                Arguments.of(General.of(position, team), Soldier.of(position.add(new Vector(1, 0)), ohterTeam), 1, 0),
                Arguments.of(Soldier.of(position, team), Soldier.of(position.add(new Vector(1, 0)), ohterTeam), 1, 0),
                Arguments.of(Horse.of(position, team), Soldier.of(position.add(new Vector(1, 2)), ohterTeam), 1, 2),
                Arguments.of(Elephant.of(position, team), Soldier.of(position.add(new Vector(2, 3)), ohterTeam), 2, 3),
                Arguments.of(Chariot.of(position, team), Soldier.of(position.add(new Vector(3, 0)), ohterTeam), 3, 0),
                Arguments.of(Cannon.of(position, team), Soldier.of(position.add(new Vector(3, 0)), ohterTeam), 3, 0));
    }

    @ParameterizedTest
    @MethodSource("provideAllyPiece")
    @DisplayName("모든 기물은 목적지에 아군이 존재할 경우 이동할 수 없다")
    void cannotMoveWhenExistAllyPieceInDestination(Piece piece,
                                                   Piece allyPiece,
                                                   int rowDirection,
                                                   int columnDirection) {
        // given
        Board board = Board.from(Pieces.empty().addAll(List.of(piece, allyPiece)));
        Position movedPosition = piece.getPosition().add(new Vector(rowDirection, columnDirection));

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
        Position movedPosition = piece.getPosition().add(new Vector(rowDirection, columnDirection));

        if (piece.getType().isCannon()) {
            pieces.add(Soldier.of(movedPosition.add(new Vector(-1, 0)), Team.HAN));
        }

        Board board = Board.from(Pieces.empty().addAll(pieces));

        // when
        // then
        assertThatCode(() -> piece.move(board, movedPosition))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("자신이 죽을 때, 보드에서 자신의 위치를 제거하고, 자신의 타입에 맞는 점수를 반환한다")
    void dieShouldCallRemoverAndReturnCorrectScore() {
        // given
        Position position = Position.of(2, 5);
        Piece piece = Guard.of(position, Team.CHO);
        List<Position> removedPositions = new ArrayList<>();

        // when
        Score score = piece.die(removedPositions::add);

        // then
        assertAll(() -> {
            assertThat(removedPositions).containsExactly(position);
            assertThat(score).isEqualTo(Score.guard());
        });
    }
}

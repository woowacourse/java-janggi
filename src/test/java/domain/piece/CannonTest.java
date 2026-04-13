package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.board.Board;
import domain.board.Country;
import domain.board.Position;
import domain.board.TableSetting;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CannonTest {
    @ParameterizedTest
    @DisplayName("포의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedCannonPaths")
    void cannonPathTest(Position from, Position to, List<Position> paths) {
        Piece choCannon = new Cannon(Country.CHO);
        Piece hanCannon = new Cannon(Country.HAN);
        assertThat(choCannon.findPaths(from, to)).isEqualTo(paths);
        assertThat(hanCannon.findPaths(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedCannonPaths() {
        return Stream.of(
                Arguments.arguments(new Position(4, 4), new Position(4, 0),
                        List.of(new Position(4, 3), new Position(4, 2), new Position(4, 1), new Position(4, 0))),
                Arguments.arguments(new Position(4, 4), new Position(0, 4),
                        List.of(new Position(3, 4), new Position(2, 4), new Position(1, 4), new Position(0, 4))),
                Arguments.arguments(new Position(4, 4), new Position(8, 4),
                        List.of(new Position(5, 4), new Position(6, 4), new Position(7, 4), new Position(8, 4))),
                Arguments.arguments(new Position(4, 4), new Position(4, 8),
                        List.of(new Position(4, 5), new Position(4, 6), new Position(4, 7), new Position(4, 8)))
        );
    }

    @Test
    @DisplayName("포가 하나의 방향으로만 이동하지 않을 경우 예외가 발생한다.")
    void cannonOneDirectionExceptionTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(1, 2);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> cannon.findPaths(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 하나의 방향으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("포가 대각선으로 이동할 경우 예외가 발생한다.")
    void cannonDiagonalExceptionTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(1, 1);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> cannon.findPaths(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 직선으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("포가 포를 잡을 경우 예외가 발생한다.")
    void cannonCatchCannonExceptionTest() {
        Board board = Board.create(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);
        board.move(new Position(0, 3), new Position(1, 3));

        Position from = new Position(1, 2);
        Position to = new Position(1, 7);

        assertThatThrownBy(() -> board.move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 잡을 수 없습니다.");
    }

    @Test
    @DisplayName("포가 궁성 내부에서 대각선으로 이동할 수 있다.")
    void cannonDiagonalInPalaceTest() {
        Piece choCannon = new Cannon(Country.CHO);
        Piece hanCannon = new Cannon(Country.HAN);

        assertThat(choCannon.findPaths(new Position(3, 0), new Position(5, 2)))
                .isEqualTo(List.of(new Position(4, 1), new Position(5, 2)));
        assertThat(hanCannon.findPaths(new Position(3, 9), new Position(5, 7)))
                .isEqualTo(List.of(new Position(4, 8), new Position(5, 7)));
    }

    @Test
    @DisplayName("포가 궁성 내부에서 대각선 이동 시 중간 기물이 있으면 이동할 수 있다.")
    void cannonDiagonalInPalaceWithMiddlePieceTest() {
        Board board = Board.create(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);
        board.move(new Position(3, 0), new Position(3, 1));
        board.move(new Position(3, 1), new Position(3, 2));
        board.move(new Position(1, 2), new Position(5, 2));

        assertDoesNotThrow(() -> board.move(new Position(5, 2), new Position(3, 0)));
    }

    @Test
    @DisplayName("포가 궁성 내부에서 대각선 이동 시 중간에 기물이 없으면 예외가 발생한다.")
    void cannonDiagonalInPalaceWithoutMiddlePieceTest() {
        Board board = Board.create(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);
        board.move(new Position(3, 0), new Position(3, 1));
        board.move(new Position(3, 1), new Position(3, 2));
        board.move(new Position(1, 2), new Position(5, 2));
        board.move(new Position(4, 1), new Position(5, 1));

        assertThatThrownBy(() -> board.move(new Position(5, 2), new Position(3, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.");
    }
}

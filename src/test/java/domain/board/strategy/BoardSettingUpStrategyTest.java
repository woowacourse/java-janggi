package domain.board.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.board.Board;
import domain.board.BoardSettingUpStrategy;
import domain.board.SettingUp;
import domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class BoardSettingUpStrategyTest {

    @DisplayName("상차림 타입을 통해 상차림 전략을 가져온다.")
    @ParameterizedTest
    @EnumSource(SettingUp.class)
    void boardSettingUpStrategyTest(SettingUp settingUp) {
        BoardSettingUpStrategy strategy = settingUp.getStrategy();

        assertThat(strategy).isInstanceOf(BoardSettingUpStrategy.class);
    }

    @DisplayName("장기판을 마상마상으로 세팅한다.")
    @Test
    void hanMaSangMaSangTest() {
        Board board = new Board(new MaSangMaSang(), new MaSangMaSang());

        assertThat(board.findPieceByCoordinate(new Coordinate(1, 8)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 7)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 3)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 2)).getType()).isEqualTo(PieceType.SANG);

        assertThat(board.findPieceByCoordinate(new Coordinate(10, 2)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 3)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 7)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 8)).getType()).isEqualTo(PieceType.SANG);
    }

    @DisplayName("장기판을 마상상마로 세팅한다.")
    @Test
    void hanMaSangSangMaTest() {
        Board board = new Board(new MaSangSangMa(), new MaSangSangMa());

        assertThat(board.findPieceByCoordinate(new Coordinate(1, 8)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 7)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 3)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 2)).getType()).isEqualTo(PieceType.MA);

        assertThat(board.findPieceByCoordinate(new Coordinate(10, 2)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 3)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 7)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 8)).getType()).isEqualTo(PieceType.MA);
    }

    @DisplayName("장기판을 상마마상로 세팅한다.")
    @Test
    void hanSangMaMaSangTest() {
        Board board = new Board(new SangMaMaSang(), new SangMaMaSang());

        assertThat(board.findPieceByCoordinate(new Coordinate(1, 8)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 7)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 3)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 2)).getType()).isEqualTo(PieceType.SANG);

        assertThat(board.findPieceByCoordinate(new Coordinate(10, 2)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 3)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 7)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 8)).getType()).isEqualTo(PieceType.SANG);
    }

    @DisplayName("장기판을 상마상마로 세팅한다.")
    @Test
    void hanSangMaSangMaTest() {
        Board board = new Board(new SangMaSangMa(), new SangMaSangMa());

        assertThat(board.findPieceByCoordinate(new Coordinate(1, 8)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 7)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 3)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 2)).getType()).isEqualTo(PieceType.MA);

        assertThat(board.findPieceByCoordinate(new Coordinate(10, 2)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 3)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 7)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 8)).getType()).isEqualTo(PieceType.MA);
    }

}

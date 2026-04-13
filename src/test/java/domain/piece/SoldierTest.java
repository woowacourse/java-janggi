package domain.piece;

import domain.Position;
import domain.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierTest {

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "5, 7, 5, 6",
            "5, 7, 4, 7",
            "5, 7, 6, 7"
    })
    @DisplayName("초 병이 이동 가능한 위치로 이동하면 경로를 반환한다")
    void 초_병이_이동_가능한_위치로_이동하면_경로를_반환한다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.SOLDIER.create(Side.CHO);

        assertThat(piece.findRoute(source)).contains(target);
    }

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "5, 7, 5, 8",
            "5, 7, 5, 5"
    })
    @DisplayName("초 병이 이동 불가능한 위치로 이동하면 예외가 발생한다")
    void 초_병이_이동_불가능한_위치로_이동하면_예외가_발생한다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.SOLDIER.create(Side.CHO);

        assertThat(piece.findRoute(source)).doesNotContain(target);
    }

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "5, 4, 5, 5",
            "5, 4, 4, 4",
            "5, 4, 6, 4"
    })
    @DisplayName("한 졸이 이동 가능한 위치로 이동하면 경로를 반환한다")
    void 한_졸이_이동_가능한_위치로_이동하면_경로를_반환한다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.SOLDIER.create(Side.HAN);

        assertThat(piece.findRoute(source)).contains(target);
    }

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "5, 4, 5, 3",
            "5, 4, 5, 2"
    })
    @DisplayName("한 졸이 이동 불가능한 위치로 이동하면 예외가 발생한다")
    void 한_졸이_이동_불가능한_위치로_이동하면_예외가_발생한다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.SOLDIER.create(Side.HAN);

        assertThat(piece.findRoute(source)).doesNotContain(target);
    }
}

package domain.piece;

import domain.vo.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @DisplayName("포는 이동한 경로를 반환한다.")
    @Test
    void 포는_이동한_경로를_반환한다() {
        // given
        Position sourcePosition = Position.of(1, 1);
        Position targetPosition = Position.of(1, 5);
        Piece piece = PieceType.CANNON.create(Side.HAN);

        // when
        List<Position> positions = piece.findRoute(sourcePosition, targetPosition);

        // then
        List<Position> expected = List.of(Position.of(1, 2), Position.of(1, 3), Position.of(1, 4));
        for (int i = 0; i < 3; i++) {
            Assertions.assertThat(positions.get(i)).isEqualTo(expected.get(i));
        }
    }

    @DisplayName("포는 목적지까지 이동할 수 없으면 예외를 발생한다.")
    @Test
    void 포는_목적지까지_이동할_수_없으면_예외를_발생한다() {
        // given
        Position sourcePosition = Position.of(1, 1);
        Position targetPosition = Position.of(2, 5);
        Piece piece = PieceType.CANNON.create(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> piece.findRoute(sourcePosition, targetPosition))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("이동할 수 없는 목적지입니다.");
    }

    @DisplayName("포는 궁성 대각선으로 이동 경로를 반환한다.")
    @Test
    void 포는_궁성_대각선으로_이동_경로를_반환한다() {
        // given
        Position sourcePosition = Position.of(4, 1);
        Position targetPosition = Position.of(6, 3);
        Piece piece = PieceType.CANNON.create(Side.HAN);

        // when
        List<Position> positions = piece.findRoute(sourcePosition, targetPosition);

        // then
        Assertions.assertThat(positions).containsExactly(Position.of(5, 2), Position.of(6, 3));
    }

    @DisplayName("포는 궁성 상단 중앙에서 대각선으로 이동할 수 없다.")
    @Test
    void 포는_궁성_상단_중앙에서_대각선으로_이동할_수_없다() {
        // given
        Position sourcePosition = Position.of(5, 1);
        Position targetPosition = Position.of(6, 2);
        Piece piece = PieceType.CANNON.create(Side.HAN);

        // when & then
        Assertions.assertThatThrownBy(() -> piece.findRoute(sourcePosition, targetPosition))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("이동할 수 없는 목적지입니다.");
    }

    @DisplayName("포 이동 경로에 포를 제외한 기물이 하나 존재해야 한다.")
    @Test
    void 포_이동_경로에_포를_제외한_기물이_하나_존재해야_한다() {
        // given
        List<Piece> pieces = List.of(new Empty(), PieceType.HORSE.create(Side.CHO), new Empty());
        // when
        Piece cannon = PieceType.CANNON.create(Side.CHO);
        Assertions.assertThatCode(() -> cannon.checkRoute(pieces))
            .doesNotThrowAnyException();
    }

    @DisplayName("포 이동 경로에 포가 존재하면 예외를 발생한다.")
    @Test
    void 포_이동_경로에_포가_존재하면_예외를_발생한다() {
        // given
        List<Piece> pieces = List.of(new Empty(), PieceType.CANNON.create(Side.CHO), new Empty());
        Piece cannon = PieceType.CANNON.create(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> cannon.checkRoute(pieces))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("포를 넘어갈 수 없습니다.");
    }

    @DisplayName("포 이동 경로에 기물이 두 개 이상 존재하면 예외를 발생한다.")
    @Test
    void 포_이동_경로에_기물이_두_개_이상_존재하면_예외를_발생한다() {
        // given
        List<Piece> pieces = List.of(new Empty(), PieceType.HORSE.create(Side.CHO),
            PieceType.ELEPHANT.create(Side.CHO));
        Piece cannon = PieceType.CANNON.create(Side.HAN);

        // when & then
        Assertions.assertThatThrownBy(() -> cannon.checkRoute(pieces))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("이동할 수 없는 목적지입니다.");
    }
}

package domain.piece;

import domain.Position;
import domain.Side;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CannonTest {

    @DisplayName("포는 목적지까지 이동할 수 없으면 빈 경로를 반환한다.")
    @Test
    void 포는_목적지까지_이동할_수_없으면_빈_경로를_반환한다() {
        // given
        Position sourcePosition = Position.of(1,1);
        Position targetPosition = Position.of(2,5);
        Piece piece = PieceType.CANNON.create(Side.CHO);

        // when & then
        assertThat(piece.findRoute(sourcePosition)).doesNotContain(targetPosition);
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
        List<Piece> pieces = List.of(PieceType.EMPTY.create(Side.NONE), PieceType.HORSE.create(Side.CHO), PieceType.ELEPHANT.create(Side.CHO));
        Piece cannon = PieceType.CANNON.create(Side.HAN);

        // when & then
        Assertions.assertThatThrownBy(() -> cannon.checkRoute(pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 정확히 하나의 기물을 넘어야 합니다.");
    }
}

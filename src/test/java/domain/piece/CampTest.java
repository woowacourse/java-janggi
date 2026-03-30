package domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.HORSE;
import static org.assertj.core.api.Assertions.assertThat;

public class CampTest {

    @Test
    @DisplayName("초나라 기준 y 좌표는 한나라 기준 좌표를 반전해서 계산한다.")
    void choResolveY() {
        int y = Camp.CHO.resolveY(1);

        assertThat(y).isEqualTo(10);
    }

    @Test
    @DisplayName("한나라 기준 y 좌표는 그대로 사용한다.")
    void hanResolveY() {
        int y = Camp.HAN.resolveY(1);

        assertThat(y).isEqualTo(1);
    }

    @Test
    @DisplayName("초나라의 상마 배치 순서는 그대로 유지된다.")
    void choArrange() {
        List<PieceType> pieceTypes = Camp.CHO.arrange(List.of(ELEPHANT, HORSE, ELEPHANT, HORSE));

        assertThat(pieceTypes).containsExactly(ELEPHANT, HORSE, ELEPHANT, HORSE);
    }

    @Test
    @DisplayName("한나라의 상마 배치 순서는 반대로 뒤집힌다.")
    void hanArrange() {
        List<PieceType> pieceTypes = Camp.HAN.arrange(List.of(HORSE, ELEPHANT, HORSE, ELEPHANT));

        assertThat(pieceTypes).containsExactly(ELEPHANT, HORSE, ELEPHANT, HORSE);
    }
}

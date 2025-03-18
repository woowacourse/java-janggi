package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GungTest {

    @Nested
    @DisplayName("궁 말 초기화 테스트")
    class InitPieceTest {

        @Test
        @DisplayName("팀당 한 개의 말을 생성할 수 있다.")
        void createOnePiecePerTeam() {
            List<Gung> gungs = Gung.values();

            assertThat(gungs).hasSize(2);
        }
    }
}

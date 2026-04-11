package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Camp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceDisplayNameTest {

    @ParameterizedTest
    @DisplayName("진영(Camp)에 따라 기물의 올바른 표시 이름을 반환한다")
    @CsvSource({
            "ADVISOR, CHO, 士",
            "ADVISOR, HAN, 士",
            "CANNON, CHO, 包",
            "CANNON, HAN, 包",
            "CHARIOT, CHO, 車",
            "CHARIOT, HAN, 車",
            "ELEPHANT, CHO, 象",
            "ELEPHANT, HAN, 象",
            "GENERAL, CHO, 楚",
            "GENERAL, HAN, 漢",
            "HORSE, CHO, 馬",
            "HORSE, HAN, 馬",
            "SOLDIER, CHO, 卒",
            "SOLDIER, HAN, 兵"
    })
    void findDisplayName_giveCamp_ReturnDisplayNameByCamp(PieceDisplayName pieceDisplayName, Camp camp, String expectedName) {
        String displayName = pieceDisplayName.findDisplayName(camp);

        assertThat(displayName).isEqualTo(expectedName);
    }
}
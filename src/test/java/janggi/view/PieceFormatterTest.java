package janggi.view;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Camp;
import janggi.piece.PieceSymbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceFormatterTest {

    @DisplayName("기물의 출력 형태로 포맷팅할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "CANNON, CHU, \u001B[32m포\u001B[0m",
            "CANNON, HAN, \u001B[31m포\u001B[0m",
            "CHARIOT, CHU, \u001B[32m차\u001B[0m",
            "CHARIOT, HAN, \u001B[31m차\u001B[0m",
            "ELEPHANT, CHU, \u001B[32m상\u001B[0m",
            "ELEPHANT, HAN, \u001B[31m상\u001B[0m",
            "GENERAL, CHU, \u001B[32m왕\u001B[0m",
            "GENERAL, HAN, \u001B[31m왕\u001B[0m",
            "GUARD, CHU, \u001B[32m사\u001B[0m",
            "GUARD, HAN, \u001B[31m사\u001B[0m",
            "HORSE, CHU, \u001B[32m마\u001B[0m",
            "HORSE, HAN, \u001B[31m마\u001B[0m",
            "SOLDIER_JOL, CHU, \u001B[32m졸\u001B[0m",
            "SOLDIER_BYEONG, HAN, \u001B[31m병\u001B[0m"
    })
    void formatTest(PieceSymbol pieceSymbol, Camp camp, String expected) {
        // when
        String formatted = PieceFormatter.formatPiece(pieceSymbol, camp);

        // then
        assertThat(formatted)
                .contains(expected);
    }
}

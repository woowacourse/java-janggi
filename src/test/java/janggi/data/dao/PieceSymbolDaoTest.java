package janggi.data.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.data.dao.test.FakePieceSymbolDao;
import janggi.piece.PieceSymbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceSymbolDaoTest {

    private final PieceSymbolDao pieceSymbolDao = new FakePieceSymbolDao();

    @DisplayName("기물 유형을 저장하고 이름으로 ID를 찾을 수 있다.")
    @Test
    void saveAllTest() {
        // when
        pieceSymbolDao.saveAll(PieceSymbol.values());

        // then
        assertAll(
                () -> assertThat(pieceSymbolDao.findIdByName("CANNON"))
                        .isEqualTo(1),
                () -> assertThat(pieceSymbolDao.findIdByName("CHARIOT"))
                        .isEqualTo(2),
                () -> assertThat(pieceSymbolDao.findIdByName("GENERAL"))
                        .isEqualTo(4)
        );
    }

    @DisplayName("존재하지 않는 기물 유형 이름으로 ID를 찾을 경우 예외가 발생한다.")
    @Test
    void findIdByNameWithNonExistentName() {
        // when & then
        assertThatCode(() -> pieceSymbolDao.findIdByName("NON_EXISTENT"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 이름의 기물 유형이 존재하지 않습니다.");
    }
}

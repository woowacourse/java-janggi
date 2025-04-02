package janggi.data.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.data.dao.test.FakeCampDao;
import janggi.piece.Camp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CampDaoTest {

    private final CampDao campDao = new FakeCampDao();

    @DisplayName("진영을 저장하고 이름으로 id를 찾을 수 있다.")
    @Test
    void saveAllTest() {
        // when
        campDao.saveAll(Camp.values());

        // then
        assertAll(
                () -> assertThat(campDao.findIdByName("HAN"))
                        .isEqualTo(1),
                () -> assertThat(campDao.findIdByName("CHU"))
                        .isEqualTo(2)
        );
    }

    @DisplayName("존재하지 않는 진영 이름으로 id를 찾을 경우 예외가 발생한다.")
    @Test
    void findIdByNameWithNonExistentName() {
        // when & then
        assertThatCode(() -> campDao.findIdByName("NON_EXISTENT"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("진영 정보를 조회하는 도중 오류가 발생했습니다.");
    }
}

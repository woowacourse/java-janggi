package infra.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import infra.entity.TurnEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TurnDaoTest {

    private final TurnDao turnDao = new TurnDao();

    @BeforeEach
    void setUp() {
        turnDao.deleteAll();
    }

    @AfterEach
    void tearDown() {
        turnDao.deleteAll();
    }

    @Nested
    class ValidCases {

        @Test
        @DisplayName("턴 정보를 저장할 수 있다.")
        void save() {
            // given
            TurnEntity turn = new TurnEntity("RED");
            turnDao.save(turn);

            // when & then
            TurnEntity saved = turnDao.findLast();
            assertSoftly(softly -> {
                softly.assertThat(saved.getId()).isNotNull();
                softly.assertThat(saved.getTeam()).isEqualTo("RED");
            });
        }

        @Test
        @DisplayName("턴 정보가 존재하는지 확인할 수 있다.")
        void exists() {
            // when & then
            assertSoftly(softly -> {
                softly.assertThat(turnDao.exists()).isFalse();
                turnDao.save(new TurnEntity("GREEN"));
                softly.assertThat(turnDao.exists()).isTrue();
            });
        }

        @Test
        @DisplayName("마지막 턴을 조회할 수 있다.")
        void findLast() {
            // given
            turnDao.save(new TurnEntity("GREEN"));
            TurnEntity target = turnDao.save(new TurnEntity("RED"));

            // when
            TurnEntity latest = turnDao.findLast();

            // then
            assertThat(latest).isEqualTo(target);
        }

        @Test
        @DisplayName("모든 턴 정보를 삭제할 수 있다.")
        void deleteAll() {
            // given
            turnDao.save(new TurnEntity("GREEN"));

            // when
            turnDao.deleteAll();

            // then
            assertThat(turnDao.exists()).isFalse();
        }
    }
}

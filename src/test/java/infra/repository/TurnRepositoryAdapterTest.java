package infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Team;
import domain.turn.Turn;
import infra.dao.TurnDao;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TurnRepositoryAdapterTest {

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
        @DisplayName("턴을 저장할 수 있다.")
        void save() {
            // given
            TurnRepositoryAdapter repository = new TurnRepositoryAdapter(turnDao);
            Turn turn = new Turn(Team.GREEN);
            repository.save(turn);

            // when
            Turn loadedTurn = repository.findLast().get();

            // then
            assertThat(loadedTurn).isEqualTo(turn);
        }

        @Test
        @DisplayName("턴이 존재하지 않음을 판별한다.")
        void exists() {
            // given
            TurnRepositoryAdapter repository = new TurnRepositoryAdapter(turnDao);

            // when & then
            assertThat(repository.exists()).isFalse();
        }

        @Test
        @DisplayName("저장된 턴을 가져올 수 있다.")
        void findLast_savedTurn() {
            // given
            TurnRepositoryAdapter repository = new TurnRepositoryAdapter(turnDao);
            Turn turn = new Turn(Team.RED);
            repository.save(turn);

            // when
            Turn loadedTurn = repository.findLast().get();

            // then
            assertThat(loadedTurn).isEqualTo(turn);
        }

        @Test
        @DisplayName("저장된 턴이 없으면 가져올 수 없다.")
        void findLast_noSavedTurn() {
            // given
            TurnRepositoryAdapter repository = new TurnRepositoryAdapter(turnDao);

            // when
            Optional<Turn> savedTurn = repository.findLast();

            // when & then
            assertThat(savedTurn).isEmpty();
        }

        @Test
        @DisplayName("턴을 삭제하면 데이터가 사라진다.")
        void deleteAll() {
            // given
            TurnRepositoryAdapter repository = new TurnRepositoryAdapter(turnDao);
            repository.save(new Turn(Team.GREEN));

            // when
            repository.deleteAll();

            // then
            assertThat(repository.exists()).isFalse();
        }
    }
}

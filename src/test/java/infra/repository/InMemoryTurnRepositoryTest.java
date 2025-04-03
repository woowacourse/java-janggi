package infra.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import domain.piece.Team;
import domain.turn.Turn;
import fake.InMemoryTurnRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class InMemoryTurnRepositoryTest {

    @Nested
    class ValidCases {

        @Test
        @DisplayName("턴을 저장할 수 있다.")
        void save() {
            // given
            InMemoryTurnRepository repository = new InMemoryTurnRepository();
            Turn turn = new Turn(Team.GREEN);
            repository.save(turn);

            // when & then
            assertThat(repository.findLast().get()).isEqualTo(turn);
        }

        @Test
        @DisplayName("턴이 존재함을 판별한다.")
        void exists() {
            // given
            InMemoryTurnRepository repository = new InMemoryTurnRepository();

            // when & then
            assertSoftly(softly -> {
                softly.assertThat(repository.exists()).isFalse();
                repository.save(new Turn(Team.RED));
                softly.assertThat(repository.exists()).isTrue();
            });
        }

        @Test
        @DisplayName("현재 턴을 가져온다.")
        void findLast() {
            // given
            InMemoryTurnRepository repository = new InMemoryTurnRepository();
            Turn turn = new Turn(Team.GREEN);
            repository.save(turn);

            // when & then
            assertThat(repository.findLast().get()).isEqualTo(turn);
        }

        @Test
        @DisplayName("턴을 삭제하면 데이터가 사라진다.")
        void deleteAll() {
            // given
            InMemoryTurnRepository repository = new InMemoryTurnRepository();
            repository.save(new Turn(Team.GREEN));
            repository.deleteAll();

            // when & then
            assertThat(repository.exists()).isFalse();
        }
    }
}

package janggi.infra.repository.turn_repository;

import janggi.domain.Country;
import janggi.infra.connector.InMemoryConnector;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.*;

class JdbcTurnRepositoryTest {

    private void runWithPieceRepository(Consumer<TurnRepository> testCode) {
        final TurnRepository repository = new JdbcTurnRepository(new InMemoryConnector());
        repository.createTable();
        testCode.accept(repository);
        repository.deleteTable();
    }

    @Test
    void 다음_차례를_저장한다() {
        runWithPieceRepository((repository) -> {
            // given

            // expected
            assertThatCode(() -> repository.saveTurn(1, Country.CHO))
                    .doesNotThrowAnyException();
        });
    }

    @Test
    void 저장한_차례를_불러온다() {
        runWithPieceRepository((repository) -> {
            // given
            repository.saveTurn(5, Country.CHO);

            // when
            final Country result = repository.findNextTurn(5);

            // then
            assertThat(result).isEqualTo(Country.CHO);
        });
    }

}
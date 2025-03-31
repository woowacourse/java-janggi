package janggi.infra.repository.turn_repository;

import janggi.domain.Country;
import janggi.test_util.InMemoryConnector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class JdbcTurnRepositoryTest {

    private final TurnRepository repository = new JdbcTurnRepository(new InMemoryConnector());

    @BeforeEach
    void setUp() {
        repository.createTable();
    }

    @AfterEach
    void tearDown() {
        repository.deleteTable();
    }

    @Test
    void 다음_차례를_저장한다() {
        // given

        // expected
        assertThatCode(() -> repository.saveTurn(1, Country.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 저장한_차례를_불러온다() {
        // given
        repository.saveTurn(5, Country.CHO);

        // when
        final Country result = repository.findNextTurn(5);

        // then
        assertThat(result).isEqualTo(Country.CHO);
    }

}
package dao;

import database.H2ConnectManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.piece.Country;
import domain.position.LineDirection;

import java.util.EnumMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CountryDaoTest {

    private CountryDirectionDao countryDao;

    @BeforeEach
    void setUp() {
        countryDao = new CountryDirectionDao(new H2ConnectManager());
    }

    @Test
    void saveDirectionAndLoadDirections() {
        // given
        Map<Country, LineDirection> directions = new EnumMap<>(Country.class);
        directions.put(Country.HAN, LineDirection.UP);
        directions.put(Country.CHO, LineDirection.DOWN);

        // when
        countryDao.saveDirection(directions);
        Map<Country, LineDirection> loaded = countryDao.loadDirections();

        // then
        assertThat(loaded).containsEntry(Country.HAN, LineDirection.UP);
        assertThat(loaded).containsEntry(Country.CHO, LineDirection.DOWN);
    }
}

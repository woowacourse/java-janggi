package dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piece.Country;

import java.util.EnumMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BoardDaoTest {

    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        boardDao = new BoardDao();
    }

    @Test
    void saveScoreAndLoadScore() {
        // given
        Map<Country, Integer> scoreMap = new EnumMap<>(Country.class);
        scoreMap.put(Country.HAN, 3);
        scoreMap.put(Country.CHO, 5);

        // when
        boardDao.saveScore(scoreMap);
        Map<Country, Integer> loaded = boardDao.loadScore();

        // then
        assertThat(loaded).containsEntry(Country.HAN, 3);
        assertThat(loaded).containsEntry(Country.CHO, 5);
    }
}

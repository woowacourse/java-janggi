package dao;

import static org.assertj.core.api.Assertions.assertThat;

import domain.fake.InMemoryJanggiDao;
import game.Janggi;
import game.Turn;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiDaoImplTest {

    @Test
    @DisplayName("save 테스트")
    void test1() {
        //given
        final Turn turn = Turn.create();
        final Janggi janggi = new Janggi(null, turn);

        //when
        final InMemoryJanggiDao inMemoryJanggiDao = new InMemoryJanggiDao();
        inMemoryJanggiDao.save(janggi);
        final Optional<Turn> dataSource = inMemoryJanggiDao.findTurn();

        //then
        assertThat(dataSource).isPresent();
    }

    @Test
    @DisplayName("clear 테스트")
    void test2() {
        //given
        final Turn turn = Turn.create();
        final Janggi janggi = new Janggi(null, turn);

        //when
        final InMemoryJanggiDao inMemoryJanggiDao = new InMemoryJanggiDao();
        inMemoryJanggiDao.save(janggi);
        inMemoryJanggiDao.clear();
        final Optional<Turn> dataSource = inMemoryJanggiDao.findTurn();

        //then
        assertThat(dataSource).isEmpty();
    }
}

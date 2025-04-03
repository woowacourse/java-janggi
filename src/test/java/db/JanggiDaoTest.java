package db;

import static org.assertj.core.api.Assertions.assertThat;

import dao.JanggiDao;

public class JanggiDaoTest {
// Dao 테스트는 외부의 DB 없이는 아직 테스타가 어렵다고 생각합니다.

    JanggiDao janggiDao = new JanggiDao();


//
//    @Test
//    public void connection() throws SQLException {
//        try (final var connection = janggiDao.getConnection()) {
//            assertThat(connection).isNotNull();
//        }
//    }
}

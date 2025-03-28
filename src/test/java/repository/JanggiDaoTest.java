package repository;

import janggi.domain.ReplaceUnderBar;
import janggi.repository.JanggiDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@ReplaceUnderBar
public class JanggiDaoTest {

    private final JanggiDao janggiDao = new JanggiDao();

    @BeforeEach
    void piece_테이블_삭제() throws SQLException {
        try (Connection connection = janggiDao.getConnection()) {
            String dropTableQuery = "DROP TABLE IF EXISTS piece";
            PreparedStatement preparedStatement = connection.prepareStatement(dropTableQuery);
            preparedStatement.executeUpdate(dropTableQuery);
            System.out.println("piece 테이블 삭제 완료");
        }
    }

    @Test
    void DB_Connection_연결_테스트() throws SQLException {
        try (Connection connection = janggiDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}

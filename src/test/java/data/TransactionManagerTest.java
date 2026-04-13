package data;

import domain.piece.Camp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionManagerTest extends DatabaseTestHelper {
    private DataSource dataSource;
    private TransactionManager transactionManager;
    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        dataSource = createDataSource();
        transactionManager = new TransactionManager(dataSource);
        boardDao = new BoardDao();
    }

    @Test
    void 트랜잭션이_성공하면_커밋한다() throws Exception {
        Long boardId = transactionManager.executeTransaction(
                connection -> boardDao.insertBoard(connection, true, Camp.CHO.name())
        );

        try (Connection connection = getConnection(dataSource)) {
            assertThat(boardDao.getBoard(connection, boardId)).isPresent();
        }
    }

    @Test
    void 트랜잭션_중_예외가_발생하면_롤백한다() throws Exception {
        assertThatThrownBy(() -> transactionManager.executeTransaction(connection -> {
            boardDao.insertBoard(connection, true, Camp.CHO.name());
            throw new IllegalArgumentException();
        })).isInstanceOf(RuntimeException.class);

        try (Connection connection = getConnection(dataSource)) {
            assertThat(boardDao.getBoard(connection, 1L)).isEmpty();
        }
    }
}

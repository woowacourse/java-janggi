package data;

import domain.piece.Camp;
import domain.piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PieceDaoTest extends DatabaseTestHelper {
    private DataSource dataSource;
    private BoardDao boardDao;
    private PieceDao pieceDao;

    @BeforeEach
    void setUp() {
        dataSource = createDataSource();
        boardDao = new BoardDao();
        pieceDao = new PieceDao();
    }

    @Test
    void 특정_장기판의_모든_기물을_조회한다() throws Exception {
        try (Connection connection = getConnection(dataSource)) {
            Long boardId = boardDao.insertBoard(connection, true, Camp.CHO.name());
            pieceDao.insertPiece(connection, boardId, PieceType.CHARIOT.name(), Camp.CHO.name(), 0, 0);
            pieceDao.insertPiece(connection, boardId, PieceType.GENERAL.name(), Camp.HAN.name(), 4, 9);

            List<PieceDto> result = pieceDao.getAllPieceByBoard(connection, boardId);

            assertThat(result).containsExactlyInAnyOrder(
                    new PieceDto(0, 0, PieceType.CHARIOT, Camp.CHO),
                    new PieceDto(4, 9, PieceType.GENERAL, Camp.HAN)
            );
        }
    }

    @Test
    void 특정_장기판의_기물을_모두_삭제한다() throws Exception {
        try (Connection connection = getConnection(dataSource)) {
            Long boardId = boardDao.insertBoard(connection, true, Camp.CHO.name());
            pieceDao.insertPiece(connection, boardId, PieceType.CHARIOT.name(), Camp.CHO.name(), 0, 0);
            pieceDao.insertPiece(connection, boardId, PieceType.GENERAL.name(), Camp.HAN.name(), 4, 9);

            pieceDao.deleteAllByBoard(connection, boardId);

            assertThat(pieceDao.getAllPieceByBoard(connection, boardId)).isEmpty();
        }
    }
}

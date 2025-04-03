package dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Position;
import dao.connector.DBConnector;
import dao.connector.H2DBConnector;
import piece.Chariot;
import piece.King;
import piece.Piece;
import piece.Team;

class PieceDaoTest {

    private static Connection connection;
    private PieceDao pieceDao;

    @BeforeAll
    static void beforeAll() throws SQLException {
        DBConnector dbConnector = new H2DBConnector();
        connection = dbConnector.getConnection();
        String sql = """
                CREATE TABLE IF NOT EXISTS piece
                (
                    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                    row_value    INT         NOT NULL,
                    column_value INT         NOT NULL,
                    piece_type   VARCHAR(20) NOT NULL,
                    team         VARCHAR(20) NOT NULL,
                    UNIQUE (row_value, column_value)
                );
                """;
        connection.prepareStatement(sql).execute();
        connection.setAutoCommit(false);
    }

    @BeforeEach
    void setUp() {
        pieceDao = new PieceDao(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
    }

    @Test
    void 모든_기물을_저장한다() {
        Position position1 = new Position(2, 5);
        Position position2 = new Position(9, 5);
        Piece piece1 = new King(Team.RED);
        Piece piece2 = new King(Team.BLUE);
        List<PieceEntity> pieceEntities = List.of(
                PieceConverter.toEntity(position1, piece1), PieceConverter.toEntity(position2, piece2)
        );

        pieceDao.saveAll(pieceEntities);

        assertThat(pieceDao.findAll()).isEqualTo(pieceEntities);
    }

    @Test
    void 모든_기물을_조회한다() {
        Position position1 = new Position(2, 5);
        Position position2 = new Position(9, 5);
        Piece piece1 = new King(Team.RED);
        Piece piece2 = new King(Team.BLUE);
        List<PieceEntity> pieceEntities = List.of(
                PieceConverter.toEntity(position1, piece1), PieceConverter.toEntity(position2, piece2)
        );
        pieceDao.saveAll(pieceEntities);

        assertThat(pieceDao.findAll()).isEqualTo(pieceEntities);
    }

    @Test
    void 갱신할_위치의_기물을_삭제하고_업데이트한다() {
        Position originPosition = new Position(2, 5);
        Position updatePosition = new Position(9, 5);
        Piece updatedPiece = new Chariot(Team.RED);
        Piece removedPiece = new Chariot(Team.BLUE);
        List<PieceEntity> pieceEntities = List.of(
                PieceConverter.toEntity(originPosition, updatedPiece),
                PieceConverter.toEntity(updatePosition, removedPiece)
        );
        pieceDao.saveAll(pieceEntities);

        pieceDao.removeAndUpdatePosition(originPosition, updatePosition);

        assertThat(pieceDao.findAll()).isEqualTo(List.of(PieceConverter.toEntity(
                updatePosition, updatedPiece
        )));
    }

    @Test
    void 모든_기물을_삭제한다() {
        Position position1 = new Position(2, 5);
        Position position2 = new Position(9, 5);
        Piece piece1 = new King(Team.RED);
        Piece piece2 = new King(Team.BLUE);
        List<PieceEntity> pieceEntities = List.of(
                PieceConverter.toEntity(position1, piece1), PieceConverter.toEntity(position2, piece2)
        );
        pieceDao.saveAll(pieceEntities);

        pieceDao.removeAll();

        assertThat(pieceDao.findAll()).isEmpty();
    }

}

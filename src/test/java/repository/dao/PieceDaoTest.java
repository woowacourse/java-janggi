package repository.dao;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E8;

import janggi.piece.Piece;
import janggi.piece.palacePiece.King;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.ConnectH2;
import repository.connection.ConnectDatabase;
import repository.converter.PieceConverter;

public class PieceDaoTest {

    private static ConnectDatabase connectDatabase;
    private static PieceDao pieceDao;
    private static Connection connection;

    @BeforeEach
    void setUpDatabase() throws SQLException {
        connectDatabase = new ConnectH2();
        connection = connectDatabase.create();
        pieceDao = new PieceDao(connectDatabase);

        String createPieceTable = """
                CREATE TABLE IF NOT EXISTS PIECE (
                    piece_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    row_index INT NOT NULL,
                    column_index INT NOT NULL,
                    piece_type_name VARCHAR(20) NOT NULL,
                    team_name VARCHAR(20) NOT NULL,
                    UNIQUE(row_index, column_index)
                );
                """;

        connection.prepareStatement(createPieceTable).execute();
    }

    @Test
    @DisplayName("기물들을 추가할 수 있다.")
    public void addAllTest() {
        // given
        Piece king1 = new King(CHO, E1);
        Piece king2 = new King(HAN, E8);
        Set<PieceConverter> pieceConverters = new HashSet<>();
        pieceConverters.add(PieceConverter.toEntity(king1));
        pieceConverters.add(PieceConverter.toEntity(king2));

        // when - then
        assertThatCode(() -> pieceDao.addAll(pieceConverters))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("기물들을 가져올 수 있다.")
    public void findAllTest(){
        // given
        Piece king1 = new King(CHO, E1);
        Piece king2 = new King(HAN, E8);
        Set<PieceConverter> pieceConverters = new HashSet<>();
        pieceConverters.add(PieceConverter.toEntity(king1));
        pieceConverters.add(PieceConverter.toEntity(king2));
        pieceDao.addAll(pieceConverters);

        // when
        Set<Piece> pieces = pieceDao.findAll();

        // then
        assertThat(pieces.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("기물들을 삭제할 수 있다.")
    public void deleteAllTest(){
        // given
        Piece king1 = new King(CHO, E1);
        Piece king2 = new King(HAN, E8);
        Set<PieceConverter> pieceConverters = new HashSet<>();
        pieceConverters.add(PieceConverter.toEntity(king1));
        pieceConverters.add(PieceConverter.toEntity(king2));
        pieceDao.addAll(pieceConverters);

        // when - then
        assertThatCode(() -> pieceDao.deleteAll())
                .doesNotThrowAnyException();
    }

    @AfterEach
    void closeConnection() throws SQLException {
        String clearTable = "DELETE FROM PIECE;";

        connection.prepareStatement(clearTable).execute();

        connectDatabase.close(connection);
    }

}

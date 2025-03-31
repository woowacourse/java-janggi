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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.ConnectH2;
import repository.connection.ConnectDatabase;
import repository.converter.PieceConverter;

public class PieceDaoTest {

    private static final ConnectDatabase connectDatabase = new ConnectH2();
    private static final Connection connection = connectDatabase.create();
    PieceDao pieceDao = new PieceDao(connectDatabase);

    @BeforeAll
    static void setUpDatabase() throws SQLException {
        String createPieceTable = """
                CREATE TABLE PIECE (
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

}

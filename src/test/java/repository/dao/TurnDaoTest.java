package repository.dao;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThatCode;
import static position.PositionFixtures.E8;

import janggi.piece.Piece;
import janggi.piece.Team;
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
import repository.converter.TurnConverter;

public class TurnDaoTest {

    private static final ConnectDatabase connectDatabase = new ConnectH2();
    private static final Connection connection = connectDatabase.create();
    TurnDao turnDao = new TurnDao(new ConnectH2());

    @BeforeAll
    static void setUpDatabase() throws SQLException {
        String createTurnTable = """
            CREATE TABLE IF NOT EXISTS TURN (
                turn VARCHAR(20) UNIQUE NOT NULL PRIMARY KEY
            );""";

        connection.prepareStatement(createTurnTable).execute();
    }

    @Test
    @DisplayName("턴을 추가할 수 있다.")
    void addTurnTest(){
        // given
        TurnConverter turnConverter = TurnConverter.toEntity(CHO);

        assertThatCode(() -> turnDao.addTurn(turnConverter))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("현재 턴을 찾을 수 있다.")
    void findTurnTest(){

    }


}

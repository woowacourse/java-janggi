package janggi.domain.board.dao;

import janggi.domain.board.JanggiBoard;
import janggi.database.utils.DatabaseUtils;
import janggi.database.DBConnectorTest;
import janggi.domain.board.dao.JanggiBoardDAO;
import janggi.domain.board.dao.JanggiBoardDAOImpl;
import janggi.domain.board.dao.TeamDAO;
import janggi.domain.board.dao.TeamDAOImpl;
import janggi.domain.piece.Piece;
import janggi.domain.setting.AssignType;
import janggi.domain.value.JanggiPosition;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class JanggiBoardDAOImplTest {
    private static JanggiBoardDAO janggiBoardDAO;
    private static DatabaseUtils databaseUtils;

    @BeforeAll
    static void setUp() {
        databaseUtils = new DatabaseUtils(new DBConnectorTest());
        janggiBoardDAO = new JanggiBoardDAOImpl(databaseUtils);
    }

    private void createPiecesTable() {
        databaseUtils.executeQuery(createPiecesTableQuery(), stmt -> {
           try {
               stmt.executeUpdate();

               return null;
           } catch (SQLException e) {
               throw new RuntimeException("[ERROR] 테스트 Pieces 테이블 만들다가 에러 발생", e);
           }
        });
    }

    private String createPiecesTableQuery() {
        return "CREATE TABLE IF NOT EXISTS pieces ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "team_id INT NOT NULL, "
                + "piece_type CHAR(1) NOT NULL, "
                + "x INT NOT NULL, "
                + "y INT NOT NULL, "
                + "FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE"
                + ")";
    }

    private void createTeamTable() {
        databaseUtils.executeQuery(createTeamTableQuery(), stmt -> {
           try {
               stmt.executeUpdate();

               return null;
           } catch (SQLException e) {
               throw new RuntimeException("[ERROR] 테스트 team 테이블 만들다가 에러 발생", e);
           }
        });
    }

    private String createTeamTableQuery() {
        return "CREATE TABLE IF NOT EXISTS team (id INT AUTO_INCREMENT PRIMARY KEY, name CHAR(1) NOT NULL)";
    }

    @DisplayName("장기말 시작 데이터 삽입 확인")
    @Test
    void test1() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(AssignType.IN_SANG, AssignType.IN_SANG);
        TeamDAO teamDAO = new TeamDAOImpl(databaseUtils);
        createTeamTable();
        teamDAO.insertTeam();
        createPiecesTable();

        //when
        janggiBoardDAO.insertPieces(janggiBoard);

        List<Piece> choPieces = janggiBoardDAO.selectChoRecords();
        List<Piece> hanPieces = janggiBoardDAO.selectHanRecords();

        janggiBoardDAO.dropTables();
        teamDAO.dropTeamTable();
        //then
        Assertions.assertThat(choPieces).hasSize(16);
        Assertions.assertThat(hanPieces).hasSize(16);
    }

    @DisplayName("pieces 테이블 삭제 확인")
    @Test
    void test2() {
        //given
        TeamDAO teamDAO = new TeamDAOImpl(databaseUtils);
        createTeamTable();
        createPiecesTable();

        //when & then
        Assertions.assertThatCode(janggiBoardDAO::dropTables).doesNotThrowAnyException();
        teamDAO.dropTeamTable();
    }

    @DisplayName("pieces 테이블 레코드 업데이트 확인")
    @Test
    void test3() {
        //given
        TeamDAO teamDAO = new TeamDAOImpl(databaseUtils);
        createTeamTable();
        teamDAO.insertTeam();
        createPiecesTable();

        JanggiBoard janggiBoard = new JanggiBoard(AssignType.IN_SANG, AssignType.IN_SANG);
        janggiBoardDAO.insertPieces(janggiBoard);

        //when
        janggiBoardDAO.updateRecords(new JanggiPosition(0,9), new JanggiPosition(0,7), 1);

        //then
        List<Piece> choPieces = janggiBoardDAO.selectChoRecords();
        boolean movedPiece = choPieces.stream()
                .anyMatch(piece -> piece.getPosition().equals(new JanggiPosition(0,7)));

        Assertions.assertThat(movedPiece).isTrue();
        janggiBoardDAO.dropTables();
        teamDAO.dropTeamTable();
    }

    @DisplayName("pieces 테이블 레코드 삭제 확인")
    @Test
    void test4() {
        //given
        TeamDAO teamDAO = new TeamDAOImpl(databaseUtils);
        createTeamTable();
        teamDAO.insertTeam();
        createPiecesTable();

        JanggiBoard janggiBoard = new JanggiBoard(AssignType.IN_SANG, AssignType.IN_SANG);
        janggiBoardDAO.insertPieces(janggiBoard);

        //when
        janggiBoardDAO.deleteRecords(new JanggiPosition(0,9), 1);

        //then
        List<Piece> choPieces = janggiBoardDAO.selectChoRecords();
        boolean isDeletedPiece = choPieces.stream()
                .noneMatch(piece -> piece.getPosition().equals(new JanggiPosition(0,9)));

        Assertions.assertThat(isDeletedPiece).isTrue();
        janggiBoardDAO.dropTables();
        teamDAO.dropTeamTable();
    }

}
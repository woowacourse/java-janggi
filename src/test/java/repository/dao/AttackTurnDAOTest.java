package repository.dao;

import janggi.piece.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.connection.ConnectionManager;
import repository.connection.H2ConnectManager;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttackTurnDAOTest {

    private static ConnectionManager connectionManager;
    private static AttackTurnDAO attackTurnDAO;
    private static Connection connection;

    @BeforeEach
    void setUpTestDatabase() throws SQLException {
        connectionManager = new H2ConnectManager();
        attackTurnDAO = new AttackTurnDAO(connectionManager);
        connection = connectionManager.getConnection();

        String attackTurnTable = """
                CREATE TABLE IF NOT EXISTS attack_turn (
                    team_name VARCHAR(20) UNIQUE NOT NULL PRIMARY KEY
                );
                """;

        connection.prepareStatement(attackTurnTable).execute();

        attackTurnDAO.resetTurn();
    }

    @Test
    void 공격_턴을_저장하고_턴_정보를_불러온다() {
        attackTurnDAO.saveTurn(Team.RED);
        Team loadedTeam = attackTurnDAO.loadAttackTeam();

        assertThat(loadedTeam).isEqualTo(Team.RED);
    }

    @Test
    void 저장된_데이터가_없으면_예외가_발생한다() {
        assertThatThrownBy(() -> attackTurnDAO.loadAttackTeam())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("공격 턴 데이터 없음");
    }

    @Test
    void 턴_정보를_업데이트_한다() {
        attackTurnDAO.saveTurn(Team.RED);
        attackTurnDAO.updateTurn(Team.GREEN);

        Team updatedTeam = attackTurnDAO.loadAttackTeam();
        assertThat(updatedTeam).isEqualTo(Team.GREEN);
    }

    @Test
    void 공격_턴을_리셋하면_불러올_수_없다() {
        attackTurnDAO.saveTurn(Team.RED);
        attackTurnDAO.resetTurn();

        assertThatThrownBy(() -> attackTurnDAO.loadAttackTeam())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("공격 턴 데이터 없음");
    }
}

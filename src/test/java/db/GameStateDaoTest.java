package db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import domain.GameState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameStateDaoTest {

    @Mock
    private DatabaseConnector databaseConnector;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private GameStateDao gameStateDao;

    @BeforeEach
    void setUp() {
        when(databaseConnector.getConnection()).thenReturn(connection);
        gameStateDao = new GameStateDao(databaseConnector);
    }

    @Test
    void getGameState_정상작동() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("state")).thenReturn("RUNNING");

        GameState state = gameStateDao.getGameState();

        assertThat(state).isEqualTo(GameState.RUNNING);
    }

    @Test
    void updateGameState_정상작동() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        gameStateDao.updateGameState(GameState.END);

        verify(preparedStatement).setString(1, "END");
        verify(preparedStatement).executeUpdate();
    }
}

package db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import domain.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TurnDaoTest {

    @Mock
    private DatabaseConnector databaseConnector;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private TurnDao turnDao;

    @BeforeEach
    void setUp() {
        when(databaseConnector.getConnection()).thenReturn(connection);
        turnDao = new TurnDao(databaseConnector);
    }

    @Test
    void changeTurn_정상작동() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        turnDao.changeTurn(Team.RED);

        verify(preparedStatement).setString(1, "RED");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void getTurn_정상작동() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("team")).thenReturn("green");

        Team result = turnDao.getTurn();

        assertThat(result).isEqualTo(Team.GREEN);
    }
}

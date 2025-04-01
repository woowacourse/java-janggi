package queue;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import domain.piece.character.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageQueueTest {

    @Mock
    private Connection testConnection;

    @Mock
    private PreparedStatement testPreparedStatement;

    @BeforeEach
    void resetQueue() {
        MessageQueue.getInstance().clear();
    }

    @Test
    void 메시지_큐에_있는_SQL_쿼리문들을_정상적으로_실행한다() throws SQLException {
        // given
        final String sql = "INSERT INTO game_room VALUES (?, ?)";
        final List<Object> params = List.of("room1", Team.CHO.name());
        final String sql2 = "INSERT INTO game_room VALUES (?, ?)";
        final List<Object> params2 = List.of("room2", Team.HAN.name());

        final DelayedQuery delayedQuery = new DelayedQuery(sql, params);
        final DelayedQuery delayedQuery2 = new DelayedQuery(sql2, params2);
        MessageQueue.getInstance().addLast(delayedQuery);
        MessageQueue.getInstance().addLast(delayedQuery2);

        when(testConnection.prepareStatement(sql)).thenReturn(testPreparedStatement);
        final int rowAffected = 1;
        when(testPreparedStatement.executeUpdate()).thenReturn(rowAffected);

        // when
        MessageQueue.getInstance().executeDelayedQueries(testConnection);

        // then
        verify(testConnection, times(2)).prepareStatement(sql);
        verify(testPreparedStatement, times(1)).setObject(1, "room1");
        verify(testPreparedStatement, times(1)).setObject(2, Team.CHO.name());
        verify(testPreparedStatement, times(1)).setObject(1, "room2");
        verify(testPreparedStatement, times(1)).setObject(2, Team.HAN.name());
        verify(testPreparedStatement, times(2)).executeUpdate();
    }

    @Test
    void SQL_문법이_맞지_않는_경우_SQLSyntaxErrorException_예외를_던진다() throws SQLException {
        // given
        final String sql = "SYNTAX ERROR";
        final List<Object> params = List.of("room1", Team.CHO.name());
        final DelayedQuery delayedQuery = new DelayedQuery(sql, params);
        MessageQueue.getInstance().addLast(delayedQuery);

        when(testConnection.prepareStatement(sql)).thenReturn(testPreparedStatement);
        doThrow(new SQLSyntaxErrorException())
                .when(testPreparedStatement).executeUpdate();

        // when
        MessageQueue.getInstance().executeDelayedQueries(testConnection);

        // then
        verify(testConnection).prepareStatement(sql);
        verify(testPreparedStatement).setObject(1, "room1");
        verify(testPreparedStatement).setObject(2, Team.CHO.name());
        Assertions.assertThatThrownBy(() -> testPreparedStatement.executeUpdate())
                .isInstanceOf(SQLSyntaxErrorException.class);
    }

    @Test
    void DB_연결이_되지_않은_경우_SQLException_예외를_던진다() throws SQLException {
        // given
        final String sql = "INSERT INTO game_room VALUES (?, ?)";
        final List<Object> params = List.of("room1", Team.CHO.name());
        final DelayedQuery delayedQuery = new DelayedQuery(sql, params);
        MessageQueue.getInstance().addLast(delayedQuery);

        when(testConnection.prepareStatement(sql)).thenReturn(testPreparedStatement);
        doThrow(new SQLException())
                .when(testPreparedStatement).executeUpdate();

        // when
        MessageQueue.getInstance().executeDelayedQueries(testConnection);

        // then
        verify(testConnection).prepareStatement(sql);
        verify(testPreparedStatement).setObject(1, "room1");
        verify(testPreparedStatement).setObject(2, Team.CHO.name());
        Assertions.assertThatThrownBy(() -> testPreparedStatement.executeUpdate())
                .isInstanceOf(SQLException.class);
    }
}
package db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import domain.Team;
import domain.piece.PieceType;
import domain.position.Point;
import domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PositionDaoTest {

    @Mock
    private DatabaseConnector databaseConnector;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement pointStmt;
    @Mock
    private PreparedStatement pieceStmt;
    @Mock
    private ResultSet resultSet;

    private PositionDao positionDao;

    @BeforeEach
    void setUp() {
        when(databaseConnector.getConnection()).thenReturn(connection);
        positionDao = new PositionDao(databaseConnector);
    }

    @Test
    void savePosition_정상작동() throws Exception {
        when(connection.prepareStatement(contains("INSERT INTO point"), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(pointStmt);
        when(connection.prepareStatement(contains("INSERT INTO piece")))
                .thenReturn(pieceStmt);
        when(pointStmt.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        Position position = Position.newInstance(Point.of("2", "4"),
                PieceType.find(PieceType.CHARIOT, Team.RED));
        positionDao.savePosition(position);

        verify(pointStmt).setInt(1, 2);
        verify(pointStmt).setInt(2, 4);
        verify(pointStmt).executeUpdate();

        verify(pieceStmt).setString(1, "CHARIOT");
        verify(pieceStmt).setString(2, "RED");
        verify(pieceStmt).setInt(3, 1);
        verify(pieceStmt).executeUpdate();
    }

    @Test
    void updatePoint_정상작동() throws Exception {
        PreparedStatement updateStmt = mock(PreparedStatement.class);
        PreparedStatement findStmt = mock(PreparedStatement.class);
        ResultSet findRs = mock(ResultSet.class);

        when(connection.prepareStatement(contains("SELECT id"))).thenReturn(findStmt);
        when(connection.prepareStatement(contains("UPDATE point"))).thenReturn(updateStmt);
        when(findStmt.executeQuery()).thenReturn(findRs);
        when(findRs.next()).thenReturn(true);
        when(findRs.getInt("id")).thenReturn(42); // 임의의 ID

        positionDao.updatePoint(Point.of("3", "4").value(), Point.of("5", "6").value());

        verify(updateStmt).setInt(1, 5);
        verify(updateStmt).setInt(2, 6);
        verify(updateStmt).setInt(3, 42);
        verify(updateStmt).executeUpdate();
    }

    @Test
    void deletePosition_정상작동() throws Exception {
        PreparedStatement deletePieceStmt = mock(PreparedStatement.class);
        PreparedStatement deletePointStmt = mock(PreparedStatement.class);
        PreparedStatement findStmt = mock(PreparedStatement.class);
        ResultSet findRs = mock(ResultSet.class);

        when(connection.prepareStatement(contains("SELECT id"))).thenReturn(findStmt);
        when(connection.prepareStatement(contains("DELETE FROM piece"))).thenReturn(deletePieceStmt);
        when(connection.prepareStatement(contains("DELETE FROM point"))).thenReturn(deletePointStmt);
        when(findStmt.executeQuery()).thenReturn(findRs);
        when(findRs.next()).thenReturn(true);
        when(findRs.getInt("id")).thenReturn(99);

        positionDao.deletePosition(Point.of("7", "8").value());

        verify(deletePieceStmt).setInt(1, 99);
        verify(deletePieceStmt).executeUpdate();
        verify(deletePointStmt).setInt(1, 99);
        verify(deletePointStmt).executeUpdate();
    }

    @Test
    void getPositions_정상작동() throws Exception {
        PreparedStatement selectStmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(selectStmt);
        when(selectStmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getString("pieceType")).thenReturn("CHARIOT");
        when(rs.getString("team")).thenReturn("RED");
        when(rs.getInt("x")).thenReturn(1);
        when(rs.getInt("y")).thenReturn(2);

        var result = positionDao.getPositions();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getPieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(result.getFirst().getPointValue().x()).isEqualTo(1);
        assertThat(result.getFirst().getPointValue().y()).isEqualTo(2);
    }

    @Test
    void deleteAllPosition_정상작동() throws Exception {
        PreparedStatement deletePieceStmt = mock(PreparedStatement.class);
        PreparedStatement deletePointStmt = mock(PreparedStatement.class);

        when(connection.prepareStatement(contains("DELETE FROM piece"))).thenReturn(deletePieceStmt);
        when(connection.prepareStatement(contains("DELETE FROM point"))).thenReturn(deletePointStmt);

        positionDao.deleteAllPosition();

        verify(deletePieceStmt).executeUpdate();
        verify(deletePointStmt).executeUpdate();
    }
}

package dao.fake;

import domain.player.Player;
import dto.BoardLocation;
import dto.BoardLocations;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FakePreparedStatement implements PreparedStatement {
    private final String sql;
    private final InMemoryDatabase database;
    private final List<Map<Integer, Object>> batchParameters = new ArrayList<>();

    private Map<Integer, Object> parameters = new HashMap<>();

    public FakePreparedStatement(final String sql, final InMemoryDatabase database) {
        this.sql = sql.toLowerCase();
        this.database = database;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        if (isGamesActivateQuery(sql)) {
            return executeGamesActivateQuery();
        }

        if (isMaxIdQuery(sql)) {
            return executeMaxIdQuery();
        }

        if (isPlayerByIdQuery(sql)) {
            return executePlayerByIdQuery();
        }

        if (isPlayersByGameIdQuery(sql)) {
            return executePlayersByGameIdQuery();
        }

        if (isLocationsByGameIdQuery(sql)) {
            return executeLocationsByGameIdQuery();
        }

        throw new SQLException("선언되지 않은 쿼리 형식입니다: " + sql);
    }

    @Override
    public int executeUpdate() throws SQLException {
        if (isInsertGameQuery(sql)) {
            return executeInsertGameQuery();
        }

        if (isDeactivateGameQuery(sql)) {
            return executeDeactivateGameQuery();
        }

        if (isInsertPlayerQuery(sql)) {
            return executeInsertPlayerQuery();
        }

        if (isUpdatePlayerQuery(sql)) {
            return executeUpdatePlayerQuery();
        }

        if (isInsertLocationQuery(sql)) {
            return executeInsertLocationQuery();
        }

        if (isDeleteLocationQuery(sql)) {
            return executeDeleteLocationQuery();
        }

        if (isUpdateLocationQuery(sql)) {
            return executeUpdateLocationQuery();
        }

        throw new SQLException("선언되지 않은 쿼리 형식입니다: " + sql);
    }

    @Override
    public void addBatch() {
        batchParameters.add(new HashMap<>(parameters));
        parameters = new HashMap<>();
    }

    @Override
    public int[] executeBatch() {
        final int[] results = new int[batchParameters.size()];
        for (int i = 0; i < batchParameters.size(); i++) {
            parameters = batchParameters.get(i);
            try {
                results[i] = executeUpdate();
            } catch (SQLException e) {
                results[i] = EXECUTE_FAILED;
            }
        }

        batchParameters.clear();
        parameters = new HashMap<>();
        return results;
    }

    @Override
    public void setInt(final int parameterIndex, final int x) {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setDouble(final int parameterIndex, final double x) {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setString(final int parameterIndex, final String x) {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setBoolean(final int parameterIndex, final boolean x) {
        parameters.put(parameterIndex, x);
    }

    private boolean isGamesActivateQuery(final String sql) {
        return sql.contains("select game.id from game where is_activate=true");
    }

    private boolean isMaxIdQuery(final String sql) {
        return sql.contains("select max(id)");
    }

    private boolean isPlayerByIdQuery(final String sql) {
        return sql.contains("select * from player where id = ?");
    }

    private boolean isPlayersByGameIdQuery(final String sql) {
        return sql.contains("select * from player where game_id = ?");
    }

    private boolean isLocationsByGameIdQuery(final String sql) {
        return sql.contains("select l.*, p.*, g.id from board_location l");
    }

    private boolean isInsertGameQuery(final String sql) {
        return sql.contains("insert into game");
    }

    private boolean isDeactivateGameQuery(final String sql) {
        return sql.contains("update game set is_activate = false");
    }

    private boolean isInsertPlayerQuery(final String sql) {
        return sql.contains("insert into player");
    }

    private boolean isUpdatePlayerQuery(final String sql) {
        return sql.contains("update player set score = ?, is_turn = ? where id = ?");
    }

    private boolean isInsertLocationQuery(final String sql) {
        return sql.contains("insert into board_location (location_piece, location_row,");
    }

    private boolean isDeleteLocationQuery(final String sql) {
        return sql.contains("delete l from board_location");
    }

    private boolean isUpdateLocationQuery(final String sql) {
        return sql.contains("update board_location l join player pl on");
    }

    private ResultSet executeGamesActivateQuery() {
        final List<Integer> activateGameIds = database.findAllActivateGames();

        final List<Map<String, Object>> values = new ArrayList<>();
        for (final Integer gameId : activateGameIds) {
            final Map<String, Object> value = new HashMap<>();
            value.put("id", gameId);
            values.add(value);
        }
        return new FakeResultSet(values);
    }

    private ResultSet executeMaxIdQuery() {
        final Map<String, Object> value = new HashMap<>();
        value.put("last_id", database.getLastPlayerId());
        return new FakeResultSet(List.of(value));
    }

    private ResultSet executePlayerByIdQuery() {
        final int gameId = (Integer) parameters.get(1);
        final Player player = database.findPlayerById(gameId);

        final Map<String, Object> value = createPlayerResultMap(player);
        return new FakeResultSet(List.of(value));
    }

    private ResultSet executePlayersByGameIdQuery() {
        final int gameId = (int) parameters.get(1);
        final List<Player> players = database.findAllPlayersByGameId(gameId);

        final List<Map<String, Object>> values = new ArrayList<>();
        for (final Player player : players) {
            values.add(createPlayerResultMap(player));
        }
        return new FakeResultSet(values);
    }

    private ResultSet executeLocationsByGameIdQuery() {
        final int gameId = (int) parameters.get(1);
        final BoardLocations locations = database.findAllLocationsByGameId(gameId);

        final List<Map<String, Object>> values = new ArrayList<>();
        for (final BoardLocation location : locations) {
            values.add(createLocationResultMap(location));
        }
        return new FakeResultSet(values);
    }

    private int executeInsertGameQuery() {
        database.createGame();
        return 1;
    }

    private int executeDeactivateGameQuery() {
        final int gameId = (Integer) parameters.get(1);
        database.deactivateGame(gameId);
        return 1;
    }

    private int executeInsertPlayerQuery() {
        final String team = (String) parameters.get(1);
        final Double score = (Double) parameters.get(2);
        final boolean isTurn = (boolean) parameters.get(3);
        final int gameId = (int) parameters.get(4);

        return database.createPlayer(team, score, isTurn, gameId);
    }

    private int executeUpdatePlayerQuery() {
        final Double score = (Double) parameters.get(1);
        final boolean isTurn = (boolean) parameters.get(2);
        final int id = (int) parameters.get(3);

        return database.updatePlayer(score, isTurn, id);
    }

    private int executeInsertLocationQuery() {
        final String piece = (String) parameters.get(1);
        final int row = (int) parameters.get(2);
        final int column = (int) parameters.get(3);
        final int playerId = (int) parameters.get(4);

        return database.createLocation(piece, row, column, playerId);
    }

    private int executeDeleteLocationQuery() {
        final int row = (int) parameters.get(1);
        final int column = (int) parameters.get(2);
        final int gameId = (int) parameters.get(3);

        return database.deleteLocationAt(row, column, gameId);
    }

    private int executeUpdateLocationQuery() {
        final int startRow = (int) parameters.get(1);
        final int startColumn = (int) parameters.get(2);
        final int arrivalRow = (int) parameters.get(3);
        final int arrivalColumn = (int) parameters.get(4);
        final int gameId = (int) parameters.get(5);

        return database.updateLocation(startRow, startColumn, arrivalRow, arrivalColumn, gameId);
    }

    private Map<String, Object> createPlayerResultMap(final Player player) {
        final Map<String, Object> value = new HashMap<>();
        value.put("id", player.getId());
        value.put("team", player.getTeam().name());
        value.put("score", player.getScore().value());
        value.put("is_turn", player.isTurn());
        return value;
    }

    private Map<String, Object> createLocationResultMap(final BoardLocation location) {
        final Map<String, Object> value = new HashMap<>();
        value.put("location_row", location.getRow());
        value.put("location_column", location.getColumn());
        value.put("location_piece", location.getPiece());
        value.put("player_id", location.playerId());
        return value;
    }

    // 이하는 미사용 인터페이스
    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {

    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {

    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {

    }


    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {

    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {

    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void clearParameters() throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {

    }

    @Override
    public boolean execute() throws SQLException {
        return false;
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {

    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {

    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {

    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {

    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {

    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {

    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {

    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {

    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {

    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {

    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {

    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return 0;
    }

    @Override
    public void close() throws SQLException {
        // 아무 작업도 하지 않음
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {

    }

    @Override
    public int getMaxRows() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {

    }

    @Override
    public void cancel() throws SQLException {

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public void setCursorName(String name) throws SQLException {

    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return null;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {

    }

    @Override
    public int getFetchDirection() throws SQLException {
        return 0;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {

    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        return 0;
    }

    @Override
    public void addBatch(String sql) throws SQLException {

    }

    @Override
    public void clearBatch() throws SQLException {

    }


    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}

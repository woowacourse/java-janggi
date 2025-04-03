package janggi.database;

import janggi.board.Board;
import janggi.coordinate.JanggiPosition;
import janggi.piece.Country;
import janggi.piece.Piece;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class JanggiDatabase {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user"; //  MySQL 서버 아이디
    private static final String PASSWORD = "password"; // MySQL 서버 비밀번호

    private static final int X = 2;
    private static final int Y = 3;
    private static final int TYPE = 4;
    private static final int COUNTRY = 5;

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveBoard(final Board board) {
        try (final Connection connection = getConnection()) {
            final String insertQuery = "INSERT INTO PIECE VALUES(DEFAULT, ?, ?, ?, ?)";
            final PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            for (final Map.Entry<JanggiPosition, Piece> value : board.getJanggiBoard().entrySet()) {
                savePiece(preparedStatement, value);
            }

        } catch (final SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private void savePiece(final PreparedStatement preparedStatement, final Entry<JanggiPosition, Piece> value)
            throws SQLException {
        final JanggiPosition position = value.getKey();
        final Piece piece = value.getValue();
        final String pieceName = PieceName.convertPieceName(piece);
        preparedStatement.setString(1, String.valueOf(position.x()));
        preparedStatement.setString(2, String.valueOf(position.y()));
        preparedStatement.setString(3, pieceName);
        preparedStatement.setString(4, piece.getCountry().name());

        preparedStatement.executeUpdate();
    }

    public Board readBoard() {
        try (final Connection connection = getConnection()) {
            final String readQuery = "SELECT * FROM PIECE";
            final PreparedStatement preparedStatement = connection.prepareStatement(readQuery);

            final ResultSet rs = preparedStatement.executeQuery();
            final Map<JanggiPosition, Piece> janggiBoard = new HashMap<>();

            while (rs.next()) {
                readPiece(rs, janggiBoard);
            }

            return new Board(janggiBoard);
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private void readPiece(final ResultSet rs, final Map<JanggiPosition, Piece> janggiBoard)
            throws SQLException {
        final int x = rs.getInt(X);
        final int y = rs.getInt(Y);
        final String pieceName = rs.getString(TYPE);
        final String countryText = rs.getString(COUNTRY);

        final JanggiPosition janggiPosition = new JanggiPosition(x, y);
        final Country country = Country.StringToCountry(countryText);
        final Piece piece = PieceName.convertPiece(pieceName, country);
        janggiBoard.put(janggiPosition, piece);
    }

    public boolean existsJanggiRows() {
        try (final Connection connection = getConnection()) {
            final String readCountQuery = "SELECT COUNT(*) FROM PIECE";
            final PreparedStatement preparedStatement = connection.prepareStatement(readCountQuery);

            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            return rs.getInt(1) > 0;
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public void removeAllJanggiRows() {
        try (final Connection connection = getConnection()) {
            final String removeAllQuery = "DELETE FROM PIECE WHERE ID >= 0";
            final PreparedStatement preparedStatement = connection.prepareStatement(removeAllQuery);

            preparedStatement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public boolean removeJanggiRowByPosition(final JanggiPosition janggiPosition) {
        try (final Connection connection = getConnection()) {
            final String removeByPosition = "DELETE FROM PIECE WHERE X=? AND Y=?";
            final PreparedStatement preparedStatement = connection.prepareStatement(removeByPosition);

            preparedStatement.setString(1, String.valueOf(janggiPosition.x()));
            preparedStatement.setString(2, String.valueOf(janggiPosition.y()));

            return preparedStatement.executeUpdate() == 1;
        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateJanggiRowByPosition(final JanggiPosition janggiPosition, final Piece piece) {
        try (final Connection connection = getConnection()) {
            removeJanggiRowByPosition(janggiPosition);
            final String insertByPosition = "INSERT INTO PIECE VALUES(DEFAULT, ?, ?, ?, ?)";
            final PreparedStatement preparedStatement = connection.prepareStatement(insertByPosition);

            preparedStatement.setString(1, String.valueOf(janggiPosition.x()));
            preparedStatement.setString(2, String.valueOf(janggiPosition.y()));
            preparedStatement.setString(3, PieceName.convertPieceName(piece));
            preparedStatement.setString(4, piece.getCountry().name());

            return preparedStatement.executeUpdate() == 1;
        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTurn(final Country country) {
        try (final Connection connection = getConnection()) {
            final String updateTurn = "UPDATE TURN SET COUNTRY=? WHERE COUNTRY=?";
            final PreparedStatement preparedStatement = connection.prepareStatement(updateTurn);

            preparedStatement.setString(1, country.name());
            preparedStatement.setString(2, country.toggleCountry().name());

            return preparedStatement.executeUpdate() == 1;
        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Country readCurrentTurn() {
        try (final Connection connection = getConnection()) {
            final String readQuery = "SELECT * FROM TURN";
            final PreparedStatement preparedStatement = connection.prepareStatement(readQuery);

            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            final Country country = Country.StringToCountry(rs.getString(2));

            return country;
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public void removeTurn() {
        try (final Connection connection = getConnection()) {
            final String removeQuery = "DELETE FROM TURN";
            final PreparedStatement preparedStatement = connection.prepareStatement(removeQuery);

            preparedStatement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public boolean existsTurn() {
        try (final Connection connection = getConnection()) {
            final String readQuery = "SELECT COUNT(*) FROM TURN";
            final PreparedStatement preparedStatement = connection.prepareStatement(readQuery);

            final ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1) == 1;
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public void saveTurn(final Country country) {
        try (final Connection connection = getConnection()) {
            final String readQuery = "INSERT INTO TURN VALUES(DEFAULT, ?)";
            final PreparedStatement preparedStatement = connection.prepareStatement(readQuery);

            preparedStatement.setString(1, country.name());

            preparedStatement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}

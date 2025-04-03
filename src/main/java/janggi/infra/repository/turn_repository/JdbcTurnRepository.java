package janggi.infra.repository.turn_repository;

import janggi.domain.Country;
import janggi.exception.DatabaseQueryException;
import janggi.infra.connector.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTurnRepository implements TurnRepository {

    private final DatabaseConnector connector;

    public JdbcTurnRepository(final DatabaseConnector connector) {
        this.connector = connector;
    }

    @Override
    @SuppressWarnings("SqlNoDataSourceInspection")
    public void createTable() {
        final var query = """
                CREATE TABLE turn (
                    game_number INT NOT NULL,
                    country VARCHAR(10) NOT NULL,
                    PRIMARY KEY (game_number)
                );
                """;
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseQueryException();
        }
    }

    @Override
    @SuppressWarnings("SqlNoDataSourceInspection")
    public void deleteTable() {
        final var query = """
                DROP TABLE turn;
                """;
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseQueryException();
        }
    }

    @Override
    @SuppressWarnings("SqlNoDataSourceInspection")
    public Country findNextTurn(final int number) {
        final var query = "SELECT * FROM turn WHERE turn.game_number = ?";

        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, number);
            final ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                final String countryString = result.getString("country");
                return convertToCountry(countryString);
            }
        } catch (final SQLException e) {
            throw new DatabaseQueryException();
        }

        return null;
    }

    @Override
    @SuppressWarnings("SqlNoDataSourceInspection")
    public void saveTurn(final int number, final Country country) {
        final var query = "INSERT INTO turn VALUES(?, ?)";

        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, number);
            preparedStatement.setString(2, country.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseQueryException();
        }
    }

    private Country convertToCountry(final String countryString) {
        if (countryString.equals("CHO")) {
            return Country.CHO;
        }
        if (countryString.equals("HAN")) {
            return Country.HAN;
        }
        throw new IllegalStateException("CHO 혹은 HAN으로 지정되어야 합니다.");
    }
}

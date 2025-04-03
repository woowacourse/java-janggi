package janggi.infra.repository.piece_repository;

import janggi.domain.Country;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;
import janggi.exception.DatabaseQueryException;
import janggi.infra.connector.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JdbcPieceRepository implements PieceRepository {

    private final DatabaseConnector connector;

    public JdbcPieceRepository(final DatabaseConnector connector) {
        this.connector = connector;
    }

    @Override
    @SuppressWarnings("SqlNoDataSourceInspection")
    public void createTable() {
        final var query = """
                CREATE TABLE piece (
                    game_number INT NOT NULL,
                    piece_type VARCHAR(10) NOT NULL,
                    position_rank INT NOT NULL,
                    position_file INT NOT NULL,
                    country VARCHAR(10) NOT NULL,
                    PRIMARY KEY (piece_type, position_rank, position_file)
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
                DROP TABLE piece;
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
    public Map<Country, List<Piece>> findAllPieces(final int number) {
        final Map<Country, List<Piece>> pieces = new EnumMap<>(Country.class);
        pieces.putIfAbsent(Country.CHO, new ArrayList<>());
        pieces.putIfAbsent(Country.HAN, new ArrayList<>());

        final var query = "SELECT * FROM piece WHERE piece.game_number = ?";

        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, number);
            final ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                final String pieceTypeString = result.getString("piece_type");
                final int fileString = result.getInt("position_file");
                final int rankString = result.getInt("position_rank");
                final String countryString = result.getString("country");

                final PieceType type = convertToPieceType(pieceTypeString);
                final PositionFile file = convertToFile(fileString);
                final PositionRank rank = convertToRank(rankString);
                final Country country = convertToCountry(countryString);

                pieces.get(country).add(new Piece(type, new Position(file, rank)));
            }
        } catch (final SQLException e) {
            throw new DatabaseQueryException();
        }

        return pieces;
    }

    private PieceType convertToPieceType(final String pieceTypeString) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType.name().equals(pieceTypeString))
                .findFirst()
                .orElseThrow();
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

    @Override
    @SuppressWarnings("SqlNoDataSourceInspection")
    public void saveAllPieces(final int number, final Country country, final List<Piece> pieces) {
        for (Piece piece : pieces) {
            final var query = "INSERT INTO piece VALUES(?, ?, ?, ?, ?)";

            try (final var connection = connector.getConnection();
                 final var preparedStatement = connection.prepareStatement(query)
            ) {
                preparedStatement.setInt(1, number);
                preparedStatement.setString(2, convertToTypeValue(piece).name());
                preparedStatement.setInt(3, convertToFileValue(piece.getPosition().file()));
                preparedStatement.setInt(4, convertToRankValue(piece.getPosition().rank()));
                preparedStatement.setString(5, country.name());

                preparedStatement.executeUpdate();
            } catch (final SQLException e) {
                throw new DatabaseQueryException();
            }
        }
    }

    private int convertToFileValue(final PositionFile file) {
        return file.ordinal();
    }

    private int convertToRankValue(final PositionRank rank) {
        return rank.ordinal();
    }

    private PieceType convertToTypeValue(final Piece piece) {
        return piece.getPieceType();
    }

    private PositionFile convertToFile(final int fileValue) {
        return Arrays.stream(PositionFile.values())
                .filter(file -> file.ordinal() == fileValue)
                .findFirst()
                .orElseThrow();
    }

    private PositionRank convertToRank(final int rankValue) {
        return Arrays.stream(PositionRank.values())
                .filter(rank -> rank.ordinal() == rankValue)
                .findFirst()
                .orElseThrow();
    }
}

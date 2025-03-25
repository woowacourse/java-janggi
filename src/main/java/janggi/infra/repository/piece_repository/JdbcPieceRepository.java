package janggi.infra.repository.piece_repository;

import janggi.domain.Country;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Piece;
import janggi.domain.piece.impl.*;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;
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
                     number INT NOT NULL,
                     type VARCHAR(10) NOT NULL,
                     rank INT NOT NULL,
                     file INT NOT NULL,
                     country VARCHAR(10) NOT NULL
                );
                """;
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("SqlNoDataSourceInspection")
    public Map<Country, List<Piece>> findAllPieces(final int number) {
        final Map<Country, List<Piece>> pieces = new HashMap<>();
        pieces.putIfAbsent(Country.CHO, new ArrayList<>());
        pieces.putIfAbsent(Country.HAN, new ArrayList<>());

        final var query = "SELECT * FROM piece WHERE piece.number = ?";

        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, number);
            final ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                final PieceType pieceType = PieceType.from(result.getString("type"));
                final int rankString = result.getInt("rank");
                final int fileString = result.getInt("file");
                final String countryString = result.getString("country");

                final PositionFile file = convertToFile(fileString);
                final PositionRank rank = convertToRank(rankString);
                final Country country = convertToCountry(countryString);

                switch (pieceType) {
                    case CHA -> pieces.get(country).add(new Cha(new Position(file, rank), new Gung()));
                    case SANG -> pieces.get(country).add(new Sang(new Position(file, rank)));
                    case MA -> pieces.get(country).add(new Ma(new Position(file, rank)));
                    case SA -> pieces.get(country).add(new Sa(new Position(file, rank), new Gung()));
                    case PO -> pieces.get(country).add(new Po(new Position(file, rank), new Gung()));
                    case JANG -> pieces.get(country).add(new Jang(new Position(file, rank), new Gung()));
                    case JOL -> pieces.get(country).add(new Jol(new Position(file, rank)));
                    case BYEONG -> pieces.get(country).add(new Byeong(new Position(file, rank)));
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return pieces;
    }

    private Country convertToCountry(final String countryString) {
        if (countryString.equals("CHO")) return Country.CHO;
        if (countryString.equals("HAN")) return Country.HAN;
        throw new IllegalStateException();
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
                preparedStatement.setString(2, convertToType(piece).name());
                preparedStatement.setInt(3, convertToFileValue(piece.getPosition().file()));
                preparedStatement.setInt(4, convertToRankValue(piece.getPosition().rank()));
                preparedStatement.setString(5, country.name());

                preparedStatement.executeUpdate();
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int convertToFileValue(final PositionFile file) {
        return file.ordinal();
    }

    private int convertToRankValue(final PositionRank rank) {
        return rank.ordinal();
    }

    private PieceType convertToType(final Piece piece) {
        if (piece instanceof Cha) {
            return PieceType.CHA;
        }
        if (piece instanceof Sang) {
            return PieceType.SANG;
        }
        if (piece instanceof Ma) {
            return PieceType.MA;
        }
        if (piece instanceof Sa) {
            return PieceType.SA;
        }
        if (piece instanceof Po) {
            return PieceType.PO;
        }
        if (piece instanceof Jang) {
            return PieceType.JANG;
        }
        if (piece instanceof Jol) {
            return PieceType.JOL;
        }
        if (piece instanceof Byeong) {
            return PieceType.BYEONG;
        }
        throw new IllegalStateException();
    }

    private PositionRank convertToRank(final int rankValue) {
        return Arrays.stream(PositionRank.values())
                .filter(rank -> rank.ordinal() == rankValue)
                .findFirst()
                .orElseThrow();
    }

    private PositionFile convertToFile(final int fileValue) {
        return Arrays.stream(PositionFile.values())
                .filter(file -> file.ordinal() == fileValue)
                .findFirst()
                .orElseThrow();
    }
}

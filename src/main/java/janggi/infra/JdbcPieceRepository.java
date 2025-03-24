package janggi.infra;

import janggi.domain.piece.Gung;
import janggi.domain.piece.Piece;
import janggi.domain.piece.impl.*;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static janggi.infra.PieceDao.getConnection;

public class JdbcPieceRepository implements PieceRepository {

    @Override
    public List<Piece> findAllPieces(final int number) {
        final List<Piece> pieces = new ArrayList<>();

        final var query = "SELECT * FROM piece WHERE piece.number = ?";

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, number);
            final ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                final PieceType pieceType = PieceType.from(result.getString("type"));
                final int rankString = result.getInt("rank");
                final int fileString = result.getInt("file");

                final PositionFile file = convertToFile(fileString);
                final PositionRank rank = convertToRank(rankString);

                switch (pieceType) {
                    case CHA -> pieces.add(new Cha(new Position(file, rank), new Gung()));
                    case SANG -> pieces.add(new Sang(new Position(file, rank)));
                    case MA -> pieces.add(new Ma(new Position(file, rank)));
                    case SA -> pieces.add(new Sa(new Position(file, rank), new Gung()));
                    case PO -> pieces.add(new Po(new Position(file, rank), new Gung()));
                    case JANG -> pieces.add(new Jang(new Position(file, rank), new Gung()));
                    case JOL -> pieces.add(new Jol(new Position(file, rank)));
                    case BYEONG -> pieces.add(new Byeong(new Position(file, rank)));
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return pieces;
    }

    @Override
    public void saveAllPieces(final int number, final List<Piece> pieces) {
        for (Piece piece : pieces) {
            final var query = "INSERT INTO piece VALUES(?, ?, ?, ?)";

            try (final var connection = getConnection();
                 final var preparedStatement = connection.prepareStatement(query)
            ) {
                preparedStatement.setInt(1, number);
                preparedStatement.setString(2, convertToType(piece).name());
                preparedStatement.setInt(3, convertToFileValue(piece.getPosition().file()));
                preparedStatement.setInt(1, convertToRankValue(piece.getPosition().rank()));

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

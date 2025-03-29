package janggi.view;

import janggi.domain.Country;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ViewUtil {

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";
    private static final Map<Country, String> COUNTRY_COLORS = Map.of(
            Country.HAN, RED,
            Country.CHO, BLUE
    );
    private static final Map<Country, String> COUNTRY_NAMES = Map.of(
            Country.CHO, "초나라",
            Country.HAN, "한나라"
    );

    public String convertCountry(final Country country) {
        return COUNTRY_NAMES.get(country);
    }

    public Position parseToPosition(final String value) {
        final String[] xy = value.split(",");
        final PositionFile file = PositionFile.findByAmount(Integer.parseInt(xy[0]));
        final PositionRank rank = PositionRank.findByAmount(Integer.parseInt(xy[1]));
        return new Position(file, rank);
    }

    public String parsePieceOf(final Map<Country, List<Piece>> board, final Position position) {
        final Optional<Piece> piece = getPieceOf(board, position);
        final Optional<Country> country = getCountryOf(board, position);

        if (piece.isEmpty() || country.isEmpty()) {
            return ".\t";
        }
        final String pieceName = piece.get().getPieceType().name();
        return applyColor(pieceName, country.get()) + "\t";
    }

    private static Optional<Piece> getPieceOf(final Map<Country, List<Piece>> board, final Position position) {
        return board.values().stream()
                .flatMap(Collection::stream)
                .filter(piece -> piece.getPosition().equals(position))
                .findAny();
    }

    private static Optional<Country> getCountryOf(final Map<Country, List<Piece>> board, final Position position) {
        return board.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(piece -> piece.getPosition().equals(position)))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private static String applyColor(final String value, final Country country) {
        return COUNTRY_COLORS.get(country) + value + RESET;
    }
}

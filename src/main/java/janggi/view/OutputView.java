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

public class OutputView {

    private static final Map<Country, String> COUNTRY_NAMES = Map.of(
            Country.CHO, "차",
            Country.HAN, "마"
    );

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    private static final Map<Country, String> COUNTRY_COLORS = Map.of(
            Country.HAN, RED,
            Country.CHO, BLUE
    );

    public void printBoard(Map<Country, List<Piece>> board) {
        String[][] output = new String[11][10];

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 10; j++) {
                output[i][j] = "\t";
            }
        }

        for (PositionRank rank : PositionRank.values()) {
            output[rank.amount][0] = rank.amount + "\t";
        }

        for (PositionFile file : PositionFile.values()) {
            output[0][file.amount] = file.amount + "\t";
        }

        for (PositionRank rank : PositionRank.values()) {
            for (PositionFile file : PositionFile.values()) {
                Position position = new Position(file, rank);
                output[rank.amount][file.amount] = getPieceString(getCountryOf(board, position), getPieceOf(board, position));
            }
        }

        for (int i = 10; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                System.out.print(output[i][j]);
            }
            System.out.println();
        }
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

    private String getPieceString(Optional<Country> country, Optional<Piece> piece) {
        if (piece.isEmpty() || country.isEmpty()) {
            return ".\t";
        } else {
            return applyColor(country.get(), piece.get()) + "\t";
        }
    }

    private String applyColor(Country country, Piece piece) {
        return COUNTRY_COLORS.get(country) + piece.getPieceType().name() + RESET;
    }

    public void outputWinner(final Country winner) {
        System.out.println(winner.name() + "승리!!");
    }
}

package piece_initiaizer;

import game.Country;
import game.StartingPosition;
import java.util.HashMap;
import java.util.Map;
import piece.Piece;
import piece.PieceType;
import position.Position;
import position.PositionFile;
import position.PositionRank;

public final class StaticPieceInitializer implements PieceInitializer {

    @Override
    public Map<Position, Piece> init(final StartingPosition startingPosition, final Country country) {
        final Map<Position, Piece> board = new HashMap<>();

        PieceType 졸병 = country == Country.CHO ? PieceType.졸 : PieceType.병;

        insertIntoMap(
                board,
                new Piece(new Position(PositionFile.가, PositionRank.ofEachCountry(1, country)), PieceType.차),
                new Piece(new Position(PositionFile.라, PositionRank.ofEachCountry(1, country)), PieceType.사),
                new Piece(new Position(PositionFile.바, PositionRank.ofEachCountry(1, country)), PieceType.사),
                new Piece(new Position(PositionFile.자, PositionRank.ofEachCountry(1, country)), PieceType.차),
                new Piece(new Position(PositionFile.마, PositionRank.ofEachCountry(2, country)), PieceType.장),
                new Piece(new Position(PositionFile.나, PositionRank.ofEachCountry(3, country)), PieceType.포),
                new Piece(new Position(PositionFile.아, PositionRank.ofEachCountry(3, country)), PieceType.포),
                new Piece(new Position(PositionFile.가, PositionRank.ofEachCountry(4, country)), 졸병),
                new Piece(new Position(PositionFile.다, PositionRank.ofEachCountry(4, country)), 졸병),
                new Piece(new Position(PositionFile.마, PositionRank.ofEachCountry(4, country)), 졸병),
                new Piece(new Position(PositionFile.사, PositionRank.ofEachCountry(4, country)), 졸병),
                new Piece(new Position(PositionFile.자, PositionRank.ofEachCountry(4, country)), 졸병)
        );

        switch (startingPosition) {
            case RIGHT_ELEPHANT_SETUP -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.나, PositionRank.ofEachCountry(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.다, PositionRank.ofEachCountry(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.사, PositionRank.ofEachCountry(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.아, PositionRank.ofEachCountry(1, country)), PieceType.상)
            );
            case LEFT_ELEPHANT_SETUP -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.나, PositionRank.ofEachCountry(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.다, PositionRank.ofEachCountry(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.사, PositionRank.ofEachCountry(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.아, PositionRank.ofEachCountry(1, country)), PieceType.마)
            );
            case OUTER_ELEPHANT_SETUP -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.나, PositionRank.ofEachCountry(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.다, PositionRank.ofEachCountry(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.사, PositionRank.ofEachCountry(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.아, PositionRank.ofEachCountry(1, country)), PieceType.상)
            );
            case INNER_ELEPHANT_SETUP -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.나, PositionRank.ofEachCountry(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.다, PositionRank.ofEachCountry(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.사, PositionRank.ofEachCountry(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.아, PositionRank.ofEachCountry(1, country)), PieceType.마)
            );
        }
        return board;
    }

    private static void insertIntoMap(final Map<Position, Piece> board, final Piece... pieces) {
        for (Piece piece : pieces) {
            board.put(piece.getPosition(), piece);
        }
    }
}

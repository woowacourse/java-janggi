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

        PieceType SOLDIER = country == Country.CHO ? PieceType.CHO_SOLDIER : PieceType.HAN_SOLDIER;

        insertIntoMap(
                board,
                new Piece(new Position(PositionFile.FILE_1, PositionRank.ofEachCountry(1, country)), PieceType.ROOK),
                new Piece(new Position(PositionFile.FILE_4, PositionRank.ofEachCountry(1, country)), PieceType.GUARD),
                new Piece(new Position(PositionFile.FILE_6, PositionRank.ofEachCountry(1, country)), PieceType.GUARD),
                new Piece(new Position(PositionFile.FILE_9, PositionRank.ofEachCountry(1, country)), PieceType.ROOK),
                new Piece(new Position(PositionFile.FILE_5, PositionRank.ofEachCountry(2, country)), PieceType.GENERAL),
                new Piece(new Position(PositionFile.FILE_2, PositionRank.ofEachCountry(3, country)), PieceType.CANNON),
                new Piece(new Position(PositionFile.FILE_8, PositionRank.ofEachCountry(3, country)), PieceType.CANNON),
                new Piece(new Position(PositionFile.FILE_1, PositionRank.ofEachCountry(4, country)), SOLDIER),
                new Piece(new Position(PositionFile.FILE_3, PositionRank.ofEachCountry(4, country)), SOLDIER),
                new Piece(new Position(PositionFile.FILE_5, PositionRank.ofEachCountry(4, country)), SOLDIER),
                new Piece(new Position(PositionFile.FILE_7, PositionRank.ofEachCountry(4, country)), SOLDIER),
                new Piece(new Position(PositionFile.FILE_9, PositionRank.ofEachCountry(4, country)), SOLDIER)
        );

        switch (startingPosition) {
            case RIGHT_ELEPHANT_SETUP -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.FILE_2, PositionRank.ofEachCountry(1, country)),
                            PieceType.HORSE),
                    new Piece(new Position(PositionFile.FILE_3, PositionRank.ofEachCountry(1, country)),
                            PieceType.ELEPHANT),
                    new Piece(new Position(PositionFile.FILE_7, PositionRank.ofEachCountry(1, country)),
                            PieceType.HORSE),
                    new Piece(new Position(PositionFile.FILE_8, PositionRank.ofEachCountry(1, country)),
                            PieceType.ELEPHANT)
            );
            case LEFT_ELEPHANT_SETUP -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.FILE_2, PositionRank.ofEachCountry(1, country)),
                            PieceType.ELEPHANT),
                    new Piece(new Position(PositionFile.FILE_3, PositionRank.ofEachCountry(1, country)),
                            PieceType.HORSE),
                    new Piece(new Position(PositionFile.FILE_7, PositionRank.ofEachCountry(1, country)),
                            PieceType.ELEPHANT),
                    new Piece(new Position(PositionFile.FILE_8, PositionRank.ofEachCountry(1, country)),
                            PieceType.HORSE)
            );
            case OUTER_ELEPHANT_SETUP -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.FILE_2, PositionRank.ofEachCountry(1, country)),
                            PieceType.ELEPHANT),
                    new Piece(new Position(PositionFile.FILE_3, PositionRank.ofEachCountry(1, country)),
                            PieceType.HORSE),
                    new Piece(new Position(PositionFile.FILE_7, PositionRank.ofEachCountry(1, country)),
                            PieceType.HORSE),
                    new Piece(new Position(PositionFile.FILE_8, PositionRank.ofEachCountry(1, country)),
                            PieceType.ELEPHANT)
            );
            case INNER_ELEPHANT_SETUP -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.FILE_2, PositionRank.ofEachCountry(1, country)),
                            PieceType.HORSE),
                    new Piece(new Position(PositionFile.FILE_3, PositionRank.ofEachCountry(1, country)),
                            PieceType.ELEPHANT),
                    new Piece(new Position(PositionFile.FILE_7, PositionRank.ofEachCountry(1, country)),
                            PieceType.ELEPHANT),
                    new Piece(new Position(PositionFile.FILE_8, PositionRank.ofEachCountry(1, country)),
                            PieceType.HORSE)
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

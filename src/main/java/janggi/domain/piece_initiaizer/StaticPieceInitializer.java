package janggi.domain.piece_initiaizer;

import janggi.domain.Country;
import janggi.domain.StartingPosition;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.util.HashMap;
import java.util.Map;

public final class StaticPieceInitializer implements PieceInitializer {

    @Override
    public Map<Position, Piece> init(final StartingPosition startingPosition, final Country country) {
        final Map<Position, Piece> board = new HashMap<>();

        if (country == Country.CHO) {
            insertIntoMap(board,
                    PieceFactory.createJol(new Position(PositionFile.FILE_1, PositionRank.of(4, country))),
                    PieceFactory.createJol(new Position(PositionFile.FILE_3, PositionRank.of(4, country))),
                    PieceFactory.createJol(new Position(PositionFile.FILE_5, PositionRank.of(4, country))),
                    PieceFactory.createJol(new Position(PositionFile.FILE_7, PositionRank.of(4, country))),
                    PieceFactory.createJol(new Position(PositionFile.FILE_9, PositionRank.of(4, country)))
            );
        } else {
            insertIntoMap(board,
                    PieceFactory.createByeong(new Position(PositionFile.FILE_1, PositionRank.of(4, country))),
                    PieceFactory.createByeong(new Position(PositionFile.FILE_3, PositionRank.of(4, country))),
                    PieceFactory.createByeong(new Position(PositionFile.FILE_5, PositionRank.of(4, country))),
                    PieceFactory.createByeong(new Position(PositionFile.FILE_7, PositionRank.of(4, country))),
                    PieceFactory.createByeong(new Position(PositionFile.FILE_9, PositionRank.of(4, country)))
            );
        }

        insertIntoMap(board,
                PieceFactory.createCha(country, new Position(PositionFile.FILE_1, PositionRank.of(1, country))),
                PieceFactory.createCha(country, new Position(PositionFile.FILE_9, PositionRank.of(1, country))),
                PieceFactory.createPo(country, new Position(PositionFile.FILE_2, PositionRank.of(3, country))),
                PieceFactory.createPo(country, new Position(PositionFile.FILE_8, PositionRank.of(3, country))),
                PieceFactory.createSa(country, new Position(PositionFile.FILE_4, PositionRank.of(1, country))),
                PieceFactory.createSa(country, new Position(PositionFile.FILE_6, PositionRank.of(1, country))),
                PieceFactory.createJang(country, new Position(PositionFile.FILE_5, PositionRank.of(2, country)))

        );

        switch (startingPosition) {
            case 마상마상 -> insertIntoMap(
                    board,
                    PieceFactory.createMa(new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    PieceFactory.createSang(new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    PieceFactory.createMa(new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    PieceFactory.createSang(new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
            );
            case 상마상마 -> insertIntoMap(
                    board,
                    PieceFactory.createSang(new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    PieceFactory.createMa(new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    PieceFactory.createSang(new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    PieceFactory.createMa(new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
            );
            case 상마마상 -> insertIntoMap(
                    board,
                    PieceFactory.createSang(new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    PieceFactory.createMa(new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    PieceFactory.createMa(new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    PieceFactory.createSang(new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
            );
            case 마상상마 -> insertIntoMap(
                    board,
                    PieceFactory.createMa(new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    PieceFactory.createSang(new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    PieceFactory.createSang(new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    PieceFactory.createMa(new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
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

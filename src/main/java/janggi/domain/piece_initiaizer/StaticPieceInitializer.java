package janggi.domain.piece_initiaizer;

import janggi.domain.Country;
import janggi.domain.StartingPosition;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.util.HashMap;
import java.util.Map;

public final class StaticPieceInitializer implements PieceInitializer {

    @Override
    public Map<Position, Piece> init(final StartingPosition startingPosition, final Country country) {
        final Map<Position, Piece> board = new HashMap<>();
        insertCountryDependentPieces(country, board);
        insertStartingPositionIndependentPieces(country, board);
        insertStartingPositionDependentPieces(startingPosition, country, board);
        return board;
    }

    private static void insertCountryDependentPieces(final Country country, final Map<Position, Piece> board) {
        if (country == Country.CHO) {
            insertIntoMap(board,
                    new Piece(PieceType.JOL, new Position(PositionFile.FILE_1, PositionRank.of(4, country))),
                    new Piece(PieceType.JOL, new Position(PositionFile.FILE_3, PositionRank.of(4, country))),
                    new Piece(PieceType.JOL, new Position(PositionFile.FILE_5, PositionRank.of(4, country))),
                    new Piece(PieceType.JOL, new Position(PositionFile.FILE_7, PositionRank.of(4, country))),
                    new Piece(PieceType.JOL, new Position(PositionFile.FILE_9, PositionRank.of(4, country)))
            );
            return;
        }
        insertIntoMap(board,
                new Piece(PieceType.BYEONG, new Position(PositionFile.FILE_1, PositionRank.of(4, country))),
                new Piece(PieceType.BYEONG, new Position(PositionFile.FILE_3, PositionRank.of(4, country))),
                new Piece(PieceType.BYEONG, new Position(PositionFile.FILE_5, PositionRank.of(4, country))),
                new Piece(PieceType.BYEONG, new Position(PositionFile.FILE_7, PositionRank.of(4, country))),
                new Piece(PieceType.BYEONG, new Position(PositionFile.FILE_9, PositionRank.of(4, country)))
        );
    }

    private static void insertStartingPositionIndependentPieces(final Country country, final Map<Position, Piece> board) {
        insertIntoMap(board,
                new Piece(PieceType.CHA, new Position(PositionFile.FILE_1, PositionRank.of(1, country))),
                new Piece(PieceType.CHA, new Position(PositionFile.FILE_9, PositionRank.of(1, country))),
                new Piece(PieceType.PO, new Position(PositionFile.FILE_2, PositionRank.of(3, country))),
                new Piece(PieceType.PO, new Position(PositionFile.FILE_8, PositionRank.of(3, country))),
                new Piece(PieceType.SA, new Position(PositionFile.FILE_4, PositionRank.of(1, country))),
                new Piece(PieceType.SA, new Position(PositionFile.FILE_6, PositionRank.of(1, country))),
                new Piece(PieceType.GUNG, new Position(PositionFile.FILE_5, PositionRank.of(2, country)))
        );
    }

    private static void insertStartingPositionDependentPieces(final StartingPosition startingPosition, final Country country, final Map<Position, Piece> board) {
        switch (startingPosition) {
            case MA_SANG_MA_SANG -> insertIntoMap(
                    board,
                    new Piece(PieceType.MA, new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    new Piece(PieceType.SANG, new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    new Piece(PieceType.MA, new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    new Piece(PieceType.SANG, new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
            );
            case SAMG_MA_SANG_MA -> insertIntoMap(
                    board,
                    new Piece(PieceType.SANG, new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    new Piece(PieceType.MA, new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    new Piece(PieceType.SANG, new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    new Piece(PieceType.MA, new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
            );
            case SANG_MA_MA_SANG -> insertIntoMap(
                    board,
                    new Piece(PieceType.SANG, new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    new Piece(PieceType.MA, new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    new Piece(PieceType.MA, new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    new Piece(PieceType.SANG, new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
            );
            case MA_SANG_SANG_MA -> insertIntoMap(
                    board,
                    new Piece(PieceType.MA, new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    new Piece(PieceType.SANG, new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    new Piece(PieceType.SANG, new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    new Piece(PieceType.MA, new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
            );
        }
    }

    private static void insertIntoMap(final Map<Position, Piece> board, final Piece... pieces) {
        for (Piece piece : pieces) {
            board.put(piece.getPosition(), piece);
        }
    }
}

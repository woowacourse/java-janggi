package domain.piece_initiaizer;

import domain.Country;
import domain.StartingPosition;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;
import domain.position.PositionFile;
import domain.position.PositionRank;

import java.util.HashMap;
import java.util.Map;

public final class StaticPieceInitializer implements PieceInitializer {

    @Override
    public Map<Position, Piece> init(final StartingPosition startingPosition, final Country country) {
        final Map<Position, Piece> board = new HashMap<>();

        PieceType 졸병 = country == Country.CHO ? PieceType.졸 : PieceType.병;

        insertIntoMap(
                board,
                new Piece(new Position(PositionFile.FILE_1, PositionRank.of(1, country)), PieceType.차),
                new Piece(new Position(PositionFile.FILE_4, PositionRank.of(1, country)), PieceType.사),
                new Piece(new Position(PositionFile.FILE_6, PositionRank.of(1, country)), PieceType.사),
                new Piece(new Position(PositionFile.FILE_9, PositionRank.of(1, country)), PieceType.차),
                new Piece(new Position(PositionFile.FILE_5, PositionRank.of(2, country)), PieceType.장),
                new Piece(new Position(PositionFile.FILE_2, PositionRank.of(3, country)), PieceType.포),
                new Piece(new Position(PositionFile.FILE_8, PositionRank.of(3, country)), PieceType.포),
                new Piece(new Position(PositionFile.FILE_1, PositionRank.of(4, country)), 졸병),
                new Piece(new Position(PositionFile.FILE_3, PositionRank.of(4, country)), 졸병),
                new Piece(new Position(PositionFile.FILE_5, PositionRank.of(4, country)), 졸병),
                new Piece(new Position(PositionFile.FILE_7, PositionRank.of(4, country)), 졸병),
                new Piece(new Position(PositionFile.FILE_9, PositionRank.of(4, country)), 졸병)
        );

        switch (startingPosition) {
            case 마상마상 -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.FILE_2, PositionRank.of(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.FILE_3, PositionRank.of(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.FILE_7, PositionRank.of(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.FILE_8, PositionRank.of(1, country)), PieceType.상)
            );
            case 상마상마 -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.FILE_2, PositionRank.of(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.FILE_3, PositionRank.of(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.FILE_7, PositionRank.of(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.FILE_8, PositionRank.of(1, country)), PieceType.마)
            );
            case 상마마상 -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.FILE_2, PositionRank.of(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.FILE_3, PositionRank.of(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.FILE_7, PositionRank.of(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.FILE_8, PositionRank.of(1, country)), PieceType.상)
            );
            case 마상상마 -> insertIntoMap(
                    board,
                    new Piece(new Position(PositionFile.FILE_2, PositionRank.of(1, country)), PieceType.마),
                    new Piece(new Position(PositionFile.FILE_3, PositionRank.of(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.FILE_7, PositionRank.of(1, country)), PieceType.상),
                    new Piece(new Position(PositionFile.FILE_8, PositionRank.of(1, country)), PieceType.마)
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

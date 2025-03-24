package janggi.domain.piece_initiaizer;

import janggi.domain.Country;
import janggi.domain.StartingPosition;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Piece;
import janggi.domain.piece.impl.*;
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
                    new Jol(new Position(PositionFile.FILE_1, PositionRank.of(1, country))),
                    new Jol(new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    new Jol(new Position(PositionFile.FILE_5, PositionRank.of(1, country))),
                    new Jol(new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    new Jol(new Position(PositionFile.FILE_9, PositionRank.of(1, country)))
            );
        } else {
            insertIntoMap(board,
                    new Byeong(new Position(PositionFile.FILE_1, PositionRank.of(1, country))),
                    new Byeong(new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    new Byeong(new Position(PositionFile.FILE_5, PositionRank.of(1, country))),
                    new Byeong(new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    new Byeong(new Position(PositionFile.FILE_9, PositionRank.of(1, country)))
            );
        }

        insertIntoMap(board,
                new Jol(new Position(PositionFile.FILE_1, PositionRank.of(1, country))),
                new Sa(new Position(PositionFile.FILE_4, PositionRank.of(1, country)), new Gung()),
                new Sa(new Position(PositionFile.FILE_6, PositionRank.of(1, country)), new Gung()),
                new Cha(new Position(PositionFile.FILE_9, PositionRank.of(1, country))),
                new Jang(new Position(PositionFile.FILE_5, PositionRank.of(2, country)), new Gung()),
                new Po(new Position(PositionFile.FILE_2, PositionRank.of(3, country))),
                new Po(new Position(PositionFile.FILE_8, PositionRank.of(3, country)))
        );

        switch (startingPosition) {
            case 마상마상 -> insertIntoMap(
                    board,
                    new Ma(new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    new Sang(new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    new Ma(new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    new Sang(new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
            );
            case 상마상마 -> insertIntoMap(
                    board,
                    new Sang(new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    new Ma(new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    new Sang(new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    new Ma(new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
            );
            case 상마마상 -> insertIntoMap(
                    board,
                    new Sang(new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    new Ma(new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    new Ma(new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    new Sang(new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
            );
            case 마상상마 -> insertIntoMap(
                    board,
                    new Ma(new Position(PositionFile.FILE_2, PositionRank.of(1, country))),
                    new Sang(new Position(PositionFile.FILE_3, PositionRank.of(1, country))),
                    new Sang(new Position(PositionFile.FILE_7, PositionRank.of(1, country))),
                    new Ma(new Position(PositionFile.FILE_8, PositionRank.of(1, country)))
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

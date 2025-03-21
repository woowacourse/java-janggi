package object.piece;

import java.util.Collections;
import java.util.List;
import object.Coordinate;
import object.strategy.CannonStrategy;
import object.strategy.ChariotStrategy;
import object.strategy.ElephantStrategy;
import object.strategy.GeneralStrategy;
import object.strategy.GuardStrategy;
import object.strategy.HorseStrategy;
import object.strategy.SoldierStrategy;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public static Pieces generateToInitializeFormat() {
        List<Piece> initialPieces = List.of(
                // 졸·병 생성
                new Piece(Team.BLUE, new SoldierStrategy(), new Coordinate(3, 0)),
                new Piece(Team.BLUE, new SoldierStrategy(), new Coordinate(3, 2)),
                new Piece(Team.BLUE, new SoldierStrategy(), new Coordinate(3, 4)),
                new Piece(Team.BLUE, new SoldierStrategy(), new Coordinate(3, 6)),
                new Piece(Team.BLUE, new SoldierStrategy(), new Coordinate(3, 8)),

                new Piece(Team.RED, new SoldierStrategy(), new Coordinate(6, 0)),
                new Piece(Team.RED, new SoldierStrategy(), new Coordinate(6, 2)),
                new Piece(Team.RED, new SoldierStrategy(), new Coordinate(6, 4)),
                new Piece(Team.RED, new SoldierStrategy(), new Coordinate(6, 6)),
                new Piece(Team.RED, new SoldierStrategy(), new Coordinate(6, 8)),

                // 포 생성
                new Piece(Team.BLUE, new CannonStrategy(), new Coordinate(2, 1)),
                new Piece(Team.BLUE, new CannonStrategy(), new Coordinate(2, 7)),

                new Piece(Team.RED, new CannonStrategy(), new Coordinate(7, 1)),
                new Piece(Team.RED, new CannonStrategy(), new Coordinate(7, 7)),

                // 궁 생성
                new Piece(Team.BLUE, new GeneralStrategy(), new Coordinate(1, 4)),
                new Piece(Team.RED, new GeneralStrategy(), new Coordinate(8, 4)),

                // 차 생성
                new Piece(Team.BLUE, new ChariotStrategy(), new Coordinate(0, 0)),
                new Piece(Team.BLUE, new ChariotStrategy(), new Coordinate(0, 8)),

                new Piece(Team.RED, new ChariotStrategy(), new Coordinate(9, 0)),
                new Piece(Team.RED, new ChariotStrategy(), new Coordinate(9, 8)),

                // 마 생성
                new Piece(Team.BLUE, new HorseStrategy(), new Coordinate(0, 2)),
                new Piece(Team.BLUE, new HorseStrategy(), new Coordinate(0, 7)),

                new Piece(Team.RED, new HorseStrategy(), new Coordinate(9, 2)),
                new Piece(Team.RED, new HorseStrategy(), new Coordinate(9, 7)),

                // 상 생성
                new Piece(Team.BLUE, new ElephantStrategy(), new Coordinate(0, 1)),
                new Piece(Team.BLUE, new ElephantStrategy(), new Coordinate(0, 6)),

                new Piece(Team.RED, new ElephantStrategy(), new Coordinate(9, 1)),
                new Piece(Team.RED, new ElephantStrategy(), new Coordinate(9, 6)),

                // 사 생성
                new Piece(Team.BLUE, new GuardStrategy(), new Coordinate(0, 3)),
                new Piece(Team.BLUE, new GuardStrategy(), new Coordinate(0, 5)),

                new Piece(Team.RED, new GuardStrategy(), new Coordinate(9, 3)),
                new Piece(Team.RED, new GuardStrategy(), new Coordinate(9, 5))
        );

        return new Pieces(initialPieces);
    }

    public void killPieceFrom(Piece killerPiece) {
        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            if (killerPiece.isSamePosition(piece) && !killerPiece.isSameTeam(piece)) {
                pieces.remove(i);
                return;
            }
        }
    }

    public int size() {
        return pieces.size();
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

    public Piece getFirstPiece() {
        return pieces.getFirst();
    }

    public Piece getLastPiece() {
        return pieces.getLast();
    }
}

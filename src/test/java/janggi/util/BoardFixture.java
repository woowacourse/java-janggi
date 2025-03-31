package janggi.util;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.factory.PieceInitFactory;
import janggi.factory.horse_elephant.HorseElephantFactory;
import janggi.view.HorseElephantPosition;
import java.util.Map;

public final class BoardFixture {

    private BoardFixture() {
    }

    public static Board sangMaSangMa() {
        Map<Position, Piece> initialize = PieceInitFactory.initialize();
        Map<Position, Piece> maSangByHan = HorseElephantFactory.create(HorseElephantPosition.ELEPHANT_HORSE_ELEPHANT_HORSE, Team.HAN);
        Map<Position, Piece> maSangByCho = HorseElephantFactory.create(HorseElephantPosition.ELEPHANT_HORSE_ELEPHANT_HORSE, Team.CHO);

        initialize.putAll(maSangByCho);
        initialize.putAll(maSangByHan);

        return new Board(initialize);
    }
}

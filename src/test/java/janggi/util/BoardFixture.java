package janggi.util;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.factory.PieceInitFactory;
import janggi.factory.masang.MaSangFactory;
import janggi.view.MaSangPosition;
import java.util.Map;

public final class BoardFixture {

    private BoardFixture() {
    }

    public static Board sangMaSangMa() {
        Map<Position, Piece> initialize = PieceInitFactory.initialize();
        Map<Position, Piece> maSangByHan = MaSangFactory.create(MaSangPosition.SANG_MA_SANG_MA, Side.HAN);
        Map<Position, Piece> maSangByCho = MaSangFactory.create(MaSangPosition.SANG_MA_SANG_MA, Side.CHO);

        initialize.putAll(maSangByCho);
        initialize.putAll(maSangByHan);

        return new Board(initialize);
    }
}

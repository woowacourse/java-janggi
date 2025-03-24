package janggi.factory.masang;

import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.view.MaSangPosition;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public enum MaSangFactory {
    SANG_MA_SANG_MA(MaSangPosition.SANG_MA_SANG_MA, SangMaSangMa::new),
    MA_SANG_MA_SANG(MaSangPosition.MA_SANG_MA_SANG, MaSangMaSang::new),
    MA_SANG_SANG_MA(MaSangPosition.MA_SANG_SANG_MA, MaSangSangMa::new),
    SANG_MA_MA_SANG(MaSangPosition.SANG_MA_MA_SANG, SangMaMaSang::new);

    private final MaSangPosition maSangPosition;
    private final Supplier<MaSangPlacement> supplier;

    MaSangFactory(MaSangPosition maSangPosition, Supplier<MaSangPlacement> supplier) {
        this.maSangPosition = maSangPosition;
        this.supplier = supplier;
    }

    public static Map<Position, Piece> create(MaSangPosition maSangPosition, Side side) {
        return Arrays.stream(values())
                .filter(value -> value.maSangPosition.equals(maSangPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 포지션입니다."))
                .generate(side);
    }

    private Map<Position, Piece> generate(Side side) {
        return supplier.get().generate(side);
    }
}

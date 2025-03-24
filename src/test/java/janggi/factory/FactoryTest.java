package janggi.factory;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.factory.masang.MaSangFactory;
import janggi.view.MaSangPosition;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FactoryTest {

    @DisplayName("초기화된 기물은 32개가 나와야한다")
    @Test
    void test1() {
        for (MaSangPosition maSangPositionByCho : MaSangPosition.values()) {
            for (MaSangPosition maSangPositionByHan : MaSangPosition.values()) {
                Map<Position, Piece> initializeBoard = PieceInitFactory.initialize();

                initializeBoard.putAll(MaSangFactory.create(maSangPositionByCho, Side.CHO));
                initializeBoard.putAll(MaSangFactory.create(maSangPositionByHan, Side.HAN));

                assertThat(initializeBoard).hasSize(32);
            }
        }

    }
}

package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Dynasty;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.ChuPawn;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.HanPawn;
import janggi.domain.piece.King;
import janggi.domain.piece.Knight;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Rook;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiBoardTest {

    @DisplayName("장기판을 상차림에 따라 초기화 할 수 있다.")
    @Test
    void initializeBoard() {
        //given
        HanBoardSetUp hanBoardSetUp = HanBoardSetUp.LEFT_ELEPHANT;
        ChuBoardSetUp chuBoardSetUp = ChuBoardSetUp.OUTER_ELEPHANT;

        // when
        JanggiBoard janggiBoard = JanggiBoard.of(hanBoardSetUp, chuBoardSetUp);

        // then
        Map<Point, Piece> pieces = new HashMap<>() {
            {
                put(new Point(1, 1), new Rook(Dynasty.HAN));
                put(new Point(1, 2), new Elephant(Dynasty.HAN));
                put(new Point(1, 3), new Knight(Dynasty.HAN));
                put(new Point(1, 4), new Guard(Dynasty.HAN));
                put(new Point(1, 6), new Guard(Dynasty.HAN));
                put(new Point(1, 7), new Elephant(Dynasty.HAN));
                put(new Point(1, 8), new Knight(Dynasty.HAN));
                put(new Point(1, 9), new Rook(Dynasty.HAN));
                put(new Point(2, 5), new King(Dynasty.HAN));
                put(new Point(3, 2), new Cannon(Dynasty.HAN));
                put(new Point(3, 8), new Cannon(Dynasty.HAN));
                put(new Point(4, 1), new HanPawn());
                put(new Point(4, 3), new HanPawn());
                put(new Point(4, 5), new HanPawn());
                put(new Point(4, 7), new HanPawn());
                put(new Point(4, 9), new HanPawn());

                put(new Point(10, 1), new Rook(Dynasty.CHU));
                put(new Point(10, 2), new Elephant(Dynasty.CHU));
                put(new Point(10, 3), new Knight(Dynasty.CHU));
                put(new Point(10, 4), new Guard(Dynasty.CHU));
                put(new Point(10, 6), new Guard(Dynasty.CHU));
                put(new Point(10, 7), new Knight(Dynasty.CHU));
                put(new Point(10, 8), new Elephant(Dynasty.CHU));
                put(new Point(10, 9), new Rook(Dynasty.CHU));
                put(new Point(9, 5), new King(Dynasty.CHU));
                put(new Point(8, 2), new Cannon(Dynasty.CHU));
                put(new Point(8, 8), new Cannon(Dynasty.CHU));
                put(new Point(7, 1), new ChuPawn());
                put(new Point(7, 3), new ChuPawn());
                put(new Point(7, 5), new ChuPawn());
                put(new Point(7, 7), new ChuPawn());
                put(new Point(7, 9), new ChuPawn());
            }
        };
        assertThat(janggiBoard)
                .isEqualTo(new JanggiBoard(pieces));
    }
}

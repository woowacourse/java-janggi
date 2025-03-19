package domain.board;

import static domain.piece.Country.CHO;
import static domain.piece.Country.HAN;

import domain.JanggiCoordinate;
import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Gung;
import domain.piece.Ma;
import domain.piece.Pho;
import domain.piece.Piece;
import domain.piece.Sa;
import domain.piece.Sang;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum JanggiBoardInitPosition {
    CHO_LEFT_MA(new JanggiCoordinate(3, 10), new Ma(CHO)),
    CHO_RIGHT_MA(new JanggiCoordinate(7, 10), new Ma(CHO)),
    CHO_LEFT_CHA(new JanggiCoordinate(1, 10), new Cha(CHO)),
    CHO_RIGHT_CHA(new JanggiCoordinate(9, 10), new Cha(CHO)),
    CHO_LEFT_SANG(new JanggiCoordinate(2, 10), new Sang(CHO)),
    CHO_RIGHT_SANG(new JanggiCoordinate(8, 10), new Sang(CHO)),
    CHO_LEFT_PHO(new JanggiCoordinate(2, 8), new Pho(CHO)),
    CHO_RIGHT_PHO(new JanggiCoordinate(8, 8), new Pho(CHO)),
    CHO_LEFT_SA(new JanggiCoordinate(4, 10), new Sa(CHO)),
    CHO_RIGHT_SA(new JanggiCoordinate(6, 10), new Sa(CHO)),
    CHO_BYEONG_1(new JanggiCoordinate(1, 7), new Byeong(CHO)),
    CHO_BYEONG_3(new JanggiCoordinate(3, 7), new Byeong(CHO)),
    CHO_BYEONG_5(new JanggiCoordinate(5, 7), new Byeong(CHO)),
    CHO_BYEONG_7(new JanggiCoordinate(7, 7), new Byeong(CHO)),
    CHO_BYEONG_9(new JanggiCoordinate(9, 7), new Byeong(CHO)),
    CHO_GUNG(new JanggiCoordinate(5, 9), new Gung(CHO)),

    HAN_LEFT_MA(new JanggiCoordinate(2, 1), new Ma(HAN)),
    HAN_RIGHT_MA(new JanggiCoordinate(8, 1), new Ma(HAN)),
    HAN_LEFT_CHA(new JanggiCoordinate(1, 1), new Cha(HAN)),
    HAN_RIGHT_CHA(new JanggiCoordinate(9, 1), new Cha(HAN)),
    HAN_LEFT_SANG(new JanggiCoordinate(3, 1), new Sang(HAN)),
    HAN_RIGHT_SANG(new JanggiCoordinate(7, 1), new Sang(HAN)),
    HAN_LEFT_PHO(new JanggiCoordinate(2, 3), new Pho(HAN)),
    HAN_RIGHT_PHO(new JanggiCoordinate(8, 3), new Pho(HAN)),
    HAN_LEFT_SA(new JanggiCoordinate(4, 1), new Sa(HAN)),
    HAN_RIGHT_SA(new JanggiCoordinate(6, 1), new Sa(HAN)),
    HAN_BYEONG_1(new JanggiCoordinate(1, 4), new Byeong(HAN)),
    HAN_BYEONG_3(new JanggiCoordinate(3, 4), new Byeong(HAN)),
    HAN_BYEONG_5(new JanggiCoordinate(5, 4), new Byeong(HAN)),
    HAN_BYEONG_7(new JanggiCoordinate(7, 4), new Byeong(HAN)),
    HAN_BYEONG_9(new JanggiCoordinate(9, 4), new Byeong(HAN)),
    HAN_GUNG(new JanggiCoordinate(5, 2), new Gung(HAN));

    private JanggiCoordinate coordinate;
    private Piece piece;

    JanggiBoardInitPosition(JanggiCoordinate coordinate, Piece piece) {
        this.coordinate = coordinate;
        this.piece = piece;
    }

    public static Map<JanggiCoordinate, Piece> create() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(JanggiBoardInitPosition::getCoordinate,
                        JanggiBoardInitPosition::getPiece)
                );
    }

    public Piece getPiece() {
        return piece;
    }

    public JanggiCoordinate getCoordinate() {
        return coordinate;
    }
}

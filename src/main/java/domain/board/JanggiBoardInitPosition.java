package domain.board;

import static domain.piece.Country.CHO;
import static domain.piece.Country.HAN;

import domain.Coordinate;
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

    CHO_LEFT_MA(new Coordinate(10, 3), new Ma(CHO)),
    CHO_RIGHT_MA(new Coordinate(10, 7), new Ma(CHO)),
    CHO_LEFT_CHA(new Coordinate(10, 1), new Cha(CHO)),
    CHO_RIGHT_CHA(new Coordinate(10, 9), new Cha(CHO)),
    CHO_LEFT_SANG(new Coordinate(10, 2), new Sang(CHO)),
    CHO_RIGHT_SANG(new Coordinate(10, 8), new Sang(CHO)),
    CHO_LEFT_PHO(new Coordinate(8, 2), new Pho(CHO)),
    CHO_RIGHT_PHO(new Coordinate(8, 8), new Pho(CHO)),
    CHO_LEFT_SA(new Coordinate(10, 4), new Sa(CHO)),
    CHO_RIGHT_SA(new Coordinate(10, 6), new Sa(CHO)),
    CHO_BYEONG_1(new Coordinate(7, 1), new Byeong(CHO)),
    CHO_BYEONG_3(new Coordinate(7, 3), new Byeong(CHO)),
    CHO_BYEONG_5(new Coordinate(7, 5), new Byeong(CHO)),
    CHO_BYEONG_7(new Coordinate(7, 7), new Byeong(CHO)),
    CHO_BYEONG_9(new Coordinate(7, 9), new Byeong(CHO)),
    CHO_GUNG(new Coordinate(9, 5), new Gung(CHO)),

    HAN_LEFT_MA(new Coordinate(1, 2), new Ma(HAN)),
    HAN_RIGHT_MA(new Coordinate(1, 8), new Ma(HAN)),
    HAN_LEFT_CHA(new Coordinate(1, 1), new Cha(HAN)),
    HAN_RIGHT_CHA(new Coordinate(1, 9), new Cha(HAN)),
    HAN_LEFT_SANG(new Coordinate(1, 3), new Sang(HAN)),
    HAN_RIGHT_SANG(new Coordinate(1, 7), new Sang(HAN)),
    HAN_LEFT_PHO(new Coordinate(3, 2), new Pho(HAN)),
    HAN_RIGHT_PHO(new Coordinate(3, 8), new Pho(HAN)),
    HAN_LEFT_SA(new Coordinate(1, 4), new Sa(HAN)),
    HAN_RIGHT_SA(new Coordinate(1, 6), new Sa(HAN)),
    HAN_BYEONG_1(new Coordinate(4, 1), new Byeong(HAN)),
    HAN_BYEONG_3(new Coordinate(4, 3), new Byeong(HAN)),
    HAN_BYEONG_5(new Coordinate(4, 5), new Byeong(HAN)),
    HAN_BYEONG_7(new Coordinate(4, 7), new Byeong(HAN)),
    HAN_BYEONG_9(new Coordinate(4, 9), new Byeong(HAN)),
    HAN_GUNG(new Coordinate(2, 5), new Gung(HAN));

    private Coordinate coordinate;
    private Piece piece;

    JanggiBoardInitPosition(Coordinate coordinate, Piece piece) {
        this.coordinate = coordinate;
        this.piece = piece;
    }

    public static Map<Coordinate, Piece> create() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(JanggiBoardInitPosition::getCoordinate,
                        JanggiBoardInitPosition::getPiece)
                );
    }

    public Piece getPiece() {
        return piece;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}

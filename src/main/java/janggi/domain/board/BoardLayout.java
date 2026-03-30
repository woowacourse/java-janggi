package janggi.domain.board;

class BoardLayout {
    // 기물별 열(Column) 위치
    static final int CHARIOT_LEFT = 0;
    static final int CHARIOT_RIGHT = 8;
    static final int ELEPHANT_LEFT = 1;
    static final int ELEPHANT_RIGHT = 7;
    static final int HORSE_LEFT = 2;
    static final int HORSE_RIGHT = 6;
    static final int GUARD_LEFT = 3;
    static final int GUARD_RIGHT = 5;
    static final int PALACE_COL = 4;

    // 진영별 행(Row) 위치
    // 한(HAN) 진영
    static final int HAN_BASE_ROW = 0;
    static final int HAN_PALACE_ROW = 1;
    static final int HAN_CANNON_ROW = 2;
    static final int HAN_SOLDIER_ROW = 3;

    // 초(CHO) 진영
    static final int CHO_BASE_ROW = 9;
    static final int CHO_PALACE_ROW = 8;
    static final int CHO_CANNON_ROW = 7;
    static final int CHO_SOLDIER_ROW = 6;

    // 배치 규칙 관련
    static final int SOLDIER_TOTAL_COUNT = 5;
    static final int SOLDIER_INTERVAL = 2;
    static final String ID_FIRST = "0";
    static final String ID_SECOND = "1";

    private BoardLayout() {
    }
}

package validator;

import position.Position;

public interface PalaceCheckable {

    // TODO 2025. 3. 30. 12:29: 하드 코딩 수정
    // TODO 2025. 3. 30. 12:29: - country에 따라 궁성 위치 조정됨을 처리
    int xMinRange = 4;
    int xMaxRange = 6;

    int y1MinRange = 8;
    int y1MaxRange = 10;
    int y2MinRange = 1;
    int y2MaxRange = 3;

    default void validateBound(Position dest) {
        int destX = dest.x();
        int destY = dest.y();

        if (xMinRange <= destX && destX <= xMaxRange) { // TODO 2025. 3. 30. 12:32: 조건문 하드코딩 처리
            if ((y1MinRange <= destY && destY <= y1MaxRange) || (y2MinRange <= destY && destY <= y2MaxRange)) {
                return ;
            }
        }
        throw new IllegalArgumentException("dest는 궁성 밖으로 나갈 수 없습니다.");
    }
}

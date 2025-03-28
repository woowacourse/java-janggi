package board;

import piece.Country;
import piece.Piece;
import position.PieceInitialPosition;
import position.Position;
import position.LineDirection;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public Board generateBoard(Country designatedCountry, LineDirection designatedLineDirection) {
        final Map<Position, Piece> initMap = new HashMap<>();

        // TODO 2025. 3. 27. 13:38: 뭔가 바로 아래줄이 연결되어 있지 않은 듯 함.
        // TODO 2025. 3. 27. 13:39: 만약 한줄을 안 쓰면, 컴파일 에러는 뜨지 않는데 나중에 그냥 null로 들어감
        Country.assignDirection(designatedCountry, designatedLineDirection);
        for (final Country country : Country.values()) {
            for (PieceInitialPosition pieceType : PieceInitialPosition.values()) {
                Map<Position, Piece> absolutePositions1 = pieceType.getAbsolutePositions(country);
                initMap.putAll(absolutePositions1);
            }
        }
        return new Board(initMap);
    }
}

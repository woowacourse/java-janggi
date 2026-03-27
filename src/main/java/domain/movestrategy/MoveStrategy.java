package domain.movestrategy;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

// TODO: 공통으로 마지막 목적지에 아군이면 빼고, 적군이면 포함 처리
// 할지말지: Map<> 넘기지 말고 Board(조회용으로)로 넘기도록 리팩터링 후, inBoard도 Board에서 처리하도록
public interface MoveStrategy {

    List<Position> calculateMovablePositions(Position from, Map<Position, Piece> pieces);
}

package domain.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.PieceFinder;
import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;
import domain.Position;

public class Po extends Piece {

    public Po(Country country) {
        super(country, PieceType.PO);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        return start.getX() == end.getX() || start.getY() == end.getY();
    }

    @Override
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        // TODO: 차의 규칙 더하기 필요
        List<Position> availableRoute = new ArrayList<>();
        for (Direction direction : Direction.getCardinalDirections()){
            int i = Position.MAX_ROW;
            Position now = start;
            while (i-- > 0) {
                Optional<Position> position = move(now, direction);
                if (position.isEmpty()) {
                    break;
                }
                Piece endPiece = finder.find(position.get());
                if (endPiece==None.INSTANCE) {
                    availableRoute.add(position.get());
                    now = position.get();
                    continue;
                }
                if (isDifferentCountry(endPiece.getCountry())) {
                    availableRoute.add(position.get());
                    break;
                }
            }

        }
        return availableRoute;
    }

//    @Override
//    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
//        // 중간에 기물 하나인지 && 넘는게 포인지
//        if (countSameLine(pieces) != 1) {
//            return false;
//        }
//
//        // 도착 기물이 포인지
//        return !endPieceType.equals(PieceType.PO);
//    }
//
//    private int countSameLine(List<Piece> pieces) {
//        int pieceCount = 0;
//        for (Piece piece : pieces) {
//            if (piece.getPieceType().equals(PieceType.PO)) {
//                throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
//            }
//
//            if (!piece.equals(None.INSTANCE)) {
//                pieceCount++;
//            }
//        }
//
//        return pieceCount;
//    }
}

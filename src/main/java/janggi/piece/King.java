package janggi.piece;

import janggi.movement.Direction;
import janggi.movement.Movement;
import janggi.position.Position;

import java.util.List;
import java.util.Optional;

public class King implements Piece {

    private final Movement movement = Movement.KING;
    private final Position position;

    // 장기판 초기화할 때 시작 위치
    public King(Position position) {
        this.position = position;
    }

    //user가 선택한 위치를 반영해 실제로 이동
    @Override
    public void move() {

    }

    //position에 가능한 movement를 적용한 리스트 반환
    @Override
    public List<Position> checkPossibleMoves() {
       return movement.getDirections()
               .stream()
               .map(direction -> checkOutOfBoundsPosition(direction.plusOffsetToPosition(position)))
               .flatMap(Optional::stream)
               .toList();
    }
}

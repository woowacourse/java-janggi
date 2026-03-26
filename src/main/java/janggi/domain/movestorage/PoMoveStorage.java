package janggi.domain.movestorage;

import janggi.domain.BoardState;
import janggi.domain.Position;

public class PoMoveStorage implements MoveStorage{
    // TODO: 이동 규칙 구현 (2026. 3. 26.)
    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        return true;
    }
}

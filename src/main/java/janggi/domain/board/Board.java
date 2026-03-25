package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(BoardDesignPolicyImpl boardDesignPolicyImpl) {
        this.board = boardDesignPolicyImpl.initBoard();
    }

}

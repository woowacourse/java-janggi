package janggi.piece;

import janggi.dto.BoardPieceDto;

import java.util.function.Function;

public enum PieceType {
    CHARIOT(Chariot::new,13),
    CANNON(Cannon::new,7),
    HORSE(Horse::new,5),
    ELEPHANT(Elephant::new,3),
    GUARD(Guard::new,3),
    SOLDIER(Soldier::new ,2),
    KING(King::new,0);

    private final Function<BoardPieceDto, Piece> instance;
    private final int score;

    PieceType(Function<BoardPieceDto, Piece> instance, int score) {
        this.instance = instance;
        this.score = score;
    }

    public Piece createInstance(BoardPieceDto boardPieceDto) {
        return instance.apply(boardPieceDto);
    }

    public int getScore() {
        return score;
    }
}

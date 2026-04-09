package janggi.domain.piece;

import janggi.domain.team.TeamType;
import java.util.function.Function;

public enum PieceType {

    GENERAL(General::new, 0D),
    GUARD(Guard::new, 3D),
    HORSE(Horse::new, 5D),
    ELEPHANT(Elephant::new, 3D),
    CHARIOT(Chariot::new, 13D),
    CANNON(Cannon::new, 7D),
    SOLDIER(Soldier::new, 2D);

    private final Function<TeamType, Piece> pieceMapper;
    private final double score;

    PieceType(final Function<TeamType, Piece> pieceMapper, final double score) {
        this.pieceMapper = pieceMapper;
        this.score = score;
    }

    public Piece toPiece(final TeamType teamType) {
        return pieceMapper.apply(teamType);
    }

    public double getScore() {
        return score;
    }

}

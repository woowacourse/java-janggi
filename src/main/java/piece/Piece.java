package piece;

import board.Board;
import coordinate.Coordinate;
import java.util.Set;
import team.Team;

public abstract class Piece {

    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public final boolean canMove(Board board, Coordinate departure, coordinate.Coordinate arrival) {
        if (!findMovableCandidates(departure).contains(arrival)) {
            return false;
        }
        if (!canMoveConsideringObstacles(board, departure, arrival)) {
            return false;
        }
        return true;
    }

    protected abstract Set<coordinate.Coordinate> findMovableCandidates(coordinate.Coordinate departure);

    protected abstract boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival);

    protected abstract Set<coordinate.Coordinate> findPaths(coordinate.Coordinate departure, Coordinate arrival);

    public final boolean isSameTeam(Piece piece) {
        return piece.team.equals(this.team);
    }

    public boolean isPo() {
        return this.getClass() == Type.PO.piece;
    }

    public Team getTeam() {
        return team;
    }

    public enum Type {
        CHA(Cha.class),
        MA(Ma.class),
        SANG(Sang.class),
        SA(Sa.class),
        GOONG(Goong.class),
        PO(Po.class),
        JOL(Jol.class),
        ;

        private final Class<? extends Piece> piece;

        Type(Class<? extends Piece> piece) {
            this.piece = piece;
        }

        public static Type getType(Piece piece) {
            for (Type type : values()) {
                if (piece.getClass() == type.piece) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Piece에 등록되지 않은 객체입니다.");
        }
    }
}

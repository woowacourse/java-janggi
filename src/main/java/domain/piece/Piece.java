package domain.piece;

import domain.Coordinate;
import domain.Team;
import domain.board.PieceFinder;
import java.util.List;
import java.util.Set;

public abstract class Piece {

    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public final boolean canMove(PieceFinder pieceFinder, Coordinate departure, Coordinate arrival) {
        if (!findMovableCandidates(departure).contains(arrival)) {
            return false;
        }
        return canMoveConsideringObstacles(pieceFinder, departure, arrival);
    }

    protected abstract Set<Coordinate> findMovableCandidates(Coordinate departure);

    protected boolean canMoveConsideringObstacles(PieceFinder pieceFinder, Coordinate departure, Coordinate arrival) {
        final var path = findPaths(departure, arrival);
        return pieceFinder.nonePiecesIn(path);
    }

    protected abstract List<Coordinate> findPaths(Coordinate departure, Coordinate arrival);

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

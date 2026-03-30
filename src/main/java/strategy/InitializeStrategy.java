package strategy;

import domain.piece.Cannon;
import domain.piece.Guard;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.Position;
import domain.Team;
import domain.piece.Rook;
import java.util.HashMap;
import java.util.Map;

public abstract class InitializeStrategy {
    public Map<Position, Piece> initialize(Team team) {
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.putAll(initializeDefaultFormation(team));
        pieces.putAll(initializeElephantHorseFormation(team));

        return pieces;
    }

    private Map<Position, Piece> initializeDefaultFormation(Team team) {
        if (team == Team.CHO) {
            return initializeChoDefaultFormation();
        }

        return initializeHanDefaultFormation();
    }

    /**
     * 팀에 따라 상/마의 초기 배치를 생성한다.
     *
     * 기본 기물 배치는 InitializeStrategy에서 공통으로 처리되며,
     * 각 구현체는 상/마 배치 전략만 정의한다.
     *
     * @param team 배치를 생성할 팀
     * @return 해당 팀의 상/마 배치 정보
     */
    protected abstract Map<Position, Piece> initializeElephantHorseFormation(Team team);

    /**
     * 한나라의 상/마를 제외한 초기 배치를 생성한다.
     *
     * 기본 기물 배치를 처리하며,
     * 상/마에 대한 배치는 처리하지 않는다.
     *
     * @return 한나라의 기본 기물 배치 정보
     */
    private Map<Position, Piece> initializeHanDefaultFormation() {
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(Position.from(1, 1), new Rook(Team.HAN));
        pieces.put(Position.from(1, 4), new Guard(Team.HAN));
        pieces.put(Position.from(1, 5), new King(Team.HAN));
        pieces.put(Position.from(1, 6), new Guard(Team.HAN));
        pieces.put(Position.from(1, 9), new Rook(Team.HAN));

        pieces.put(Position.from(3, 2), new Cannon(Team.HAN));
        pieces.put(Position.from(3, 8), new Cannon(Team.HAN));

        pieces.put(Position.from(4, 1), new Pawn(Team.HAN));
        pieces.put(Position.from(4, 3), new Pawn(Team.HAN));
        pieces.put(Position.from(4, 5), new Pawn(Team.HAN));
        pieces.put(Position.from(4, 7), new Pawn(Team.HAN));
        pieces.put(Position.from(4, 9), new Pawn(Team.HAN));

        return pieces;
    }

    /**
     * 초나라의 상/마를 제외한 초기 배치를 생성한다.
     *
     * 기본 기물 배치를 처리하며,
     * 상/마에 대한 배치는 처리하지 않는다.
     *
     * @return 초나라의 기본 기물 배치 정보
     */
    private Map<Position, Piece> initializeChoDefaultFormation() {
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(Position.from(10, 1), new Rook(Team.CHO));
        pieces.put(Position.from(10, 4), new Guard(Team.CHO));
        pieces.put(Position.from(10, 5), new King(Team.CHO));
        pieces.put(Position.from(10, 6), new Guard(Team.CHO));
        pieces.put(Position.from(10, 9), new Rook(Team.CHO));

        pieces.put(Position.from(8, 2), new Cannon(Team.CHO));
        pieces.put(Position.from(8, 8), new Cannon(Team.CHO));

        pieces.put(Position.from(7, 1), new Pawn(Team.CHO));
        pieces.put(Position.from(7, 3), new Pawn(Team.CHO));
        pieces.put(Position.from(7, 5), new Pawn(Team.CHO));
        pieces.put(Position.from(7, 9), new Pawn(Team.CHO));
        pieces.put(Position.from(7, 7), new Pawn(Team.CHO));

        return pieces;
    }
}

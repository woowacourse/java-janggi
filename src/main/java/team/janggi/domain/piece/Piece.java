package team.janggi.domain.piece;

import java.util.Objects;
import team.janggi.domain.Palace;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.BoardStateReader;
import team.janggi.domain.piece.strategy.CannonPalaceMoveStrategy;
import team.janggi.domain.piece.strategy.ChariotPalaceMoveStrategy;
import team.janggi.domain.piece.strategy.ElephantMoveStrategy;
import team.janggi.domain.piece.strategy.EmptyMoveStrategy;
import team.janggi.domain.piece.strategy.HorseMoveStrategy;
import team.janggi.domain.piece.strategy.MoveStrategy;
import team.janggi.domain.piece.strategy.RoyalPieceMoveStrategy;
import team.janggi.domain.piece.strategy.SoldierPalaceMoveStrategy;

public class Piece {
    private final Team team;
    private final PieceType pieceType;
    private final MoveStrategy moveStrategy;

    public Piece(Team team, PieceType pieceType, MoveStrategy moveStrategy) {
        this.team = team;
        this.pieceType = pieceType;
        this.moveStrategy = moveStrategy;
    }


    public final static Piece EMPTY_PIECE = new Piece(Team.NONE, PieceType.EMPTY, EmptyMoveStrategy.instance);

    public static Piece of(PieceType pieceType, Team team) {
        if (pieceType == PieceType.EMPTY) {
            return EMPTY_PIECE;
        }
        if (pieceType == PieceType.SOLDIER) {
            return new Piece(team, PieceType.SOLDIER, getSoldierMoveStrategyByTeam(team));
        }
        if (pieceType == PieceType.KING) {
            return new Piece(team, PieceType.KING, getRoyalMoveStrategy(team));
        }
        if (pieceType == PieceType.GUARD) {
            return new Piece(team, PieceType.GUARD, getRoyalMoveStrategy(team));
        }
        if (pieceType == PieceType.ELEPHANT) {
            return new Piece(team, PieceType.ELEPHANT, ElephantMoveStrategy.instance);
        }
        if (pieceType == PieceType.HORSE) {
            return new Piece(team, PieceType.HORSE, HorseMoveStrategy.instance);
        }
        if (pieceType == PieceType.CHARIOT) {
            return new Piece(team, PieceType.CHARIOT, ChariotPalaceMoveStrategy.instance);
        }
        if (pieceType == PieceType.CANNON) {
            return new Piece(team, PieceType.CANNON, CannonPalaceMoveStrategy.instance);
        }

        throw new IllegalArgumentException("지원하지 않는 기물 타입입니다: " + pieceType);
    }

    private static MoveStrategy getSoldierMoveStrategyByTeam(Team team) {
        if (team == Team.CHO) {
            return SoldierPalaceMoveStrategy.towardTopInstance;
        }
        if (team == Team.HAN) {
            return SoldierPalaceMoveStrategy.towardBottomInstance;
        }
        throw new IllegalArgumentException("졸은 초 또는 한 팀에 속해야 합니다.");
    }

    private static MoveStrategy getRoyalMoveStrategy(Team team) {
        if (team == Team.CHO) {
            return new RoyalPieceMoveStrategy(Palace.getChoPalaceInfo());
        }
        if (team == Team.HAN) {
            return new RoyalPieceMoveStrategy(Palace.getHanPalaceInfo());
        }
        throw new IllegalArgumentException("왕은 초 또는 한 팀에 속해야 합니다.");
    }

    public boolean canMove(Position from,
                           Position to,
                           BoardStateReader mapStatus) {

        return moveStrategy.calculateMove(from, to, mapStatus);
    }

    public boolean isSameTeam(Piece otherPiece) {
        return isSameTeam(otherPiece.team);
    }

    public boolean isSameTeam(Team otherTeam) {
        return this.team == otherTeam;
    }

    public boolean isPieceType(PieceType otherPieceType) {
        return this.pieceType == otherPieceType;
    }

    public int getScore() {
        return pieceType.getScore();
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // 클래스 타입이 정확히 일치하는지 확인 (상속 관계에서의 비교 방지)
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, getClass());
    }
}

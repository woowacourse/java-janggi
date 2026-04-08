package domain.piece;

import domain.game.Team;
import domain.rule.StraightLineRule;
import java.util.List;

public class Cannon extends Piece {
    private static final double SCORE = 7.0;

    public Cannon(Team team) {
        super(team, new StraightLineRule());
    }

    @Override
    public double score() {
        return SCORE;
    }

    @Override
    public boolean canBeTargetedByCannon() {
        return false;
    }

    @Override
    public boolean canBeJumpedByCannon() {
        return false;
    }

    @Override
    protected void validateRoute(List<Piece> piecesOnRoute) {
        validateNoCannon(piecesOnRoute);
        Long jumpCount = countJumpPieces(piecesOnRoute);
        if (jumpCount != 1) {
            throw new IllegalArgumentException("포가 넘을 수 있는 기물의 개수는 하나입니다.");
        }
    }

    private void validateNoCannon(List<Piece> piecesOnRoute) {
        boolean hasCannonOnRoute = piecesOnRoute.stream()
                .anyMatch(piece -> !piece.canBeJumpedByCannon());
        if (hasCannonOnRoute) {
            throw new IllegalArgumentException("포는 포를 넘지 못합니다.");
        }
    }

    private Long countJumpPieces(List<Piece> piecesOnRoute) {
        return piecesOnRoute.stream()
                .filter(Piece::isNotEmpty)
                .count();
    }

    @Override
    protected void validateDestination(Piece destinationPiece) {
        if (!destinationPiece.canBeTargetedByCannon()) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
        if (destinationPiece.isAlly(this)) {
            throw new IllegalArgumentException("아군 기물이 있는 위치로 이동할 수 없습니다.");
        }
    }
}

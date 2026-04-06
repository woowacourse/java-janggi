package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Palace;
import janggi.domain.Position;
import janggi.domain.movepath.MovePathStrategy;
import janggi.domain.team.TeamType;
import java.util.Optional;

public class Gung implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final Palace palace;

    public Gung(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.GUNG;
        palace = new Palace();
    }

    @Override
    public boolean isValidMovePattern(int startX, int startY, int endX, int endY) {
        return findMovePath(startX, startY, endX, endY).isPresent();
    }

    @Override
    public Optional<MovePathStrategy> findMovePath(int startX, int startY, int endX, int endY) {
        return palace.findOneStepMovePath(new Position(startX, startY), new Position(endX, endY));
    }

    @Override
    public boolean isObstaclesNotExist(Position start, Position end, Board board) {
        return true;
    }

    @Override
    public String nickname() {
        return pieceType.getNickname();
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public TeamType getTeamType() {
        return teamType;
    }
}

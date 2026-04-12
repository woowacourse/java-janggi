package janggi.domain.turn;

import janggi.domain.Board;
import janggi.domain.IdGenerator;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;

import java.util.Map;
import java.util.Objects;

public class Turn {

    private final Long id;
    private final TeamType currentTeam;
    private final Board board;
    private final TurnStatus turnStatus;

    private Turn(Long id, TeamType currentTeam, Board board, TurnStatus turnStatus) {
        this.id = id;
        this.currentTeam = currentTeam;
        this.board = board;
        this.turnStatus = turnStatus;
    }

    public static Turn createInitialTurn() {
        return new Turn(IdGenerator.createId(), TeamType.HAN, Board.createInitialBoard(), TurnStatus.DRAW);
    }

    public static Turn loadPreviousTurn(long turnId, TeamType teamType, Board board, TurnStatus turnStatus) {
        return new Turn(turnId, teamType, board, turnStatus);
    }

    public long getId() {
        return id;
    }

    public TeamType getCurrentTeam() {
        return currentTeam;
    }

    public TurnStatus getTurnStatus() {
        return turnStatus;
    }

    public boolean isRunning() {
        return turnStatus == TurnStatus.DRAW;
    }

    public Map<Position, Piece> makeBoardSnapShot() {
        return board.makeSnapShot();
    }

    public String nextTurnTeamName() {
        TeamType teamType = opponentTeamType();
        return teamType.getName();
    }

    public boolean isNextTurnTeamPieceExists(Position position) {
        return board.isPieceExists(position, opponentTeamType());
    }

    public String getPieceName(Position position) {
        return board.getPieceName(position, opponentTeamType());
    }

    public void validateCanMove(Position start, Position end) {
        board.validateCanMove(start, end, opponentTeamType());
    }

    public Turn move(Position start, Position end) {
        TeamType currentTeamType = opponentTeamType();
        Board movedBoard = board.move(start, end, currentTeamType);
        if (board.isSurviveAllGung()) {
            return new Turn(IdGenerator.createId(), currentTeamType, movedBoard, TurnStatus.DRAW);
        }
        if (board.isChuWin()) {
            return new Turn(IdGenerator.createId(), currentTeamType, movedBoard, TurnStatus.CHU_WIN);
        }
        return new Turn(IdGenerator.createId(), currentTeamType, movedBoard, TurnStatus.HAN_WIN);
    }

    public Map<Position, Piece> allPieces() {
        return board.allPieces();
    }

    public String winTeamName() {
        return board.winTeamName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return Objects.equals(id, turn.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    private TeamType opponentTeamType() {
        if (currentTeam == TeamType.CHU) {
            return TeamType.HAN;
        }
        return TeamType.CHU;
    }
}

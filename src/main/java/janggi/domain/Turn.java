package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.side.TeamType;

public class Turn {

    private final TeamType movedTeam;
    private final Board board;

    public Turn(TeamType movedTeam, Board board) {
        this.movedTeam = movedTeam;
        this.board = board;
    }

    public boolean isMyTeamPieceExist(Position position) {
        return board.isMyTeamPieceExist(position, movedTeam);
    }

    public Piece findPiece(Position position) {
        return board.findNextTurnTeamPiece(position, movedTeam);
    }

    public Turn move(Position startPosition, Position endPosition) {
        TeamType nowTurn = opponentTeamType();
        Board movedBoard = board.move(startPosition, endPosition, nowTurn);
        return new Turn(nowTurn, movedBoard);
    }

    public void canMove(Position startPosition, Position endPosition) {
        board.canMove(startPosition, endPosition, opponentTeamType());
    }

    public String nextTurnTeam() {
        TeamType teamType = opponentTeamType();
        return teamType.getName();
    }

    private TeamType opponentTeamType() {
        if (movedTeam == TeamType.CHU) {
            return TeamType.HAN;
        }
        return TeamType.CHU;
    }
}

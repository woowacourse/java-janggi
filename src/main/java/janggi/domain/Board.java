package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.side.Chu;
import janggi.domain.side.Han;
import janggi.domain.side.Team;
import janggi.domain.side.TeamType;
import janggi.dto.BoardSpot;
import janggi.dto.BoardSpots;

import java.util.HashMap;
import java.util.Optional;

public class Board {

    private static final int FIRST_INDEX = 1;
    private static final int LAST_X_INDEX = 9;
    private static final int LAST_Y_INDEX = 10;

    private final Team chu;
    private final Team han;

    private Board(Team chu, Team han) {
        this.chu = chu;
        this.han = han;
    }

    public static Board createInitialBoard() {
        return new Board(Chu.createInitialChu(), Han.createInitialHan());
    }

    public BoardSpots makeSnapShot() {
        HashMap<Position, BoardSpot> boardSpots = new HashMap<>(chu.makeSnapShot());
        boardSpots.putAll(han.makeSnapShot());
        return new BoardSpots(boardSpots);
    }

    public boolean isPieceExists(Position position, TeamType currentTeamType) {
        validateRange(position);
        Team currentTeam = currentTeam(currentTeamType);
        return currentTeam.isPieceExists(position);
    }

    public String getPieceName(Position position, TeamType currentTeamType) {
        Piece piece = findTeamPiece(position, currentTeam(currentTeamType));
        return piece.name();
    }

    public void validateCanMove(Position start, Position end, TeamType currentTeamType) {
        validateRange(end);
        Piece piece = findTeamPiece(start, currentTeam(currentTeamType));
        validateEndPosition(currentTeam(currentTeamType), end);
        piece.validateCanMove(start, end, this);
    }

    public Optional<Piece> findPiece(Position position) {
        Optional<Piece> chuPiece = chu.findPiece(position);
        if (chuPiece.isPresent()) {
            return chuPiece;
        }
        return han.findPiece(position);
    }

    public boolean hasPiece(Position position) {
        return findPiece(position).isPresent();
    }

    public Board move(Position start, Position end, TeamType currentTeamType) {
        Team updatedCurrentTeam = currentTeam(currentTeamType).move(start, end);
        Team updatedOpponentTeam = removeOpponentPiece(currentTeamType, end);
        return createMovedBoard(currentTeamType, updatedCurrentTeam, updatedOpponentTeam);
    }

    private void validateRange(Position inputPosition) {
        int x = inputPosition.getX();
        int y = inputPosition.getY();
        if (isNotInRange(FIRST_INDEX, LAST_X_INDEX, x) || isNotInRange(FIRST_INDEX, LAST_Y_INDEX, y)) {
            throw new IllegalArgumentException("입력한 좌표가 장기판 범위 밖입니다.");
        }
    }

    private boolean isNotInRange(int start, int last, int index) {
        return !isInRange(start, last, index);
    }

    private boolean isInRange(int start, int last, int index) {
        return index >= start && index <= last;
    }

    private Team currentTeam(TeamType currentTeamType) {
        if (currentTeamType == TeamType.CHU) {
            return chu;
        }
        return han;
    }

    private Piece findTeamPiece(Position position, Team currentTeam) {
        return currentTeam.findPiece(position)
                .orElseThrow(() -> new IllegalArgumentException("입력한 위치에 기물이 없습니다."));
    }

    private void validateEndPosition(Team team, Position end) {
        if (team.isPieceExists(end)) {
            throw new IllegalArgumentException("아군이 존재하는 좌표로는 이동할 수 없습니다.");
        }
    }

    private Team removeOpponentPiece(TeamType currentTeamType, Position end) {
        Team opponentTeam = opponentTeam(currentTeamType);
        if (opponentTeam.findPiece(end).isEmpty()) {
            return opponentTeam;
        }
        return opponentTeam.remove(end);
    }

    private Team opponentTeam(TeamType currentTeamType) {
        if (currentTeamType == TeamType.CHU) {
            return han;
        }
        return chu;
    }

    private Board createMovedBoard(TeamType currentTeamType, Team updatedCurrentTeam, Team updatedOpponentTeam) {
        if (currentTeamType == TeamType.CHU) {
            return new Board(updatedCurrentTeam, updatedOpponentTeam);
        }
        return new Board(updatedOpponentTeam, updatedCurrentTeam);
    }
}

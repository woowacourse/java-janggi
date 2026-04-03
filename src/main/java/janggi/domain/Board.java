package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.side.Chu;
import janggi.domain.side.Han;
import janggi.domain.side.Team;
import janggi.domain.side.TeamType;
import janggi.dto.BoardSpots;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<Position, Piece> allPieces = new HashMap<>(chu.getPieces());
        allPieces.putAll(han.getPieces());
        return BoardSpots.from(allPieces);
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
        checkSamePosition(start, end);
        validateTeamPieceExistsAtEnd(currentTeam(currentTeamType), end);
        validatePiecesInPath(start, end, currentTeam(currentTeamType));
    }

    public Optional<Piece> findPiece(Position position) {
        Optional<Piece> chuPiece = chu.findPiece(position);
        if (chuPiece.isPresent()) {
            return chuPiece;
        }
        return han.findPiece(position);
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

    private void checkSamePosition(Position start, Position end) {
        if (start.isSamePosition(end)) {
            throw new IllegalArgumentException("출발지와 목적지가 동일합니다.");
        }
    }

    private void validateTeamPieceExistsAtEnd(Team team, Position end) {
        if (team.isPieceExists(end)) {
            throw new IllegalArgumentException("아군이 존재하는 좌표로는 이동할 수 없습니다.");
        }
    }

    private Team currentTeam(TeamType currentTeamType) {
        if (currentTeamType == TeamType.CHU) {
            return chu;
        }
        return han;
    }

    private void validatePiecesInPath(Position start, Position end, Team currentTeam) {
        Piece piece = findTeamPiece(start, currentTeam);
        List<Position> piecePositionsInPath = piece.getPiecePositionsInPath(start, end);
        List<Piece> piecesInPath = piecePositionsInPath.stream()
                .map(this::findPiece)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        piece.validateCanMove(piecesInPath);
    }

    private Piece findTeamPiece(Position position, Team currentTeam) {
        return currentTeam.findPiece(position)
                .orElseThrow(() -> new IllegalArgumentException("입력한 위치에 기물이 없습니다."));
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

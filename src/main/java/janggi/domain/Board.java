package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.team.Chu;
import janggi.domain.team.Han;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {

    private final Team chu;
    private final Team han;

    private Board(Team chu, Team han) {
        this.chu = chu;
        this.han = han;
    }

    public static Board createInitialBoard() {
        return new Board(Chu.createInitialChu(), Han.createInitialHan());
    }

    public static Board loadPreviousBoard(Team chu, Team han) {
        return new Board(chu, han);
    }

    public boolean isRunning() {
        return chu.isRunning() && han.isRunning();
    }

    public Map<Position, Piece> makeSnapShot() {
        Map<Position, Piece> allPieces = new HashMap<>(chu.getPieces());
        allPieces.putAll(han.getPieces());
        return allPieces;
    }

    public boolean isPieceExists(Position position, TeamType currentTeamType) {
        Team currentTeam = currentTeam(currentTeamType);
        return currentTeam.isPieceExists(position);
    }

    public String getPieceName(Position position, TeamType currentTeamType) {
        Piece piece = findTeamPiece(position, currentTeam(currentTeamType));
        return piece.name();
    }

    public void validateCanMove(Position start, Position end, TeamType currentTeamType) {
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
        validateCanMove(start, end, currentTeamType);
        Team updatedCurrentTeam = currentTeam(currentTeamType).move(start, end);
        Team updatedOpponentTeam = removeOpponentPiece(currentTeamType, end);
        if (updatedOpponentTeam.isLose()) {
            updatedCurrentTeam = updatedCurrentTeam.updateWin();
            return createMovedBoard(currentTeamType, updatedCurrentTeam, updatedOpponentTeam);
        }
        return createMovedBoard(currentTeamType, updatedCurrentTeam, updatedOpponentTeam);
    }

    public String winTeamName() {
        if (chu.isWin()) {
            return TeamType.CHU.getName();
        }
        return TeamType.HAN.getName();
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

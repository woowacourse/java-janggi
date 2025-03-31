package janggi.dao;

import janggi.dto.PieceDto;
import janggi.dto.PieceTypeDto;
import janggi.dto.TeamTypeDto;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
import janggi.team.TeamType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FakeJanggiDao extends JanggiDao {

    private final List<PieceDto> pieces;
    private final List<PieceTypeDto> pieceTypes;
    private final List<TeamTypeDto> teamTypes;

    public FakeJanggiDao() {
        super(null);
        pieces = new ArrayList<>();
        pieceTypes = new ArrayList<>();
        teamTypes = new ArrayList<>();
    }

    @Override
    public void insertInitialPieceType() {
        int index = 1;

        for (PieceType pieceType : PieceType.values()) {
            pieceTypes.add(new PieceTypeDto(index, pieceType.getTitle()));
        }
    }

    @Override
    public void insertInitialTeam(TeamType currentTeam) {
        int index = 1;

        for (TeamType teamType : TeamType.values()) {
            boolean isCurrent = (currentTeam == teamType);
            teamTypes.add(new TeamTypeDto(index, teamType.getTitle(), isCurrent));
        }
    }

    @Override
    public void insertPieces(Map<Position, Piece> pieces) {
        int index = 1;

        for (Entry<Position, Piece> entry : pieces.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            final int teamId = findTeamTypeByTitle(piece.getTeamType().getTitle()).getId();
            final int pieceId = findPieceTypeByTitle(piece.getPieceType().getTitle()).id();
            this.pieces.add(new PieceDto(index, teamId, pieceId, position.getX(), position.getY()));
        }
    }

    @Override
    public TeamTypeDto findTeamById(int id) {
        return teamTypes.get(id - 1);
    }

    @Override
    public PieceTypeDto findPieceTypeById(int id) {
        return pieceTypes.get(id - 1);
    }

    @Override
    public List<PieceDto> findPieces() {
        return getPieces();
    }

    @Override
    public List<TeamTypeDto> findTeams() {
        return getTeamTypes();
    }

    @Override
    public void updateTeamOrder(TeamType currentTeam) {
        teamTypes.forEach(teamTypeDto -> teamTypeDto.setCurrent(false));

        TeamTypeDto target = findTeamTypeByTitle(currentTeam.getTitle());
        teamTypes.remove(target);
        target.setCurrent(true);
        teamTypes.addFirst(target);
    }

    @Override
    public void deleteAllPieceIfExists() {
        pieces.clear();
    }

    @Override
    public void deleteAllPieceTypeIfExists() {
        pieceTypes.clear();
    }

    @Override
    public void deleteAllTeamIfExists() {
        teamTypes.clear();
    }

    private PieceTypeDto findPieceTypeByTitle(String title) {
        return pieceTypes.stream()
                .filter(pieceTypeDto -> pieceTypeDto.name().equals(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 이름을 가진 기물이 없습니다."));
    }

    private TeamTypeDto findTeamTypeByTitle(String title) {
        return teamTypes.stream()
                .filter(pieceTypeDto -> pieceTypeDto.getName().equals(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 이름을 가진 팀이 없습니다."));
    }

    public List<PieceDto> getPieces() {
        return pieces;
    }

    public List<PieceTypeDto> getPieceTypes() {
        return pieceTypes;
    }

    public List<TeamTypeDto> getTeamTypes() {
        return teamTypes;
    }
}

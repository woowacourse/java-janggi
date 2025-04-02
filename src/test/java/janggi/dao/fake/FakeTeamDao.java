package janggi.dao.fake;

import janggi.dao.TeamDao;
import janggi.domain.team.TeamType;
import janggi.dto.TeamTypeDto;
import java.util.ArrayList;
import java.util.List;

public class FakeTeamDao extends TeamDao {

    private final List<TeamTypeDto> teamTypes;

    public FakeTeamDao() {
        super(null);
        teamTypes = new ArrayList<>();
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
    public TeamTypeDto findTeamById(int id) {
        return teamTypes.get(id - 1);
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
    public void deleteAllTeamIfExists() {
        teamTypes.clear();
    }

    private TeamTypeDto findTeamTypeByTitle(String title) {
        return teamTypes.stream()
                .filter(pieceTypeDto -> pieceTypeDto.getName().equals(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 이름을 가진 팀이 없습니다."));
    }

    public List<TeamTypeDto> getTeamTypes() {
        return teamTypes;
    }
}

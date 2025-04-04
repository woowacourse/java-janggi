package entity;

import dao.TeamDao;
import execptions.JanggiArgumentException;
import java.util.Optional;

public class TeamRepository {
    private final TeamDao teamDao;

    public TeamRepository(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    public TeamEntity findById(long teamId) {
        Optional<TeamEntity> teamEntity = teamDao.findById(teamId);
        if (teamEntity.isEmpty()) {
            throw new JanggiArgumentException("해당 아이디와 일치하는 팀이 존재하지 않습니다.");
        }
        return teamEntity.get();
    }

    public TeamEntity findByName(String name) {
        Optional<TeamEntity> team = teamDao.findByName(name);

        if (team.isEmpty()) {
            throw new JanggiArgumentException("동일한 이름을 가진 팀이 존재하지 않습니다.");
        }
        return team.get();
    }
}

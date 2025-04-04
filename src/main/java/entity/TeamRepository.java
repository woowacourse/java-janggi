package entity;

import dao.TeamDao;

public class TeamRepository {
    private final TeamDao teamDao;

    public TeamRepository(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    public TeamEntity findById(long teamId) {
        return teamDao.findById(teamId);
    }

    public TeamEntity findByName(String name) {
        return teamDao.findByName(name);
    }
}

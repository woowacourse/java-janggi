package service;

import dao.TurnDao;
import domain.type.JanggiTeam;
import entity.TurnEntity;

import java.util.Optional;

public class JanggiTurnService {
    private JanggiTeam currentTeam;
    private final TurnDao turnDao;

    public JanggiTurnService(TurnDao turnDao) {
        this.turnDao = turnDao;
        this.currentTeam = getInitTeam();
    }

    private JanggiTeam getInitTeam() {
        Optional<TurnEntity> turn = turnDao.findTurn();
        if (turn.isEmpty()) {
            JanggiTeam firstTurn = JanggiTeam.firstTurn();
            turnDao.save(new TurnEntity(firstTurn));
            return firstTurn;
        }
        return turn.get().team();
    }

    public void switchTurn() {
        switch (currentTeam) {
            case BLUE -> currentTeam = JanggiTeam.RED;
            case RED -> currentTeam = JanggiTeam.BLUE;
        }
        turnDao.save(new TurnEntity(currentTeam));
    }

    public void reset() {
        turnDao.deleteAll();
    }

    public JanggiTeam getCurrentTeam() {
        return currentTeam;
    }
}

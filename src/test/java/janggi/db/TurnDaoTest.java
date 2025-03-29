package janggi.db;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dao.BoardDao;
import janggi.dao.TurnDao;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.PieceType;
import janggi.model.Position;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TurnDaoTest extends DbTest {
    private final TurnDao turnDao = new TurnDao(mockConnection());

    @Test
    void 턴을_바꾼다() {
        turnDao.updateCurrentTurn(Color.RED);
        assertThat(turnDao.findCurrentTurn()).isEqualTo(Color.RED);
    }
}

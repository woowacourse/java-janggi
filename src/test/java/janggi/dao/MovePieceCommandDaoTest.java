package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.data.fixture.DBFixture;
import janggi.data.spy.TestDBConnector;
import janggi.db.DBConnector;
import janggi.game.GameInformation;
import janggi.game.MovePieceCommand;
import janggi.rule.CampType;
import janggi.rule.PieceAssignType;
import janggi.value.Position;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovePieceCommandDaoTest {

    DBConnector dbConnector = new TestDBConnector();
    GameInformationDao gameInformationDao = new GameInformationDao(dbConnector);
    MovePieceCommandDao movePieceCommandDao = new MovePieceCommandDao(dbConnector);

    @AfterEach
    void afterEach() {
        DBFixture.resetTable(dbConnector, "move_piece_commands");
        DBFixture.resetTable(dbConnector, "games");
    }

    @DisplayName("새로운 장기말 이동 명령을 삽입할 수 있다.")
    @Test
    void canAddNew() {
        GameInformation gameInformation = gameInformationDao.addNew("newGame", PieceAssignType.IN_SANG,
                PieceAssignType.IN_SANG);
        MovePieceCommand savedCommand = movePieceCommandDao.addNew(
                gameInformation.gameId(), CampType.CHO, new Position(4, 4), new Position(5, 5));

        assertAll(
                () -> assertThat(savedCommand.campType()).isEqualTo(CampType.CHO),
                () -> assertThat(savedCommand.targetPiecePosition()).isEqualTo(new Position(4, 4)),
                () -> assertThat(savedCommand.destination()).isEqualTo(new Position(5, 5))
        );
    }

    @DisplayName("게임의 장기말 이동 명령들을 조회할 수 있다.")
    @Test
    void canFinaAllMovePieceCommand() {
        GameInformation gameInformation =
                gameInformationDao.addNew("newGame", PieceAssignType.IN_SANG, PieceAssignType.IN_SANG);
        MovePieceCommand firstCommand = movePieceCommandDao.addNew(
                gameInformation.gameId(), CampType.CHO, new Position(4, 4), new Position(5, 5));
        MovePieceCommand secondCommand = movePieceCommandDao.addNew(
                gameInformation.gameId(), CampType.HAN, new Position(4, 4), new Position(5, 5));

        List<MovePieceCommand> commandsInGame = movePieceCommandDao.findAllInGameId(gameInformation.gameId());
        assertThat(commandsInGame).containsExactly(firstCommand, secondCommand);

    }
}
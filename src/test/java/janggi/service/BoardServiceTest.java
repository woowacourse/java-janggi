package janggi.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.TestDBConnection;
import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.turn.TurnManager;
import janggi.entity.BoardEntity;
import janggi.entity.GameStateEntity;
import janggi.repository.BoardRepository;
import janggi.repository.BoardRepositoryImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardServiceTest {

    DBConnection dbConnection;
    DBTableInitializer dbTableInitializer;

    BoardRepository boardRepository;
    BoardService boardService;

    @BeforeEach
    void setUp() {
        dbConnection = new TestDBConnection();
        dbTableInitializer = new DBTableInitializer(dbConnection);
        boardRepository = new BoardRepositoryImpl(dbConnection);
        boardService = new BoardService(boardRepository);

        dbConnection.init();
        dbTableInitializer.init();
    }

    @Test
    @DisplayName("보드 생성 테스트")
    void CreateBoard() {
        String name = "게임 1";
        BoardEntity expected = BoardEntity.from(1, name);

        long id = boardService.createBoard(name);
        BoardEntity actual = boardRepository.findById(id);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("보드 삭제 테스트")
    void RemoveBoard() {
        String name = "게임 1";
        long id = boardRepository.save(BoardEntity.from(name));
        boolean expected = true;

        boolean actual = boardService.removeBoard(id);

        assertThat(actual).isEqualTo(expected);
    }

}

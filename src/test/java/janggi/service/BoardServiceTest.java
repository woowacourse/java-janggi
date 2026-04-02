package janggi.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.TestDBConnection;
import janggi.entity.BoardEntity;
import janggi.repository.BoardRepository;
import janggi.repository.BoardRepositoryImpl;
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

}

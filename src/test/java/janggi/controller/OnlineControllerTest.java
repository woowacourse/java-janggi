package janggi.controller;

import janggi.data.dao.test.FakeBoardDao;
import janggi.data.dao.test.FakeCampDao;
import janggi.data.dao.test.FakePieceDao;
import janggi.data.dao.test.FakePieceSymbolDao;
import janggi.view.View;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OnlineControllerTest {

    @DisplayName("장군을 잡으면 게임이 종료된다. (FakeDao를 사용한 테스트)")
    @Test
    void startAndEndGameTest() {
        // given (테스트를 위한 가짜 입력)
        String simulatedInput = """
                y
                10
                33
                39
                38
                33
                56
                48
                39
                56
                39
                """;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        OnlineController onlineController = new OnlineController(
                new View(),
                new FakeCampDao(),
                new FakePieceSymbolDao(),
                new FakeBoardDao(),
                new FakePieceDao());

        // when
        onlineController.gameStart();
    }
}

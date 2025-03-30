package janggi.team;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.BoardSetup;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamFactoryTest {
    @DisplayName("정상: 팀을 생성하는지 확인 (초)")
    @Test
    void createTeamCho() {
        BoardSetup boardSetup = BoardSetup.of(List.of("초", "HEHE"));
        assertThatCode(() -> TeamFactory.createTeam(boardSetup)).doesNotThrowAnyException();
    }

    @DisplayName("정상: 팀을 생성하는지 확인 (한)")
    @Test
    void createTeamHan() {
        BoardSetup boardSetup = BoardSetup.of(List.of("한", "HEHE"));
        assertThatCode(() -> TeamFactory.createTeam(boardSetup)).doesNotThrowAnyException();
    }
}

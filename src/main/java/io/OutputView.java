package io;

import domain.vo.Team;

public class OutputView {
    private static final String REQUEST_SETUP = """
            [%s 진영] 배치를 선택하세요.
            1. 마-상-마-상 (Horse-Elephant-Horse-Elephant)
            2. 마-상-상-마 (Horse-Elephant-Elephant-Horse)
            3. 상-마-마-상 (Elephant-Horse-Horse-Elephant)
            4. 상-마-상-마 (Elephant-Horse-Elephant-Horse)""";

    public void printSetupTable(Team team) {
        String message = String.format(REQUEST_SETUP, team.getTeamName());
        System.out.println(message);
    }
}

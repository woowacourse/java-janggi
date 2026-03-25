package view;

import domain.board.ElephantSetup;
import java.util.List;

public class OutputView {

    public void printEnterChoPlayerNamePrompt() {
        System.out.println("초나라 플레이어의 이름을 입력하세요(2~5자, 영어만 사용):");
    }

    public void printEnterHanPlayerNamePrompt() {
        System.out.println("한나라 플레이어의 이름을 입력하세요(2~5자, 영어만 사용):");
    }

    public void printChoiceChoElephantSetupPrompt() {
        System.out.println("초나라 플레이어가 사용할 상차림 번호를 입력하세요 ");
        printElephantSetups();
    }

    public void printChoiceHanElephantSetupPrompt() {
        System.out.println("한나라 플레이어가 사용할 상차림 번호를 입력하세요 ");
        printElephantSetups();
    }

    private void printElephantSetups() {
        StringBuilder promptBuilder = new StringBuilder();
        List<String> descriptions = ElephantSetup.descriptions();
        for (int i = 0; i < descriptions.size(); i++) {
            promptBuilder.append(String.format("%d. %s ", i + 1, descriptions.get(i)));
        }
        System.out.println(promptBuilder);
    }
}

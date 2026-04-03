package janggi.view.output;

import java.util.List;

public class ConsoleWriter implements Output {

    @Override
    public void printPromptMessage(String promptMessage) {
        System.out.println(promptMessage);
    }

    @Override
    public void printErrorMessage(RuntimeException e) {
        System.out.println("[ERROR] " + e.getMessage());
    }

    @Override
    public void printStringMatrix(List<List<String>> matrix) {
        StringBuilder matrixSnapshot = new StringBuilder();
        for (List<String> strings : matrix) {
            for (String string : strings) {
                matrixSnapshot.append(string).append(" ");
            }
            matrixSnapshot.append("\n");
        }

        System.out.println(matrixSnapshot);
    }

    @Override
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

package janggi.view;

import java.util.List;

public class ConsoleWriter implements Output {

    @Override
    public void printPromptMessage(String promptMessage) {
        System.out.println(promptMessage);
    }

    @Override
    public void printErrorMessage(String errorMessage) {
        System.out.println("[ERROR] " + errorMessage);
        System.out.println();
    }

    @Override
    public void printStringMatrix(List<List<String>> matrix) {
        StringBuilder matrixSnapshot = new StringBuilder();
        for (List<String> strings : matrix) {
            for (String string : strings) {
                matrixSnapshot.append(String.format("%-2s", string));
            }
            matrixSnapshot.append("\n");
        }

        System.out.println();
        System.out.println(matrixSnapshot);
    }
}

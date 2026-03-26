package janggi.view;

import java.util.List;

public interface Output {

    void printPromptMessage(String promptMessage);

    void printErrorMessage(RuntimeException e);

    void printStringMatrix(List<List<String>> matrix);
}

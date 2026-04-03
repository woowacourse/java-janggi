package janggi.view;

import java.util.List;

public interface Output {

    void printPromptMessage(String promptMessage);

    void printErrorMessage(String errorMessage);

    void printStringMatrix(List<List<String>> matrix);
}

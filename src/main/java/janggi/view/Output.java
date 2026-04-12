package janggi.view;

import janggi.dto.PieceDto;
import java.util.List;

public interface Output {

    void printPromptMessage(String promptMessage);

    void printErrorMessage(String errorMessage);

    void printPieceMatrix(List<List<PieceDto>> matrix);
}

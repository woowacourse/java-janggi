package exception.custom;

import exception.GameErrorMessage;

public class InvalidGameInputException extends GameException{
    public InvalidGameInputException(){
        super(GameErrorMessage.INVALID_GAME_INPUT.getMessage());
    }

    public InvalidGameInputException(String message){
        super(message);
    }
}

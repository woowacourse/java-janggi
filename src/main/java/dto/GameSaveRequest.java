package dto;

import domain.game.Turn;

public record GameSaveRequest(BoardRowDetails boardRowDetails, String turn) {

}

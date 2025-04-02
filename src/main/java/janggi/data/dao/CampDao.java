package janggi.data.dao;

import janggi.piece.Camp;

public interface CampDao {

    void saveAll(Camp... camps);

    int findIdByName(String name);
}

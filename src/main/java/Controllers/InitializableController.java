package Controllers;

import java.sql.SQLException;

public interface InitializableController {
    void init(Integer Id) throws SQLException;
}

package howest.groep14.game;

import java.sql.*;

class SettingsMapper {
    private Connection connection;

    public SettingsMapper() throws SQLException {
        String sqlUser = SettingsRepository.getSqlUser();
        String sqlPassword = SettingsRepository.getSqlPwd();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DriverManager.setLoginTimeout(3);
        connection = DriverManager.getConnection("jdbc:mysql://sporegen.net/geometry-wars?"
                + "user="+sqlUser+"&password="+sqlPassword);
    }

    public String getSetting(String name) throws SQLException {
        PreparedStatement prep = connection.prepareStatement("SELECT value FROM config WHERE name = ?");

        prep.setString(1, name);
        ResultSet results = prep.executeQuery();

        if (results.next()) {
            return results.getString("value");
        } else {
            throw new SQLException("No valid setting name");
        }
    }
}

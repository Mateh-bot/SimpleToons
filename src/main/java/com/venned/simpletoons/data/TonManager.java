package com.venned.simpletoons.data;

import com.venned.simpletoons.build.PlayerTon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TonManager {
    Set<PlayerTon> playerTons = new HashSet<PlayerTon>();
    SQLService service;

    public TonManager(SQLService service) {
        this.service = service;
        loadAll();
    }

    void loadAll() {
        String query = "SELECT * FROM playertoons";
        try (Connection connection = service.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                UUID ownerUuid = UUID.fromString(resultSet.getString("owner"));
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String culture = resultSet.getString("culture");
                String gender = resultSet.getString("gender");
                String description = resultSet.getString("description");
                String race = resultSet.getString("category");
                boolean active = resultSet.getString("state").equalsIgnoreCase("active");

                PlayerTon playerTon = new PlayerTon(ownerUuid, name, age, culture, gender, description, race, active);
                playerTons.add(playerTon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<PlayerTon> getPlayerTons() {
        return playerTons;
    }
}

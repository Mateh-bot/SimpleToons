package com.venned.simpletoons.data;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.plugin.Plugin;

import java.sql.*;

public class SQLService {
    Connection connection;
    Plugin plugin;

    public SQLService(Plugin plugin) {
        this.plugin = plugin;
        setupDataBase();
    }

    void setupDataBase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/data.db");
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS playertoons (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +  // ID autoincremental
                        "name TEXT UNIQUE, " +                      // Nombre único del Toon
                        "state TEXT, " +                            // Estado del Toon (activo, inactivo, etc.)
                        "owner TEXT, " +                            // UUID del propietario
                        "category TEXT, " +                         // Raza o categoría del Toon
                        "gender TEXT, " +                           // Género
                        "description TEXT, " +                      // Descripción del Toon
                        "age INTEGER, " +                           // Edad del Toon
                        "culture TEXT)");                           // Cultura del Toon
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveAll() throws SQLException {
        if (getConnection() == null) {
            System.out.println("Cannot save PlayerTons: Database connection is closed.");
            return;
        }

        String queryInsertOrUpdate = "INSERT INTO playertoons (name, state, owner, category, gender, description, age, culture) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT(name) DO UPDATE SET " +
                "state = excluded.state, " +
                "owner = excluded.owner, " +
                "category = excluded.category, " +
                "gender = excluded.gender, " +
                "description = excluded.description, " +
                "age = excluded.age, " +
                "culture = excluded.culture";

        try (Connection conn = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(queryInsertOrUpdate)) {
                for (PlayerTon playerTon : Main.getInstance().getTonManager().getPlayerTons()) {
                    preparedStatement.setString(1, playerTon.getName());
                    preparedStatement.setString(2, playerTon.isActive() ? "active" : "inactive");
                    preparedStatement.setString(3, playerTon.getOwner_uuid().toString());
                    preparedStatement.setString(4, playerTon.getRace());
                    preparedStatement.setString(5, playerTon.getGender());
                    preparedStatement.setString(6, playerTon.getDescription());
                    preparedStatement.setInt(7, playerTon.getAge());
                    preparedStatement.setString(8, playerTon.getCulture());

                    preparedStatement.addBatch(); // Añade al lote
                }
                preparedStatement.executeBatch(); // Ejecuta el lote de actualizaciones/inserciones
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                plugin.getLogger().info("Database connection closed.");
            } catch (SQLException e) {
                plugin.getLogger().warning("Failed to close database connection: " + e.getMessage());
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:sqlite:" + Main.getInstance().getDataFolder() + "/data.db");
        }
        return connection;
    }
}

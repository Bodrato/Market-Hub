package com.markethub.apiReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApiDataFetcher {

    public static void main(String[] args) {
        String usersApiUrl = "https://api.escuelajs.co/api/v1/users";
        String productsApiUrl = "https://api.escuelajs.co/api/v1/products";
        String categoriesApiUrl = "https://api.escuelajs.co/api/v1/categories";

        fetchAndProcessApi(usersApiUrl, "account");
        fetchAndProcessApi(productsApiUrl, "product");
        fetchAndProcessApi(categoriesApiUrl, "category");
    }

    private static void fetchAndProcessApi(String apiUrl, String tableName) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(apiUrl);
            HttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                String jsonResponse = org.apache.http.util.EntityUtils.toString(response.getEntity());
                processApiResponse(jsonResponse, apiUrl, tableName);
            } else {
                System.out.println("Couldn't not get data from " + apiUrl + ": " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processApiResponse(String jsonResponse, String apiUrl, String tableName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            String jdbcUrl = "jdbc:postgresql://localhost:5432/markethub";
            String dbUser = "postgres";
            String dbPassword = "2552";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                for (JsonNode node : jsonNode) {
                    if (tableName.equals("account")) {
                        processUserNode(node, connection);
                    } else if (tableName.equals("product")) {
                        processProductNode(node, connection);
                    } else if (tableName.equals("category")) {
                        processCategoryNode(node, connection);
                    }
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void processUserNode(JsonNode node, Connection connection) throws SQLException {
        int id = node.get("id").asInt();
        String email = node.get("email").asText();
        String password = node.get("password").asText();
        String nickname = node.get("name").asText();
        String image = node.get("avatar").asText();

        if (!userExists(connection, id, email)) {
            saveUserToDatabase(id, email, password, nickname, image, connection);
        } else {
            System.out.println("Skipping user record with id: " + id + " and email: " + email + ". Already exists.");
        }
    }

    private static void processProductNode(JsonNode node, Connection connection) throws SQLException {
        int id = node.get("id").asInt();
        String name = getNodeTextValue(node, "title"); // Ajusta el campo según la respuesta real de la API
        String description = getNodeTextValue(node, "description");
        double price = node.get("price").asDouble();

        String image = "";

        if (!productExists(connection, id, name)) {
            saveProductToDatabase(id, name, description, price, image, connection);
        } else {
            System.out.println("Skipping product record with id: " + id + " and name: " + name + ". Already exists.");
        }
    }


    private static String getNodeTextValue(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null && !fieldNode.isNull()) ? fieldNode.asText() : "";
    }

    private static void processCategoryNode(JsonNode node, Connection connection) throws SQLException {
        int id = node.get("id").asInt();
        String name = node.get("name").asText();

        if (!categoryExists(connection, id, name)) {
            saveCategoryToDatabase(id, name, connection);
        } else {
            System.out.println("Skipping category record with id: " + id + " and name: " + name + ". Already exists.");
        }
    }

    private static boolean userExists(Connection connection, int id, String email) throws SQLException {
        return recordExists(connection, id, "email", email, "account");
    }

    private static boolean productExists(Connection connection, int id, String name) throws SQLException {
        return recordExists(connection, id, "name", name, "product");
    }

    private static boolean categoryExists(Connection connection, int id, String name) throws SQLException {
        return recordExists(connection, id, "name", name, "category");
    }

    private static boolean recordExists(Connection connection, int id, String columnName, String value, String tableName) throws SQLException {
        String idColumnName = "id_" + tableName;
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + idColumnName + " = ? OR " + columnName + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, value);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    private static void saveUserToDatabase(int id, String email, String password, String nickname, String image, Connection connection) throws SQLException {
        String sql = "INSERT INTO account (id_account, email, password, nickname, image) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, nickname);
            preparedStatement.setString(5, image);

            preparedStatement.executeUpdate();
        }
    }

    private static void saveProductToDatabase(int id, String name, String description, double price, String image, Connection connection) throws SQLException {
        String sql = "INSERT INTO product (id_product, name, description, price, image) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, image);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key")) {
                System.out.println("Duplicate key violation. Skipping record with id: " + id + " and name: " + name);
            } else {
                throw e;
            }
        }
    }

    private static void saveCategoryToDatabase(int id, String name, Connection connection) throws SQLException {
        String sql = "INSERT INTO category (id_category, name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();
        }
    }
}

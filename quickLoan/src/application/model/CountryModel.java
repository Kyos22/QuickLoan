package application.model;

import application.entities.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CountryModel {

    public List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = ConnectDB.connection()
                    .prepareStatement("SELECT * FROM country");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Country country = new Country();
                country.setId(resultSet.getInt("id"));
                country.setName_country(resultSet.getString("name_country"));
                country.setImage_name(resultSet.getString("image_name"));
                countries.add(country);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.disconnect();
        }

        return countries;
    }
}

package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
// import java.util.HashMap;
// import java.util.Map;


/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    // TODO Task: pick appropriate instance variable(s) to store the data necessary for this class
    // A HashMap to store country codes and their corresponding country names
    private HashMap<String, String> countryCodeMap;

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {
        countryCodeMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // TODO Task: use lines to populate the instance variable(s)
            // Populate the HashMap with country code and name pairs
            for (String line : lines) {
                String[] parts = line.split(","); // Assuming the file has a CSV format: code,name
                if (parts.length == 2) {
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    countryCodeMap.put(code, name);
                }
            }
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // TODO Task: update this code to use an instance variable to return the correct value
        return countryCodeMap.getOrDefault(code, "Unknown country code");
    }


    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // TODO Task: update this code to use an instance variable to return the correct value
        // Iterate through the entries of the countryCodeMap
        for (HashMap.Entry<String, String> entry : countryCodeMap.entrySet()) {
            // If the country name matches, return the corresponding country code
            if (entry.getValue().equalsIgnoreCase(country)) {
                return entry.getKey();
            }
        }
        // Return a default message if the country is not found
        return "Unknown country";
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // TODO Task: update this code to use an instance variable to return the correct value
        return countryCodeMap.size();
    }
}

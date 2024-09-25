package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class
    // Instance variables to store translation data
    private HashMap<String, List<String>> countryToLanguages;
    private HashMap<String, HashMap<String, String>> countryToTranslations;
    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // Initialize the instance variables
        countryToLanguages = new HashMap<>();
        countryToTranslations = new HashMap<>();

        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            // Use the data in the jsonArray to populate instance variables
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryData = jsonArray.getJSONObject(i);
                String country = countryData.getString("country");
                JSONArray languagesArray = countryData.getJSONArray("languages");

                // Populate countryToLanguages
                List<String> languages = new ArrayList<>();
                for (int j = 0; j < languagesArray.length(); j++) {
                    languages.add(languagesArray.getString(j));
                }
                countryToLanguages.put(country, languages);

                // Populate countryToTranslations
                HashMap<String, String> translations = new HashMap<>();
                JSONObject translationsObj = countryData.getJSONObject("translations");
                for (String language : languages) {
                    translations.put(language, translationsObj.getString(language));
                }
                countryToTranslations.put(country, translations);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(countryToLanguages.getOrDefault(country, new ArrayList<>()));
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(countryToLanguages.keySet());
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed
        HashMap<String, String> translations = countryToTranslations.get(country);
        if (translations != null) {
            return translations.getOrDefault(language, "No translation available");
        }
        return "Country not found";    }
}

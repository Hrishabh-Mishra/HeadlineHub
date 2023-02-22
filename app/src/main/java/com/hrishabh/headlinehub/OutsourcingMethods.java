package com.hrishabh.headlinehub;

import org.json.JSONException;
import org.json.JSONObject;

public class OutsourcingMethods {

    private static final String apiKey = "&apiKey=37d0f988a3c544c0b4770eae72b1677b";
    private static final String domainURL = "https://newsapi.org/v2/top-headlines";
    private static final String categoryPart = "&category=";
    private static final String countryPart = "?country=";

    public static NewsArticle convertJSONToNewsArticle(JSONObject obj, NewsArticle individualArticle) throws JSONException {
        individualArticle.setAuthor(obj.getString("author"));
        individualArticle.setUrl(obj.getString("url"));
        individualArticle.setDescription(obj.getString("description"));
        individualArticle.setTitle(obj.getString("title"));
        individualArticle.setUrlToImage(obj.getString("urlToImage"));
        return individualArticle;
    }
    public static String urlCreator(String country, String category){
        StringBuilder sb = new StringBuilder(domainURL);
        sb.append(countryPart).append(country).append(categoryPart).append(category).append(apiKey);
        return sb.toString();
    }
    public static String getCountryCodeFromCountry(String country){
        switch (country){
            case "India":return "in";
            case "USA":return "us";
            case "Australia":return "au";
            case "Canada":return "ca";
            case "China":return "cn";
            case "France":return "fr";
            case "Japan":return "jp";
            case "Ukraine": return "ua";
            case "United Kingdom":return "gb";
        }
        return "in";//default country code is India
    }
}

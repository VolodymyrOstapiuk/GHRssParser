package ua.ck.ostapiuk.ghostapiukrssreader.util.parser;

import com.facebook.model.GraphUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ua.ck.ostapiuk.ghostapiukrssreader.entity.User;

public class FacebookUserParser {
    public User parse(GraphUser graphUser) {
        User user = new User();
        user.setId(graphUser.getId());
        user.setName(graphUser.getName());
        user.setFirstName(graphUser.getFirstName());
        user.setMiddleName(graphUser.getMiddleName());
        user.setLastName(graphUser.getLastName());
        user.setBirthday(graphUser.getBirthday());
        user.setLink(graphUser.getLink());
//      user.setLocation((String)graphUser.getLocation().getProperty("name"));
        user.setHomeTown((String) graphUser.getProperty("hometown"));
        user.setUsername(graphUser.getUsername());
        user.setTimezone((Integer) graphUser.getProperty("timezone"));
        user.setLocale((String) graphUser.getProperty("locale"));
        user.setImage("http://graph.facebook.com/" + graphUser.getId() + "/picture");
        JSONArray languages = (JSONArray) graphUser.getProperty("languages");
        if (languages != null) {
            if (languages.length() > 0) {
                ArrayList<String> languageNames = new ArrayList<String>();
                for (int i = 0; i < languages.length(); i++) {
                    JSONObject language = languages.optJSONObject(i);
                    languageNames.add(language.optString("name"));
                }
                user.setLanguages(languageNames);
            }
        }
        return user;
    }
}
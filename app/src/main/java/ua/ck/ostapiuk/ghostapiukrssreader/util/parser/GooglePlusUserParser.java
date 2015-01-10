package ua.ck.ostapiuk.ghostapiukrssreader.util.parser;

import com.google.android.gms.plus.model.people.Person;

import java.util.ArrayList;

import ua.ck.ostapiuk.ghostapiukrssreader.entity.User;

/**
 * Created by Vova on 08.01.2015.
 */
public class GooglePlusUserParser {
    public User parse(Person person) {
        User user = new User();
        user.setName(person.getDisplayName());
        user.setImage(person.getImage().getUrl());
        user.setLocation(person.getCurrentLocation());
//        user.addLanguage(person.getLanguage());
        user.setUsername(person.getNickname());
        user.setBirthday(person.getBirthday());
        user.setLink(person.getUrl());
        user.setId(person.getId());
        return user;
    }
}

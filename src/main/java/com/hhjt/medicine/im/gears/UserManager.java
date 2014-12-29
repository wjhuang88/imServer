package com.hhjt.medicine.im.gears;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GHuang on 14/12/26.
 */
public class UserManager {

    private static UserManager ourInstance = new UserManager();
    public static UserManager getInstance() {
        return ourInstance;
    }

    private Map<String, UserData> userMap;

    private UserManager() {
        userMap = new HashMap<String, UserData>();
    }

    public String addUser(UserData data) {
        String key = data.getUserId();
        userMap.put(key, data);
        return key;
    }

    public UserData removeUser(String id) {
        if (userMap.containsKey(id)) {
            return userMap.remove(id);
        } else {
            return null;
        }
    }

    public UserData getUser(String id) {
        if (userMap.containsKey(id)) {
            return userMap.get(id);
        } else {
            return null;
        }
    }
}

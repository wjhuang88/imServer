package com.hhjt.medicine.im.gears;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Logger l = LoggerFactory.getLogger(ChannelManager.class);

    private UserManager() {
        userMap = new HashMap<String, UserData>();
    }

    public String addUser(UserData data) {
        String key = data.getUserId();
        userMap.put(key, data);
        l.info("User signed in, user_id = " + key + ", connect_id = " + data.getUserConnectId());
        return key;
    }

    public UserData removeUser(String id) {
        if (userMap.containsKey(id)) {
            l.info("User signed out, user_id = " + id);
            return userMap.remove(id);
        } else {
            l.info("User not exists.");
            return null;
        }
    }

    public UserData getUser(String id) {
        if (userMap.containsKey(id)) {
            return userMap.get(id);
        } else {
            l.info("User not exists.");
            return null;
        }
    }
}

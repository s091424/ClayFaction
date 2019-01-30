package me.clayclaw.clayfaction.database;

import java.util.List;

public class FactionData {

    public String uuid;
    public String name;
    public List<String> usersAndPermissions;
    public int capacity;

    public String convertUsersToString(){
        StringBuilder builder = new StringBuilder();
        usersAndPermissions.forEach(s -> builder.append(s));
        return builder.toString();
    }

}

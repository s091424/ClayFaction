package me.clayclaw.clayfaction;

import org.bukkit.ChatColor;

public enum GlobalMessage {

    ZHTW_RELOADED("&2\\u63d2\\u4ef6\\u91cd\\u65b0\\u8b80\\u53d6\\u6210\\u529f");

    String message;

    GlobalMessage(String message){
        this.message = message;
    }
    public String getMessage() {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String getMessage(String message){
        return GlobalMessage.valueOf(ClayFaction.LANGUAGE+message).getMessage();
    }

}

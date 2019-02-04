package me.clayclaw.clayfaction;

import org.bukkit.ChatColor;

public enum GlobalMessage {

    ZHTW_RELOADED("&2\\u63d2\\u4ef6\\u91cd\\u65b0\\u8b80\\u53d6\\u6210\\u529f"),
    ZHTW_MUSTBEPLAYER("&c\\u53ea\\u6709\\u73a9\\u5bb6\\u624d\\u80fd\\u4f7f\\u7528\\u6b64\\u6307\\u4ee4");

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

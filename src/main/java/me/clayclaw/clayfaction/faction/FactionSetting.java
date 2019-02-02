package me.clayclaw.clayfaction.faction;

public class FactionSetting {

    private FactionRank defaultRank;

    public FactionSetting(){

    }

    public FactionRank getDefaultRank() {
        return defaultRank;
    }
    public void setDefaultRank(FactionRank defaultRank) {
        this.defaultRank = defaultRank;
    }
}

package com.stgsporting.piehmecup.config;

public class DatabaseEnum {
    // Base ID for all tables
    public static final String baseId = "id";

    // OSRA TABLE
    public static final String osraTable = "OSRA";
    public static final String osraName = "name";

    // USERS TABLE
    public static final String usersTable = "USERS";
    public static final String username = "username";
    public static final String password = "password";
    public static final String osraId = "osraId";

    // BUTTONS VISIBILITY TABLE
    public static final String buttonsTable = "BUTTONS_VISIBILITY";
    public static final String buttonName = "name";
    public static final String visible = "visible";
    public static final String userRole = "userRole";

    // WELAD TABLE
    public static final String weladTable = "WELAD";
    public static final String currentCoins = "currentCoins";
    public static final String lineupRating = "lineupRating";
    public static final String cardRating = "cardRating";
    public static final String waladImgLink = "imgLink";

    // OSTAZ TABLE
    public static final String ostazTable = "OSTAZ";
    public static final String ostazId = "ostazId";

    // ADMINS TABLE
    public static final String adminsTable = "ADMINS";
    public static final String adminId = "adminId";

    // waladId for OWNED TABLES, ATTENDANCE TABLE
    public static final String waladId = "waladId";

    // OWNED PLAYERS TABLE
    public static final String ownedPlayersTable = "OWNED_PLAYERS";
    public static final String playerId = "playerId";

    // OWNED ICONS TABLE
    public static final String ownedIconsTable = "OWNED_ICONS";
    public static final String iconId = "iconId";

    // OWNED POSITIONS TABLE
    public static final String ownedPositionsTable = "OWNED_POSITIONS";
    public static final String positionId = "positionId";

    // Common attributes for PLAYERS, ICONS, POSITIONS TABLES
    public static final String price = "price";
    public static final String name = "name";
    public static final String available = "available";

    // PLAYERS TABLE
    public static final String playersTable = "PLAYERS";
    public static final String rating = "rating";
    public static final String playerImgLink = "imgLink";
    public static final String position = "position";

    // ICONS TABLE
    public static final String iconsTable = "ICONS";
    public static final String iconImgLink = "imgLink";

    // POSITIONS TABLE
    public static final String positionsTable = "POSITIONS";

    // PRICES TABLE
    public static final String pricesTable = "PRICES";
    public static final String liturgyName = "name";
    public static final String coins = "coins";

    // ATTENDANCE TABLE
    public static final String attendanceTable = "ATTENDANCE";
    public static final String timestamp = "timestamp";
    public static final String attendedLiturgy = "attendedLiturgy";
    public static final String approved = "approved";
}

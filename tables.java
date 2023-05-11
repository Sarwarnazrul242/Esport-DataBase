package test;

public class tables {
    static String gameTable = "CREATE TABLE IF NOT EXISTS Game(\n" //sql query to create table if it doesn't exist
            + "GameName TEXT NOT NULL, \n"
            + "Description TEXT NOT NULL, \n"
            + "NumPlayer  INTEGER NOT NULL, \n"
            + "PRIMARY KEY(GameName) \n"
            + ")";
    static String teamTable = "CREATE TABLE IF NOT EXISTS Team(\n" //sql query to create table if it doesn't exist
            + "GameName TEXT NOT NULL, \n"
            + "TeamID  INTEGER NOT NULL, \n"
            + "PRIMARY KEY(GameName, TeamID), \n"
            + "FOREIGN KEY(GameName) \n"
            + "References Game(GameName)\n"
            + "ON DELETE SET NULL\n"
            + "ON UPDATE CASCADE\n"
            + ")";
    static String userTable = "CREATE TABLE IF NOT EXISTS User(\n" //sql query to create table if it doesn't exist
            + "Email TEXT NOT NULL, \n"
            + "Password TEXT NOT NULL, \n"
            + "fName TEXT NOT NULL, \n"
            + "lName TEXT NOT NULL, \n"
            + "discordName TEXT , \n"
            + "userName TEXT, \n"
            + "GameName TEXT, \n"
            + "TeamID  INTEGER , \n"
            + "PRIMARY KEY(Email), \n"
            + "FOREIGN KEY(GameName, TeamID) \n"
            + "References Team(GameName, TeamID)\n"
            + "ON DELETE SET NULL\n"
            + "ON UPDATE CASCADE\n"
            + ")";
    static String tournamentTable = "CREATE TABLE IF NOT EXISTS Tournament(\n" //sql query to create table if it doesn't exist
            + "TourID  INTEGER NOT NULL, \n"
            + "Date  TEXT NOT NULL , \n"
            + "Status TEXT NOT NULL , \n"
            + "Season TEXT NOT NULL , \n"
            + "PRIMARY KEY(TourID) \n"
            + ")";
    static String competeTable = "CREATE TABLE IF NOT EXISTS Competition(\n" //sql query to create table if it doesn't exist
            + "GameName TEXT NOT NULL, \n"
            + "TeamID  INTEGER NOT NULL, \n"
            + "OpponentName  TEXT NOT NULL , \n"
            + "Result TEXT NOT NULL , \n"
            + "TourID  INTEGER NOT NULL, \n"
            + "PRIMARY KEY(GameName, TeamID, TourID), \n"
            + "FOREIGN KEY(GameName, TeamID) \n"
            + "References Team(GameName, TeamID)\n"
            + "ON DELETE SET NULL\n"
            + "ON UPDATE CASCADE,\n"
            + "FOREIGN KEY(TourID) \n"
            + "References Tournament(TourID)\n"
            + "ON DELETE SET NULL\n"
            + "ON UPDATE CASCADE\n"
            + ")";
    static String statTable = "CREATE TABLE IF NOT EXISTS Stat(\n" //sql query to create table if it doesn't exist
            + "GameName TEXT NOT NULL, \n"
            + "statName TEXT NOT NULL, \n"
            + "PRIMARY KEY(GameName, statName), \n"
            + "FOREIGN KEY(GameName) \n"
            + "References Game(GameName)\n"
            + "ON DELETE SET NULL\n"
            + "ON UPDATE CASCADE\n"
            + ")";
    static String leaderTable = "CREATE TABLE IF NOT EXISTS Leader(\n" //sql query to create table if it doesn't exist
            + "Email TEXT NOT NULL, \n"
            + "GameName TEXT NOT NULL, \n"
            + "TeamID  INTEGER NOT NULL, \n"
            + "startDate TEXT NOT NULL, \n"
            + "endDate TEXT, \n"
            + "PRIMARY KEY(Email, TeamID), \n"
            + "FOREIGN KEY(GameName, TeamID) \n"
            + "References Team(GameName,TeamID)\n"
            + "ON DELETE SET NULL\n"
            + "ON UPDATE CASCADE,\n"
            + "FOREIGN KEY(Email) \n"
            + "References User(Email)\n"
            + "ON DELETE SET NULL\n"
            + "ON UPDATE CASCADE\n"
            + ")";

}

package test;
import java.sql.*;
import java.lang.*;

public class admin {
    public static void addPlayerDB(PreparedStatement pstmt, ResultSet myRs) {
        int   tID;
        String id, checker, pass, fname, lname,disName, uName, gName;
        try {
            System.out.print("Email: ");
            id = esports.s.next(); //gets email from user
            while (esports.myRs.next()) { //checks to make sure the user is in the DB
                checker = esports.myRs.getString("Email");
                while (id == checker) {
                    System.out.print("Email should be unique. Enter another Email\nEmail: ");
                    id = esports.s.next();
                }
            }
            System.out.print("Default Password: ");
            pass = esports.s.next();
            System.out.print("First Name: ");
            fname = esports.s.next();
            System.out.print("Last Name: ");
            lname = esports.s.next();
            System.out.print("Discord Name: ");
            disName = esports.s.next();
            System.out.print("Username for the game the user competes in: ");
            uName = esports.s.next();
            System.out.print("Game the user competes in: ");
            esports.s.nextLine();
            gName = esports.s.nextLine();
            System.out.print("Team ID: ");
            tID = esports.s.nextInt(); //gets last name from user
            esports.pstmt.setString(1, id);
            esports.pstmt.setString(2, pass); //inputs all the data
            esports.pstmt.setString(3, fname);
            esports.pstmt.setString(4, lname);
            esports.pstmt.setString(5, disName);
            esports.pstmt.setString(6, uName);
            esports.pstmt.setString(7, gName);
            esports.pstmt.setInt(8, tID);
            esports.pstmt.executeUpdate();
            System.out.println("Player Added to system.\n");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public static void removePlayerDB(PreparedStatement pstmt, ResultSet myRs){
        int   tID;
        String id, checker, pass, fname, lname,disName, uName, gName, confirm;
        try {
            System.out.print("Enter Email of player to be removed: ");
            id = esports.s.next(); //gets email from user
            while (esports.myRs.next()) {
                checker = esports.myRs.getString("Email"); //gets all the value in each row
                pass = esports.myRs.getString("Password");
                fname = esports.myRs.getString("fName");
                lname = esports.myRs.getString("lName");
                disName = esports.myRs.getString("discordName");
                uName = esports.myRs.getString("userName");
                gName = esports.myRs.getString("GameName");
                tID = esports.myRs.getInt("TeamID");
                if (checker.equals(id)) { //if the id is equal to the current result drop the employee
                    System.out.println("Are you sure you would like to remove player? (y or n)");
                    confirm = esports.s.next();
                    if(confirm.equals("y")) {
                        esports.pstmt.setString(1, id);
                        esports.pstmt.executeUpdate();
                        System.out.println("The following Player has been removed from the system:");
                        System.out.println(checker + ", " + fname + ", " + lname + "\n");
                    }
                    else {
                        System.out.println("The following Player was NOT removed from the system:\n");
                        System.out.println(checker + ", " + fname + ", " + lname);
                        return;
                    }
                }
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }

    public static void addCaptain(PreparedStatement pstmt, ResultSet myRs) {
        int tID, noDB = 1;
        String id, checker, gName, startDate;
        try {
            System.out.println("Enter email of the user to be captain: ");
            id = esports.s.next(); //gets email from user
            while (esports.myRs.next()) { //goes through rows to make sure the id isn't already in there
                checker = esports.myRs.getString("Email");
                if(checker.equals(id)) {
                    noDB = 0;
                    break;
                }
            }
            if(noDB == 0){
                esports.myRs = esports.myStmt.executeQuery(esports.viewCaptains);
                while(esports.myRs.next()){
                    checker = esports.myRs.getString("Email");
                    while (checker.equals(id)) {
                        System.out.print("The user with this email is already a captain. Enter another Email\\nEmail: ");
                        id = esports.s.next();
                    }
                }
            }
            else{
                System.out.println("This user is not in the system");
                return;
            }
            System.out.println("Which game will they be captain of?: ");
            esports.s.nextLine();
            gName = esports.s.nextLine();
            System.out.println("Which team will they be captain of? (1 or 2): ");
            tID = esports.s.nextInt();
            System.out.println("When will their duties of captain begin? (MM-DD-YYYY): ");
            startDate = esports.s.next();
            esports.pstmt.setString(1, id);
            esports.pstmt.setString(2, gName);
            esports.pstmt.setInt(3, tID);
            esports.pstmt.setString(4, startDate);
            esports.pstmt.executeUpdate();
            System.out.println("Player successfully added as a captain.\n");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public static void removeCaptain(PreparedStatement pstmt, ResultSet myRs){
        int   tID, noDB = 1;
        String id, checker, confirm, endDate;
        try {
            System.out.println("Enter Email of captain to be removed: ");
            id = esports.s.next(); //gets email from user
            while (esports.myRs.next()) {
                checker = esports.myRs.getString("Email"); //gets all the value in each row
                if (checker.equals(id)) { //if the id is equal to the current result drop the employee
                    noDB = 0;
                    break;
                }
            }
            if(noDB == 0){
                esports.myRs = esports.myStmt.executeQuery(esports.viewCaptains);
                while(esports.myRs.next()){
                    checker = esports.myRs.getString("Email");
                    if(checker.equals(id)) {
                        System.out.println("Are you sure you would like to remove captain? (y or n)");
                        confirm = esports.s.next();
                        if(confirm.equals("y")) {
                            System.out.println("When will their duties of captain end? (MM-DD-YYYY): ");
                            endDate = esports.s.next();
                            esports.pstmt.setString(1, endDate);
                            esports.pstmt.setString(2, id);
                            esports.pstmt.executeUpdate();
                            System.out.println("The following captain has been removed:");
                            System.out.println(checker);
                        }
                        else {
                            System.out.println("The following captain was NOT removed:\n");
                            System.out.println(checker + "\n");
                            return;
                        }
                    }
                }
            }
            else{
                System.out.println("This user is not in the system\n");
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }
    public static void displayCaptains(ResultSet myRs){ //uses query that select * to display all the employees
        int tID;
        String email, gName, sDate, eDate;
        String[][] captains = new String[5][100];
        try {
            System.out.println("Captain List:\n");
            captains[0][0] =  "Email:\t";
            captains[1][0] =  "Game:\t";
            captains[2][0] =  "Team:\t";
            captains[3][0] =  "Start Date:";
            captains[4][0] =  "End Date:";
            int j = 0;
            while (esports.myRs.next()) {
                email = esports.myRs.getString("Email");
                gName = esports.myRs.getString("GameName");
                tID = esports.myRs.getInt("TeamID");
                sDate = esports.myRs.getString("startDate");
                eDate = esports.myRs.getString("endDate");
                j = j + 1;
                captains[0][j] =  email;
                captains[1][j] =  gName;
                captains[2][j] =  Integer.toString(tID);
                captains[3][j] =  sDate;
                captains[4][j] =  eDate;
            }
            for(int i = 0; i < captains.length; i++){
                for(int h = 0; h <=j; h++){
                    System.out.print(captains[i][h] + "\t");
                }
                System.out.println();
            }
            System.out.println();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }
    public static void addGame(PreparedStatement pstmt, ResultSet myRs){
        int numPlay;
        String id, checker, desc;
        try {
            System.out.print("Game Name: ");
            esports.s.nextLine();
            id = esports.s.nextLine(); //gets email from user
            while (esports.myRs.next()) { //checks to make sure the user is in the DB
                checker = esports.myRs.getString("GameName");
                while (id == checker) {
                    System.out.print("This game already exists please enter a new game name: ");
                    esports.s.nextLine();
                    id = esports.s.nextLine();
                }
            }
            System.out.println("Game Description:");
            //esports.s.nextLine();
            desc = esports.s.nextLine();
            System.out.println("Number of players needed to compete: ");
            numPlay = esports.s.nextInt();
            esports.pstmt.setString(1, id);
            esports.pstmt.setString(2, desc);
            esports.pstmt.setInt(3, numPlay);
            esports.pstmt.executeUpdate();
            System.out.println("Game added successfully.");
        }catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public static void removeGame(PreparedStatement pstmt, ResultSet myRs){
        int   numPlay;
        String id, checker, desc, confirm;
        try {
            System.out.print("Enter name of game to be removed: ");
            esports.s.nextLine();
            id = esports.s.nextLine(); //gets email from user
            while (esports.myRs.next()) {
                checker = esports.myRs.getString("GameName"); //gets all the value in each row
                System.out.println(checker);
                desc = esports.myRs.getString("Description");
                numPlay = esports.myRs.getInt("NumPlayer");
                if (checker.equals(id)) { //if the id is equal to the current result drop the employee
                    System.out.println("Are you sure you would like to remove game? (y or n)");
                    confirm = esports.s.next();
                    if(confirm.equals("y")) {
                        esports.pstmt.setString(1, id);
                        esports.pstmt.executeUpdate();
                        System.out.println("The following Game has been removed from the system:");
                        System.out.println(checker + ", " + desc + ", " + numPlay);
                    }
                    else {
                        System.out.println("The following Game was NOT removed from the system:\n");
                        System.out.println(checker);
                    }
                    return;
                }
            }
            System.out.println("This game is not in the system.");
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }
    public static void addTournament(PreparedStatement pstmt, ResultSet myRs){
        int tourID = 1;
        String date,season,status;
        try {
            while (esports.myRs.next()) { //checks to make sure the user is in the DB
                tourID++; //makes the tournament id the next value
            }
            System.out.println("What is the status for the tournament? (upcoming,completed, or cancelled):");
            status = esports.s.next();
            System.out.println("When will/did the tournament occur? (MM-DD-YYYY):");
            date = esports.s.next();
            System.out.println("During which season is the competition? (YYYY):");
            season = esports.s.next();
            esports.pstmt.setInt(1, tourID);
            esports.pstmt.setString(2, date);
            esports.pstmt.setString(3, status);
            esports.pstmt.setString(4,season);
            esports.pstmt.executeUpdate();
            System.out.println("Tournament added!");
        }catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public static void updateStatus(PreparedStatement pstmt, ResultSet myRs){
        int tourID;
        String date,season,status;
        try {
            while (esports.myRs.next()) { //checks to make sure the user is in the DB
                tourID = esports.myRs.getInt("TourID");
                date = esports.myRs.getString("Date");
                status = esports.myRs.getString("Status");
                season = esports.myRs.getString("Season");
            }
            System.out.println("Which tournament status would you like to update? (tourID):");
            tourID = esports.s.nextInt();
            System.out.println("What is the new status for the tournament? (upcoming,completed, or cancelled):");
            status = esports.s.next();
            esports.pstmt.setInt(1, tourID);
            esports.pstmt.setString(2, status);
            esports.pstmt.executeUpdate();
            System.out.println("Tournament updated!");
        }catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public static void addCompetition(PreparedStatement pstmt, ResultSet myRs){
        int tid, tourID,checkT, checkTour;
        String gName, oName, result, checkG;
        try {
            System.out.println("Which tournament did the competition occur? (tourID): ");
            tourID = esports.s.nextInt();
            System.out.println("Which game was played at the competition?: ");
            esports.s.nextLine();
            gName = esports.s.nextLine();
            System.out.println("Which team competed at the competition?: ");
            tid = esports.s.nextInt();
            while (esports.myRs.next()) { //checks to make sure the user is in the DB
                checkG = esports.myRs.getString("GameName");
                checkT = esports.myRs.getInt("TeamID");
                oName = esports.myRs.getString("OpponentName");
                result = esports.myRs.getString("Result");
                checkTour = esports.myRs.getInt("TourID");
                while(checkG.equals(gName) && checkT == tid && checkTour == tourID){
                    System.out.println("This competition already exists. ");
                    System.out.println("Which tournament did the competition occur? (tourID): ");
                    tourID = esports.s.nextInt();
                    System.out.println("Which game was played at the competition?: ");
                    esports.s.nextLine();
                    gName = esports.s.nextLine();
                    System.out.println("Which team competed at the competition?: ");
                    tid = esports.s.nextInt();
                }
            }
            System.out.println("Who was the opponent?: ");
            esports.s.nextLine();
            oName = esports.s.nextLine();
            System.out.println("What was the result? (win or loss): ");
            result = esports.s.next();
            esports.pstmt.setString(1, gName);
            esports.pstmt.setInt(2, tid);
            esports.pstmt.setString(3, oName);
            esports.pstmt.setString(4, result);
            esports.pstmt.setInt(5, tourID);
            esports.pstmt.executeUpdate();
            System.out.println("Competition added!");
        }catch (SQLException se) {
            se.printStackTrace();
        }
    }
}

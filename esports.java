package test;
import java.sql.*;
import java.util.Scanner;
import java.lang.*;

public class esports {

    static Connection myConn = null;
    static Statement myStmt = null;
    static ResultSet myRs = null;
    static PreparedStatement pstmt = null;
    static Scanner s = new Scanner(System.in);
    static int choice;
    static String viewCaptains = "SELECT * FROM Leader";
    static String addToTeam = "UPDATE User SET GameName = ? AND TeamID = ? WHERE Email = ?";
    static String removeFromTeam = "UPDATE User SET GameName = NULL AND TeamID = NULL WHERE Email = ?";

    public static void main(String[] args) {

        String email, password, checks, checkE, checkDate;
        int notInDB = 0, getPass = 1;
        int isCAP = 0;
        //check email or pass
        String checkEmail = "SELECT Email FROM User"; //check if the user is in the database, if not they cannot get access
        String checkPassword = "SELECT Password FROM User"; //checks the password for the email and will be used to see if they match

        //makes changes to DB
        String changePassword = "UPDATE User SET Password = ? WHERE Email = ?";
        String addCaptain = "INSERT INTO Leader(Email, GameName, TeamID, startDate) VALUES(?,?,?,?)";
        String addPlayerToDB = "INSERT INTO User(Email, Password, fName, lName, discordName, userName, GameName, TeamID) VALUES(?,?,?,?,?,?,?,?)";
        String addGame = "INSERT INTO Game(GameName, Description, NumPlayer) VALUES(?,?,?)";
        String addCompetition = "INSERT INTO Competition(GameName, TeamID, OpponentName, Result, TourID) VALUES(?,?,?,?,?)";
        String addTournament = "INSERT INTO Tournament(TourID, Date, Status, Season) VALUES(?,?,?,?)";
        String removePlayerFromDB = "DELETE FROM User Where Email = ?";
        String removeCaptain = "UPDATE Leader Set endDate = ? WHERE Email = ?";
        String removeGame = "DELETE FROM Game WHERE GameName = ?";
        String updateTournamentStatus = "UPDATE Tournament SET status = ? WHERE TourID = ?";


        //used for viewing
        String viewTeam = "SELECT fName, lName FROM User WHERE GameName = ? AND TeamID = ? ";
        String viewCompetition = "SELECT * FROM Competition WHERE GameName = ? ";
        String checkComp = "SELECT * FROM Competition";
        String viewTournament = "SELECT * FROM Tournament WHERE Season = ?";
        String viewUsers = "SELECT * FROM User";
        String viewPlayerBasicInfo = "SELECT Email, fName, lName FROM User";
        String viewGame = "SELECT * FROM Game";
        String getTourID = "SELECT * FROM Tournament";
        String checkCap = "SELECT Email FROM Leader";
        String checkCapTeam = "SELECT GameName, TeamID FROM Leader Where Email = ?";



        try {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection("jdbc:sqlite:esports.db");

            // 2. Create a statement
            myStmt = myConn.createStatement(); //ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY wanted to do this to allow for error checking but not supported by SQLITE

            //3. Execute SQL Query
            myStmt.executeUpdate(tables.gameTable);
            myStmt.executeUpdate(tables.teamTable);
            myStmt.executeUpdate(tables.userTable);
            myStmt.executeUpdate(tables.tournamentTable);
            myStmt.executeUpdate(tables.competeTable);
            myStmt.executeUpdate(tables.leaderTable);
            myStmt.executeUpdate(tables.statTable);

            //4. Login
            System.out.println("\nWelcome to the Detroit Mercy esports application! You must login to enter. \nEmail: ");
            email = s.next();
            myRs = myStmt.executeQuery(checkEmail);
            while (myRs.next()) {
                checks = myRs.getString("Email");
                if (checks.equals(email)) {
                    notInDB = 0;
                    break;
                } else notInDB = 1;
            }
            if (notInDB == 1) {
                System.out.println("Email was not found. Please contact esports administrator.");
                System.exit(0);
            }
            while (getPass == 1) { //loops until password is correct
                System.out.println("Password: ");
                password = s.next();
                myRs = myStmt.executeQuery(checkPassword);
                while (myRs.next() && getPass == 1) {
                    checks = myRs.getString("Password");
                    if (checks.equals(password)) {
                        getPass = 0;
                    }
                }
                if (getPass == 1) {
                    System.out.println("Password Incorrect! Please try again. ");
                }
            }
            /*myRs = myStmt.executeQuery(checkCap);
            while(myRs.next()){
                checkE = myRs.getString("Email");
                checkDate = myRs.getString("endDate"); //makes sure there is no end date
                if(email.equals(checkE) && checkDate == null){
                    isCAP = 1;
                    break;
                }
            }*/
            //Check if admin, captain, or user
            if (email.equals("manorlm@udmercy.edu")) { //make constant variable for email
                System.out.println("Hello Admin!\n");
                while (choice != 17) {
                    System.out.print(
                            "Main Menu\n(1) Add a player to the system\n(2) Remove a player from the system\n" //submenus for th
                                    + "(3) Display players\n(4) Add Captain\n(5) Remove Captain\n(6) Display Captains\n(7) Add Game\n(8) Remove Game\n(9) Display Games\n(10) Add Tournament\n"
                                    + "(11) Update Tournament Status\n(12) Display Tournaments\n(13) Add Competition\n(14) Display Competition\n(15) View team\n(16) Change Password\n(17) Log out\nEnter your choice: "
                    );
                    choice = s.nextInt();
                    System.out.println();
                    switch (choice) {
                        case 1:
                            pstmt = myConn.prepareStatement(addPlayerToDB);
                            myRs = myStmt.executeQuery(checkEmail);
                            admin.addPlayerDB(pstmt, myRs);
                            break;
                        case 2:
                            pstmt = myConn.prepareStatement(removePlayerFromDB);
                            myRs = myStmt.executeQuery(viewUsers);
                            admin.removePlayerDB(pstmt, myRs);
                            break;
                        case 3:
                            myRs = myStmt.executeQuery(viewPlayerBasicInfo);
                            user.displayPlayers(myRs);
                            break;
                        case 4:
                            pstmt = myConn.prepareStatement(addCaptain);
                            myRs = myStmt.executeQuery(viewUsers);
                            admin.addCaptain(pstmt, myRs);
                            break;
                        case 5:
                            pstmt = myConn.prepareStatement(removeCaptain);
                            myRs = myStmt.executeQuery(viewUsers);
                            admin.removeCaptain(pstmt, myRs);
                            break;
                        case 6:
                            myRs = myStmt.executeQuery(viewCaptains);
                            admin.displayCaptains(myRs);
                            break;
                        case 7:
                            pstmt = myConn.prepareStatement(addGame);
                            myRs = myStmt.executeQuery(viewGame);
                            admin.addGame(pstmt, myRs);
                            break;
                        case 8:
                            pstmt = myConn.prepareStatement(removeGame);
                            myRs = myStmt.executeQuery(viewGame);
                            admin.removeGame(pstmt, myRs);
                            break;
                        case 9:
                            myRs = myStmt.executeQuery(viewGame);
                            user.displayGames(myRs);
                            break;
                        case 10:
                            pstmt = myConn.prepareStatement(addTournament);
                            myRs = myStmt.executeQuery(getTourID);
                            admin.addTournament(pstmt, myRs);
                            break;
                        case 11:
                            pstmt = myConn.prepareStatement(updateTournamentStatus);
                            myRs = myStmt.executeQuery(getTourID);
                            admin.updateStatus(pstmt, myRs);
                            break;
                        case 12:
                            pstmt = myConn.prepareStatement(viewTournament);
                            user.displayTournaments(pstmt);
                            break;
                        case 13:
                            pstmt = myConn.prepareStatement(addCompetition);
                            myRs = myStmt.executeQuery(checkComp);
                            admin.updateStatus(pstmt, myRs);
                            break;
                        case 14:
                            pstmt = myConn.prepareStatement(viewCompetition);
                            user.displayCompetitions(pstmt);
                            break;
                        case 15:
                            pstmt = myConn.prepareStatement(viewTeam);
                            user.displayTeam(pstmt);
                            break;
                        case 16:
                            pstmt = myConn.prepareStatement(changePassword);
                            user.changePass(pstmt);
                            break;
                        case 17:
                            System.out.println("Goodbye!");
                            System.exit(0);
                    }
                }
            }
            if(isCAP == 1){
                System.out.println("Hello Captain!\n");
                while (choice != 9) {
                    System.out.print(
                            "Main Menu\n(1) Add a player to your team\n(2) Remove a player from your team\n"
                                    + "(3) Display players\n(4) Display Games\n(5) Display Tournaments\n"
                                    + "(6) Display Competition\n(7) View team\n(8) Change Password\n(9) Log out\nEnter your choice: "
                    );
                    choice = s.nextInt();
                    System.out.println();
                    switch (choice) {
                        case 1:
                            pstmt = myConn.prepareStatement(checkCapTeam);
                            pstmt.setString(1, email);
                            myRs = pstmt.executeQuery(); //gets game and team the captain is leader of to only allow them to edit their team
                            pstmt = myConn.prepareStatement(viewUsers);
                            captain.addPlayer(pstmt, myRs);
                            break;
                        case 2:
                            pstmt = myConn.prepareStatement(checkCapTeam);
                            pstmt.setString(1, email);
                            myRs = pstmt.executeQuery();
                            pstmt = myConn.prepareStatement(viewUsers);
                            captain.removePlayer(pstmt, myRs);
                            break;
                        case 3:
                            myRs = myStmt.executeQuery(viewPlayerBasicInfo);
                            user.displayPlayers(myRs);
                            break;
                        case 4:
                            myRs = myStmt.executeQuery(viewGame);
                            user.displayGames(myRs);
                            break;
                        case 5:
                            pstmt = myConn.prepareStatement(viewTournament);
                            user.displayTournaments(pstmt);
                            break;
                        case 6:
                            pstmt = myConn.prepareStatement(viewCompetition);
                            user.displayCompetitions(pstmt);
                            break;
                        case 7:
                            pstmt = myConn.prepareStatement(viewTeam);
                            user.displayTeam(pstmt);
                            break;
                        case 8:
                            pstmt = myConn.prepareStatement(changePassword);
                            user.changePass(pstmt);
                            break;
                        case 9:
                            System.out.println("Goodbye!");
                            System.exit(0);
                    }
                }
            }
            else{
                System.out.println("Hello User!\n");
                while (choice != 7) {
                    System.out.print(
                            "Main Menu\n"
                                    + "(1) Display players\n(2) Display Games\n(3) Display Tournaments\n"
                                    + "(4) Display Competition\n(5) View team\n(6) Change Password\n(7) Log out\nEnter your choice: "
                    );
                    choice = s.nextInt();
                    System.out.println();
                    switch (choice) {
                        case 1:
                            myRs = myStmt.executeQuery(viewPlayerBasicInfo);
                            user.displayPlayers(myRs);
                            break;
                        case 2:
                            myRs = myStmt.executeQuery(viewGame);
                            user.displayGames(myRs);
                            break;
                        case 3:
                            pstmt = myConn.prepareStatement(viewTournament);
                            user.displayTournaments(pstmt);
                            break;
                        case 4:
                            pstmt = myConn.prepareStatement(viewCompetition);
                            user.displayCompetitions(pstmt);
                            break;
                        case 5:
                            pstmt = myConn.prepareStatement(viewTeam);
                            user.displayTeam(pstmt);
                            break;
                        case 6:
                            pstmt = myConn.prepareStatement(changePassword);
                            user.changePass(pstmt);
                            break;
                        case 7:
                            System.out.println("Goodbye!");
                            System.exit(0);
                    }
                }
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            try {
                if (myStmt != null)
                    myStmt.close();
            } catch (SQLException se2) {
            }// nothing we can do

            try {
                if (myConn != null)
                    myConn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try

            try {
                if (myRs != null)
                    myRs.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }
}
package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class captain {
    public static void addPlayer(PreparedStatement pstmt, ResultSet myRs){
        int tID, noDB = 1;
        String gName, email, checker;
        try {
            while (esports.myRs.next()) { //checks to make sure the user is in the DB
                gName = esports.myRs.getString("GameName");
                tID = esports.myRs.getInt("TeamID");
                System.out.println("Enter email of player you would like to add:");
                email = esports.s.next();
                esports.pstmt.setString(1, email);
                esports.myRs = esports.pstmt.executeQuery();
                while (esports.myRs.next()) {
                    checker = esports.myRs.getString("Email");
                    if(checker.equals(email)) {
                        noDB = 0;
                        break;
                    }
                }
                if(noDB == 0){
                    esports.pstmt = esports.myConn.prepareStatement(esports.addToTeam);
                    esports.pstmt.setString(1, gName);
                    esports.pstmt.setInt(2, tID);
                    esports.pstmt.setString(3, email);
                    esports.pstmt.executeUpdate();
                    System.out.println("Player Added to your team.\n");
                }
                else{
                    System.out.println("This user is not in the system");
                    return;
                }
            }
        }catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public static void removePlayer(PreparedStatement pstmt, ResultSet myRs){
        int tID, noDB = 1, uTID;
        String gName, email, checker, uGName;
        try {
            while (esports.myRs.next()) { //checks to make sure the user is in the DB
                gName = esports.myRs.getString("GameName");
                tID = esports.myRs.getInt("TeamID");
                System.out.println("Enter email of player you would like to remove:");
                email = esports.s.next();
                esports.pstmt.setString(1, email);
                esports.myRs = esports.pstmt.executeQuery();
                while (esports.myRs.next()) {
                    checker = esports.myRs.getString("Email");
                    uGName = esports.myRs.getString("GameName");
                    uTID = esports.myRs.getInt("TeamID");
                    if(checker.equals(email)) {
                        if(uGName.equals(gName)){
                            if(uTID == tID){
                                noDB = 0;
                                break;
                            }
                            else{
                                System.out.println("This user is not on your team");
                                return;
                            }
                        }
                        else{
                            System.out.println("This user is not on your team");
                            return;
                        }
                    }
                    else{
                        System.out.println("This user is not in the system");
                        return;
                    }
                }
                if(noDB == 0){
                    esports.pstmt = esports.myConn.prepareStatement(esports.removeFromTeam);
                    esports.pstmt.setString(1, email);
                    esports.pstmt.executeUpdate();
                    System.out.println("Player Removed from your team.\n");
                }
                else{
                    System.out.println("This user is not in the system");
                    return;
                }
            }
        }catch (SQLException se) {
            se.printStackTrace();
        }
    }
}

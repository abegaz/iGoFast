package edu.ung.mccb.csci.csci3300.iGoFast.Model;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Admin {

    String adminID, username, password, salt;

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int saveUserIntoDatabase(String adminID, String username, String password, String salt) {
        int affectedRow=0;
        String query = "insert into admin" + "(adminID, username, password, salt)"
                + "values(?,?,?,?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement sqlStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            sqlStatement.setString(1, adminID);
            sqlStatement.setString(2, username);
            sqlStatement.setString(3, password);
            sqlStatement.setString(4, salt);

            // get the number of return rows
            affectedRow = sqlStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Status: operation failed due to " + e);

        }
        return affectedRow;

    }

    public String generateSaltedHashedPassword(String passwordToHash, String salt)  {
        String generatedHashedPassword = null;
        BufferedReader readSalt = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            //Plain text password: passwordToHash
            messageDigest.update(passwordToHash.getBytes(StandardCharsets.UTF_8));
            messageDigest.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = messageDigest.digest();
            StringBuilder sBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedHashedPassword = sBuilder.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        finally {
            if(readSalt!= null) {
                try {
                    readSalt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return generatedHashedPassword;
    }
    public boolean verifyLogin(String password, String adminID)
    {
        boolean isRegistered=false;
        String query = "select * from admin where adminid='"+adminID+"'";

        try {

            Connection conn = DatabaseConfig.getConnection();
            Statement stmts = (Statement) conn.createStatement();
            ResultSet rs = stmts.executeQuery(query);
            if (!rs.next()) {
                throw new SecurityException("Admin is not registered");
            } else {
                String storedHashedPassword = rs.getString("password");
                String storedSalt = rs.getString("salt");
                String generatedHashedPassword = generateSaltedHashedPassword(password, storedSalt);
                if (storedHashedPassword.equals(generatedHashedPassword)) {
                    isRegistered = true;
                } else {
                    System.out.println("Login failed");
                }

            }
        } catch (Exception e) {
            System.out.println("Status: operation failed due to " + e);

        }
        return isRegistered;

    }


}

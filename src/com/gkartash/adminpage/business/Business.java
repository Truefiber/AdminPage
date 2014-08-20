package com.gkartash.adminpage.business;

import com.gkartash.adminpage.data.DBManager;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by gkartashevskyy on 8/15/2014.
 */
public class Business {

    private static PreparedStatement statement = null;
    private static Map<String, Boolean> mapOfUsersStatus = null;

    static Logger logger = Logger.getLogger(Business.class.getCanonicalName());

    public static List<String> getUserList() {

        return null;
    }

    public static boolean isAdmin(String login, String password) {

        boolean isSuccessLogin = false;

        statement = DBManager.getLoginStatement(login, password);
        try {

            logger.info(statement.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isSuccessLogin = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.info("We logged in: " + isSuccessLogin);

        return isSuccessLogin;


    }

    public static Map<String, Boolean> getMapOfUsers(){

        statement = DBManager.getUsersStatusQuery();
        mapOfUsersStatus = new HashMap<String, Boolean>();

        try {
            ResultSet resultSet = statement.executeQuery();

            String user = "";
            boolean isOnline = false;
            Date lastStatusChange = null;

            while (resultSet.next()) {
                if (resultSet.getString(DBManager.CLIENT_FIELD).equals(user)) {
                    if (!lastStatusChange.after(new Date(resultSet.getTimestamp(1).getTime()))) {

                        isOnline = resultSet.getBoolean(DBManager.STATUS_FIELD);
                        logger.info("same user, another status");
                    }



                } else {
                    user = resultSet.getString(DBManager.CLIENT_FIELD);
                    isOnline = resultSet.getBoolean(DBManager.STATUS_FIELD);
                    lastStatusChange = new Date(resultSet.getTimestamp(1).getTime());
                    logger.info("Date " + lastStatusChange);
                }

                if (!user.equals("null")){
                mapOfUsersStatus.put(user, isOnline);
                    logger.info("Map of users: " + user + ", " + isOnline);
                }

            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapOfUsersStatus;
    }

    public static XYSeriesCollection getUserStats(){
        statement = DBManager.getStatQuery();
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("Users online");
        int userOnlineCounter = 0;

        try {
            ResultSet resultSet = statement.executeQuery();
            logger.info("Before while ");
            while (resultSet.next()) {

                if (resultSet.getBoolean(DBManager.STATUS_FIELD)) {
                    userOnlineCounter++;
                } else {
                    userOnlineCounter--;
                }

                logger.info("User counter " + userOnlineCounter + ", time " + resultSet.getTime(1).getTime());
                //Почему время не идёт последовательно?
                s1.add(resultSet.getTime(1).getTime(), userOnlineCounter);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataset.addSeries(s1);

        return dataset;

    }


}

package com.carloscb21.contactos.util;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Administrador on 15/03/2015.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil{
    public static void main(String [] args) throws IOException, SQLException{
        writeConfigFile("ornlite_config.txt");
    }
}

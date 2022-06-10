package com.cfive.classroom.library.database;

import com.cfive.classroom.library.database.util.NoConfigException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class PoolHelper {
    private static final Logger LOGGER = LogManager.getLogger();
    private static HikariDataSource hikariDataSource;
    private static String JDBC_URL = "jdbc:mysql://%s:%s/%s?serverTimezone=Asia/Shanghai&useSSL=false&characterEncoding=utf8";
    private static String JDBC_USERNAME;
    private static String JDBC_PASSWORD;
    private static final String CACHE_PREP_STMTS = "true";
    private static final String PREP_STMT_CACHE_SIZE = "100";
    private static final String MAXIMUM_POOL_SIZE = "10";

    public static void init() throws NoConfigException {
        loadProperties();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(JDBC_USERNAME);
        config.setPassword(JDBC_PASSWORD);
        config.addDataSourceProperty("cachePrepStmts", CACHE_PREP_STMTS);
        config.addDataSourceProperty("prepStmtCacheSize", PREP_STMT_CACHE_SIZE);
        config.addDataSourceProperty("maximumPoolSize", MAXIMUM_POOL_SIZE);
        hikariDataSource = new HikariDataSource(config);
    }

    private static  void loadProperties() throws NoConfigException {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("mysql.properties"));
            if (properties.getProperty("ip").isEmpty() || properties.getProperty("port").isEmpty() || properties.getProperty("database").isEmpty() || properties.getProperty("username").isEmpty() || properties.getProperty("password").isEmpty()) {
                LOGGER.error("配置有误，请重新配置或删除配置文件重新生成");
                throw new NoConfigException();
            } else {
                JDBC_URL = String.format(JDBC_URL, properties.getProperty("ip"), properties.getProperty("port"), properties.getProperty("database"));
                JDBC_USERNAME = properties.getProperty("username");
                JDBC_PASSWORD = properties.getProperty("password");
            }
        } catch (IOException e) {
            properties.setProperty("ip", "");
            properties.setProperty("port", "");
            properties.setProperty("database", "");
            properties.setProperty("username", "");
            properties.setProperty("password", "");
            try {
                properties.store(new FileWriter("mysql.properties"), "MySQL Configuration");
                LOGGER.info("已创建配置文件“mysql.properties”");
            } catch (IOException ioException) {
                LOGGER.error("无法创建配置文件“mysql.properties”,请手动配置：创建配置文件并添加以下内容\nip=[数据库IP]\nport=[数据库端口]\ndatabase=[数据库名]\nusername=[数据用户名]\npassword=[数据库密码]", e);
            }
            throw new NoConfigException();
        }

    }

    public static void close() {
        hikariDataSource.close();
    }

    public static Connection getConnection() throws SQLException, NoConfigException {
        if (hikariDataSource == null) {
            init();
        }
        return hikariDataSource.getConnection();
    }
}

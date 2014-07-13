
package com.sniperzciinema.mcinfected.MySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.plugin.Plugin;


public class MySQLManager {
	
	private Connection	connection;
	private MySQL				mysql;
	private Statement		statement;
	
	/**
	 * Create a mysql connection
	 * 
	 * @param plugin
	 * @param host
	 * @param port
	 * @param database
	 * @param username
	 * @param password
	 */
	public MySQLManager(Plugin plugin, String host, String port, String database, String username, String password)
	{
		this.mysql = new MySQL(plugin, host, port, database, username, password);
	}
	
	/**
	 * First columns is the player's UUID
	 * For example:
	 * <p>
	 * "Kills INT(10), Deaths INT(10), Points INT(10), Score INT(10), PlayingTime INT(15), HighestKillStreak INT(10)"
	 * <p>
	 * ^ Would check if a table with all that exists, and if not it creates it
	 * 
	 * @param table
	 * @param mySqlColoumData
	 * @throws SQLException
	 */
	public void checkIfTableExists(String table, String mySqlColoumData) throws SQLException {
		this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table + " (UUID VARCHAR(40), " + mySqlColoumData + ");");
	}
	
	/**
	 * Closes the connection
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		this.connection.close();
		this.connection = null;
	}
	
	/**
	 * Closes the statement
	 * 
	 * @throws SQLException
	 */
	public void closeStatement() throws SQLException {
		this.statement.close();
		this.statement = null;
	}
	
	/**
	 * Assumes all players are in columns 1
	 * 
	 * @param tableName
	 *          - The tables name
	 * @return All the players in the table
	 */
	public ArrayList<UUID> getAllPlayers(String tableName) {
		try
		{
			ResultSet set = this.statement.executeQuery("SELECT * FROM `" + tableName + "` ");
			ArrayList<UUID> players = new ArrayList<UUID>();
			while (true)
			{
				set.next();
				players.add(UUID.fromString(set.getString("UUID")));
				if (set.isLast())
					break;
			}
			set.close();
			return players;
		}
		catch (SQLException e)
		{
			ArrayList<UUID> nope = new ArrayList<UUID>();
			return nope;
		}
	}
	
	/**
	 * In the try and catch, put setInt with the parameters
	 * 
	 * @param tableName
	 *          - The tables name
	 * @param columnName
	 *          - The stats name
	 * @param uuid
	 * @return the players stats
	 * @throws SQLException
	 */
	public int getInt(String tableName, String columnName, UUID uuid) throws SQLException {
		ResultSet set = this.statement.executeQuery("SELECT " + columnName + " FROM " + tableName + " WHERE UUID = '" + uuid.toString() + "';");
		int i = 0;
		set.next();
		i = set.getInt(columnName);
		set.close();
		return i;
	}
	
	/**
	 * Gets if it has a open connection
	 * 
	 * @return
	 */
	public boolean hasOpenConnection() {
		return this.connection != null;
	}
	
	/**
	 * Gets if it has an open statement
	 * 
	 * @return
	 */
	public boolean hasOpenStatement() {
		return this.statement != null;
	}
	
	/**
	 * Opens a new connection
	 */
	public void openConnection() {
		this.connection = this.mysql.openConnection();
	}
	
	/**
	 * Opens a new statement
	 * 
	 * @throws SQLException
	 */
	public void openStatement() throws SQLException {
		this.statement = this.connection.createStatement();
	}
	
	/**
	 * Force the setting of the players value
	 * 
	 * @param tableName
	 *          - The tables name
	 * @param columnName
	 *          - The stats name
	 * @param value
	 * @param playerName
	 * @throws SQLException
	 */
	private void setInt(String tableName, String columnName, int value, UUID uuid) throws SQLException {
		this.statement.execute("INSERT INTO " + tableName + " (`UUID`, `" + columnName + "`) VALUES ('" + uuid + "', '" + value + "');");
	}
	
	/**
	 * Safely update/set the value
	 * Will set the value only if the table doesn't have the player already,
	 * otherwise it'll just update the players values
	 * 
	 * @param tableName
	 *          - The tables name
	 * @param columnName
	 *          - The stats name
	 * @param value
	 * @param playerName
	 * @throws SQLException
	 */
	public void update(String tableName, String columnName, int value, UUID uuid) throws SQLException {
		try
		{
			this.statement.execute("UPDATE " + tableName + " SET " + columnName + "=" + value + " WHERE UUID ='" + uuid + "';");
		}
		catch (SQLException e)
		{
			setInt(tableName, columnName, value, uuid);
		}
	}
}

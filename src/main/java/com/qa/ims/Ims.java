package com.qa.ims;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



import com.qa.ims.controller.Action;
import com.qa.ims.controller.CrudController;
import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.ItemController;
import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.CustomerDaoMysql;
import com.qa.ims.persistence.dao.ItemDaoMysql;
import com.qa.ims.persistence.dao.OrderDaoMysql;
import com.qa.ims.persistence.domain.Domain;
import com.qa.ims.utils.Utils;

import Services.CustomerServices;
import Services.ItemServices;
import Services.OrderServices;

public class Ims {

	public static final Logger LOGGER = LogManager.getLogger(Ims.class);

	public void imsSystem() {
		LOGGER.info("What is your username");
		String username = Utils.getInput();
		LOGGER.info("What is your password");
		String password = Utils.getInput();

		init(username, password);

		LOGGER.info("Which entity would you like to use?");
		Domain.printDomains();
		Domain domain = Domain.getDomain();
		if (domain.name().toUpperCase() == "EXIT") {
			LOGGER.info("Thank you for using this application, please re-run the application if you'd like to use anything else");
			System.exit(0);
		} else {
			LOGGER.info("What would you like to do with " + domain.name().toLowerCase() + ":");
			Action.printActions();
			Action action = Action.getAction();

			switch (domain) {
			case CUSTOMER:

				CustomerController customerController = new CustomerController(
						new CustomerServices(new CustomerDaoMysql(username, password)));
				doAction(customerController, action);
				break;
			case ITEM:
				ItemController itemController = new ItemController(
						new ItemServices(new ItemDaoMysql(username, password)));
				doAction(itemController, action);
				break;
			case ORDER:
				OrderController orderController = new OrderController(new OrderServices(
						new OrderDaoMysql(username, password)),
						    new ItemServices(new ItemDaoMysql(username, password)));
						doAction(orderController, action);
				break;
			case EXIT:
				break;
			default:
				break;
			}

		}
	} 

	public void doAction(CrudController<?> crudController, Action action) {
		switch (action) {
		case CREATE:
			crudController.create();
			break;	
		case READ:
			crudController.readAll();
			break;
		case UPDATE:
			crudController.update();
			break;
		case DELETE:
			crudController.delete();
			break;
		case RETURN:
			break;
		default:
			break;
		}
	}

	/**
	 * To initialise the database schema. DatabaseConnectionUrl will default to
	 * localhost.
	 * 
	 * @param username
	 * @param password
	 */

	// UPDATE THIS
	public void init(String username, String password) {
		init("jdbc:mysql://127.0.0.1:3306/?user=root", username, password, "src/main/resources/sql-schema.sql");
	}

	public String readFile(String fileLocation) {
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(fileLocation));) {
			String string;
			while ((string = br.readLine()) != null) {
				stringBuilder.append(string);
				stringBuilder.append("\r\n");
			}
		} catch (IOException e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				LOGGER.debug(ele);
			}
			LOGGER.error(e.getMessage());
		}
		return stringBuilder.toString();
	}

	/**
	 * To initialise the database with the schema needed to run the application
	 */
	public void init(String jdbcConnectionUrl, String username, String password, String fileLocation) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				BufferedReader br = new BufferedReader(new FileReader(fileLocation));) {
			String string;
			while ((string = br.readLine()) != null) {
				try (Statement statement = connection.createStatement();) {
					statement.executeUpdate(string);
				}
			}
		} catch (SQLException | IOException e) {
			for (StackTraceElement ele : e.getStackTrace()) {
				LOGGER.debug(ele);
			}
			LOGGER.error(e.getMessage());
		}
	}

}
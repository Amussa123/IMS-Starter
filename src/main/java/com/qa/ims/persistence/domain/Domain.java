package com.qa.ims.persistence.domain;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.qa.ims.utils.Utils;

public enum Domain {

	CUSTOMER("Information about customers"),
	ITEM("Individual Items"),
	ORDER("Purchases of items"),
	EXIT("To close the application");
	
	public static final Logger LOGGER = LogManager.getLogger(Domain.class);

	private String description;
	
	private Domain(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.name() + ": " +this.description;
	}
	
	public static void printDomains() {
		for (Domain domain : Domain.values()) {
			LOGGER.info(domain.getDescription());
		}
	}
	
	public static Domain getDomain() {
		Domain domain;
		while (true) {
			try {
				domain = Domain.valueOf(Utils.getInput().toUpperCase());
				break;
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid selection please try again");
			}
		}
		return domain;
	}
	
}
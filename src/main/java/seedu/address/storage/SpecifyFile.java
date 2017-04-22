package seedu.address.storage;

import java.util.logging.Logger;

import seedu.address.model.person.Person;

public class SpecifyFile{
	private Person person;
	private Logger logger;
	private String folderPath;
	private String filePath;
	public SpecifyFile(){
	}
	public SpecifyFile(Logger logger, Person person, String folderPath, String filePath){
		this.person = person;
	  	this.logger = logger;
	   	this.folderPath = folderPath;
	   	this.filePath = filePath;
	}
	public void setPerson(Person person){
		this.person = person;
	}
	public void setLogger(Logger logger){
	   	this.logger = logger;
	}
	public void setFolderPath(String folderPath){
	   	this.folderPath = folderPath;
	}
	public void setFilePath(String filePath){
	  	this.filePath = filePath;
	}
	public Person getPerson(){
		return person;
	}
	public Logger getLogger(){
	   	return logger;
	}
	public String getFolderPath(){
	  	return folderPath;
	}
	public String getFilePath(){
	   	return filePath;
	}
}
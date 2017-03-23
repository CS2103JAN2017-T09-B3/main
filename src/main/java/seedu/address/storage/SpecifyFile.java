package seedu.address.storage;

import java.util.logging.Logger;

public class SpecifyFile {
	private Logger logger;
	private String folderPath;
    private String filePath;
    public SpecifyFile(){
    	
    }
    public SpecifyFile(Logger logger, String folderPath, String filePath){
    	this.logger = logger;
    	this.folderPath = folderPath;
    	this.filePath = filePath;
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
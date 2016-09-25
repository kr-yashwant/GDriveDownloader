package com.util;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.bean.FileBean;
import com.exception.DriveSearchException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class DriveSearch {
	/**
	 * @author Yash
	 * @param fileNameAttr
	 * @return
	 * @throws DriveSearchException
	 */
	public List<FileBean> searchOnDrive(Drive service, String fileNameAttr) throws DriveSearchException {
		/*
		 * Takes a fileName as a search input and searched the drive for files having similar names
		 * Performs only prefix matching for a name. 
		 * For example, the name "HelloWorld" would match for name contains 'Hello' but not name contains 'World'.
		 * Returns a list of file names hence found
		 */
		List<FileBean> searchedFiles = new LinkedList<FileBean>();
		try {
			String pageToken = null;
			do {
			    FileList result = service.files().list()
			            .setQ("name contains '"+fileNameAttr+"'")
			            .setSpaces("drive")
			            .setFields("nextPageToken, files(id, name)")
			            .setPageToken(pageToken)
			            .execute();
			    for(File file: result.getFiles()) {
			    	//Avoid adding folder names to the search result
			    	if(file.getName().contains(".")) {
				    	FileBean bean = new FileBean();
				    	bean.setFileId(file.getId());
				    	bean.setFileName(file.getName());
				    	searchedFiles.add(bean);
			    	}
			    }
			    pageToken = result.getNextPageToken();
			} while (pageToken != null);
		} catch (IOException e) {
			throw new DriveSearchException(e.getMessage());
		}
		
		return searchedFiles;
	}
}

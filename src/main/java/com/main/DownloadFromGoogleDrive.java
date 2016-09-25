package com.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.Console;

import com.bean.FileBean;
import com.exception.DriveSearchException;
import com.google.api.services.drive.Drive;
import com.util.DriveSearch;
import com.util.DriveSearchAndSave;
import com.util.DriveService;
import com.util.UserValidator;

public class DownloadFromGoogleDrive {

	public static void main(String[] args) {
		String username = "", password = "";
		System.out.println("Welcome to G drive Download utility\n\n");
		System.out.println("Please enter your username");
		Scanner scanner = new Scanner(System.in);
		username = scanner.nextLine();
		System.out.println("Please enter your password");
		password = scanner.nextLine();
		UserValidator userValidator = new UserValidator();
		if(userValidator.isValidatedUser(username, password)) {
        try {
        	Drive service = DriveService.getDriveService();
			int count = 0;
			List <FileBean> filesSearched;
        	nameInputLoop: while(true) {
        		System.out.println("\nPlease press enter to list all files");
				System.out.println("or enter a file name and press enter to search:\t");
				String fileNameAttr = scanner.nextLine();
				filesSearched = new DriveSearch().searchOnDrive(service, fileNameAttr);
				if(filesSearched.isEmpty()) {
					System.out.println("No file name matches the entered name\nPlease try again.\n\n");
					continue nameInputLoop;
				}
				System.out.println();
				System.out.println("Index"+"\t"+"File");
				System.out.println();
				for(int fileNum = 1; fileNum<=filesSearched.size(); fileNum++) {
					System.out.println(Integer.toString(fileNum)+"\t"+filesSearched.get(fileNum-1).getFileName());
					count=fileNum;
				}
				System.out.println();
				System.out.println();
				break nameInputLoop;
        	}
			int numberOfFileNum;
			indexInputLoop: while(true) {
				System.out.println("Enter the index of file to save:\t");
				String numberOfFile = scanner.nextLine();
				try {
					numberOfFileNum = Integer.parseInt(numberOfFile);
					if(numberOfFileNum > count || numberOfFileNum < 1) {
						System.out.println("Invalid entry. Please enter a valid file number\n");
						continue indexInputLoop;
					}
					break indexInputLoop;
				} catch (NumberFormatException e) {
					System.out.println("Invalid entry. Please enter a valid file number\n");
				}
			}
			String fileId = filesSearched.get(numberOfFileNum-1).getFileId();
			String fileName = filesSearched.get(numberOfFileNum-1).getFileName();
			OutputStream os = null;
			try {
				System.out.println("Downloading "+fileName);
				os = new DriveSearchAndSave().downloadFile(service, fileId);
			}catch(DriveSearchException e) {
				System.out.println(fileName+ e.getMessage()+"\n\nPlease try again.");
			}
			if(os != null) {
				directoryInputLoop: while(true) {
					System.out.println("\nEnter an existing directory path to save the file");
					String path = scanner.nextLine();
					try(OutputStream outputStream = new FileOutputStream(new File(path+File.separator+fileName))) {
						System.out.println("Saving "+fileName+ " to "+ path+"\n.\n.\n.");
					    ((ByteArrayOutputStream) os).writeTo(outputStream);
					    System.out.println("File saved");
					    break directoryInputLoop;
					} catch(IOException e) {
						System.out.println("File could not be saved \n"+e.getMessage()+"\nNew directory can\'t be created with this API\nPlease try again.");
					}
				}
			}
			
        } catch (DriveSearchException e) {
        	e.printStackTrace();
			System.out.println("An Exception has occurred while trying to contact the Google Drive"+e.getMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("An Exception has occurred while trying to contact the Google Drive"+e1.getMessage());
		} finally {
			scanner.close();
		}
		} else {
			System.out.println("\n\nWrong credentials. Please try again.");
			scanner.close();
		}
    }

}

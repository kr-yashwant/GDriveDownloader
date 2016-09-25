package com.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.exception.DriveSearchException;
import com.google.api.services.drive.Drive;

public class DriveSearchAndSave extends DriveSearch{
	public OutputStream downloadFile(Drive service, String key) throws DriveSearchException {
		OutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			service.files().get(key).executeMediaAndDownloadTo(outputStream);
		} catch (IOException e) {
			throw new DriveSearchException(" seems to be a Google Doc. Google Docs are not supported as of now");
		}
		return outputStream;
	}
}

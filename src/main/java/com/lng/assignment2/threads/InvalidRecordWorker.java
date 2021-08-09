package com.lng.assignment2.threads;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class InvalidRecordWorker extends Thread {
	ArrayList invalidRecords;

	public void run() {
		BufferedWriter bufferWriterObj;
		try {
			bufferWriterObj = new BufferedWriter(new FileWriter(
					"C:\\Users\\Dell\\Downloads\\assignment2\\src\\main\\resources\\InvalidRecords.csv"));

			bufferWriterObj.write("UserName,Email,MobileNo,DOB,Country,State,City\n");
			Iterator i = getInvalidRecords().iterator();
			while (i.hasNext()) {
				String line = String.valueOf(i.next());
				String lineWithoutSerialNo = line.substring(line.indexOf(",") + 1, line.length());
				bufferWriterObj.write(lineWithoutSerialNo + "\n");
			}
			bufferWriterObj.flush();
		} catch (IOException e) {
			System.out.println("Some error occured");
		}
	}

	public ArrayList getInvalidRecords() {
		return invalidRecords;
	}

	public void setInvalidRecords(ArrayList invalidRecords) {
		this.invalidRecords = invalidRecords;
	}
}

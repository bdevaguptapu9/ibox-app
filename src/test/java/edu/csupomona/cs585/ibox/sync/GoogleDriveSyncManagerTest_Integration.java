package edu.csupomona.cs585.ibox.sync;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

import org.junit.Test;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.Delete;
import com.google.api.services.drive.Drive.Files.Insert;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.Drive.Files.Update;
import com.google.api.services.drive.model.FileList;
import org.junit.Before;

import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;



public class GoogleDriveSyncManagerTest_Integration {
    String p;
	Drive provide;
	GoogleDriveFileSyncManager gfds;
//	GoogleDriveFileSyncManager gfds = new GoogleDriveFileSyncManager(GoogleDriveServiceProvider.get().getGoogleDriveClient());
	File tf;
	
	
	@Before
	public void setup(){
		provide = GoogleDriveServiceProvider.get().getGoogleDriveClient();
//		GoogleDriveFileSyncManager gfds = new GoogleDriveFileSyncManager(GoogleDriveServiceProvider.get().getGoogleDriveClient());
     gfds = new GoogleDriveFileSyncManager(provide);                 
     tf = new File("C:\\Users\\devaguptapus\\Desktop\\Don\\roy.txt");     
	}     
	
	
	@Test
	public void testAAddFiles() throws Exception {
		gfds.addFile(tf);
	    p = gfds.getFileId("roy.txt");
		assertNotNull(p);
		}
		
	@Test
	public void testBUpdateFiles() throws IOException {
		gfds.updateFile(tf);
	    p = gfds.getFileId("roy.txt");
		assertNotNull(p);
		}
		
	
	@Test
	public void testCDeleteFiles() throws IOException {
		gfds.deleteFile(tf);
		p = gfds.getFileId("roy.txt");
		assertNotNull(p);
	                                            }
}
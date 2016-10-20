package edu.csupomona.cs585.ibox;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.Delete;
import com.google.api.services.drive.Drive.Files.Insert;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.Drive.Files.Update;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;

@RunWith(MockitoJUnitRunner.class)
public class GoogleDriveFileSynManagerTest_Unit {

	private Drive mDrive = mock(Drive.class);
	private Files mFiles = mock(Files.class);
	private List mList = mock(List.class);
	private Insert mInsert = mock(Insert.class);
	private Update mUpdate = mock(Update.class);
	private Delete mDelete = mock(Delete.class);

	private java.io.File file = new java.io.File("testFile");
	private FileList fileList = new FileList();
	private java.util.List<File> itemList = new ArrayList<File>();

	private GoogleDriveFileSyncManager gd;

	public GoogleDriveFileSynManagerTest_Unit() throws IOException {

		gd = new GoogleDriveFileSyncManager(mDrive);

		// initialization to testAddFile()
		when(mDrive.files()).thenReturn(mFiles);
		when(mFiles.insert(isA(File.class), isA(FileContent.class)))
				.thenReturn(mInsert);
		when(mInsert.execute()).thenReturn(new File());

		// initialization to testUpdateFile()
		when(mFiles.list()).thenReturn(mList);
		when(mList.execute()).thenReturn(fileList);
		when(
				mFiles.update(isA(String.class), isA(File.class),
						isA(FileContent.class))).thenReturn(mUpdate);
		when(mUpdate.execute()).thenReturn(new File());

		// initialization to testDeleteFile()
		when(mFiles.delete(isA(String.class))).thenReturn(mDelete);
	}

	@Test
	public void test_AddFile() throws IOException {

		gd.addFile(file);
        
		verify(mDrive).files();
		verify(mFiles).insert(isA(File.class), isA(FileContent.class));
		verify(mInsert).execute();
	}

	@Test
	public void test_UpdateFile() throws IOException {

		// test when updateFile(java.io.File) cannot find a file to update
		fileList.setItems(itemList);

		gd.updateFile(file);

		verify(mFiles).list();
		verify(mList).execute();

		// test when updateFile(java.io.File) successfully update a file
		itemList.add(new File().setTitle(file.getName()).setId("testId"));

		gd.updateFile(file);

		verify(mFiles).update(isA(String.class), isA(File.class),
				isA(FileContent.class));
		verify(mUpdate).execute();
	}

	@Test
	public void test_DeleteFile() throws IOException {

		// test when deleteFile(java.io.File) cannot find a file to delete
		fileList.setItems(itemList);

		try {
			gd.deleteFile(file);
		} catch (FileNotFoundException fnfe) {
		}

		verify(mFiles).list();
		verify(mList).execute();

		// test when deleteFile(java.io.File) successfully delete a file
		itemList.add(new File().setTitle(file.getName()).setId("testId"));
		gd.deleteFile(file);

		verify(mFiles).delete(isA(String.class));
		verify(mDelete).execute();
	}
}
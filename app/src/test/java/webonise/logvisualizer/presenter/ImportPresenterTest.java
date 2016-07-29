package webonise.logvisualizer.presenter;

import android.app.Activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import webonise.logvisualizer.view.interfaces.ImportView;

/**
 * Import presenter test class can have following tests
 * 1. Test if the file imported is valid file or not.
 * 2. Test if the file content is valid or not.
 */

public class ImportPresenterTest {

    private ImportPresenter importPresenter;
    private ImportView mockImportView;

    @Before
    public void setUp() throws Exception {
        mockImportView = Mockito.mock(ImportView.class);
        importPresenter = new ImportPresenter(mockImportView, Mockito.mock(Activity.class));
    }

    @Test
    public void testIsFileContentValidForValidData() throws Exception {
        String validFileContent = "Wed Jun 05:01:21.815 PM I/MainActivity: battery: 89%";
        Assert.assertTrue(importPresenter.isFileContentValid(validFileContent));
    }

    @Test
    public void testIsFileContentValidForNull() throws Exception {
        String validFileContent = null;
        Assert.assertFalse(importPresenter.isFileContentValid(validFileContent));
    }

    @Test
    public void testIsFileContentValidForInvalidData() throws Exception {
        String validFileContent = "aksjdfjlasdlfj";
        Assert.assertFalse(importPresenter.isFileContentValid(validFileContent));
    }
}

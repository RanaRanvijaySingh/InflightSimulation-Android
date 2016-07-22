package webonise.logvisualizer.presenter;

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
        importPresenter = new ImportPresenter(mockImportView);
    }

    @Test
    public void testIsFileContentValidWithValidData() throws Exception {
        String validFileContent = "Wed Jun 05:01:21.815 PM I/MainActivity: battery: 89%";
        Assert.assertTrue(importPresenter.isFileContentValid(validFileContent));
    }

    @Test
    public void testIsFileContentValidWithNull() throws Exception {
        String validFileContent = null;
        Assert.assertFalse(importPresenter.isFileContentValid(validFileContent));
    }

    @Test
    public void testIsFileContentValidWithInvalidData() throws Exception {
        String validFileContent = "aksjdfjlasdlfj";
        Assert.assertFalse(importPresenter.isFileContentValid(validFileContent));
    }
}

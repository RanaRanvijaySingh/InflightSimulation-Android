package webonise.logvisualizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import webonise.logvisualizer.presenter.ImportPresenter;
import webonise.logvisualizer.utilities.Constants;
import webonise.logvisualizer.view.interfaces.ImportView;

public class ImportActivity extends AppCompatActivity implements ImportView {

    public static final int PICK_FILE_RESULT_CODE = 2;
    private ImportPresenter importPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        importPresenter = new ImportPresenter(this, this);
        initializeComponent();
    }

    /**
     * Function to initialize components of the class
     */
    private void initializeComponent() {
        TextView tvImport = (TextView) findViewById(R.id.tvImport);
        tvImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importFlightPlan();
            }
        });
    }

    /**
     * Function to import file
     */
    private void importFlightPlan() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //For reading plain text file use : "text/plain"
        //For reading any type of file use : "*/*"
        intent.setType(Constants.FileConstants.IMPORT_FILE_EXTENSION);
        startActivityForResult(intent, PICK_FILE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        importPresenter.getFileData(requestCode, data, ImportActivity.this);
    }

    @Override
    public void showToast(int toastMessageId) {
        Toast.makeText(this, getString(toastMessageId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

package client.ops.zf.customzfprogressdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ops.zf.progress.model.ProgressInfo;
import com.ops.zf.progress.model.ProgressOrientation;
import com.ops.zf.progress.model.ProgressType;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.custom_progress_1)
    com.ops.zf.progress.CustomProgressView customProgress1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initProgressView(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopProgressView();
    }

    private void initProgressView(int minutes) {
        customProgress1.setProgressInfo(getProgressInfo(minutes));
        customProgress1.progressStart();
    }

    private void stopProgressView() {
        if (null != customProgress1) {
            customProgress1.progressStop();
        }
    }
    private ProgressInfo getProgressInfo(int minutes) {
        ProgressInfo progressInfo = new ProgressInfo();
        progressInfo.setMin(0);
        progressInfo.setMax(minutes * 60);
        progressInfo.setProgressType(ProgressType.TYPE_HORIZONTAL);
        progressInfo.setProgressOrientation(ProgressOrientation.ORIENTATION_RIGHT2LEFT);
        progressInfo.setTimeTotal(minutes * 60 * 1000);
        return progressInfo;
    }
}

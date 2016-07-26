package client.ops.zf.customzfprogressdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ops.zf.progress.CustomProgressView;
import com.ops.zf.progress.model.ProgressInfo;
import com.ops.zf.progress.model.ProgressOrientation;
import com.ops.zf.progress.model.ProgressType;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.custom_progress_1)
    CustomProgressView customProgress1;
    @Bind(R.id.custom_progress_2)
    CustomProgressView customProgress2;
    @Bind(R.id.custom_progress_3)
    CustomProgressView customProgress3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initProgressRight2Left(customProgress1, 2);
        initProgressLeft2Right(customProgress2, 2);
        initProgressTop2Bottom(customProgress3, 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopProgressView(customProgress1);
        stopProgressView(customProgress2);
    }

    private void initProgressRight2Left(CustomProgressView progressView, int minutes) {
        progressView.setProgressInfo(getProgressInfoRight2Left(minutes));
        progressView.progressStart();
    }

    private void initProgressLeft2Right(CustomProgressView progressView, int minutes) {
        progressView.setProgressInfo(getProgressInfoLeft2Right(minutes));
        progressView.progressStart();
    }

    private void initProgressTop2Bottom(CustomProgressView progressView, int minutes) {
        progressView.setProgressInfo(getProgressInfoTop2Bottom(minutes));
        progressView.progressStart();
    }

    private void stopProgressView(CustomProgressView progressView) {
        if (null != progressView) {
            progressView.progressStop();
        }
    }

    private ProgressInfo getProgressInfoRight2Left(int minutes) {
        ProgressInfo progressInfo = new ProgressInfo();
        progressInfo.setMin(0);
        progressInfo.setMax(minutes * 60);
        progressInfo.setProgressType(ProgressType.TYPE_HORIZONTAL);
        progressInfo.setProgressOrientation(ProgressOrientation.ORIENTATION_RIGHT2LEFT);
        progressInfo.setTimeTotal(minutes * 60 * 1000);
        return progressInfo;
    }

    private ProgressInfo getProgressInfoLeft2Right(int minutes) {
        ProgressInfo progressInfo = new ProgressInfo();
        progressInfo.setMin(0);
        progressInfo.setMax(minutes * 60);
        progressInfo.setProgressType(ProgressType.TYPE_HORIZONTAL);
        progressInfo.setProgressOrientation(ProgressOrientation.ORIENTATION_LEFT2RIGHT);
        progressInfo.setTimeTotal(minutes * 60 * 1000);
        return progressInfo;
    }

    private ProgressInfo getProgressInfoTop2Bottom(int minutes) {
        ProgressInfo progressInfo = new ProgressInfo();
        progressInfo.setMin(0);
        progressInfo.setMax(minutes * 60);
        progressInfo.setProgressType(ProgressType.TYPE_VERTICAL);
        progressInfo.setProgressOrientation(ProgressOrientation.ORIENTATION_TOP2BOTTOM);
        progressInfo.setTimeTotal(minutes * 60 * 1000);
        return progressInfo;
    }
}

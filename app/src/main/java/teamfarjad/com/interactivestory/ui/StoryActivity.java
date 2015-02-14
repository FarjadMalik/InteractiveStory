package teamfarjad.com.interactivestory.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import teamfarjad.com.interactivestory.R;
import teamfarjad.com.interactivestory.model.Page;
import teamfarjad.com.interactivestory.model.Story;

public class StoryActivity extends Activity {

    public static final String TAG = StoryActivity.class.getSimpleName();
    private Story myStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoiceButton1;
    private Button mChoiceButton2;
    private String mName;
    private Page mCurrentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));
        if(mName == null)
            mName = "Friend";
        //Toast.makeText(this,mName,Toast.LENGTH_LONG).show();
        //Log.d(TAG , mName);

        mImageView = (ImageView) findViewById(R.id.storyImageView);
        mTextView = (TextView) findViewById(R.id.storyTextView);
        mChoiceButton1 = (Button) findViewById(R.id.choiceButton1);
        mChoiceButton2 = (Button) findViewById(R.id.choiceButton2);

        loadPage(0);
    }

    private void loadPage(int choice){
        mCurrentPage = myStory.getPage(choice);

        mImageView.setImageDrawable(getResources().getDrawable(mCurrentPage.getImageId()));

        String pageText = mCurrentPage.getText();
        pageText = String.format(pageText,mName); // add a name if placeholder found
        mTextView.setText(pageText);


        if(mCurrentPage.isFinal()){
            mChoiceButton1.setVisibility(View.INVISIBLE);
            mChoiceButton2.setText("PLAY AGAIN");

            mChoiceButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        else {
            mChoiceButton1.setText(mCurrentPage.getChoice1().getText());
            mChoiceButton2.setText(mCurrentPage.getChoice2().getText());

            mChoiceButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadPage(mCurrentPage.getChoice1().getNextPage());
                }
            });

            mChoiceButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadPage(mCurrentPage.getChoice2().getNextPage());
                }
            });
        }
    }
}

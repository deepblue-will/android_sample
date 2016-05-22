package jp.study.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import org.w3c.dom.Text;

import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mInputMessage;
    private Button mSendMessage;
    private LinearLayout mMessageLog;
    private TextView mCpuMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // xmlのviewを取得
        mInputMessage = (EditText) findViewById(R.id.input_message);
        mSendMessage = (Button) findViewById(R.id.send_message);
        mMessageLog = (LinearLayout) findViewById(R.id.message_log);
        mCpuMessage = (TextView) findViewById(R.id.cpu_message);
        mSendMessage.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        if (getPackageManager().queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0).size() == 0) {
            menu.removeItem(R.id.action_voice_input);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_voice_input) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mSendMessage)) {
            send();
        }
    }

    private void send() {
        String inputText = mInputMessage.getText().toString();
        TextView userMessage = new TextView(this);
        int messageColor = getResources().getColor(R.color.message_color);
        userMessage.setTextColor(messageColor);
        userMessage.setText(inputText);
        //コメントサイズに合わせる
        LinearLayout.LayoutParams userMessageLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        userMessageLayout.gravity = Gravity.END;
        final int marginSize = getResources().getDimensionPixelSize(R.dimen.message_margin);
        userMessageLayout.setMargins(0, marginSize, 0, marginSize);
        userMessage.setBackgroundResource(R.drawable.user_message);
        mMessageLog.addView(userMessage, 0, userMessageLayout);

        String answer = "それはいいですね";
        final TextView cpuMessage = new TextView(this);
        cpuMessage.setTextColor(messageColor);
        cpuMessage.setText(answer);
        cpuMessage.setGravity(Gravity.START);

        mInputMessage.setText("");
        TranslateAnimation userMessageAnimation = new TranslateAnimation(mMessageLog.getWidth(), 0, 0, 0);
        userMessageAnimation.setDuration(1000);
        userMessageAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LinearLayout.LayoutParams cpuMessageLayout = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                cpuMessageLayout.gravity = Gravity.START;
                cpuMessageLayout.setMargins(marginSize, marginSize, marginSize, marginSize);
                mMessageLog.addView(cpuMessage, 0, cpuMessageLayout);
                TranslateAnimation cpuAnimation = new TranslateAnimation(-mMessageLog.getWidth(), 0, 0, 0);
                cpuAnimation.setDuration(1000);
                cpuMessage.setAnimation(cpuAnimation);
                cpuMessage.setBackgroundResource(R.drawable.cpu_message);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        userMessage.startAnimation(userMessageAnimation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK && data.hasExtra(RecognizerIntent.EXTRA_RESULTS)) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results.size() > 0) {
                mInputMessage.setText(results.get(0));
                send();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

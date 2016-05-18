package sveta.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private final static String TAG = "LAMA_SUDOKU";
    private int gameLevel = 40;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        context = this;

    }

    public void changeDifficult(View v) {
        try {
            //final EditText textInput = new EditText(context);
            AlertDialog.Builder difficultyDialog = new AlertDialog.Builder(context)
              //      .setView(textInput)
                    .setTitle("Difficulty")
                    .setMessage("Choose your level: ")
                    .setPositiveButton("Easy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                gameLevel = 50;
                                dialog.cancel();
                            } catch (Exception e) {

                                Log.i(TAG, e.toString());
                            }
                        }
                    })
                    .setNeutralButton("Medium", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gameLevel = 80;
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Hard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gameLevel = 100;
                            dialog.cancel();
                        }
                    });
            difficultyDialog.show();
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    public void startNewGame(View v) {

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("LEVEL", gameLevel);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

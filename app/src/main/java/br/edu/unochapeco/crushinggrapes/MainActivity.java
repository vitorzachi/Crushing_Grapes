package br.edu.unochapeco.crushinggrapes;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import AndGraph.AGGameManager;


public class MainActivity extends Activity {

    private AGGameManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        manager = new AGGameManager(this, false);

        manager.addScene(new IntroScene(manager));
        manager.addScene(new MenuScene(manager));

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

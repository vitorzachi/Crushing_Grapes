package br.edu.unochapeco.crushinggrapes;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import AndGraph.AGGameManager;
import AndGraph.AGInputManager;


public class MainActivity extends Activity {

    private AGGameManager manager;

      
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = new AGGameManager(this, false);
        manager.addScene(new IntroScene(manager));
        manager.addScene(new MenuScene(manager));
        manager.addScene(new Credits(manager));
        manager.addScene(new Game(manager));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AGInputManager.vrTouchEvents.bBackButtonClicked = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        manager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.release();
        manager = null;
    }
}

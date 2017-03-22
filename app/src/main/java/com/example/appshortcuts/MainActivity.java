package com.example.appshortcuts;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button add_shortcut, remove_shortcut, add_activity_shortcut, remove_activity_shortcut, rank_shortcut;
    ShortcutInfo shortcutGoogle = null;
    ShortcutInfo dynamicShortcut = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_activity_shortcut = (Button) findViewById(R.id.add_activity_shortcut);
        add_activity_shortcut.setOnClickListener(this);
        remove_activity_shortcut = (Button) findViewById(R.id.remove_activity_shortcut);
        remove_activity_shortcut.setOnClickListener(this);
        rank_shortcut = (Button) findViewById(R.id.rank_shortcut);
        rank_shortcut.setOnClickListener(this);

        add_shortcut = (Button) findViewById(R.id.add_shortcut);
        add_shortcut.setOnClickListener(this);
        remove_shortcut = (Button) findViewById(R.id.remove_shortcut);
        remove_shortcut.setOnClickListener(this);
        shortcutGoogle = new ShortcutInfo.Builder(MainActivity.this, "shortcut_google")
                .setShortLabel("google.com")
                .setLongLabel("Tap for google.com")
                .setIcon(Icon.createWithResource(MainActivity.this, R.drawable.google_logo))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com")))
                .build();
        dynamicShortcut = new ShortcutInfo.Builder(this, "shortcut_third_activity")
                .setShortLabel("Activity")
                .setLongLabel("Open Third Activity")
                .setIcon(Icon.createWithResource(this, R.drawable.three))
                .setIntents(
                        new Intent[]{
                                new Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK),
                                new Intent(ThirdActivity.ACTION)
                        })
                .build();

    }

    @Override
    public void onClick(View v) {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        switch (v.getId()) {

            case R.id.add_activity_shortcut:
                if (shortcutManager.getDynamicShortcuts().size() == 1) {
                    shortcutManager.setDynamicShortcuts(Arrays.asList(shortcutGoogle, dynamicShortcut));
                } else {
                    shortcutManager.setDynamicShortcuts(Collections.singletonList(dynamicShortcut));
                }


                break;

            case R.id.remove_activity_shortcut:
                List<String> id = new ArrayList<String>();
                id.add("shortcut_third_activity");
                shortcutManager.removeDynamicShortcuts(id);
                break;

            case R.id.rank_shortcut:
                ShortcutInfo googleShortcut = new ShortcutInfo.Builder(MainActivity.this, "shortcut_google")
                        .setRank(1)
                        .build();
                ShortcutInfo shortcutActivity = new ShortcutInfo.Builder(MainActivity.this, "shortcut_third_activity")
                        .setRank(0)
                        .build();
                shortcutManager.updateShortcuts(Arrays.asList(shortcutGoogle, shortcutActivity));
                break;
            case R.id.remove_shortcut:
                List<String> ids = new ArrayList<String>();
                ids.add("shortcut_google");
                shortcutManager.removeDynamicShortcuts(ids);
                break;

            case R.id.add_shortcut:

                if (shortcutManager.getDynamicShortcuts().size() == 1) {
                    shortcutManager.setDynamicShortcuts(Arrays.asList(shortcutGoogle, dynamicShortcut));
                } else {
                    shortcutManager.setDynamicShortcuts(Collections.singletonList(shortcutGoogle));
                }
                break;
        }
    }
}

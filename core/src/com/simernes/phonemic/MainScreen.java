package com.simernes.phonemic;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.IOException;

public class MainScreen implements Screen {

    private TextButton beHostButton;
    private TextButton beMicButton;
    private Stage stage;

    public MainScreen(Skin skin) {
        stage = new Stage(new StretchViewport(1024, 720));

        beHostButton = new TextButton("be host", skin);
        beHostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                becomeHost();
            }
        });
        beHostButton.setPosition(100, 100);
        stage.addActor(beHostButton);

        beMicButton = new TextButton("be mic", skin);
        beMicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                becomeMic();
            }
        });
        beMicButton.setPosition(200, 200);
        stage.addActor(beMicButton);
    }

    private void becomeMic() {
        new Thread() {
            @Override
            public void run() {
                PhoneMicClient phoneMicClient = new PhoneMicClient();
                try {
                    phoneMicClient.discoverMicrophoneServers();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void becomeHost() {
        new Thread() {
            @Override
            public void run() {
                PhoneMicServer phoneMicServer = new PhoneMicServer(new SoundPlayer());
                try {
                    phoneMicServer.discoverReceivers();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

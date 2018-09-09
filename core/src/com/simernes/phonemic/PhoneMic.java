package com.simernes.phonemic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PhoneMic extends Game {

	
	@Override
	public void create () {
		Skin skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
		MainScreen mainScreen = new MainScreen(skin);
		setScreen(mainScreen);
	}
	
	@Override
	public void dispose () {
	}
}

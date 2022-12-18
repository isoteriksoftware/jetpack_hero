package com.isoterik.jetpack_hero;

import com.badlogic.gdx.graphics.Texture;
import com.isoterik.racken.GameDriver;
import com.isoterik.racken.Scene;

public class JetpackHero extends GameDriver {
	@Override
	protected Scene initGame() {
		racken.assets.enqueueFolderContents("Hero", Texture.class);
		racken.assets.loadAssetsNow();
		return new DemoScene();
	}
}
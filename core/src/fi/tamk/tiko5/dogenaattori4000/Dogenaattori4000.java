package fi.tamk.tiko5.dogenaattori4000;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dogenaattori4000 extends Game {

	private SpriteBatch sb;
	private Music backgroundMusic;
	private boolean soundOn;

	@Override
	public void create () {
		soundOn = true;
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundmusic.mp3"));
		backgroundMusic.setVolume(0.25f);
		backgroundMusic.play();
		backgroundMusic.setLooping(true);
		sb = new SpriteBatch();

		setScreen(new MainMenuScreen(this, true));
	}

	public void setBackgroundMusic(boolean onOff){
		if (onOff == true);
		soundOn = true;
		backgroundMusic.play();
		if (onOff == false){
			soundOn = false;
			backgroundMusic.pause();
		}
	}

	public boolean isSoundOn(){
		return soundOn;
	}

	public void setSoundOn(boolean setOnOff){
		soundOn = setOnOff;
	}

	public SpriteBatch getSb(){
		return sb;
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		sb.dispose();
	}
}

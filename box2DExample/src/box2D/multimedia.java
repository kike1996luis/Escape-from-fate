package box2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class multimedia {
	public Texture Tbackground;
	public TextureRegion background;
	public Music musica, fire, camina, muerte;

	public void cargarAssets() {

		musica = Gdx.audio.newMusic(Gdx.files.internal(
				"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sounds/mazmorra.wav"));
		fire = Gdx.audio.newMusic(Gdx.files.internal(
				"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sounds/Ak47 shoot CS-GO Sound Effect.wav"));
		muerte = Gdx.audio.newMusic(Gdx.files.internal(
				"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sounds/muerte.wav"));

	}

	public void disposeAssets() {
		Tbackground.dispose();
		musica.dispose();
	}
}

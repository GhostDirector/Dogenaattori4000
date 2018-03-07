package fi.tamk.tiko5.dogenaattori4000;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;


public class CreditsScreen implements Screen {

    private Dogenaattori4000 d4000;
    private Texture background;
    private BitmapFont font;
    private SpriteBatch sb;
    private Stage creditsStage;
    private boolean gameIsOn;

    private final float WIDTH = 800;
    private final float HEIGHT = 480;
    private ArrayList<String> credits;

    public CreditsScreen(Dogenaattori4000 dogenaattori4000){

        gameIsOn = true;
        credits = new ArrayList<String>();

        sb = d4000.getSb();

        background = new Texture(Gdx.files.internal("background.png"));
        creditsStage = new Stage(new FitViewport(WIDTH, HEIGHT), sb);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("comic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 32;
        param.color = Color.CORAL;
        param.borderWidth = 2;
        font = generator.generateFont(param);

        ReturnButton rButton = new ReturnButton();
        creditsStage.addActor(rButton);

        Gdx.input.setInputProcessor(creditsStage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        if (gameIsOn){
            Gdx.gl.glClearColor(0,0,0,0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            creditsStage.getBatch().begin();
            creditsStage.getBatch().draw(background, 0, 0, WIDTH, HEIGHT);
            font.draw(sb, "Programming:", 100, 450);
            font.draw(sb, "Kummitus", 100, 400);
            font.draw(sb, "Graphics:", 100, 300);
            font.draw(sb, "Kummitus", 100, 250);
            font.draw(sb, "opengameart.org/content/kenney-16x16", 100, 200);

            font.draw(sb, "Music: bensound.com", 100, 100);

            creditsStage.getBatch().end();
            creditsStage.act(Gdx.graphics.getDeltaTime());
            creditsStage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        gameIsOn = false;
    }

    @Override
    public void resume() {
        gameIsOn = true;
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        gameIsOn = false;
    }

    public class ReturnButton extends Actor {

        private Texture rButtonTexture;

        public ReturnButton(){

            rButtonTexture = new Texture(Gdx.files.internal("return.png"));
            setWidth(rButtonTexture.getWidth());
            setHeight(rButtonTexture.getHeight());
            setBounds(680, 370, getWidth(), getHeight());

            addListener(new rbuttonListener());
        }

        public void draw(Batch batch, float delta){
            batch.draw(rButtonTexture, getX(), getY(), getWidth(), getHeight());
        }

        public void act(float delta){
            super.act(delta);
        }

        public void pressedTexture(){
            rButtonTexture = new Texture("returnpressed.png");
        }
        public void releasedTexture(){
            rButtonTexture = new Texture("return.png");
        }


        class rbuttonListener extends InputListener {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                pressedTexture();
                Gdx.app.log("returnButton", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                releasedTexture();
                d4000.setScreen(new MainMenuScreen(d4000, false));
            }
        }
    }
}

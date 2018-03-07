package fi.tamk.tiko5.dogenaattori4000;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen implements Screen {

    private Texture background;
    public Dogenaattori4000 d4000;
    private Stage mainStage;
    private SpriteBatch sb;

    private final float WIDTH = 8f;
    private final float HEIGHT = 4.8f;
    private StartButton startButton;
    private CreditsButton creditsButton;
    private QuitButton quitButton;
    private SoundButton soundButton;
    private FitViewport fitViewport;
    private boolean gameIsOn;



    public MainMenuScreen(Dogenaattori4000 dogenaattori4000, boolean musicOn){

        gameIsOn = true;

        fitViewport = new FitViewport(WIDTH,HEIGHT);
        d4000 = dogenaattori4000;
        sb = d4000.getSb();
        background = new Texture(Gdx.files.internal("background.png"));
        mainStage = new Stage(fitViewport, sb);

        startButton = new StartButton();
        mainStage.addActor(startButton);
        creditsButton = new CreditsButton();
        mainStage.addActor(creditsButton);
        quitButton = new QuitButton();
        mainStage.addActor(quitButton);
        soundButton = new SoundButton();
        mainStage.addActor(soundButton);

        Gdx.input.setInputProcessor(mainStage);

    }

    @Override
    public void show() {
    }



    @Override
    public void render(float delta) {
        if (gameIsOn){
            Gdx.gl.glClearColor(0,0,0,0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            sb.begin();
            sb.draw(background, 0, 0, background.getWidth() / 170f, background.getHeight() / 170f);
            sb.end();

            mainStage.act(Gdx.graphics.getDeltaTime());

            mainStage.draw();
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

    public class StartButton extends Actor{

        private Texture sButtonTexture;

        public StartButton(){

            sButtonTexture = new Texture(Gdx.files.internal("startbutton.png"));
            setWidth(sButtonTexture.getWidth());
            setHeight(sButtonTexture.getHeight());
            setBounds(4.7f, 2.8f, getWidth() / 100f, getHeight() / 100f);

            addListener(new sbuttonListener());
        }

        public void draw(Batch batch, float delta){
            batch.draw(sButtonTexture, getX(), getY(), getWidth(), getHeight());
        }

        public void act(float delta){
            super.act(delta);
        }

        public void pressedTexture(){
            sButtonTexture = new Texture("startbuttonpressed.png");
        }

        public void releasedTexture(){
            sButtonTexture = new Texture("startbutton.png");
        }

        class sbuttonListener extends InputListener{

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                pressedTexture();
                Gdx.app.log("Startbutton", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                releasedTexture();
                d4000.setScreen(new LevelMenu(d4000));
            }
        }
    }

    public class CreditsButton extends Actor{

        private Texture cButtonTexture;

        public CreditsButton(){

            cButtonTexture = new Texture(Gdx.files.internal("creditsbutton.png"));
            setWidth(cButtonTexture.getWidth());
            setHeight(cButtonTexture.getHeight());
            setBounds(4.7f, 1.8f, getWidth() / 100f, getHeight() / 100f);

            addListener(new cbuttonListener());
        }

        public void draw(Batch batch, float delta){
            batch.draw(cButtonTexture, getX(), getY(), getWidth(), getHeight());
        }

        public void pressedTexture(){
            cButtonTexture = new Texture("creditsbuttonpressed.png");
        }

        public void releasedTexture(){
            cButtonTexture = new Texture("creditsbutton.png");
        }

        public void act(float delta){
            super.act(delta);
        }

        class cbuttonListener extends InputListener{

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                pressedTexture();
                Gdx.app.log("creditsbutton", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                releasedTexture();
                d4000.setScreen(new CreditsScreen(d4000));
            }
        }
    }

    public class QuitButton extends Actor{

        private Texture qButtonTexture;

        public QuitButton(){

            qButtonTexture = new Texture(Gdx.files.internal("quitbutton.png"));
            setWidth(qButtonTexture.getWidth());
            setHeight(qButtonTexture.getHeight());
            setBounds(4.7f, 0.8f, getWidth() / 100f, getHeight() / 100f);

            addListener(new qbuttonListener());
        }

        public void draw(Batch batch, float delta){
            batch.draw(qButtonTexture, getX(), getY(), getWidth(), getHeight());
        }

        public void pressedTexture(){
            qButtonTexture = new Texture("quitbuttonpressed.png");
        }

        public void releasedTexture(){
            qButtonTexture = new Texture("quitbutton.png");
        }

        public void act(float delta){
            super.act(delta);
        }

        class qbuttonListener extends InputListener{

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                pressedTexture();
                Gdx.app.log("quitbutton", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                releasedTexture();
                System.exit(0);
            }
        }
    }

    public class SoundButton extends Actor{

        private Texture soundButtonTexture;

        public SoundButton(){

            pressedTexture();
            setWidth(soundButtonTexture.getWidth());
            setHeight(soundButtonTexture.getHeight());
            setBounds(0.2f, 0.2f, getWidth() / 100f, getHeight() / 100f);

            addListener(new soundButtonListener());
        }

        public void draw(Batch batch, float delta){
            batch.draw(soundButtonTexture, getX(), getY(), getWidth(), getHeight());
        }

        public void pressedTexture(){
            if (d4000.isSoundOn() == true){
                soundButtonTexture = new Texture("sound.png");
            }
            if (d4000.isSoundOn() == false){
                soundButtonTexture = new Texture("mute.png");
            }
        }

        public void act(float delta){
            super.act(delta);
        }

        class soundButtonListener extends InputListener{

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (d4000.isSoundOn() == true){
                    d4000.setBackgroundMusic(false);
                    pressedTexture();
                }else {
                    d4000.setBackgroundMusic(true);
                    pressedTexture();
                }

                Gdx.app.log("sound", "touch started at (" + x + ", " + y + ")");
                return true;
            }
        }
    }
}
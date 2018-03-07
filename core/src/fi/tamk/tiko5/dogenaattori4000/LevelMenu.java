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

public class LevelMenu implements Screen {

    private BitmapFont font;
    private Texture background;
    public Dogenaattori4000 d4000;
    private Stage levelStage;
    private SpriteBatch sb;
    private boolean gameIsOn;

    private Texture selectedLevel;
    private final float WIDTH = 800;
    private final float HEIGHT = 480;
    private int currentLevel;
    private ArrayList<Level> levels;

    public LevelMenu(Dogenaattori4000 dogenaattori4000){

        gameIsOn = true;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("comic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 26;
        param.color = Color.CORAL;
        param.borderWidth = 3;

        font = generator.generateFont(param);
        currentLevel = 0;
        levels = new ArrayList<Level>();
        loadLevels();
        background = new Texture(Gdx.files.internal("background.png"));
        d4000 = dogenaattori4000;
        sb = d4000.getSb();
        selectedLevel = levels.get(0).getPic();
        levelStage = new Stage(new FitViewport(WIDTH,HEIGHT), sb);

        PreviousButton previousButton = new PreviousButton();
        levelStage.addActor(previousButton);
        NextButton nextButton = new NextButton();
        levelStage.addActor(nextButton);
        ReturnButton returnButton = new ReturnButton();
        levelStage.addActor(returnButton);
        PlayButton playButton = new PlayButton();
        levelStage.addActor(playButton);

        Gdx.input.setInputProcessor(levelStage);
    }

    public void setLevel( int l){
        int tmp = currentLevel + l;
        if (tmp >= 0 && tmp <= levels.size() - 1){
            currentLevel = currentLevel + l;
            selectedLevel = levels.get(currentLevel).getPic();
            Gdx.app.log("selectedLevel", ""+currentLevel);
        }
    }

    public void loadLevels(){
        //int id, String name, String picture, String smap, float x, float y
        levels.add(new Level(1, "ArmaDogetton", "level1.png", "map1.tmx", 1f, 7f));
        levels.add(new Level(2, "House of the Doge", "level2.png", "map2.tmx", 1f, 7f));
        levels.add(new Level(3, "Fying Doges", "level3.png", "map3.tmx", 1f, 7f));
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

            sb.draw(background, 0, 0, background.getWidth(), background.getHeight());
            sb.draw(selectedLevel, 220, 100, selectedLevel.getWidth() * 1.10f, selectedLevel.getHeight()* 1.10f);
            font.draw(sb, "Select level: "+levels.get(currentLevel).getId()+"/"+levels.size(), 200, 400);
            font.draw(sb, "Level name: "+levels.get(currentLevel).getLevelName(), 200, 70);
            sb.end();

            levelStage.act(Gdx.graphics.getDeltaTime());

            levelStage.draw();
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

    public class PreviousButton extends Actor {

        private Texture pButtonTexture;

        public PreviousButton(){

            pButtonTexture = new Texture(Gdx.files.internal("previous.png"));
            setWidth(pButtonTexture.getWidth());
            setHeight(pButtonTexture.getHeight());
            setBounds(30, 200, getWidth(), getHeight());

            addListener(new pbuttonListener());
        }

        public void draw(Batch batch, float delta){
            batch.draw(pButtonTexture, getX(), getY(), getWidth(), getHeight());
        }

        public void act(float delta){
            super.act(delta);
        }

        public void pressedTexture(){
            pButtonTexture = new Texture("previouspressed.png");
        }

        public void releasedTexture(){
            pButtonTexture = new Texture("previous.png");
        }

        class pbuttonListener extends InputListener {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                pressedTexture();
                Gdx.app.log("PreviousButton", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                setLevel(-1);
                releasedTexture();
            }
        }
    }

    public class NextButton extends Actor{

        private Texture nButtonTexture;

        public NextButton(){

            nButtonTexture = new Texture(Gdx.files.internal("next.png"));
            setWidth(nButtonTexture.getWidth());
            setHeight(nButtonTexture.getHeight());
            setBounds(680, 200, getWidth(), getHeight());

            addListener(new nbuttonListener());
        }

        public void draw(Batch batch, float delta){
            batch.draw(nButtonTexture, getX(), getY(), getWidth(), getHeight());
        }

        public void pressedTexture(){
            nButtonTexture = new Texture("nextpressed.png");
        }

        public void releasedTexture(){
            nButtonTexture = new Texture("next.png");
        }

        public void act(float delta){
            super.act(delta);
        }

        class nbuttonListener extends InputListener{

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                pressedTexture();
                Gdx.app.log("Nextbutton", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                setLevel(1);
                releasedTexture();
            }
        }
    }

    public class ReturnButton extends Actor{

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

        public void pressedTexture(){
            rButtonTexture = new Texture("returnpressed.png");
        }

        public void releasedTexture(){
            rButtonTexture = new Texture("return.png");
        }

        public void act(float delta){
            super.act(delta);
        }

        class rbuttonListener extends InputListener{

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                pressedTexture();
                Gdx.app.log("ReturnButton", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                releasedTexture();
                d4000.setScreen(new MainMenuScreen(d4000, false));
            }
        }
    }

    public class PlayButton extends Actor{

        private Texture plButtonTexture;

        public PlayButton(){

            plButtonTexture = new Texture(Gdx.files.internal("accept.png"));
            setWidth(plButtonTexture.getWidth());
            setHeight(plButtonTexture.getHeight());
            setBounds(680, 30, getWidth(), getHeight());

            addListener(new plbuttonListener());
        }

        public void draw(Batch batch, float delta){
            batch.draw(plButtonTexture, getX(), getY(), getWidth(), getHeight());
        }

        public void pressedTexture(){
            plButtonTexture = new Texture("acceptpressed.png");
        }

        public void releasedTexture(){
            plButtonTexture = new Texture("accept.png");
        }

        public void act(float delta){
            super.act(delta);
        }

        class plbuttonListener extends InputListener{

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                pressedTexture();
                Gdx.app.log("PlayButton", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                releasedTexture();
                d4000.setScreen(new GameScreen(d4000, levels.get(currentLevel)));
            }
        }
    }
}

package fi.tamk.tiko5.dogenaattori4000;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private Integer score;

    private Label objectiveLabel, linkLabel;
    private static Label scoreLabel;

    private Dogenaattori4000 d4000;

    public Hud(Dogenaattori4000 dogenaattori4000) {
        score = 0;
        d4000 = dogenaattori4000;
        viewport = new FitViewport(800, 480, new OrthographicCamera());
        stage = new Stage(viewport, d4000.getSb());
        scoreLabel = new Label(String.format("%02d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        objectiveLabel = new Label("Objective: push doges into the hole", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        linkLabel = new Label("DOGES LEFT", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(linkLabel).expandX().padTop(10);
        table.add(objectiveLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();

        ReturnButton returnButton = new ReturnButton();
        stage.addActor(table);
        stage.addActor(returnButton);

        Gdx.input.setInputProcessor(stage);

    }

    public void setScore(int value) {
        scoreLabel.setText(String.format("%02d", value));
    }

    public void setObjectiveLabel(){
        objectiveLabel.setText("Level Completed. Press ---->");
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public class ReturnButton extends Actor {

        private Texture rButtonTexture;

        public ReturnButton(){

            rButtonTexture = new Texture(Gdx.files.internal("return.png"));
            setWidth(rButtonTexture.getWidth());
            setHeight(rButtonTexture.getHeight());
            setBounds(700, 420, getWidth() * 0.7f, getHeight() * 0.7f);

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
                Gdx.app.log("return", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                releasedTexture();
                d4000.setScreen(new LevelMenu(d4000));
            }
        }
    }
}
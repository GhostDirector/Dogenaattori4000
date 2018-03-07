package fi.tamk.tiko5.dogenaattori4000;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class GameScreen extends ApplicationAdapter implements Screen{

    private final float WINDOW_WIDTH = 8f;
    private final float WINDOW_HEIGHT = 4.8f;
    private Dogenaattori4000 d4000;
    private Array<Body> removalBodies = new Array<Body>();
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch sb;
    private OrthographicCamera camera;
    private Entity playerEntity;
    private ArrayList<Entity> entities;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Level levelInfo;
    private Hud hud;
    private int dogeCount;
    private boolean gameIsOn;

    public GameScreen(Dogenaattori4000 dogenaattori4000, Level level){
        gameIsOn = true;
        d4000 = dogenaattori4000;
        sb = d4000.getSb();
        levelInfo = level;
        entities = new ArrayList<Entity>();
        hud= new Hud(d4000);
        world = new World(new Vector2(0, -9.8f), true);

        //entity params: textureString, posX, posY, isPlayer, world, userdata, density, restitution, friction, radius
        playerEntity = new Entity("doge.png", level.getStartX(), level.getStartY(), world, "player", 3.2f, 0.07f, 0.8f, 0.15f);
        createEntities();
        dogeCount = entities.size();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,         // y points up
                WINDOW_WIDTH,            // width
                WINDOW_HEIGHT);          // height

        tiledMap = new TmxMapLoader().load(level.getMap());
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 100f);


        //bb.buildShapes(tiledMap, "world-wall-rectangles", 0.01f, world, "wall");
        //bb.buildShapes(tiledMap, "ground-rectangles", 0.01f, world, "wall");
        //bb.buildShapes(tiledMap, "bounce-rectangles", 0.01f, world, "bounce");
        //bb.buildShapes(tiledMap, "polygon", 0.01f, world, "polygon");


        transformWallsToBodies("world-wall-rectangles", "wall");
        transformWallsToBodies("ground-rectangles", "wall");
        transformWallsToBodies("bounce-rectangles", "bounce");
        moveCamera();

        debugRenderer = new Box2DDebugRenderer();
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                String userDataA = (String) (contact.getFixtureA().getBody().getUserData());
                String userDataB = (String) (contact.getFixtureB().getBody().getUserData());

                if(userDataA.equals("")) {
                    contact.setEnabled(false);
                    removalBodies.add(contact.getFixtureA().getBody());
                }
                if(userDataB.equals("")) {
                    contact.setEnabled(false);
                    removalBodies.add(contact.getFixtureB().getBody());
                }

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    private void createEntities(){

        switch (levelInfo.getId()){
            case 1:
                entities.add(new Entity("doge.png", 7.5f, 14.6f, world, "doge1", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 14.23f, 13.6f, world, "doge2", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 19.9f, 12.32f, world, "doge3", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 20.30f, 14.60f, world, "doge4", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 28.40f, 13.60f, world, "doge5", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 9.50f, 8.80f, world, "doge6", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 6.58f, 12.00f, world, "doge7", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 8.50f, 6.55f, world, "doge8", 3.2f, 0.07f, 0.8f, 0.15f));
                break;

            case 2:
                entities.add(new Entity("doge.png", 20.651999f,8.4762945f, world, "doge1", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 6.618011f, 11.99638f, world, "doge2", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 14.244395f, 13.595353f, world, "doge3", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 29.57343f,11.674997f, world, "doge4", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 29.53155f, 9.099631f, world, "doge5", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 20.628717f, 8.475931f, world, "doge6", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 14.867326f, 5.274999f, world, "doge7", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 17.35108f, 8.468476f, world, "doge8", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 11.120739f, 11.675994f, world, "doge9", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 3.4038227f, 6.5570464f, world, "doge10", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 10.0170555f, 7.8360267f, world, "doge11", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 23.449074f, 14.555378f, world, "doge12", 3.2f, 0.07f, 0.8f, 0.15f));
                break;

            case 3:
                entities.add(new Entity("doge.png", 20.651999f,8.4762945f, world, "doge1", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 6.618011f, 11.99638f, world, "doge2", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 14.244395f, 13.595353f, world, "doge3", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 29.57343f,11.674997f, world, "doge4", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 29.53155f, 9.099631f, world, "doge5", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 20.628717f, 8.475931f, world, "doge6", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 14.867326f, 5.274999f, world, "doge7", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 17.35108f, 8.468476f, world, "doge8", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 11.120739f, 11.675994f, world, "doge9", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 3.4038227f, 6.5570464f, world, "doge10", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 10.0170555f, 7.8360267f, world, "doge11", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 23.449074f, 14.555378f, world, "doge12", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 10.0170555f, 7.8360267f, world, "doge11", 3.2f, 0.07f, 0.8f, 0.15f));
                entities.add(new Entity("doge.png", 23.449074f, 14.555378f, world, "doge12", 3.2f, 0.07f, 0.8f, 0.15f));
                break;
        }
    }

    public void resurrect(){

        if (playerEntity.getEntityBody().getPosition().y < -1 * playerEntity.getRadius() * 2){
            playerEntity.getEntityBody().setTransform(new Vector2(1f, 7f + playerEntity.getRadius()*2), 0);
        }
    }

    private void checkUserInput() {

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerEntity.getEntityBody().applyForceToCenter(new Vector2(-1.5f, 0), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerEntity.getEntityBody().applyForceToCenter(new Vector2(1.5f, 0), true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            playerEntity.getEntityBody().applyLinearImpulse(new Vector2(0, 1f),
                    playerEntity.getEntityBody().getWorldCenter(), true);

        }

        if(Gdx.input.getAccelerometerY() < -1) {
            playerEntity.getEntityBody().applyForceToCenter(new Vector2(-1.5f, 0), true);
        }

        if(Gdx.input.getAccelerometerY() > 1) {
            playerEntity.getEntityBody().applyForceToCenter(new Vector2(1.5f, 0), true);
        }

        if (Gdx.input.isTouched()) {
            playerEntity.getEntityBody().applyLinearImpulse(new Vector2(0, 0.1f),
                    playerEntity.getEntityBody().getWorldCenter(), true);
        }



    }

    private void clearScreen() {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void moveCamera() {

        camera.position.set(playerEntity.getEntityBody().getPosition().x,
                playerEntity.getEntityBody().getPosition().y,
                0);

        camera.update();
    }

    public void doHeavyStuff(){
        try {
            Thread.sleep(20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double accumulator = 0;
    private float TIME_STEP = 1 / 60f;

    private void doPhysicsStep(float deltaTime) {

        float frameTime = deltaTime;

        // If it took ages (over 4 fps, then use 4 fps)
        // Avoid of "spiral of death"
        if(deltaTime > 1 / 4f) {
            frameTime = 1 / 4f;
        }

        accumulator += frameTime;

        while (accumulator >= TIME_STEP) {
            // It's a fixed time step!
            world.step(TIME_STEP, 8, 3);
            accumulator -= TIME_STEP;
        }
    }

    private void transformWallsToBodies(String layer, String userData) {
        MapLayer collisionObjectLayer = tiledMap.getLayers().get(layer);
        MapObjects mapObjects = collisionObjectLayer.getObjects();
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        for (RectangleMapObject rectangleObject : rectangleObjects) {
            Rectangle tmp = rectangleObject.getRectangle();

            Rectangle rectangle = scaleRect(tmp, 1 / 100f);
            createStaticBody(rectangle, userData);
        }
    }

    private void transformPolyLineToBodies(String layer, String userData) {
        MapLayer collisionObjectLayer = tiledMap.getLayers().get(layer);
        MapObjects mapObjects = collisionObjectLayer.getObjects();
        Array<PolygonMapObject> polygonObjects = mapObjects.getByType(PolygonMapObject.class);

        for (PolygonMapObject polygonObject : polygonObjects) {
            float[] vertices = polygonObject.getPolygon().getTransformedVertices();
            Polygon polygon = polygonObject.getPolygon();
            float[] worldVertices = new float[vertices.length];
            for (int i = 0; i < vertices.length; ++i) {
                System.out.println(vertices[i]);
                worldVertices[i] = vertices[i] / 16;
            }
            polygon.scale(0.01f);
            createPolyStaticBody(polygon, userData, worldVertices);
        }
    }
    public void createPolyStaticBody(Polygon poly, String userData, float[] worldVertices) {
        BodyDef myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;

        myBodyDef.position.set((poly.getX() * 0.01f),
                poly.getY() * 0.01f);


        Body wall = world.createBody(myBodyDef);

        wall.setUserData(userData);
        PolygonShape polygon = new PolygonShape();
        polygon.set(poly.getTransformedVertices());
        //polygon.set(worldVertices);

        wall.createFixture(polygon, 0.0f);
    }

    public void createStaticBody(Rectangle rect, String userData) {
        BodyDef myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;


        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width/2 + x;
        float centerY = height/2 + y;

        myBodyDef.position.set(centerX, centerY);
        Body wall = world.createBody(myBodyDef);

        wall.setUserData(userData);
        PolygonShape groundBox = new PolygonShape();

        groundBox.setAsBox(width / 2 , height / 2 );
        wall.createFixture(groundBox, 0.0f);
    }

    private Rectangle scaleRect(Rectangle r, float scale) {
        Rectangle rectangle = new Rectangle();
        rectangle.x      = r.x * scale;
        rectangle.y      = r.y * scale;
        rectangle.width  = r.width * scale;
        rectangle.height = r.height * scale;
        return rectangle;
    }


    @Override
    public void show() {

    }

    public void clearBodies() {

        for (int i = 0; i < entities.size(); i++){
            if(entities.get(i).getEntityBody().getPosition().y < -1 * entities.get(i).getRadius()*2) {
                entities.get(i).getEntityBody().setLinearVelocity(new Vector2(0,0));
                entities.get(i).setDead();
                dogeCount--;
            }
        }

        for (int j = 0; j < entities.size(); j++){
            if (entities.get(j).getIsDead() == true){
                float yPos = entities.get(j).getEntityBody().getPosition().y;
                if (yPos < -1 * entities.get(j).getRadius() * 2) {

                    if(!world.isLocked())
                        entities.get(j).getEntityBody().getWorld().destroyBody(entities.get(j).getEntityBody());
                    entities.remove(j);
                }
            }
        }
        hud.setScore(dogeCount);
    }

    @Override
    public void render(float delta) {

        if (gameIsOn){
            sb.setProjectionMatrix(camera.combined);

            clearScreen();

            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
            debugRenderer.render(world, camera.combined);

            checkUserInput();
            moveCamera();

            Gdx.app.log("playerPosition", "" + playerEntity.getEntityBody().getPosition());

            sb.begin();

            sb.draw(playerEntity.getEntityTexture(),
                    playerEntity.getEntityBody().getPosition().x - playerEntity.getRadius(),
                    playerEntity.getEntityBody().getPosition().y - playerEntity.getRadius(),
                    playerEntity.getRadius(),                 // originX
                    playerEntity.getRadius(),                 // originY
                    playerEntity.getRadius() * 2,             // width
                    playerEntity.getRadius() * 2,             // height
                    1.0f,                          // scaleX
                    1.0f,                          // scaleY
                    playerEntity.getEntityBody().getTransform().getRotation() * MathUtils.radiansToDegrees,
                    0,                             // Start drawing from x = 0
                    0,                             // Start drawing from y = 0
                    playerEntity.getEntityTexture().getWidth(),      // End drawing x
                    playerEntity.getEntityTexture().getHeight(),     // End drawing y
                    false,                         // flipX
                    false);                        // flipY

            resurrect();

            for (int i = 0; i < entities.size(); i++){
                if (entities.get(i).getIsDead() == false)
                    sb.draw(entities.get(i).getEntityTexture(),
                            entities.get(i).getEntityBody().getPosition().x - entities.get(i).getRadius(),
                            entities.get(i).getEntityBody().getPosition().y - entities.get(i).getRadius(),
                            entities.get(i).getRadius(),                 // originX
                            entities.get(i).getRadius(),                 // originY
                            entities.get(i).getRadius() * 2,             // width
                            entities.get(i).getRadius() * 2,             // height
                            1.0f,                          // scaleX
                            1.0f,                          // scaleY
                            entities.get(i).getEntityBody().getTransform().getRotation() * MathUtils.radiansToDegrees,
                            0,                             // Start drawing from x = 0
                            0,                             // Start drawing from y = 0
                            entities.get(i).getEntityTexture().getWidth(),      // End drawing x
                            entities.get(i).getEntityTexture().getHeight(),     // End drawing y
                            false,                         // flipX
                            false);                        // flipY
            }

            sb.end();
            sb.setProjectionMatrix(hud.stage.getCamera().combined);
            hud.stage.draw();

            world.step(1 / 60f, 8, 3);
            clearBodies();
            if (dogeCount == 0){
                hud.setObjectiveLabel();
            }
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        gameIsOn = false;
    }

    @Override
    public void resume() {
        gameIsOn = true;
    }
}


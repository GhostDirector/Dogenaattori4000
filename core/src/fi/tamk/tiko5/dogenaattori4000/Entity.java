package fi.tamk.tiko5.dogenaattori4000;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Entity extends BodyDef {

    private Texture entityTexture;
    private Body entityBody;
    private FixtureDef entityFixtureDef;
    private CircleShape circleshape;
    private boolean isDead;
    private float posX, posY, radius;
    private World world;

    public Entity(String textureString, float x, float y, World world, String userdata, float density, float restitution, float friction, float radius){
        entityTexture = new Texture(Gdx.files.internal(textureString));
        type = BodyDef.BodyType.DynamicBody;
        posX = x;
        posY = y;
        this.radius = radius;
        this.world = world;
        this.isDead = false;

        position.set(posX, posY); //1f, 7f

        entityBody = this.world.createBody(this);
        entityBody.setUserData(userdata);
        entityFixtureDef = new FixtureDef();

        entityFixtureDef.density     = density; //4.5f
        entityFixtureDef.restitution = restitution; //0.7f
        entityFixtureDef.friction    = friction; //0.7f

        circleshape = new CircleShape();
        circleshape.setRadius(radius);

        entityFixtureDef.shape = circleshape;
        entityBody.createFixture(entityFixtureDef);
    }

    public Body getEntityBody() {
        return entityBody;
    }

    public Texture getEntityTexture() {
        return entityTexture;
    }

    public FixtureDef getEntityFixtureDef(){
        return entityFixtureDef;
    }

    public CircleShape getCircleshape() {
        return circleshape;
    }

    public void setDead(){
        isDead = true;
    }

    public boolean getIsDead(){
        return isDead;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getRadius() {
        return radius;
    }

}

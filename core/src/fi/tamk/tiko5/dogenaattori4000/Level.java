package fi.tamk.tiko5.dogenaattori4000;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Level {

    private int id;
    private String levelName;
    private Texture pic;
    private String map;
    private float startX;
    private float startY;


    public Level(int id, String name, String picture, String smap, float x, float y){
        this.id = id;
        levelName = name;
        pic = new Texture(Gdx.files.internal(picture));
        map = smap;
        startX = x;
        startY = y;
    }


    public int getId(){
        return id;
    }
    public String getLevelName(){
        return levelName;
    }
    public Texture getPic(){
        return pic;
    }
    public String getMap(){
        return map;
    }
    public float getStartX(){
        return startX;
    }
    public float getStartY(){
        return startY;
    }

    //Work in progress (entityInfo)

    class entityInfo{

        private String textureString;
        private float startX;
        private float startY;
        private String userdata;
        private float density;
        private float restitution;
        private float friction;
        private float radius;

        public entityInfo(String textureString, float x, float y, String userdata, float density, float restitution, float friction){
            this.textureString = textureString;
            this.startX = x;
            this.startY = y;
            this.userdata = userdata;
            this.density = density;
            this.restitution = restitution;
            this.friction = friction;
            this.radius = 0.15f;
        }

        public String getTextureString() {
            return textureString;
        }

        public float getStartX() {
            return startX;
        }

        public float getStartY() {
            return startY;
        }

        public String getUserdata() {
            return userdata;
        }

        public float getDensity() {
            return density;
        }

        public float getRestitution() {
            return restitution;
        }

        public float getFriction() {
            return friction;
        }

        public float getRadius() {
            return radius;
        }
    }
}

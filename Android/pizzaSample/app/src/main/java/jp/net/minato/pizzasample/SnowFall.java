package jp.net.minato.pizzasample;

/**
 * Created by User2 on 10/24/2015.
 */
import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.particle.BatchedPseudoSpriteParticleSystem;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;


public class SnowFall extends SimpleBaseGameActivity{

    private final int CAMERA_WIDTH = 480;
    private final int CAMERA_HEIGHT = 836;

    private ITexture mSnowParticle;
    private ITextureRegion mSnowParticleRegion;

    public static final boolean DEBUG = true;

    private Camera mCamera;
    private TextureRegion mRegion;
    private Scene mScene;


    BitmapTextureAtlas giftBoxTexture;
    ITextureRegion giftBoxTextureRegion;
    PhysicsWorld mPhysicsWorld;



    @Override
    public EngineOptions onCreateEngineOptions() {
        this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.PORTRAIT_SENSOR,new FillResolutionPolicy(), this.mCamera);
    }

    @Override
    public void onCreateResources() throws IOException {
        this.mSnowParticle = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "snow_sprite_1.png", TextureOptions.BILINEAR);
        this.mSnowParticleRegion = TextureRegionFactory.extractFromTexture(this.mSnowParticle);
        this.mSnowParticle.load();
        SnowFallBackgroundImage();
        LoadGiftBoxImage();
    }

    private void LoadGiftBoxImage(){
        giftBoxTexture = new BitmapTextureAtlas(this.getTextureManager(),297,287);
        giftBoxTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(giftBoxTexture,this,"giftbox.png",0,0);
        giftBoxTexture.load();
    }

    private void SnowFallBackgroundImage(){
        BitmapTextureAtlas atlas = new BitmapTextureAtlas(this.getTextureManager(),CAMERA_WIDTH,CAMERA_HEIGHT);
        this.mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, this, "snow_bg.png", 0, 0);
        atlas.load();
    }

    private void createWalls() {
        // TODO Auto-generated method stub
        FixtureDef WALL_FIX = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.0f);
        Rectangle ground = new Rectangle(0,-1, CAMERA_WIDTH -15, 15, this.mEngine.getVertexBufferObjectManager());
        ground.setColor(new Color(15, 50, 0));
        PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyDef.BodyType.StaticBody, WALL_FIX);
        this.mScene.attachChild(ground);
    }
    @Override
    public Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        this.mScene = new Scene();
        float centerX = CAMERA_WIDTH /2;
        float centerY = CAMERA_HEIGHT /2;

        this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH),false);
        this.mScene.registerUpdateHandler(this.mPhysicsWorld);

        final Body[] body = new Body[1];
        Sprite sprite = new Sprite(centerX,centerY,this.mRegion,this.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
                    Sprite sPlayer = new Sprite(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, giftBoxTextureRegion, mEngine.getVertexBufferObjectManager());
                    final FixtureDef PLAYER_FIX = PhysicsFactory.createFixtureDef(10.0f, 1.0f, 0.0f);
                    body[0] = PhysicsFactory.createCircleBody(mPhysicsWorld, sPlayer, BodyDef.BodyType.DynamicBody, PLAYER_FIX);
                    mScene.attachChild(sPlayer);
                   mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sPlayer, body[0],true,false) /*new PhysicsConnector(sPlayer, body, true, false)*/);

                }
                return true;
            }
        };

        this.mScene.setBackground(new SpriteBackground(sprite));

        final BatchedPseudoSpriteParticleSystem particleSystem = new BatchedPseudoSpriteParticleSystem(new RectangleParticleEmitter(CAMERA_WIDTH / 2, CAMERA_HEIGHT, CAMERA_WIDTH, 1), 2, 5, 100, mSnowParticleRegion, this.getVertexBufferObjectManager());
        particleSystem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
        particleSystem.addParticleInitializer(new VelocityParticleInitializer<Entity>(-3, 3, -20, -40));
        particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Entity>(-3, 3, -3, -5));
        particleSystem.addParticleInitializer(new RotationParticleInitializer<Entity>(0.0f, 360.0f));
        particleSystem.addParticleInitializer(new ExpireParticleInitializer<Entity>(30f));
        particleSystem.addParticleInitializer(new ScaleParticleInitializer<Entity>(0.5f, 0.8f));
        particleSystem.addParticleInitializer(new RegisterXSwingEntityModifierInitializer<Entity>(30f, 0f, (float) Math.PI * 8, 3f, 25f, true));

        particleSystem.addParticleModifier(new AlphaParticleModifier<Entity>(6f, 30f, 1.0f, 0.0f));

        this.mScene.attachChild(particleSystem);

        this.mScene.registerTouchArea(sprite);

        createWalls();
        return this.mScene;
    }

}


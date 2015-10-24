package jp.net.minato.pizzasample;

/**
 * Created by User2 on 10/24/2015.
 */
import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
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
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import android.opengl.GLES20;


public class SnowFall extends SimpleBaseGameActivity {

    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 1280;

    private ITexture mSnowParticle;
    private ITextureRegion mSnowParticleRegion;

    public static final boolean DEBUG = true;


    @Override
    public EngineOptions onCreateEngineOptions() {

        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.PORTRAIT_SENSOR,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    public void onCreateResources() throws IOException {
        this.mSnowParticle = new AssetBitmapTexture(this.getTextureManager(),
                this.getAssets(), "snow_sprite_1.png", TextureOptions.BILINEAR);
        this.mSnowParticleRegion = TextureRegionFactory.extractFromTexture(
                this.mSnowParticle);
        this.mSnowParticle.load();
    }

    @Override
    public Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final Scene scene = new Scene();
        scene.setBackground(new Background(0.0f, 0.0f, 0.0f, 0.0f));

        final BatchedPseudoSpriteParticleSystem particleSystem = new BatchedPseudoSpriteParticleSystem(
                new RectangleParticleEmitter(CAMERA_WIDTH / 2, CAMERA_HEIGHT, CAMERA_WIDTH, 1),
                2, 5, 100, mSnowParticleRegion,
                this.getVertexBufferObjectManager());
        particleSystem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
        particleSystem.addParticleInitializer(new VelocityParticleInitializer<Entity>(-3, 3, -20, -40));
        particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Entity>(-3, 3, -3, -5));
        particleSystem.addParticleInitializer(new RotationParticleInitializer<Entity>(0.0f, 360.0f));
        particleSystem.addParticleInitializer(new ExpireParticleInitializer<Entity>(30f));
        particleSystem.addParticleInitializer(new ScaleParticleInitializer<Entity>(0.5f, 0.8f));
        particleSystem.addParticleInitializer(new RegisterXSwingEntityModifierInitializer<Entity>(30f, 0f, (float) Math.PI * 8, 3f, 25f, true));

        particleSystem.addParticleModifier(new AlphaParticleModifier<Entity>(6f, 30f, 1.0f, 0.0f));

        scene.attachChild(particleSystem);


        return scene;
    }

}


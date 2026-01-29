package io.github.Buchshot;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import io.github.Buchshot.GameItem.CurrentProp;
import io.github.Buchshot.Logic.AiLogic;
import io.github.Buchshot.Logic.Gamelogic;

import java.util.IllegalFormatWidthException;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    CurrentProp currentProp=new CurrentProp();
    Gamelogic gamelogic = new Gamelogic();

    private float inputCooldown = 0f;
    private final float INPUT_DELAY = 0.3f;

    private SpriteBatch batch;
    private Texture image;
    private Music sounds;
    private StretchViewport  viewport;
    private BitmapFont font;
    private  Texture woodMap;
    private Sprite Null_bullets;
    private Sprite True_bullets;
    private Gamelogic Current_game;
    private Sound GunReport;
    private Sound GunTrue;
    private Sound GunFalse;

    private BitmapFont N_bulletsNumble;
    private BitmapFont T_bulletsNumble;
    private BitmapFont Is_yourRound;
    private BitmapFont My_Health;
    private BitmapFont His_Health;
    private BitmapFont OverPlay;

    private boolean is_high_bullets=false;
    private Sprite high_bullets;
    AiLogic ai;
    private int currenstate;
    private static final float INPUT_COOLDOWN_TIME = 0.3f;
    boolean IsTrueBu=false;

    @Override
    public void create() {
        is_high_bullets=true;
        //以上是测试数据;

        Null_bullets=new Sprite(new TextureRegion(new Texture(Gdx.files.internal("Bullets.png")),2,0,25/3,60));
        N_bulletsNumble=new BitmapFont();
        True_bullets=new Sprite(new TextureRegion(new Texture(Gdx.files.internal("Bullets.png")),13,2,25/3,60));
        T_bulletsNumble=new BitmapFont();
        Is_yourRound=new BitmapFont();
        My_Health=new BitmapFont();
        His_Health=new BitmapFont();
        OverPlay=new BitmapFont();
        ai=new AiLogic();

        high_bullets=new Sprite(new TextureRegion(new Texture(Gdx.files.internal("Bullets.png")),49,2,25/3,120));

        batch = new SpriteBatch();
        woodMap = new Texture("wood1.png");
        image = new Texture("Uibackground.png");

        GunReport = Gdx.audio.newSound(Gdx.files.internal("GunReport.mp3"));
        GunTrue =Gdx.audio.newSound(Gdx.files.internal("GunTrue.wav"));
        GunFalse =Gdx.audio.newSound(Gdx.files.internal("Gunfalse.wav"));

        sounds = Gdx.audio.newMusic(Gdx.files.internal("backgroundmusic.mp3"));
        viewport = new StretchViewport(800,480);
        sounds.setLooping(true); // 设置循环播放，避免手动检查
        sounds.setVolume(0.5f); // 设置音量
        sounds.play();
    }

    @Override
    public void render() {


        if(!currentProp.GameOver) {
            SoundPlay();
            if(currentProp.cnt%2==1)
            {
                input();
            }
            else
            {
                OrtherInput();
            }
            if(currentProp.IsTureBu==1){
                GunTrue.play();
                currentProp.IsTureBu=0;
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            else if(currentProp.IsTureBu==-1){
                GunFalse.play();
                currentProp.IsTureBu=0;
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            drow();
        }
        else{
            Overdrow();
            Overinput();
        }
    }


    private void SoundPlay(){
        if(currentProp.GameRound){
            GunReport.play();
            currentProp.GameRound=false;
        }
    }
    private void OrtherInput()
    {
        ai.relation(currentProp);
    }

    private void Overinput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
    private void Overdrow(){
        float x = 100;
        float y = 100;
        String Over="Wrong";
        if(currentProp.Health1<=0) { Over="YOU DIE";}
        else  if(currentProp.Health2<=0){Over="YOU WIN";}
        float originalWidth = image.getWidth();
        float originalHeight = image.getHeight();
        float scale = 0.3f;
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        batch.draw(woodMap,0,0,viewport.getWorldWidth(),viewport.getWorldHeight());
        OverPlay.draw(batch,Over,viewport.getWorldWidth() / 2 - 90, viewport.getWorldHeight() / 2);
        batch.end();
    }
    private void drow(){
        float x = 100;
        float y = 100;
        float originalWidth = image.getWidth();
        float originalHeight = image.getHeight();
        float scale = 0.3f;
        String N_b=String.valueOf(currentProp.N_bn);
        String T_b=String.valueOf(currentProp.T_bn);
        String My_HeaithB="Health:"+String.valueOf(currentProp.Health1);
        String His_HeaithB="Health:"+String.valueOf(currentProp.Health2);

        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        Null_bullets.setPosition(0,viewport.getWorldHeight()/2);
        True_bullets.setPosition(viewport.getWorldWidth()- (float) 25 /3,viewport.getWorldHeight()/2);
        high_bullets.setPosition(50,0);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();

        //batch.draw(image, 0, 0,viewport.getWorldWidth(),viewport.getWorldHeight());
        batch.draw(woodMap,0,0,viewport.getWorldWidth(),viewport.getWorldHeight());

        Null_bullets.draw(batch);
        N_bulletsNumble.draw(batch,N_b,21,viewport.getWorldHeight()/2+30);
        N_bulletsNumble.getData().setScale(2.5f);

        True_bullets.draw(batch);
        T_bulletsNumble.draw(batch,T_b,viewport.getWorldWidth()-50,viewport.getWorldHeight()/2+30);
        T_bulletsNumble.getData().setScale(2.5f);

        My_Health.draw(batch,My_HeaithB,viewport.getWorldWidth()-150,30);
        My_Health.getData().setScale(2.5f);

        His_Health.draw(batch,His_HeaithB,0,viewport.getWorldHeight());
        His_Health.getData().setScale(2.5f);

        if(currentProp.cnt%2==1) {
            Is_yourRound.draw(batch, "Your Round", viewport.getWorldWidth() / 2 - 90, viewport.getWorldHeight() / 2);
            Is_yourRound.getData().setScale(2.5f);
        }
        else{
            Is_yourRound.draw(batch, "Please Wait", viewport.getWorldWidth() / 2 - 90, viewport.getWorldHeight() / 2);
            Is_yourRound.getData().setScale(2.5f);
        }
        if (is_high_bullets && high_bullets != null) {
            high_bullets.draw(batch);
        };
        batch.end();
    }

    private void input() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            is_high_bullets = !is_high_bullets;

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            int ZZ=gamelogic.RoundOver(1,currentProp );
            gamelogic.IsGameOver(ZZ,currentProp);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            int ZZ=gamelogic.RoundOver(2,currentProp );
            gamelogic.IsGameOver(ZZ,currentProp);
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        sounds.dispose();
        font.dispose();
        N_bulletsNumble.dispose();
        T_bulletsNumble.dispose();
        Is_yourRound.dispose();
        My_Health.dispose();
        His_Health.dispose();
        OverPlay.dispose();
    }

    public void Load_player(){

    }
}

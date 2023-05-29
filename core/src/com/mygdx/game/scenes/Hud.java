package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.scenes.ProgressBar.Stamina;
import com.mygdx.game.scenes.ProgressBar.HealthBar;


public class Hud implements Disposable {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    public Stage stage1;
    private Viewport viewport;
    private PlayerData playerData;

    //Mario score/time Tracking Variables
    private Integer worldTimer;
    private static Integer health;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private static Integer score;

    //Scene2D widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;
    private static Label healthLabel;
    private BitmapFont white;
    private HealthBar healthBar;
    private Stamina endurance;


    public Hud(SpriteBatch sb, PlayerData playerData) {
        //define our tracking variables;
        worldTimer = 300;
        timeCount = 0;
        this.playerData = playerData;
        score = 0;


        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(Constants.WIDTH_SCREEN_STANDART, Constants.HEIGHT_SCREEN_STANDART, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
//        Table table = new Table();
//
//        //Top-Align table
//        table.top();
//        //make the table fill the entire stage
//        table.setFillParent(true);
//
//
//        //define our labels using the String, and a Label style consisting of a font and color
//        healthLabel = new Label(String.format("%02d", (int) playerData.getHp()), new Label.LabelStyle(new BitmapFont(), Color.RED));
//        scoreLabel = new Label(String.format("%01d", score), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
//        //add our labels to our table, padding the top, and giving them all equal width with expandX
//        table.add(healthLabel).expandX().pad(170,320,0,0);
//        table1.add(scoreLabel).expandX().pad(100,320,0,0);
//        //add our table to the stage
//        stage.addActor(table);

    }



//    public void update(){
//        Integer hp = (int) playerData.getHp();
//        healthLabel.setText(String.format("%01d", hp));
//    }
    public void createHeathBar(){
        stage = new Stage();
        healthBar = new HealthBar(120, 10, playerData);
        healthBar.setPosition(10, Gdx.graphics.getHeight() - 20);
        healthBar.setValue((int) playerData.getHp());
        stage.addActor(healthBar);
    }
    public void createStamina(){
        endurance  = new Stamina(120, 10, playerData);
        endurance.setPosition(10, Gdx.graphics.getHeight() - 40);
        endurance.setValue(endurance.getValue() - 1);
        stage.addActor(endurance);
    }
    public void update(){
        createHeathBar();
        createStamina();
        stage.draw();
        stage.act();
    }


    @Override
    public void dispose() { stage.dispose(); }

    public boolean isTimeUp() { return timeUp; }

}

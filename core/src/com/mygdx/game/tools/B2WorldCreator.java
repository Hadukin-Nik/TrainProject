package com.mygdx.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.enemy.SlimeProvider.EnemyFactory;
import com.mygdx.game.entity.enemy.EnemyProvider;
import com.mygdx.game.entity.items.PotionFactory;
import com.mygdx.game.entity.platforms.MovingPlatform;
import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.screen.PlayScreen;


public class B2WorldCreator {
    private PlayerProvider player;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //creating player
        if(0 < map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class).size) {
            Rectangle rect = map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
            player = new PlayerProvider(new Vector2((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2)/ Constants.PPM), new PlayerData(), screen);
            screen.addToUpdate(player);

        }

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Constants.PPM, rect.getHeight() / 2 / Constants.PPM);
            fdef.filter.categoryBits = Masks.GROUND_BIT;
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //create elevators
       for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            Vector2 size = new Vector2(64,8);
            Vector2 from = new Vector2((rect.x + size.x / 2)/Constants.PPM, (rect.y + size.y / 2)/Constants.PPM);
            Vector2 to = new Vector2((rect.x + rect.getWidth() - size.x / 2)/Constants.PPM, (rect.y + rect.getHeight() - size.y / 2)/Constants.PPM);
            Texture texture = new Texture(Constants.PATH_TO_ENTITIES + "/elevator/elevator.png");
            MovingPlatform mv = new MovingPlatform(from, to, Constants.SPEED_STANDART / 2);
            mv.definePlatform(screen, texture, size, size);
        }


        //create turrel
        for(MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            EnemyProvider enemy = EnemyFactory.turrel(rect, screen, player);
            screen.addToUpdate(enemy);
        }
        //create slimes

        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            EnemyProvider enemy = EnemyFactory.randSlime(rect, screen, player);
            screen.addToUpdate(enemy);
        }

        //create dogs
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            EnemyProvider enemy = EnemyFactory.dog(rect, screen, player);
            screen.addToUpdate(enemy);
        }

        //create potions
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            EntityProvider potion = PotionFactory.createRandomPotion(rect, screen, (PlayerData) player.getEntityData());
            screen.addToUpdate(potion);
        }

    }

    public PlayerProvider getPlayer() {
        return player;
    }
}

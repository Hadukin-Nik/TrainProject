package com.mygdx.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.screen.PlayScreen;


public class B2WorldCreator {

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM / 4, (rect.getY() + rect.getHeight() / 2) / Constants.PPM / 4);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / 4 / Constants.PPM, rect.getHeight() / 2 / 4 / Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create boxes bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM / 4, (rect.getY() + rect.getHeight() / 2) / Constants.PPM / 4);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / 4 / Constants.PPM, rect.getHeight() / 2 / 4 / Constants.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Masks.OBJECT_BIT;
            body.createFixture(fdef);
        }
    }
}

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;



        import com.badlogic.gdx.ApplicationAdapter;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.graphics.g2d.Sprite;
        import com.badlogic.gdx.utils.ScreenUtils;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Input.Keys;

public class Barrier {

    public Vector2 pos;
    public Vector2 dim;
    public Vector2 nw_corner;
    public Vector2 se_corner;
    public Vector2 ne_corner;
    public Sprite sprite;
    public float speed = 200;
    public int dir=0;

    public Barrier(Vector2 dimensions){

        //sprite = new Sprite(img);
        //sprite.setScale(3);
        dim = dimensions;
        pos = new Vector2(Gdx.graphics.getWidth()/2 - dim.x/2,Gdx.graphics.getHeight()/2 - dim.y/2);
        nw_corner = new Vector2(pos.x,pos.y+dim.y);
        se_corner = new Vector2(pos.x+dim.x,pos.y);
        ne_corner = new Vector2(pos.x+dim.x,pos.y+dim.y);

    }
    public void Update(float deltaTime){
        //if(position.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0) position.x = (sprite.getWidth()*sprite.getScaleX()/2);
        //if(position.x+(sprite.getWidth()*sprite.getScaleX()/2)>=Gdx.graphics.getWidth()) position.x = Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);
        //position_bullet.y+=deltaTime*speed_bullet;
    }
    public void Draw(SpriteBatch batch){
        Update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(pos.x, pos.y);
        sprite.draw(batch);
    }

    public boolean intersection(Vector2 v, Vector2 vv){
        if(doIntersect(v,vv,pos,ne_corner) || doIntersect(v,vv,nw_corner,se_corner)){return true;}
        return false;
    }

    public static int orientation(Vector2 p, Vector2 q, Vector2 r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0)
            return 0; // Collinear
        return (val > 0) ? 1 : 2; // Clockwise or Counterclockwise
    }

    // Function to check if line segment AB
    // and line segment CD intersect.
    public static boolean doIntersect(Vector2 A, Vector2 B, Vector2 C, Vector2 D) {
        // Find the 4 orientations needed for
        // general and special cases
        int o1 = orientation(A, B, C);
        int o2 = orientation(A, B, D);
        int o3 = orientation(C, D, A);
        int o4 = orientation(C, D, B);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases

        // A, B and C are collinear and C lies on segment AB
        if (o1 == 0 && onSegment(A, C, B))
            return true;

        // A, B and D are collinear and D lies on segment AB
        if (o2 == 0 && onSegment(A, D, B))
            return true;

        // C, D and A are collinear and A lies on segment CD
        if (o3 == 0 && onSegment(C, A, D))
            return true;

        // C, D and B are collinear and B lies on segment CD
        if (o4 == 0 && onSegment(C, B, D))
            return true;

        return false; // Doesn't fall in any of the above cases
    }

    // Function to check if point q lies on line segment 'pr'
    public static boolean onSegment(Vector2 p, Vector2 q, Vector2 r) {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && q.y <= Math.max(p.y, r.y)
                && q.y >= Math.min(p.y, r.y))
            return true;
        return false;
    }

}


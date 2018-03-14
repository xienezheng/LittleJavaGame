package cn.lazycat.fly.obj.bullet;

import cn.lazycat.fly.flag.FlyingObject;
import cn.lazycat.fly.flag.Enemy;

import java.awt.image.BufferedImage;

public class BossBullet extends FlyingObject implements Enemy {

    protected int speed;

    private int hit;

    BossBullet() {

    }

    public BossBullet(int speed, int hit, BufferedImage image, int x, int y) {
        this.speed = speed;
        this.hit = hit;
        this.image = image;
        super.x = x;
        super.y = y;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public boolean isDead() {  // Boss发射的子弹永远不会死亡
        return false;
    }

    @Override
    public int getHit() {
        return hit;
    }

    @Override
    public void step() {
        super.y += speed;
    }
}

package cn.lazycat.fly.obj.bullet;

import cn.lazycat.fly.flag.Enemy;

import java.awt.image.BufferedImage;

/**
 * 斜向走的Boss子弹
 */
public class RoundBossBullet extends BossBullet implements Enemy {

    private int hit;
    private int way;
    private int k;

    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    public RoundBossBullet(int speed, int k, int way, int hit, BufferedImage image, int x, int y) {
        this.speed = speed;
        this.hit = hit;
        this.image = image;
        this.k = k;
        super.x = x;
        super.y = y;
        this.way = way;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public int getHit() {
        return hit;
    }

    @Override
    public void step() {
        y += speed;
        int flag = way == RIGHT ? 1 : -1;
        x += flag * speed * k;
    }
}

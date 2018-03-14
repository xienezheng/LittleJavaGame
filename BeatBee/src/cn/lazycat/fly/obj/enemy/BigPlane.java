package cn.lazycat.fly.obj.enemy;

import cn.lazycat.fly.FlyGame;
import cn.lazycat.fly.flag.Enemy;
import cn.lazycat.fly.flag.FlyingObject;
import cn.lazycat.fly.flag.ShootEnemy;
import cn.lazycat.fly.obj.bullet.BossBullet;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BigPlane extends FlyingObject implements Enemy, ShootEnemy {

    private int life;
    private int speed = 1;
    private int foot;  // 代表大飞机飞到下面的哪个位置

    public BigPlane(int life) {
        super.image = FlyGame.bigPlane;
        super.width = super.image.getWidth();
        super.height = super.image.getHeight();
        Random random = new Random();
        this.life = life;
        foot = random.nextInt(300) % 201 + 100;
        super.x = random.nextInt(FlyGame.WIDTH - this.width);
        super.y = -this.height;
        switch (life) {
            case 6:
                ++speed;
            case 5:
                ++speed;
            case 4:
                ++speed;
        }
    }

    @Override
    public int getScore() {
        return 20;
    }

    @Override
    public boolean isDead() {
        --life;
        return life <= 0;
    }

    @Override
    public int getHit() {
        return 2;
    }

    @Override
    public void step() {
        if (super.y < foot) {  // 向下入场
            super.y += this.speed;
        } else { // 左右摇摆
            x += speed;
            if (x <= 0) {  // 此时到最左边了
                speed = Math.abs(speed);
            }
            if (x >= FlyGame.WIDTH - this.width) {  // 此时到最右边了
                speed = -speed;
            }
        }
    }

    @Override
    public List<BossBullet> shoot() {
        List<BossBullet> bullets = new LinkedList<>();
        if (super.y >= foot - 10) {
            // 打飞机发射子弹是在中间产生的
            bullets.add(new BossBullet(3, 1, FlyGame.bossBullet1,
                    super.x + super.width / 2, super.y + 20));
        }
        return bullets;
    }
}

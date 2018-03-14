package cn.lazycat.fly.obj.boss;


import cn.lazycat.fly.flag.Award;
import cn.lazycat.fly.flag.Boss;
import cn.lazycat.fly.flag.Enemy;
import cn.lazycat.fly.obj.bullet.BossBullet;
import cn.lazycat.fly.FlyGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Boss1 extends Boss implements Enemy {

    public Boss1(int bossLevel, int level) {
        super.image = FlyGame.boss1;
        super.speed = 1;
        super.health = 20 * level * bossLevel;
        super.width = image.getWidth();
        super.height = image.getHeight();
        this.bossLevel = bossLevel;
    }


    @Override
    public int getScore() {
        return 100;
    }

    @Override
    public int getHit() {
        return 1;
    }

    @Override
    public List<BossBullet> shoot() {
        int y = super.y + height - 60;
        int xStep = width / 4;
        List<BossBullet> bossBullets = new ArrayList<>(3);
        bossBullets.add(new BossBullet(bossLevel, bossLevel, FlyGame.bossBullet1, x + xStep,  y));
        bossBullets.add(new BossBullet(bossLevel, bossLevel, FlyGame.bossBullet1, x + 2 * xStep,  y));
        bossBullets.add(new BossBullet(bossLevel, bossLevel, FlyGame.bossBullet1, x + 3 * xStep,  y));

        return bossBullets;
    }

    @Override
    public int getType() {
        return Award.SPEED_SLOW;
    }
}

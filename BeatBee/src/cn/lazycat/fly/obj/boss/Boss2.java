package cn.lazycat.fly.obj.boss;

import cn.lazycat.fly.flag.Award;
import cn.lazycat.fly.flag.Boss;
import cn.lazycat.fly.flag.Enemy;
import cn.lazycat.fly.obj.bullet.BossBullet;
import cn.lazycat.fly.FlyGame;
import cn.lazycat.fly.obj.bullet.RoundBossBullet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Boss2 extends Boss implements Enemy {

    public Boss2(int bossLevel, int level) {
        super.image = FlyGame.boss2;
        super.speed = 1;
        super.health = 40 * level * bossLevel;
        super.width = image.getWidth();
        super.height = image.getHeight();
        this.bossLevel = bossLevel;
    }


    @Override
    public int getScore() {
        return 500;
    }

    @Override
    public int getHit() {
        return 2;
    }

    @Override
    public List<BossBullet> shoot() {
        int y = super.y + height - 60;
        int xStep = width / 4;
        List<BossBullet> bossBullets = new ArrayList<>(6);
        bossBullets.add(new RoundBossBullet(2, 2, RoundBossBullet.LEFT,
                bossLevel, FlyGame.bossBullet1, x,  y));
        bossBullets.add(new RoundBossBullet(2, 0, RoundBossBullet.LEFT,
                bossLevel, FlyGame.bossBullet1, x,  y));
        bossBullets.add(new RoundBossBullet(2, 1, RoundBossBullet.LEFT,
                bossLevel, FlyGame.bossBullet1, x + xStep,  y));
        bossBullets.add(new RoundBossBullet(2, 0, RoundBossBullet.LEFT,
                bossLevel, FlyGame.bossBullet1, x + 2 * xStep,  y));
        bossBullets.add(new RoundBossBullet(2, 1, RoundBossBullet.RIGHT,
                bossLevel, FlyGame.bossBullet1, x + 3 * xStep,  y));
        bossBullets.add(new RoundBossBullet(2, 2, RoundBossBullet.RIGHT,
                bossLevel, FlyGame.bossBullet1, x + width,  y));
        bossBullets.add(new RoundBossBullet(2, 0, RoundBossBullet.RIGHT,
                bossLevel, FlyGame.bossBullet1, x + width,  y));
        return bossBullets;
    }

    @Override
    public int getType() {
        return Award.SPEED_MID;
    }
}

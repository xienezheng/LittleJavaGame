package cn.lazycat.fly.obj.boss;

import cn.lazycat.fly.flag.Award;
import cn.lazycat.fly.flag.Boss;
import cn.lazycat.fly.flag.Enemy;
import cn.lazycat.fly.obj.bullet.BossBullet;
import cn.lazycat.fly.FlyGame;
import cn.lazycat.fly.obj.bullet.Bullet;
import cn.lazycat.fly.obj.bullet.RoundBossBullet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Boss3 extends Boss implements Enemy {

    public Boss3(int bossLevel, int level) {
        super.image = FlyGame.boss3;
        super.speed = 2;
        super.health = 80 * level * bossLevel;
        super.width = image.getWidth();
        super.height = image.getHeight();
        this.bossLevel = bossLevel;
        x = 120;
    }

    @Override
    public int getScore() {
        return 1000;
    }

    @Override
    public int getHit() {
        return 5;
    }

    @Override
    public List<BossBullet> shoot() {
        int y = super.y + height - 60;
        int xStep = width / 4;
        List<BossBullet> bossBullets = new ArrayList<>(6);
        Random random = new Random();
        for (int i = 1; i < 4; ++i) {
            bossBullets.add(new RoundBossBullet(random.nextInt(3) + 1, 1, RoundBossBullet.LEFT,
                    bossLevel, FlyGame.bossBullet1, x + i * xStep,  y));
            bossBullets.add(new RoundBossBullet(random.nextInt(3) + 1, 1, RoundBossBullet.RIGHT,
                    bossLevel, FlyGame.bossBullet1, x + i * xStep,  y));
            bossBullets.add(new RoundBossBullet(random.nextInt(3) + 1, 0, RoundBossBullet.RIGHT,
                    bossLevel, FlyGame.bossBullet1, x + i * xStep,  y));
            bossBullets.add(new RoundBossBullet(random.nextInt(3) + 1, 1, RoundBossBullet.LEFT,
                    bossLevel, FlyGame.bossBullet1, x + i * xStep,  y));
            bossBullets.add(new RoundBossBullet(random.nextInt(3) + 1, 1, RoundBossBullet.RIGHT,
                    bossLevel, FlyGame.bossBullet1, x + i * xStep,  y));
            bossBullets.add(new RoundBossBullet(random.nextInt(3) + 1, 0, RoundBossBullet.RIGHT,
                    bossLevel, FlyGame.bossBullet1, x + i * xStep,  y));
        }

        return bossBullets;
    }

    @Override
    public int getType() {
        return Award.SPEED_HIGH;
    }
}

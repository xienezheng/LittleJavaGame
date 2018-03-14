package cn.lazycat.fly.flag;

import cn.lazycat.fly.obj.bullet.BossBullet;

import java.util.List;

/**
 * 代表可以发射子弹的普通敌人
 */
public interface ShootEnemy {

    /**
     * 发射子弹
     * @return 所有发射的子弹
     */
    List<BossBullet> shoot();

}

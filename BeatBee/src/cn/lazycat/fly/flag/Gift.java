package cn.lazycat.fly.flag;

/**
 * 飞行中的补给物品
 */
public interface Gift {

    int BLOOD = 0;   // 回复生命
    int CLEAR = 1;   // 清除所有敌机（不清除BOSS机）
    int DOUBLR_FIRE = 2;   // 双倍火力

    int getType();

}

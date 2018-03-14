package cn.lazycat.fly.flag;

import cn.lazycat.fly.obj.bullet.Bullet;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {

    protected BufferedImage image;
    protected int width;
    protected int height;
    protected int x;
    protected int y;

    public abstract void step();
    public int getY() {
        return this.y;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public BufferedImage getImage() {
        return image;
    }

    /**
     * 判断有没有被飞行物射击
     */
    public boolean shootBy(Bullet bullet) {
        int x1 = this.x;
        int x2 = this.x + this.width;
        int y1 = this.y;
        int y2 = this.y + this.height;

        int x = bullet.getX();
        int y = bullet.getY();

        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }
}

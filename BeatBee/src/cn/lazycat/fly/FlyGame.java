package cn.lazycat.fly;

import cn.lazycat.fly.flag.*;
import cn.lazycat.fly.obj.*;
import cn.lazycat.fly.obj.boss.Boss1;
import cn.lazycat.fly.obj.boss.Boss2;
import cn.lazycat.fly.obj.boss.Boss3;
import cn.lazycat.fly.obj.bullet.BossBullet;
import cn.lazycat.fly.obj.bullet.Bullet;
import cn.lazycat.fly.obj.enemy.Airplane;
import cn.lazycat.fly.obj.enemy.Bee;
import cn.lazycat.fly.obj.enemy.BigPlane;
import cn.lazycat.fly.obj.gift.Blood;
import cn.lazycat.fly.obj.gift.Bomb;
import cn.lazycat.fly.obj.gift.DoubleFire;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.util.*;
import java.util.List;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.*;

public class FlyGame extends JPanel {

    // 窗口的宽和高
    public static final int WIDTH = 400;
    private static final int HEIGHT = 654;

    // 游戏需要的所有图片
    private static BufferedImage background;
    private static BufferedImage start;
    private static BufferedImage pause;
    private static BufferedImage gameover;
    public static BufferedImage airplane;
    public static BufferedImage bee;
    public static BufferedImage bullet;
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage boss1;
    public static BufferedImage boss2;
    public static BufferedImage boss3;
    public static BufferedImage bossBullet1;
    public static BufferedImage bossBullet2;
    public static BufferedImage blood;
    public static BufferedImage bomb;
    public static BufferedImage doubleFire;
    public static BufferedImage bigPlane;
    public static BufferedImage superBullet;
    public static BufferedImage laser;
    private static BufferedImage powerfulBlood;


    private static List<BufferedImage> bloodBars;
    private static List<BufferedImage> powerBars;

    // 游戏的得分
    private static int score = 0;

    // 游戏等级(控制敌人产生的频率)
    private static int level = 1;

    // 子弹射击频率
    private static int speedLevel = 1;

    // BOSS 的强度等级
    private static int bossLevel = 1;

    // 初始化各种游戏参数
    static {
        try {
            // 加载所有的图片
            background = ImageIO.read(FlyGame.class.getResource("images/background.png"));
            start = ImageIO.read(FlyGame.class.getResource("images/start.png"));
            pause = ImageIO.read(FlyGame.class.getResource("images/pause.png"));
            gameover = ImageIO.read(FlyGame.class.getResource("images/gameover.png"));
            airplane = ImageIO.read(FlyGame.class.getResource("images/airplane.png"));
            bee = ImageIO.read(FlyGame.class.getResource("images/bee.png"));
            bullet = ImageIO.read(FlyGame.class.getResource("images/bullet.png"));
            hero0 = ImageIO.read(FlyGame.class.getResource("images/hero0.png"));
            hero1 = ImageIO.read(FlyGame.class.getResource("images/hero1.png"));
            boss1 = ImageIO.read(FlyGame.class.getResource("images/boss1.png"));
            boss2 = ImageIO.read(FlyGame.class.getResource("images/boss2.png"));
            boss3 = ImageIO.read(FlyGame.class.getResource("images/boss3.png"));
            bossBullet1 = ImageIO.read(FlyGame.class.getResource("images/bossBullet1.png"));
            bossBullet2 = ImageIO.read(FlyGame.class.getResource("images/bossBullet2.png"));
            blood = ImageIO.read(FlyGame.class.getResource("images/blood.png"));
            bomb = ImageIO.read(FlyGame.class.getResource("images/bomb.png"));
            doubleFire = ImageIO.read(FlyGame.class.getResource("images/doubleFire.png"));
            bigPlane = ImageIO.read(FlyGame.class.getResource("images/bigPlane.png"));
            laser = ImageIO.read(FlyGame.class.getResource("images/laser.png"));

            powerfulBlood = ImageIO.read(FlyGame.class.getResource("images/bloodBars/powerfulBlood.png"));
            bloodBars = new ArrayList<>(67);
            for (int i = 0; i < 67; ++i) {
                bloodBars.add(ImageIO.read(FlyGame.class.getResource(
                        "images/bloodBars/bloodBar" + (i + 1) + ".png")));
            }

            powerBars = new ArrayList<>(11);
            for (int i = 0; i < 11; ++i) {
                powerBars.add(ImageIO.read(FlyGame.class.getResource(
                        "images/powerBars/powerBar" + i + ".png")));
            }


            superBullet = ImageIO.read(FlyGame.class.getResource("images/superBullet.png"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 游戏的四个状态
    private static final int START = 0;      // 启动
    private static final int RUNNING = 1;    // 运行中
    private static final int PAUSE = 2;      // 暂停
    private static final int GAME_OVER = 3;  // 游戏结束

    // 游戏当前状态
    private int status = START;

    private Hero hero = new Hero();         // 英雄机
    private Boss boss = null;                      // Boss机
    private List<ShootEnemy> shootEnemies = new LinkedList<>();  // 所有要射击的敌方飞机
    private List<FlyingObject> flyings = new LinkedList<>(); // 所有对方飞行物
    private List<Bullet> bullets = new LinkedList<>();          // 所有子弹

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);

        switch (status) {
            case START:
                g.drawImage(start, 0, 0, null);
                break;
            case PAUSE:
                g.drawImage(pause, 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(gameover, 0, 0, null);
                return;

        }
        switch (status) {
            case RUNNING:
                paintBullets(g);
                paintEnemies(g);
                paintPower(g);
            case PAUSE:
                paintLife(g);
            case GAME_OVER:
                paintMark(g);
        }
        paintHero(g);
    }

    private void paintHero(Graphics g) {
        g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
    }

    private void paintBullets(Graphics g) {
        for (Bullet bullet : bullets) {
            g.drawImage(bullet.getImage(), bullet.getX(),
                    bullet.getY(), null);
        }
    }

    private void paintEnemies(Graphics g) {

        synchronized (this) {

            for (FlyingObject flying : flyings) {
                g.drawImage(flying.getImage(), flying.getX(),
                        flying.getY(), null);
            }

        }
    }

    private void paintMark(Graphics g) {  // 绘制计分板
        g.setColor(new Color(255, 0, 0));
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g.drawString("得分: " + score, 10, 20);
        g.drawString("双倍火力:  " + hero.getDoubleFire(), 10, 40);
        g.drawString("子弹速度:  " + speedLevel, 10, 60);
        g.drawString("级别: " + level, 10, 80);
    }

    private void paintLife(Graphics g) {
        if (hero.getLife() >= 1 && hero.getLife() <= 67) {
            g.drawImage(bloodBars.get(hero.getLife() - 1), 20, HEIGHT - 100, null);
        }
        if (hero.getLife() > 67) {
            g.drawImage(bloodBars.get(66), 20, HEIGHT - 100, null);
        }
    }

    private void paintPower(Graphics g) {
        if (hero.getPower() >= 0 && hero.getPower() < 110) {
            g.drawImage(powerBars.get(hero.getPower() / 10), 20, HEIGHT - 70, null);
        } else if (hero.getPower() < 0) {
            g.drawImage(powerBars.get(0), 20, HEIGHT - 70, null);
        } else {
            g.drawImage(powerBars.get(10), 20, HEIGHT - 70, null);
        }
    }

    private int flyIndex = 0;    // 敌人入场计时器
    private void enterAction() {  // 敌人入场、敌人前进
        flyIndex++;
        if (flyIndex >= (65 - level * 10)) {
            flyIndex = 0;
            // 此时让一个敌人入场
            Random random = new Random();
            int type = random.nextInt(40);
            // 蜜蜂的入场频率应该低于敌机
            FlyingObject flying;
            if (type < 6) {
                flying = new Bee();
            } else {
                flying = new Airplane(level);
            }
            if (level >= 5 && type > 6 && type < 10) {
                flying = new BigPlane(level);
                shootEnemies.add((BigPlane) flying);  // 加入到发射队列
            }
            flyings.add(flying);
        }
    }

    private void stepAction() { // 让所有飞行物前进一步
        nextStep(flyings);
        hero.step();
        nextStep(bullets);
    }
    private void nextStep(List<?> objects) {
        for (Object object : objects) {
            ((FlyingObject) object).step();
        }
    }

    private short clearIndex = 0;  // 清除敌机计时器
    private void clearAction() {  // 清除那些出场的敌机
        clearIndex++;
        if (clearIndex == 80) {
            clearIndex = 0;
            nextClear(flyings);
            nextClear(bullets);
        }
    }
    private synchronized void nextClear(List<?> objects) {
        Iterator<?> ite = objects.iterator();
        while (ite.hasNext()) {
            FlyingObject flying = (FlyingObject) ite.next();
            if (flying instanceof Enemy) {
                if (flying.getY() > FlyGame.HEIGHT + 10) {
                    ite.remove();
                }
            }
            if (flying instanceof Bullet) {
                if (flying.getY() < -10) {
                    ite.remove();
                }
            }
        }
    }

    private short heroShootIndex = 0;
    private short bossShootIndex = 0;
    private short shootEnemyIndex = 0;
    private void shootAction() {  // 发射子弹
        ++heroShootIndex;
        if (heroShootIndex >= (40 - speedLevel)) {  // 控制发射间隔
            heroShootIndex = 0;
            // 英雄机发射子弹
            List<Bullet> shootBullets = hero.shoot();
            bullets.addAll(shootBullets);
        }

        if (boss != null) {  // Boss发射子弹
            ++bossShootIndex;
            if (bossShootIndex >= 70 - (level * 5)) {
                bossShootIndex = 0;
                List<BossBullet> bossBullets = boss.shoot();
                flyings.addAll(bossBullets);
            }
        }

        ++shootEnemyIndex;
        if (shootEnemyIndex >= 60) {
            shootEnemyIndex = 0;
            for (ShootEnemy enemy : shootEnemies) {
                List<BossBullet> bullets = enemy.shoot();
                flyings.addAll(bullets);  // 发射子弹
            }
        }
    }

    private void bitAction() {  // 子弹和敌人的碰撞

        Iterator<FlyingObject> iteEnemy = flyings.iterator();
        while (iteEnemy.hasNext()) {
            FlyingObject flying = iteEnemy.next();
            // 判断每个子弹和这个敌机是否发生碰撞
            Iterator<Bullet> iteBullet = bullets.iterator();
            while (iteBullet.hasNext()) {
                Bullet bullet = iteBullet.next();
                if (flying.shootBy(bullet)) {  // 敌人被子弹击中
                    if (flying instanceof Award) {  // 这时需要增加奖励
                        Award award = (Award) flying;
                        switch (award.getType()) {
                            case Award.DOUBLE_FIRE:  // 此时增加双倍火力
                                hero.addDoubleFire(8);
                                break;
                            case Award.LIFE:   // 此时增加生命
                                hero.addLife(1);
                                break;
                        }
                    }
                    if (flying instanceof Enemy) {
                        Enemy enemy = (Enemy) flying;
                        if (enemy.isDead()) {  // 敌人死亡

                            // 英雄机需要增加能量值，默认为1
                            int addPower = 1;

                            if (flying instanceof Boss) {  // 击杀Boss
                                addPower = 20;
                                boss = null;
                                // 需要奖励子弹速度
                                Award award = (Award) flying;
                                int addSpeedLevel = 0;
                                switch (award.getType()) {
                                    case Award.SPEED_SLOW:
                                        addSpeedLevel = 2;
                                        break;
                                    case Award.SPEED_MID:
                                        addSpeedLevel = 4;
                                        break;
                                    case Award.SPEED_HIGH:
                                        addSpeedLevel = 8;
                                        break;
                                }
                                if (speedLevel <= 30) {  // 子弹速度不超过25
                                    speedLevel += addSpeedLevel;
                                }
                            }

                            if (flying instanceof ShootEnemy) {  // 击杀发射子弹的普通敌人
                                addPower = 2;
                                // 把敌人从发射队列剔除
                                ShootEnemy shootEnemy1 = (ShootEnemy) flying;
                                shootEnemies.removeIf(shootEnemy1::equals);
                            }

                            iteEnemy.remove();  // 清除这个敌人
                            if (!hero.isSuper()) {    // 超级模式下不加能量
                                hero.addPower(addPower);  // 英雄机增加能量
                            }
                            score += enemy.getScore();  // 击败敌人都能加分

                        }
                        iteBullet.remove();  // 清除这个子弹
                    }
                    break;  // 不需要再继续循环了
                }
            }
        }

    }

    private void heroBitAction() {
        Iterator<FlyingObject> flyingIte = flyings.iterator();
        while (flyingIte.hasNext()) {
            FlyingObject flying = flyingIte.next();
            if (hero.hitBy(flying)) {  // 英雄被撞击了
                if (flying instanceof Enemy) {  // 被敌人撞击了
                    Enemy enemy = (Enemy) flying;
                    flyingIte.remove();      // 敌人灭亡
                    hero.reduceLife(enemy.getHit());
                    if (flying instanceof ShootEnemy) {  // 从发射队列删除被装毁的敌机
                        shootEnemies.removeIf(flying::equals);
                    }
                }
                if (flying instanceof Gift) {  // 吃到了补给物品
                    flyingIte.remove(); // 补给品消失
                    Gift gift = (Gift) flying;
                    switch (gift.getType()) {
                        case Gift.BLOOD:
                            hero.addLife(3);
                            break;
                        case Gift.CLEAR:
                                // BOSS 机不能被炸弹消灭
                                flyings.removeIf(enemy -> !(enemy instanceof Boss)
                                        && enemy instanceof Enemy);
                            break;
                        case Gift.DOUBLR_FIRE:
                            hero.addDoubleFire(20);
                            break;
                    }
                }
            }
        }
    }

    private int bossIndex = 0;
    private void updateLevel() {  // 升级难度、生成BOSS机
        if (score > 300 && score < 1000) {
            level = 2;
        } else if (score >= 1000 && score < 2000) {
            level = 3;
        } else if (score >= 2000 && score < 10000) {
            level = 4;
        } else if (score >= 10000 && score < 40000) {
            level = 5;
        } else if (score > 40000) {
            level = 6;
        }
        if (boss == null) {
            ++bossIndex;
            if (bossIndex >= 500 && bossIndex < 2000) {
                bossIndex = 2000;
                boss = new Boss1(bossLevel, level);
                flyings.add(boss);
            }
            if (bossIndex >= 8000 && bossIndex < 9000) {
                bossIndex = 10000;
                boss = new Boss2(bossLevel, level);
                flyings.add(boss);
            }
            if (bossIndex >= 16000) {
                bossIndex = 0;
                boss = new Boss3(bossLevel, level);
                flyings.add(boss);
                if (bossLevel <= 10) {
                    ++bossLevel;  // 刷完一轮BOSS，下一轮BOSS的难度变大
                }
            }
        }
    }

    private void checkGameOverAction() {
        if (hero.getLife() <= 0) {
            this.status = GAME_OVER;
        }
    }

    private int giftIndex = 0;
    private void enterGiftAction() {

        ++giftIndex;
        if (giftIndex >= 500) {  // 每 5 秒可能产生一个礼包(或者敌人)
            giftIndex = 0;
            Random random = new Random();
            int rand = random.nextInt(20);
            FlyingObject flying;
            if (rand >= 1 && rand <= 3) {
                flying = new Blood();
            } else if (rand > 3 && rand <= 4) {
                flying = new DoubleFire();
            } else if (rand == 5) {
                flying = new Bomb();
            } else {  // 没有礼包，获得一个敌人QAQ
                flying = new BigPlane(level);
                shootEnemies.add((BigPlane) flying);  // 加入到发射队列
            }
            flyings.add(flying);

        }

    }

    private void powerAction() {
        if (!hero.isSuper()) {
            if (hero.getPower() >= 110) {  // 进入超级模式
                hero.setLife(67);  // 满血
                hero.powerFire();
            }
        } else {
            if (hero.getPower() <= 0) {    // 退出超级模式
                hero.powerFireDown();
            }
        }
    }


    private void action() {  // 绑定事件、启动计时器

        // 创建鼠标事件监听器
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // 鼠标点击
                switch (status) {
                    case GAME_OVER:
                        if (e.getButton() == MouseEvent.BUTTON1) { // 表示重新开始
                            hero.init();
                            bullets.clear();
                            flyings.clear();
                            shootEnemies.clear();
                            boss = null;
                            score = 0;
                            level = 1;
                            bossLevel = 1;
                        }
                        status = START;
                        break;
                    case START:
                        status = RUNNING;
                        break;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {  // 鼠标进入
                if (status == PAUSE) {
                    status = RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {  // 鼠标离开
                if (status == RUNNING) {
                    status = PAUSE;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {  // 鼠标移动
                if (status == RUNNING) {
                    // 英雄机跟着鼠标移动
                    hero.moveTo(e.getX(), e.getY());
                }
            }
        };
        // 绑定监听器
        this.addMouseListener(mouseAdapter);        // 鼠标操作
        this.addMouseMotionListener(mouseAdapter);  // 鼠标移动

        java.util.Timer timer = new Timer();
        int pre = 10;    // 定时间隔(ms)
        // 启动一个定时事件，代表每经过10个ms触发一次事件
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                switch (status) {
                    case RUNNING:

                        checkGameOverAction(); // 检测游戏是否失败
                        enterAction();  // 敌机进场
                        stepAction();  // 所有飞行物前进一次
                        shootAction();  // 发射子弹
                        bitAction();  // 子弹射击敌机
                        heroBitAction();  // 敌机撞击英雄机
                        clearAction();  // 清理敌机
                        enterGiftAction();  // 发射礼包
                        powerAction();  // 增加能量
                        updateLevel();   // 增加游戏难度

                    case GAME_OVER:
                    case PAUSE:
                    case START:
                        repaint();
                }
            }
        }, pre, pre);
    }


    public static void main(String[] args) {

        JFrame frame = new JFrame("打飞机小游戏");
        FlyGame game = new FlyGame();
        game.hideCursor();  // 隐藏鼠标指针
        frame.add(game);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.action();  // 启动游戏
    }

    // 隐藏鼠标指针
    private void hideCursor() {
        Image image = Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(0, 0, new int[0], 0, 0));
        this.setCursor(Toolkit.getDefaultToolkit().
                createCustomCursor(image, new Point(0, 0), null));
    }

}

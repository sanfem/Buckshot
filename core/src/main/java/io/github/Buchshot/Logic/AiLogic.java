package io.github.Buchshot.Logic;

import com.badlogic.gdx.Gdx;
import io.github.Buchshot.GameItem.CurrentProp;

import java.util.Random;
import java.util.Timer;

public class AiLogic implements Runnable {

    Gamelogic gamelogic=new Gamelogic();
    CurrentProp currentProp;

    public AiLogic(CurrentProp currentProp){
        this.currentProp=currentProp;
    }

    private int RandomAi() {
        Random rand = new Random(System.currentTimeMillis());
        int r = rand.nextInt(2);
        if (r == 0) return 1;
        else return 0;
    }

    @Override
    public void run(){
        try {
            while(true) {
                relation();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public  void relation() throws InterruptedException {

        synchronized (this) {
            while (currentProp.cnt % 2 == 1) {
                System.out.println("AI等待玩家回合...");
                wait();  // 等待时释放锁
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            return;
        }
            int ZZ;
            switch (RandomAi()) {
                    case 0:
                        ZZ = gamelogic.RoundOver(1, currentProp);
                        gamelogic.IsGameOver(ZZ, currentProp);
                        break;
                    case 1:
                        ZZ = gamelogic.RoundOver(2, currentProp);
                        gamelogic.IsGameOver(ZZ, currentProp);
                        break;
            }

    }

}

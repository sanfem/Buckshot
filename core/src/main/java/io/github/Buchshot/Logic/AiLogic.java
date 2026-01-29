package io.github.Buchshot.Logic;

import com.badlogic.gdx.Gdx;
import io.github.Buchshot.GameItem.CurrentProp;

import java.util.Random;
import java.util.Timer;

public class AiLogic {

    Gamelogic gamelogic=new Gamelogic();

    private int RandomAi() {
        Random rand = new Random(System.currentTimeMillis());
        int r = rand.nextInt(2);
        if (r == 0) return 1;
        else return 0;
    }

    public void relation(CurrentProp currentProp ) {

            int ZZ;
            try {
                Thread.sleep(3000);
                switch (RandomAi()) {
                    case 0:
                        ZZ = gamelogic.RoundOver(1, currentProp );
                        gamelogic.IsGameOver(ZZ, currentProp);
                        break;
                    case 1:
                        ZZ= gamelogic.RoundOver(2, currentProp );
                        gamelogic.IsGameOver(ZZ, currentProp);
                        break;
                }
            }catch (InterruptedException e){
                 return;
            }

    }

}

package io.github.Buchshot.GameItem;

import java.util.Random;

public class CurrentProp implements Prop {
    public int cnt;
    public int N_bn;
    public int T_bn;
    public int H_bn;
    public int R_bn;
    public int O_bn;
    public int P_bn;
    public int Health1;
    public int Health2;
    public boolean GameOver;
    public boolean GameRound;
    public  int IsTureBu;

    public CurrentProp(){

        cnt=1;
        H_bn=1;
        R_bn=1;
        O_bn=1;
        P_bn=1;
        Health1=2;
        Health2=2;
        Random rand = new Random(System.currentTimeMillis());
        int r= (rand.nextInt(10)+1);
        r=r+(r+1)%2;
        int N =rand.nextInt(r)+2;
        N_bn = r+N+(r+N+1)%2;
        T_bn = N;
        GameOver=false;
        GameRound=true;
        IsTureBu=0;
    }//测试用

}

package io.github.Buchshot.Logic;

import io.github.Buchshot.GameItem.CurrentProp;
import io.github.Buchshot.GameItem.CurrentProp;

import java.util.Random;

public class Gamelogic {
        private boolean randow(int NB,int N){
            Random rand = new Random(System.currentTimeMillis());
            int r= rand.nextInt(N);
            return r < NB;
        }

        private void CreateRound(CurrentProp currentProp){
            Random rand = new Random(System.currentTimeMillis());
            int r= (rand.nextInt(10)+1);
            r=r+(r+1)%2;
            int N =rand.nextInt(r)+2;
            currentProp.N_bn = r+N+(r+N+1)%2;
            currentProp.T_bn = N;
        };

        private boolean JudgeMent(CurrentProp currentProp){
            if(randow(currentProp.T_bn,currentProp.N_bn)){return true; }
            else return false;
        }


        public void IsGameOver(int ZZ,CurrentProp currentProp){
            if(ZZ!=0){
                currentProp.GameOver=true;
            }
            else if(currentProp.T_bn<=0||currentProp.N_bn<=0){
                CreateRound(currentProp);
            }
        }

        public int RoundOver(int To_who,CurrentProp currentProp){
            int cnt=currentProp.cnt;
            boolean flag=false,flag1=false;
            int Hea,Hea1;
            if(cnt%2==1) {Hea=currentProp.Health1;Hea1=currentProp.Health2;flag=true;}
            else  {Hea=currentProp.Health2;Hea1=currentProp.Health1;}
            if(To_who==1){
                if(JudgeMent(currentProp)) {
                    Hea--;
                    currentProp.T_bn--;
                    currentProp.IsTureBu=1;
                }
                else {currentProp.IsTureBu=-1;}
            }
            else if(To_who==2) {
                if (JudgeMent(currentProp)) {
                    Hea1--;
                    currentProp.IsTureBu=1;
                    currentProp.T_bn--;
                }
                else {currentProp.IsTureBu=-1;}
                cnt++;
            }
            currentProp.N_bn--;
            currentProp.GameRound=true;
            if(flag) { currentProp.Health1=Hea;currentProp.Health2=Hea1;}
            else  { currentProp.Health2=Hea;currentProp.Health1=Hea1;}
            currentProp.cnt=cnt;
            if(currentProp.N_bn<=0||currentProp.T_bn<=0){CreateRound(currentProp);}
            if(Hea<=0) return 1;
            else if(Hea1<=0) return -1;
            else return 0;
        }



}

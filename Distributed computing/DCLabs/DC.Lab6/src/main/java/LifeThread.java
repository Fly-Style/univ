import java.util.Random;

/**
 * @Author is flystyle
 * Created on 01.04.16.
 */
class LifeThread implements Runnable {
    final int[][] cells = {
            {-1, -1},   {-1, 0},    {-1, 1},
            { 0, -1},               { 0, 1},
            { 1, -1},   { 1, 0},    { 1, 1}
    };
    Life life;
    int startx;
    int starty;
    int finx;
    int finy;
    int populationNum;


    public LifeThread(Life life, int startx, int starty, int finx, int finy, int populationNum) {
        this.life = life;
        this.startx = startx;
        this.starty = starty;
        this.finy = finy;
        this.finx = finx;
        this.populationNum = populationNum;
    }


    public void run() {
        int count = 0;
        int[] popCount = new int[life.countPopulations];
        int popCountMax;
        Random r = new Random();

        while (!Thread.interrupted()){
            for (int i = startx; i < finx; i++){
                for (int j = starty; j < finy; j++){
                    count = 0;
                    for (int t = 0; t < cells.length; t++){
                        if (life.get(i + cells[t][0], j + cells[t][1])  == populationNum)
                            count++;
                    }
                    int cell = life.get(i, j);
                    //
                    if (cell == populationNum && (count < 2 || count > 3)) {
                        life.set(i, j, 0);
                    } else if (cell == 0 && count == 3){
                        life.set(i, j, populationNum);
                    }
                    /*
                    else if (cell != 0  && cell != populationNum
                            && (count > 2)  // new condition for entertainment
                            ){
                        life.set(i, j, populationNum);
                    }
                    */
                }
            }


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
package A;

import java.lang.*;

/**
 * @Author is flystyle
 * Created on 09.03.16.
 */

/*  а)
     Задача о Винни-Пухе или правильные пчелы. В одном лесу живут n пчел и один медведь,
     которые используют один горшок меда, вместимостью Н глотков. Сначала горшок пустой.
     Пока горшок не наполнится, медведь спит. Как только горшок заполняется, медведь
     просыпается и съедает весь мед, после чего снова засыпает. Каждая пчела многократно
     собирает по одному глотку меда и кладет его в горшок. Пчела, которая приносит последнюю
     порцию меда, будит медведя. Создать многопоточное приложение, моделирующее поведение
     пчел и медведя.
*/

public class TrueBees {

    public static int sema = 0;
    public static final int threads = 10;
    public static boolean filled = false;

    static Bank bank = new Bank(threads);

    public static void main(String[] args) {
        Process process = new Process();
        Bear bear = new Bear();

        bear.start();
        process.Action();
    }

}

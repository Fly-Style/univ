/**
  * Created by flystyle on 16.02.16.
  */

class Incrementer  extends Runnable {
  override def run(): Unit = {
    while(true) {
      if (L1_A.counter < 90) {
        L1_A.counter = L1_A.counter + 1
        System.out.println(L1_A.counter)
      }
    }
  }
}

class Decrementer () extends Runnable {
  override def run(): Unit = {
    while (true) {
      if (L1_A.counter > 10) {
        L1_A.counter = L1_A.counter - 1
        System.out.println(L1_A.counter)
      }
    }
  }
}
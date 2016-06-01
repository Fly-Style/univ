/**
  * Created by flystyle on 15.02.16.
  */


object L1_A {

  var counter : Int =  50

  def main (args: Array[String]) {
    val t1 = new Thread(new Incrementer())
    t1.setPriority(Thread.MAX_PRIORITY)

    val t2 = new Thread(new Decrementer())
    t2.setPriority(Thread.MIN_PRIORITY)

    t1.start()
    t2.start()

  }
}


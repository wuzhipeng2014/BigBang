/**
  * Created by zhipengwu on 17-12-26.
  * 控制结构练习
  */
object ControlStructure {

  def main(args: Array[String]): Unit = {

    val a =12
    val b=a match {
      case 1=>println("one")
      case 2=>println("two")
      case 10=>print("ten")
        //默认匹配,可以制定任意的变量名称
      case other=>print("invalid input")
    }




  }

}

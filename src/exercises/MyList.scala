package exercises

abstract class MyList[+A] {

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](elem: B): MyList[B]
  def map[B](transformer: MyTransformer[A, B]): MyList[B]
  def filter(filter: MyPredicate[A]): MyList[A]
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]
  def ++[B >: A](list: MyList[B]): MyList[B]
  def toString: String


}

case object EmptyList extends MyList[Nothing] {

  //As we want to do this generic we can use Nothing as substitute of any type
  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](elem: B): MyList[B] = List[B](elem, EmptyList)

  override def toString: String = ""

  override def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = EmptyList

  override def filter(filter: MyPredicate[Nothing]): MyList[Nothing] = EmptyList

  override def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = EmptyList

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
}

case class List[+A](h: A, t: MyList[A]) extends MyList[A] {

  override def head: A = h

  override def tail: MyList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](elem: B): MyList[B] = List(elem, this)

  override def toString: String = if (tail.isEmpty) head.toString else s"$head, ${tail.toString}"

  override def map[B](transformer: MyTransformer[A, B]): MyList[B] = List(transformer.transform(head), tail.map(transformer))

  override def filter(filter: MyPredicate[A]): MyList[A] = if (filter.test(head)) List(head, tail.filter(filter)) else tail.filter(filter)

  override def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = transformer.transform(head) ++ tail.flatMap(transformer)

  override def ++[B >: A](list: MyList[B]): MyList[B] = List(head, tail ++ list)
}

object ListTest extends App {
//  val list = new List(2, new List(1, EmptyList))
//  println(list.add(3))
  val list1: MyList[String] = EmptyList
  val list2: MyList[Int] = EmptyList


  println(list2.getClass)

  //We can actually add anything as the method add will make the list to be of type Anyval
  println(list2.add(1).add("Hello").add(true))

  println(list2.add(2).add(1).map(new MyTransformer[Int, String] {
    override def transform(a: Int): String = (a*2).toString
  }))

  println(list2.add(2).add(1).filter(new MyPredicate[Int] {
    override def test(a: Int): Boolean = a != 1
  }))

  println(list2.add(2).add(1).flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(a: Int): MyList[Int] = EmptyList.add(a+1).add(a)
  }))
}


trait MyPredicate[-T]{
  def test(t: T): Boolean
}

trait MyTransformer[-A, B]{
  def transform(a: A): B
}
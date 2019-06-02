package lectures

object Notes extends App{

  def greetingFunction(name: String, age: Int) = s"Hi my name is $name and I am $age years old"

  def factorial(n: Int): Int = if (n <= 1) 1 else n * factorial(n - 1)

  def fibonacci(n: Int): Int = if (n <= 2) 1 else fibonacci(n - 1) + fibonacci(n - 2)

  println(greetingFunction("Pablo", 22))

  println(factorial(1))
  println(factorial(2))
  println(factorial(3))
  println(factorial(4))
  println(factorial(5))
  println(factorial(6))

  println(fibonacci(1))
  println(fibonacci(2))
  println(fibonacci(3))
  println(fibonacci(4))
  println(fibonacci(5))
  println(fibonacci(6))

  //By-name parameters delays the evaluation of the parameter until it's used (n: =>Int)
  //You can have default parameters and also pass them by name
  //you can interpolate strings with raw, s or f
  //operators are methods in the end with just one parameter (you can call 1 parameter methods with <class> <method> <parameter>) and you can override all of them
  //you can override unary operators (+ - ~ !) declaring def unary_<operator> : <return type> = <blablabla>
  //postfix operators are just methods with no parameter and called with an space
  //you can define an apply method in a class (even though is not case class) and the object itself will be able to be called as a method

  //Scala objects are singleton instances
  //We can have an object and a class with the same name and this "pattern" is called companion
  //Companions can access each other's private members
  //We can implement factory methods in objects to retrieve new instances of the companion class (for example with method apply in the object)

  //Scala applications are scala objects with def main(args: Array[String]): Unit

  //Inheritance is similar to Java (extends non-private members etc..)
  //To extend a class with parameters you need to "call" the super constructor(i.e.
  //class Person(name: String)
  //class Adult(name: String, idCard: Long) extends Person(name)
  //). Either way compiler will complain

  //To define an auxiliary constructor you do it with "def this(...) = {}
  //We can override a value or method of a superclass. In the case of a value we can override it from the constructor or as a field
  //To prevent overriding we can use "final" on a member or on the class itself
  //We can also "seal" a class to allow extending in the same file but avoid extending in other file.

  //Abstract classes (cannot be instantiated)
  abstract class Animal {
    val creatureType: String
    def eat: Unit
  }

  class Dog extends Animal {
    //Override keyword can be omitted
    override val creatureType: String = "Domestic"

    override def eat: Unit = println("Crunch, crunch")
  }

  //Traits (class can extend more than one trait, not more than one abstract class)
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore{
    override val creatureType: String = "Carnivore"

    override def eat: Unit = println("nomnomnom")

    override def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}")
  }

  val c = new Crocodile
  c.eat(c)

  //Both abstract classes and traits can have abstract and non-abstract members
  //Traits cannot have constructor parameters
  //you can only extend one class but inherit multiple traits
  //We should choose traits if they describe WHAT IT DOES and not WHAT IT IS

  //GENERICS
  class CovariantList[+A]
  class InvariantList[A]
  class ContravariantList[-A]
  class Vehicle
  class Car extends Vehicle
  class Bike extends Vehicle

  //We can't do this
  //val list: InvariantList[Vehicle] = new InvariantList[Car]
  //We can do this
  val list1: InvariantList[Vehicle] = new InvariantList[Vehicle]

  //We can do this
  val list2: CovariantList[Vehicle] = new CovariantList[Car]

  //We can do this
  val list3: ContravariantList[Car] = new ContravariantList[Vehicle]

  //Bounded types
  //Only accepts subtypes of Vehicle
  class Garage[A <: Vehicle](vehicle: A)
  //Only accepts supertypes of Vehicle
  //class Garage[A >: Vehicle](vehicle: A)

  //This will cause compilation errors
  //val garage = new Garage(new Crocodile)

  //This will work
  val garage = new Garage(new Car)

  //class MyList[+A]{
  //  def add[B >: A](elem: B): MyList[B] = ???
  //}
  //Example of this... If we have:
  //val list: MyList[Animal] = new MyList[Cat]
  //and I decide to add a Dog
  //then the list of cats will become a list of animals because the method add accepts supertypes of cat (i.e. Dog bc its an Animal)



}

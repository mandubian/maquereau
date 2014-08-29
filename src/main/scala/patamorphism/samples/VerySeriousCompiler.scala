package org.mandubian.maquereau.patamorphism

package samples

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.tools.reflect.Eval
import scala.util.Random

import scala.language.implicitConversions

import scala.reflect.macros.{ Context, Macro }

import org.mandubian.maquereau.patamorphism._

/**
  * VerySeriousCompiler Pure PataMorphism implementation
  *
  * VerySeriousCompiler is a pure patamorphism allowing to choose how long your 
  * compiling will last while displaying great messages at a given speed. It accepts
  * any code and this code is purely reinjected after macro execution in compile-chain
  * without any modification.
  * 
  * VerySeriousCompiler is a useful tool when you want to have a coffeeor discuss quietly 
  * at work with colleagues and fool your boss making him/her believe you're waiting for 
  * the end of a very long compiling phase.
  * So you just have to modify your code using :
  * {{{VerySeriousCompiler.sprout(...some code...)}}} 
  * Then you launch compile for the duration you want, displaying meaningful
  * messages in case your boss looks at your screen. Thus, you have an excuse if your boss
  * is not happy about your long pause: "look, it's compiling"
  * 
  * What's also very interesting about this PataMorphism is that you can use it 
  * without fearing it might pollute your code at runtime, it has only effects
  * at compile-time and don't inject any other code in the AST except the code you 
  * provided to sprout.
  * 
  * Usage :
  * {{{
  * import VerySeriousCompiler._
  * 
  * // using default seed
  * sprout(Toto("toto")) must beEqualTo(Toto("toto"))
  *
  * // using custom seed
  * sprout{
  *   val a = "this is"
  *   val b = "some code"
  *   val c = 123L
  *   s"msg $a $b $c"
  * }(
  *   VerySeriousCompiler.seed( 
  *     60000L,     // duration of compiling in ms
  *     200L,       // speed between each message display in ms
  *     Seq(        // the message to display randomly 
  *       "very interesting message", 
  *       "cool message"
  *     )
  *   )
  * ) must beEqualTo( "msg this is some code 123" )
  * }}}
  */
object VerySeriousCompiler extends PataMorphism {
  // Specialization of Seed using SeriousCompilerSeed
  type Seed = SeriousCompilerSeed

  // the real sprout function expected for patatoid
  def sprout[T](t: T)(implicit seed: Seed): T = macro sproutMacro[T]

  // the macro implementation
  def sproutMacro[T: c1.WeakTypeTag](c1: Context)(t: c1.Expr[T])(seed: c1.Expr[Seed]): c1.Expr[T] = {
    val helper = new { val c: c1.type = c1 } with VerySeriousCompilerHelper
    helper.sprout(t, seed)
  }

  /** Custom Seed containing:
    * @param duration the duration of compiling in ms
    * @param speed the speed between each message display in ms
    * @param messages the messages to display
    */
  case class SeriousCompilerSeed(duration: Long, speed: Long, messages: Seq[String])  

  /** Seed builder 
    * @param duration the duration of compiling in ms
    * @param speed the speed between each message display in ms
    * @param messages the messages to display
    */
  def seed(duration: Long, speed: Long, messages: Seq[String]) = SeriousCompilerSeed(duration, speed, messages)

  /** Default seed that you can copy */
  implicit val defaultSeed = SeriousCompilerSeed(
    duration = 5000L, // compiling lasts 15s
    speed = 400L,      // new random message displayed each 400ms
    messages = Seq(       // messages
      "verifying isomorphic behavior", 
      "constructing costate comonad", 
      "inflating into applicative functor",
      "binarizing compiler AST",
      "computing fast fourier transform code optimization",
      "expanding inference driving macro",
      "resolving implicit typeclass from scope",
      "generating language systemic metafunction",
      "Finding ring kernel that rules them all",
      "analyzing dystopic behavior in current compiler runtime",
      "invoking Nyarlathotep to prevent crawling chaos",
      "watering Gizmo after midnight",
      "asking why Obiwan Kenobi",
      "verifying rules of pataphysics",
      "organizing bacchanales with Harry Potter and Justin bieber as victims",
      "Hear me carefully, your eyelids are very heavy, you're a koalaaaaa",
      "Do you like gladiator movies?"
    ) 
  )

}


abstract class VerySeriousCompilerHelper {
  type Seed = VerySeriousCompiler.Seed

  val c: Context

  import c.universe._

  def sprout[A](a: Expr[A], seedExpr: Expr[Seed]): c.Expr[A] = {
    //println(showRaw(seedExpr.tree))
    seedExpr.tree match {
      case l: Select => 
        val s: Seed = c.eval(c.Expr[Seed](c.resetAllAttrs(l.duplicate)))
        loseTime(s.duration, s.speed, s.messages)
      case Apply(_, durationTree :: speedTree :: messagesTree :: _) => 
        val messages = messagesTree match {
          case Apply(_, params) =>
            params.foldLeft(Seq[String]())((all, eltTree) => 
              all :+ c.eval(c.Expr[String](c.resetAllAttrs(eltTree.duplicate)))
            )
          case l : Select => 
            c.eval(c.Expr[Seq[String]](c.resetAllAttrs(l.duplicate)))
        }
        val duration = c.eval(c.Expr[Long](c.resetAllAttrs(durationTree.duplicate)))
        val speed = c.eval(c.Expr[Long](c.resetAllAttrs(speedTree.duplicate)))
        //println(s"duration:$duration speed:$speed messages:$messages")

        loseTime(duration, speed, messages)
    }
    
    reify(a.splice)
  }

  def loseTime(duration: Long, speed: Long, messages: Seq[String]) = {
    val start = new java.util.Date().getTime
    var previous = 0

    while(new java.util.Date().getTime - start < duration) {
      // evaluates some reified code to consume electricity because compiling is energivore, it's well known
      c.eval(
        reify{
          var newRnd = Random.nextInt(messages.length)
          while(newRnd == previous) newRnd = Random.nextInt(messages.length)
          previous = newRnd
          printf(messages(newRnd))
        }
      )
      // just nothing but points twinkling
      val startSpeed = new java.util.Date().getTime
      while(new java.util.Date().getTime - startSpeed < speed) {
        printf(".")
        Thread.sleep(20)
      }
      printf("\n")
    }
  }
}

object Test {
  def test: String = macro testImpl

  // the macro implementation
  def testImpl(c: Context): c.Expr[String] = {
    import c.universe._

    try {
      reify(23 + )
      } catch {
        case e: Throwable => println(e.getMessage); reify("toto")
      }
  }
}

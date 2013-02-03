package org.mandubian.maquereau

/** PataMorphism definition 
  *
  * "A patamorphism is a patatoid in the category of xenofunctors" 
  * Ernst Von Schmurtz (1976)
  *
  * This definition implies Patamorphism shall be:
  * - synchronous & blocking (pataphysics is beyond concurrent contingencies)
  * - mutable and produce side-effects (pataphysics can change the nature of things)
  * 
  * In the domain of programming, it means:
  * - Effect only at compile-time
  * - No effect at run-time
  * 
  * Naturally, as pataphysics is the science of exceptions, all those rules are true 
  * except when there are exceptions to the rules.
  *
  * Xenofunctor category refers to those morphisms applied on any element outside 
  * the realm of pataphysics meaning it can accept any structure from your code and
  * execute it in the pataphysics realm.
  *
  * As you may know, the patatoid nature involves that we provide the so-called `sprout` 
  * operation which has the signature of an identity morphism contextualized with a Seed
  * as following: 
  * {{{
  * trait Patatoid{
  *   // Seed is specific to a Patatoid and is used to configure the sprout mechanism  
  *   type Seed
  *   // function producing it effect only at compile-time and no effect at runtime
  *   def sprout[T](t: T)(implicit seed: Seed): T
  * }
  * }}}
  *
  */
package patamorphism

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.tools.reflect.Eval
import scala.util.Random

import scala.language.implicitConversions

import scala.reflect.macros.{ Context, Macro }

/** Patatoid Macro implementation
  *
  * Please note that this is the translation of Patatoid definition found in package
  * ScalaDoc in the Scala Macro.
  * We can't provide directly {{{def sprout[T](t: T)(implicit seed: Seed): T}}} because
  * Macros have a few limitations: a macro can't override abstract function defined in a 
  * mixin trait.
  * So we declare it as {{{sproutMacro}}} and in implementations of patamorphism, you should
  * provide the real {{{sprout}}} implementation.
  */
trait Patatoid{
  // Seed is specific to a Patatoid and is used to configure the sprout mechanism  
  type Seed
  // function producing it effect only at compile-time and no effect at runtime
  def sproutMacro[T: c1.WeakTypeTag](c1: Context)(t: c1.Expr[T])(seed: c1.Expr[Seed]): c1.Expr[T]
}

/**
  * PataMorphism 
  */
trait PataMorphism extends Patatoid

// trait RottenPataMorphism[A, F] extends PataMorphism {
//   def sprout(fertilizer: F): A
// }


import org.specs2.mutable._

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

import org.mandubian.maquereau.patamorphism.samples._

case class Toto(name: String)

@RunWith(classOf[JUnitRunner])
class PataMorphismSpec extends Specification {
  "VerySeriousCompiler" should {
    "sprout with default seed" in {
      import VerySeriousCompiler._

      sprout(Toto("toto")) must beEqualTo(Toto("toto"))
    }

    "sprout with custom seed" in {
      import VerySeriousCompiler._

      sprout{
        val a = "this is"
        val b = "some code"
        val c = 123L
        s"msg $a $b $c"
      }(
        VerySeriousCompiler.seed(1000L, 200L, Seq("coucou", "toto"))
      ) must beEqualTo( "msg this is some code 123" )
    }

  }

  
}
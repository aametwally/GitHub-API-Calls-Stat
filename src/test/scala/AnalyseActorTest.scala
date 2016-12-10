import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import org.junit.{Assert, Test}
import java.nio.file.{Paths, Files}
import scala.io.Source

/**
  * Created by ahmedmetwally on 12/9/16.
  */


class AnalyseActorTest extends TestKit(ActorSystem("TestActor")) {
  val actorRef = TestActorRef[AnalyzerActor.ComparisonActor]
  val actor = actorRef.underlyingActor


  @Test
  // Checks whether or not the statistics file is written correctly or not
  def testWriteToFile: Unit ={
    var v = new Visitor;
    v.operators=2
    v.summer=10
    actor.writeToFile("../../datasets/test", v)
    Assert.assertEquals(Files.exists(Paths.get("../../datasets/test_statistics.txt")), true)

    val filename = "../../datasets/test_statistics.txt"
    for (line <- Source.fromFile(filename).getLines()) {
      line match {
        case s if s.startsWith("Percentage") => Assert.assertEquals(s.split(":")(1).replace( " ","" ), "20.0%")
        case _ =>
      }
    }
  }
}
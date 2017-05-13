import org.scalatest.FunSuite

/**
 *
 * @author roger1224@gmail.com
 *
 *         $$DateTime$$
 *         $$Change$$
 *         $$Author$$
 */
class RecordSuite extends FunSuite {
  test("constructor") {
    val record = new Record(1, 100, 50, 200)

    assert(record.teamNo == 1)
    assert(record.finalSpeed == 100)
    assert(record.elapsedTime == 50)
    assert(record.distance == 200)
  }
}

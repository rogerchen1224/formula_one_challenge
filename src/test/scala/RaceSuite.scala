import org.scalamock.scalatest.MockFactory
import org.scalatest.FunSuite

/**
 *
 * @author roger1224@gmail.com
 *
 *         $$DateTime$$
 *         $$Change$$
 *         $$Author$$
 */
class RaceSuite extends FunSuite with MockFactory {

  test("A Race should init cars when being constructed") {
    val race: Race = new Race(2, null, 3)

    assert(race._cars.length == 3)

    assert(race._cars(0).teamNo == 1)
    assert(race._cars(0).currentPosition == 0)
    assert(race._cars(0).acceleration == 2)
    assert(race._cars(0).topSpeed == 44.44444444444444)
    assert(race._cars(0).finished == false)

    assert(race._cars(1).teamNo == 2)
    assert(race._cars(1).currentPosition == -200)
    assert(race._cars(1).acceleration == 4)
    assert(race._cars(1).topSpeed == 47.22222222222222)
    assert(race._cars(1).finished == false)

    assert(race._cars(2).teamNo == 3)
    assert(race._cars(2).currentPosition == -400)
    assert(race._cars(2).acceleration == 6)
    assert(race._cars(2).topSpeed == 50)
    assert(race._cars(2).finished == false)
  }

  test("A Race should throw exception when numOfTeams is less than 1") {
    assertThrows[IllegalArgumentException] {
      new Race(-1, null, 0)
    }

    assertThrows[IllegalArgumentException] {
      new Race(-1, null, -1)
    }
  }

  test("Records should be returned when race is over") {
    val reassessmentMock = mock[Reassessment]
    (reassessmentMock.reassess _).expects(*, *).once.returns(List(
      new Car(1, 0, 200, 100, 10),
      new Car(2, 0, 200, 100, 10),
      new Car(3, 0, 200, 100, 10)))

    val race: Race = new Race(2, reassessmentMock, 3)
    assert(race._cars.length == 3)

    val records: List[Record] = race.run

    assert(records.length == 3)
  }

  test("Records should be returned after time reaches MaxTime - test Record Set") {
    val reassessmentMock = mock[Reassessment]
    (reassessmentMock.reassess _).expects(*, *).twice.returns(List(
      new Car(1, 0, 200, 100, 10),
      new Car(2, 0, 200, 100, 10),
      new Car(2, 0, 200, 100, 10)))

    val race: Race = new Race(2, reassessmentMock, 3)
    race.MaxTime = 4
    assert(race._cars.length == 3)

    val records: List[Record] = race.run

    assert(records.length == 2)
  }

  test("No Records will be returned after time reaches MaxTime") {
    val reassessmentMock = mock[Reassessment]
    (reassessmentMock.reassess _).expects(*, *).twice.returns(null)

    val race: Race = new Race(2, reassessmentMock, 3)
    race.MaxTime = 4
    assert(race._cars.length == 3)

    val records: List[Record] = race.run

    assert(records.length == 0)

  }

  test("Run race - 1m - 1 car") {
    val reassessment = new DefaultReassessment(1, 10)
    val race: Race = new Race(1, reassessment, 1)
    assert(race._cars.length == 1)

    val records: List[Record] = race.run

    assert(records.length == 1)
    assert(records(0).teamNo == 1)
    assert(records(0).finalSpeed == 2)
    assert(records(0).elapsedTime == 1)
    assert(records(0).distance == 1)
  }

  test("Run race - 10m - 1 car") {
    val reassessment = new DefaultReassessment(10, 10)
    val race: Race = new Race(1, reassessment, 1)
    assert(race._cars.length == 1)

    val records: List[Record] = race.run

    assert(records.length == 1)
    assert(records(0).teamNo == 1)
    assert(records(0).finalSpeed == 8)
    assert(records(0).elapsedTime == 4)
    assert(records(0).distance == 16)
  }

  test("Run race - 100m - 5 cars") {
    val reassessment = new DefaultReassessment(100, 10)
    val race: Race = new Race(2, reassessment, 5)
    assert(race._cars.length == 5)

    val records: List[Record] = race.run

    assert(records.length == 5)
    assert(records(0).teamNo == 1)
    assert(records(0).finalSpeed == 20)
    assert(records(0).elapsedTime == 10)
    assert(records(0).distance == 100)

    assert(records(1).teamNo == 2)
    assert(records(1).finalSpeed == 47.22222222222222)
    assert(records(1).elapsedTime == 14)
    assert(records(1).distance == 182.44444444444446)

    assert(records(2).teamNo == 3)
    assert(records(2).finalSpeed == 50)
    assert(records(2).elapsedTime == 14)
    assert(records(2).distance == 100)

    assert(records(3).teamNo == 4)
    assert(records(3).finalSpeed == 52.77777777777778)
    assert(records(3).elapsedTime == 18)
    assert(records(3).distance == 183.77777777777777)

    assert(records(4).teamNo == 5)
    assert(records(4).finalSpeed == 55.55555555555556)
    assert(records(4).elapsedTime == 20)
    assert(records(4).distance == 208.88888888888883)
  }
}

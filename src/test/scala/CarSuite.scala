import org.scalatest.FunSuite

/**
 *
 * @author roger1224@gmail.com
 *
 *         $$DateTime$$
 *         $$Change$$
 *         $$Author$$
 */
class CarSuite extends FunSuite {

  test("test constructor-1") {
    val car: Car = new Car(1, 2, 200, 50, 30)

    assert(car.teamNo == 1)
    assert(car.acceleration == 2)
    assert(car.topSpeed == 200 * 1000 / 3600d)
    assert(car.currentPosition == 50)
    assert(car.currentSpeed == 30)
    assert(car.finished == false)
    assert(car.isNitroUsed == false)
  }

  test("test constructor-2") {
    val car: Car = new Car(1, 2, 200, 50)

    assert(car.teamNo == 1)
    assert(car.acceleration == 2)
    assert(car.topSpeed == 200 * 1000 / 3600d)
    assert(car.currentPosition == 50)
    assert(car.currentSpeed == 0)
    assert(car.finished == false)
    assert(car.isNitroUsed == false)
  }

  test("enable Nitro once only") {
    val car: Car = new Car(1, 2, 100, 0, 10)

    assert(car.teamNo == 1)
    assert(car.topSpeed == 100 * 1000 / 3600d)
    assert(car.currentSpeed == 10)
    assert(car.isNitroUsed == false)

    car.enableNitro
    assert(car.currentSpeed == 20)
    assert(car.isNitroUsed == true)

    car.enableNitro
    assert(car.currentSpeed == 20)
    assert(car.isNitroUsed == true)
  }

  test("enable Nitro but not over topSpeed") {
    val car: Car = new Car(1, 2, 100, 0, 15)

    assert(car.teamNo == 1)
    assert(car.topSpeed == 100 * 1000 / 3600d)
    assert(car.currentSpeed == 15)
    assert(car.isNitroUsed == false)

    car.enableNitro
    assert(car.currentSpeed == 100 * 1000 / 3600d)
    assert(car.isNitroUsed == true)
  }

  test("encounter handling problems") {
    val car: Car = new Car(1, 2, 100, 0, 20)

    assert(car.teamNo == 1)
    assert(car.topSpeed == 100 * 1000 / 3600d)
    assert(car.currentSpeed == 20)

    car.encounterHandlingProblem
    assert(car.currentSpeed == 16)

    car.encounterHandlingProblem
    assert(car.currentSpeed == 12.8)
  }

  test("finish the race") {
    val car: Car = new Car(1, 2, 100, 0, 20)

    assert(car.teamNo == 1)
    assert(car.topSpeed == 100 * 1000 / 3600d)
    assert(car.finished == false)

    car.finish
    assert(car.finished == true)

    assertThrows[IllegalStateException] {
      car.finish
    }
  }

  test("run for certain period of time") {
    val car: Car = new Car(1, 2, 100, 0, 10)

    assert(car.teamNo == 1)
    assert(car.topSpeed == 100 * 1000 / 3600d)
    assert(car.currentSpeed == 10)

    car.run(1)
    assert(car.currentSpeed == 12)
    assert(car.currentPosition == 11)

    car.run(1)
    assert(car.currentSpeed == 14)
    assert(car.currentPosition == 24)

    car.run(10)
    // Reached the top speed
    assert(car.currentSpeed == 100 * 1000 / 3600d)
    assert(car.currentPosition == 264)

    car.run(0)
    assert(car.currentSpeed == 100 * 1000 / 3600d)
    assert(car.currentPosition == 264)

    assertThrows[AssertionError] {
      car.run(-1)
    }
  }


  test("run for certain period of time at top speed") {
    val car: Car = new Car(1, 2, 360, 0, 150)

    assert(car.teamNo == 1)
    assert(car.topSpeed == 100)
    assert(car.currentSpeed == 100)

    car.run(1)
    assert(car.currentSpeed == 100)
    assert(car.currentPosition == 100)

    car.run(1)
    assert(car.currentSpeed == 100)
    assert(car.currentPosition == 200)
  }

}

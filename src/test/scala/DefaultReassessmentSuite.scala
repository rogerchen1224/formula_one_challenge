import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer

/**
 *
 * @author roger1224@gmail.com
 *
 *         $$DateTime$$
 *         $$Change$$
 *         $$Author$$
 */
class DefaultReassessmentSuite extends FunSuite {

  test("constructor") {
    assertThrows[IllegalArgumentException] {
      new DefaultReassessment(0, 10)
    }
    assertThrows[IllegalArgumentException] {
      new DefaultReassessment(-1, 10)
    }

    assertThrows[IllegalArgumentException] {
      new DefaultReassessment(100, 0)
    }
    assertThrows[IllegalArgumentException] {
      new DefaultReassessment(100, -1)
    }
  }

  test("adjust speed for handling problem-1") {
    val target = new DefaultReassessment(1000, 10)

    val cars: ListBuffer[Car] = new ListBuffer[Car]()
    cars += new Car(1, 0, 200, 100, 10)
    cars += new Car(2, 0, 200, 110, 20)
    cars += new Car(3, 0, 200, 119, 30)

    target.assesHanldingProblem(cars.toList)

    assert(cars(0).currentSpeed == 8)
    assert(cars(1).currentSpeed == 16)
    assert(cars(2).currentSpeed == 24)
  }

  test("adjust speed for handling problem-2") {
    val target = new DefaultReassessment(1000, 10)

    val cars: ListBuffer[Car] = new ListBuffer[Car]()
    cars += new Car(1, 0, 200, 100, 10)
    cars += new Car(2, 0, 200, 102, 20)
    cars += new Car(3, 0, 200, 120, 30)

    target.assesHanldingProblem(cars.toList)

    assert(cars(0).currentSpeed == 8)
    assert(cars(1).currentSpeed == 16)
    assert(cars(2).currentSpeed == 30)
  }

  test("adjust speed for handling problem-3") {
    val target = new DefaultReassessment(1000, 10)

    val cars: ListBuffer[Car] = new ListBuffer[Car]()
    cars += new Car(1, 0, 200, 100, 10)
    cars += new Car(2, 0, 200, 120, 20)
    cars += new Car(3, 0, 200, 125, 30)

    target.assesHanldingProblem(cars.toList)

    assert(cars(0).currentSpeed == 10)
    assert(cars(1).currentSpeed == 16)
    assert(cars(2).currentSpeed == 24)
  }

  test("adjust speed for handling problem-4") {
    val target = new DefaultReassessment(1000, 10)

    val cars: ListBuffer[Car] = new ListBuffer[Car]()
    cars += new Car(1, 0, 200, 100, 10)
    cars += new Car(2, 0, 200, 120, 20)
    cars += new Car(3, 0, 200, 140, 30)

    target.assesHanldingProblem(cars.toList)

    assert(cars(0).currentSpeed == 10)
    assert(cars(1).currentSpeed == 20)
    assert(cars(2).currentSpeed == 30)
  }

  test("adjust speed for handling problem-emptyList") {
    val target = new DefaultReassessment(1000, 10)

    val cars: ListBuffer[Car] = new ListBuffer[Car]()

    target.assesHanldingProblem(cars.toList)

    assert(cars.isEmpty)
  }


  test("reassessAndFinished-1") {
    val target = new DefaultReassessment(20, 10)
    val car = new Car(1, 0, 200, 0, 10)

    assert(car.finished == false)
    assert(car.currentPosition == 0)
    assert(car.currentSpeed == 10)

    val finished = target.reassessAndFinished(10, car)

    assert(finished == true)
    assert(car.currentPosition == 100)
    assert(car.currentSpeed == 10)
  }

  test("reassessAndFinished-2") {
    val target = new DefaultReassessment(200, 10)
    val car = new Car(1, 0, 200, 0, 10)

    assert(car.finished == false)
    assert(car.currentPosition == 0)
    assert(car.currentSpeed == 10)

    val finished = target.reassessAndFinished(10, car)

    assert(finished == false)
    assert(car.currentPosition == 100)
    assert(car.currentSpeed == 10)
  }


  test("reassess - all finished") {
    val target = new DefaultReassessment(10, 10)

    val cars: ListBuffer[Car] = new ListBuffer[Car]()
    cars += new Car(1, 0, 200, 0, 10)
    cars += new Car(2, 0, 200, 0, 20)
    cars += new Car(3, 0, 200, 0, 30)

    assert(cars(0).finished == false)
    assert(cars(1).finished == false)
    assert(cars(2).finished == false)
    assert(cars(0).currentPosition == 0)
    assert(cars(1).currentPosition == 0)
    assert(cars(2).currentPosition == 0)

    val finishedCars: List[Car] = target.reassess(1, cars.toList)

    assert(finishedCars.length == 3)
    assert(finishedCars(0).finished == true)
    assert(finishedCars(1).finished == true)
    assert(finishedCars(2).finished == true)
    assert(finishedCars(0).currentPosition == 10)
    assert(finishedCars(1).currentPosition == 20)
    assert(finishedCars(2).currentPosition == 30)
  }

  test("reassess - all unfinished") {
    val target = new DefaultReassessment(50, 10)

    val cars: ListBuffer[Car] = new ListBuffer[Car]()
    cars += new Car(1, 0, 200, 0, 10)
    cars += new Car(2, 0, 200, 0, 20)
    cars += new Car(3, 0, 200, 0, 30)

    assert(cars(0).finished == false)
    assert(cars(1).finished == false)
    assert(cars(2).finished == false)
    assert(cars(0).currentPosition == 0)
    assert(cars(1).currentPosition == 0)
    assert(cars(2).currentPosition == 0)

    val finishedCars: List[Car] = target.reassess(1, cars.toList)

    assert(finishedCars.length == 0)
    assert(cars(0).finished == false)
    assert(cars(1).finished == false)
    assert(cars(2).finished == false)
    assert(cars(0).currentPosition == 10)
    assert(cars(1).currentPosition == 20)
    assert(cars(2).currentPosition == 30)
    assert(cars(0).currentSpeed == 16)
    assert(cars(1).currentSpeed == 16)
    assert(cars(2).currentSpeed == 24)
    assert(cars(0).isNitroUsed == true)
    assert(cars(1).isNitroUsed == false)
    assert(cars(2).isNitroUsed == false)
  }
}

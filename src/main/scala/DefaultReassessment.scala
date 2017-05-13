import scala.collection.mutable.ListBuffer

/**
 *
 * @author roger1224@gmail.com
 *
 *         $$DateTime$$
 *         $$Change$$
 *         $$Author$$
 */
class DefaultReassessment(private val _trackLength: Int,
                          private val _handlingProblemDistance: Double) extends Reassessment {

  if (_handlingProblemDistance <= 0) throw new IllegalArgumentException("handlingProblemDistance <= 0")
  if (_trackLength <= 0) throw new IllegalArgumentException("trackLength <= 0")

  /**
   * Reassess all cars to calculate the current position and the final speed based on passed time
   * in seconds
   *
   * @param elapsedTime time passed in seconds
   * @param cars all running cars
   * @return cars that finished the track in this assessment
   */
  override def reassess(elapsedTime: Double, cars: List[Car]): List[Car] = {
    assert(elapsedTime >= 0)

    val finishedCars: ListBuffer[Car] = new ListBuffer();
    val unFinishedCars: ListBuffer[Car] = new ListBuffer();

    for (car: Car <- cars) {
      if (!car.finished) {
        if (reassessAndFinished(elapsedTime, car)) finishedCars += car else unFinishedCars += car
      }
    }

    val sortedCars: List[Car] = unFinishedCars.sortWith(_.currentPosition > _.currentPosition).toList
    assesHanldingProblem(sortedCars)

    if (sortedCars.length > 1) sortedCars.last.enableNitro

    finishedCars.toList
  }


  def assesHanldingProblem(cars: List[Car]): Unit = {
    if (cars.isEmpty) return

    var lastCar: Car = null
    var lastCarEncounteredHandlingIssue = false
    for (car: Car <- cars) {
      if (lastCar != null) {
        if (math.abs(lastCar.currentPosition - car.currentPosition) <= _handlingProblemDistance) {
          if (!lastCarEncounteredHandlingIssue) {
            lastCar.encounterHandlingProblem
          }

          car.encounterHandlingProblem
          lastCarEncounteredHandlingIssue = true
        }
        else {
          lastCarEncounteredHandlingIssue = false
        }
      }

      lastCar = car
    }

  }


  /**
   * Reassess the car after the specified elapsedTime in seconds. currentPosition and currentSpeed
   * will be adjusted accordingly after the reassessment.
   *
   * @param elapsedTime passed time in seconds
   * @param car the car to be reassessed
   * @return true if the car reached the finish line, false if not
   */
  def reassessAndFinished(elapsedTime: Double, car: Car): Boolean = {
    assert(!car.finished)
    assert(elapsedTime >= 0)

    car.run(elapsedTime)

    if (car.currentPosition >= _trackLength) car.finish
    car.finished
  }
}

/**
 * A Reassessment will be called by Race every certain period of time to reassess the positions
 * for all cars and adjust the state of the car
 *
 * @author roger1224@gmail.com
 *
 *         $$DateTime$$
 *         $$Change$$
 *         $$Author$$
 */
trait Reassessment {
  /**
   * Reassess all cars to calculate the current position and the final speed based on passed time
   * in seconds
   *
   * @param elapsedTime time passed in seconds
   * @param cars all running cars
   * @return cars that finished the track in this assessment
   */
  def reassess(elapsedTime: Double, cars: List[Car]): List[Car]

}

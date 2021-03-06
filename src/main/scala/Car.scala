/**
 * A racing car for one team
 *
 * @author roger1224@gmail.com
 *
 *         $$DateTime$$
 *         $$Change$$
 *         $$Author$$
 */
class Car(private val _teamNo: Int,
          private val _acceleration: Double,
          private val topSpeedInKmHour: Double,
          private var _currentPosition: Double,
          private var _currentSpeed: Double) {

  def this(teamNo: Int, acceleration: Double, topSpeed: Double, currentPos: Double) {
    this(teamNo, acceleration, topSpeed, currentPos, 0)
  }

  /**
   * Whether to turn on debug mode for detail logs
   */
  val Debug = false

  /**
   * Predefined constants
   */
  val HandlingFactor: Double = 0.8
  val NitroFactor: Double = 2


  /**
   * Top speed in meters per second
   */
  private val _topSpeed = topSpeedInKmHour * 1000d / 3600d

  /**
   * Whether the car has reached the finish line
   */
  private var _finished: Boolean = false

  private var _enabledNitro: Boolean = false

  def teamNo = _teamNo

  def acceleration = _acceleration

  def topSpeed = _topSpeed

  def currentPosition = _currentPosition

  def currentSpeed = _currentSpeed

  def finished = _finished

  adjustSpeed(_currentSpeed)

  def enableNitro: Boolean = {
    if (_enabledNitro) return false

    adjustSpeed(_currentSpeed * NitroFactor)
    if (Debug) printf("Team %s enabled Nitro. speed=%s\n", _teamNo, _currentSpeed)
    _enabledNitro = true
    _enabledNitro
  }

  def isNitroUsed = _enabledNitro

  def encounterHandlingProblem = {
    adjustSpeed(_currentSpeed * HandlingFactor)
    if (Debug) printf("Team %s encountered handling issue. speed=%s\n", _teamNo, _currentSpeed)
  }

  def finish = {
    if (!_finished) {
      _finished = true
      if (Debug) printf("Team %s finished. speed=%s\n", _teamNo, _currentSpeed)
    } else throw new IllegalStateException("already finished the race")
  }

  def run(elapsedTime: Double) = {
    assert(elapsedTime >= 0)

    var distance: Double = 0
    var endSpeed: Double = 0

    /**
     * If current speed has not reached the top speed yet, we need to calculate the distance separately.
     * First, calculate the distance with acceleration. Then calculate the distance with full speed.
     */
    if (_currentSpeed < topSpeed) {
      val timeToReachTopSpeed = (topSpeed - _currentSpeed) / acceleration
      val accelerationTime = math.min(timeToReachTopSpeed, elapsedTime)

      /**
       * Calculate the acceleration part
       */
      distance = _currentSpeed * accelerationTime + (1d / 2d) * _acceleration * accelerationTime * accelerationTime
      endSpeed = _currentSpeed + _acceleration * accelerationTime

      /**
       * Calculate the full speed part if any
       */
      val timeLeft = elapsedTime - accelerationTime
      if (timeLeft > 0) {
        distance = distance + topSpeed * timeLeft
        endSpeed = topSpeed
      }
    }
    else {
      /**
       * Calculate with the top speed only
       */
      distance = _currentSpeed * elapsedTime
      endSpeed = topSpeed
    }

    adjustSpeed(endSpeed);
    _currentPosition = _currentPosition + distance

    if (Debug) printf("Team %s run %ss. speed=%s pos=%s\n", _teamNo, elapsedTime, _currentSpeed, _currentPosition)
  }

  def adjustSpeed(expectedSpeed: Double) = {
    _currentSpeed = math.min(expectedSpeed, topSpeed)
  }

}

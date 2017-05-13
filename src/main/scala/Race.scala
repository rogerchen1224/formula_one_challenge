import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * A Race hosts the competition for all racing cars.
 *
 * @author roger1224@gmail.com
 *
 *         $$DateTime$$
 *         $$Change$$
 *         $$Author$$
 */
class Race(private val _assessmentInterval: Int,
           private val _reassessment: Reassessment,
           private val numOfTeam: Int) {

  if (numOfTeam <= 0) throw new IllegalArgumentException("numOfTeam <= 0")
  if (_assessmentInterval <= 0) throw new IllegalArgumentException("assessmentInterval <= 0")

  val AccelerationBase = 2
  val TopSpeedBase = 150
  val TopSpeedIncBase = 10
  val CarStartLineBase = 200
  var MaxTime = Int.MaxValue

  val _cars: List[Car] = initTeams


  /**
   * Init teams by adding race cars
   * @return a List of cars
   */
  private def initTeams = {
    val cars: ListBuffer[Car] = new ListBuffer();

    for (i <- 1 to numOfTeam) {
      var car = new Car(i,
        AccelerationBase * i,
        TopSpeedBase + TopSpeedIncBase * i,
        -CarStartLineBase * (i - 1))
      cars += car

      printf("added car => team %s, acc=%sm/s2, topSpeed=%sm/s, pos=%sm\n",
        car.teamNo,
        car.acceleration,
        car.topSpeed,
        car.currentPosition)
    }

    cars.toList
  }


  /**
   * Run the race and get the completion records for all teams
   * @return a List of completed records
   */
  def run: List[Record] = {
    val records: ListBuffer[Record] = new ListBuffer[Record]
    val teamNoSet: mutable.SortedSet[Int] = new mutable.TreeSet[Int]()

    var timeInSec = 0;
    while (records.length < _cars.length && timeInSec < MaxTime) {
      timeInSec += _assessmentInterval

      val finishedCars = _reassessment.reassess(_assessmentInterval, _cars)
      if (finishedCars != null)
        for (car: Car <- finishedCars) {
          if (teamNoSet.add(car.teamNo))
            records += new Record(car.teamNo, car.currentSpeed, timeInSec, car.currentPosition)
        }
    }

    records.toList
  }
}

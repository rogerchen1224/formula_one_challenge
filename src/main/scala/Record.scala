/**
 * A Record is used to store the racing result for every team
 *
 * @author roger1224@gmail.com
 *
 *         $$DateTime$$
 *         $$Change$$
 *         $$Author$$
 */
class Record(val _teamNo: Int,
             val _finalSpeed: Double,
             val _elapsedTime: Double,
             val _distance: Double) {
  def teamNo = _teamNo

  def finalSpeed = _finalSpeed

  def elapsedTime = _elapsedTime

  def distance = _distance
}

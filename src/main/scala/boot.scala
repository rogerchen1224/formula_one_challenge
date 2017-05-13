

/**
 * The main entry of the program
 *
 * @author roger1224@gmail.com
 *
 *         $$DateTime$$
 *         $$Change$$
 *         $$Author$$
 */
object boot extends App {

  if (args.length < 2) {
    Console.err.println("Usage: boot <Track length in meters> <number of teams>")
    System.exit(1)
  }

  val TrackLength = args(0).toInt
  val NumOfTeams = args(1).toInt
  val AssessmentInterval = 2
  val HandlingProblemDistance = 10

  val _reassessment: Reassessment = new DefaultReassessment(TrackLength, HandlingProblemDistance)

  printf("Start race. trackLength=%sm, %s teams\n", TrackLength, NumOfTeams)
  val records = new Race(AssessmentInterval, _reassessment, NumOfTeams).run

  println("Racing Result:")
  for (rec: Record <- records) {
    printf("Team %s: finalSpeed=%sm/s, elapsedTime=%ss, distance=%sm\n",
      rec.teamNo, rec.finalSpeed, rec.elapsedTime, rec.distance)
  }
}

package regression

import scala.math
import breeze.linalg._
import breeze.plot._

object SimpleLinearRegression {

  type Phi = (Double) => Double

  def calc(x: DenseVector[Double], y: DenseVector[Double], phis: List[Phi], lambda: Double): DenseVector[Double] = {
    val n = x.size
    val m = phis.size

    val designM = DenseMatrix.zeros[Double](n, m)
    for ( i <- 0 until n ) {
      designM(i, ::) := DenseVector(phis.map( phi => phi(x(i)) ):_*).t
    }

    val regTerm = lambda * DenseMatrix.eye[Double](m)
    val w = inv(regTerm + designM.t * designM) * designM.t * y
    w
  }

}

object SimpleLinearRegressionExample {

  val gaussian = breeze.stats.distributions.Gaussian(0, 1)

  def targatFunc(x: Double): Double = {
    math.pow(x, 2) + gaussian.sample
  }

  def plotGraph(x: DenseVector[Double], y: DenseVector[Double], res: DenseVector[Double]): Unit = {
    val f = Figure()
    val p = f.subplot(0)
    p += plot(x, y)
    p += plot(x, res)
  }

  def main(args: Array[String]): Unit = {
    val x = DenseVector((-7.0 to 7.0 by 0.01):_*)
    val y = x.map( x => this.targatFunc(x) )
    val phis = List(
      { (x: Double) => 1.0 },
      { (x: Double) => x },
      { (x: Double) => math.pow(x, 2) },
    )

    val lambda = 1.0
    val w = SimpleLinearRegression.calc(x, y, phis, lambda)
    println(w)

    val res = x.map { x =>
      w.t * DenseVector(phis.map( phi => phi(x)):_*)
    }

    this.plotGraph(x, y, res)
  }

}

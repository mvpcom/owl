// code by jph
package ch.ethz.idsc.sophus.curve;

import ch.ethz.idsc.sophus.math.GeodesicInterface;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** dual scheme */
public class Split3Dual3PointCurveSubdivision extends Dual3PointCurveSubdivision {
  /** @param geodesicInterface
   * @param pq_f
   * @param qr_f
   * @param pqqr
   * @return */
  public static CurveSubdivision of(GeodesicInterface geodesicInterface, Scalar pq_f, Scalar qr_f, Scalar pqqr) {
    return new Split3Dual3PointCurveSubdivision(geodesicInterface, pq_f, qr_f, pqqr);
  }

  // ---
  private final Scalar pq_f;
  private final Scalar qr_f;
  private final Scalar pqqr;

  private Split3Dual3PointCurveSubdivision( //
      GeodesicInterface geodesicInterface, //
      Scalar pq_f, Scalar qr_f, Scalar pqqr) {
    super(geodesicInterface);
    this.pq_f = pq_f;
    this.qr_f = qr_f;
    this.pqqr = pqqr;
  }

  // point between p and q but more towards q
  @Override
  protected Tensor lo(Tensor p, Tensor q, Tensor r) {
    Tensor pq = geodesicInterface.split(p, q, pq_f);
    Tensor qr = geodesicInterface.split(q, r, qr_f);
    return geodesicInterface.split(pq, qr, pqqr);
  }
}

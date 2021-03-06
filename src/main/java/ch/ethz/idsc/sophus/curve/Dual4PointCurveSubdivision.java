// code by jph
package ch.ethz.idsc.sophus.curve;

import java.io.Serializable;
import java.util.Objects;

import ch.ethz.idsc.sophus.math.GeodesicInterface;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** dual scheme */
public class Dual4PointCurveSubdivision implements CurveSubdivision, Serializable {
  private final GeodesicInterface geodesicInterface;
  private final Scalar pq_f;
  private final Scalar rs_f;
  private final Scalar pqrs;

  /** @param geodesicInterface non-null
   * @param pq_f
   * @param rs_f
   * @param pqrs */
  public Dual4PointCurveSubdivision( //
      GeodesicInterface geodesicInterface, //
      Scalar pq_f, Scalar rs_f, Scalar pqrs) {
    this.geodesicInterface = Objects.requireNonNull(geodesicInterface);
    this.pq_f = pq_f;
    this.rs_f = rs_f;
    this.pqrs = pqrs;
  }

  @Override // from CurveSubdivision
  public Tensor cyclic(Tensor tensor) {
    ScalarQ.thenThrow(tensor);
    Tensor curve = Tensors.empty();
    int length = tensor.length();
    for (int index = 0; index < length; ++index) {
      Tensor p = tensor.get((index - 1 + length) % tensor.length());
      Tensor q = tensor.get(index);
      Tensor r = tensor.get((index + 1) % length);
      Tensor s = tensor.get((index + 2) % length);
      curve.append(lo(p, q, r, s)).append(lo(s, r, q, p));
    }
    return curve;
  }

  @Override // from CurveSubdivision
  public Tensor string(Tensor tensor) {
    throw new UnsupportedOperationException();
  }

  // @return point between q and r but more towards q
  private Tensor lo(Tensor p, Tensor q, Tensor r, Tensor s) {
    Tensor pq = geodesicInterface.split(p, q, pq_f);
    Tensor rs = geodesicInterface.split(r, s, rs_f);
    return geodesicInterface.split(pq, rs, pqrs);
  }
}

// code by jph
package ch.ethz.idsc.sophus.app.api;

import ch.ethz.idsc.sophus.group.LieGroup;
import ch.ethz.idsc.sophus.math.GeodesicInterface;
import ch.ethz.idsc.tensor.Tensor;

public interface GeodesicDisplay {
  /** @return */
  GeodesicInterface geodesicInterface();

  /** @return polygon to visualize the control point */
  Tensor shape();

  /** @param xya
   * @return coordinates of control point */
  Tensor project(Tensor xya);

  /** @param p control point
   * @return {x, y} */
  Tensor toPoint(Tensor p);

  /** @param p control point
   * @return matrix with dimensions 3 x 3 */
  Tensor matrixLift(Tensor p);

  /** @return lie group if the space is a lie group, otherwise null */
  LieGroup lieGroup();

  @Override // from Object
  String toString();
}

// code by jph
package ch.ethz.idsc.owl.bot.se2.glc;

import ch.ethz.idsc.owl.bot.se2.Se2CoveringWrap;
import ch.ethz.idsc.owl.bot.se2.Se2Wrap;
import ch.ethz.idsc.owl.glc.adapter.GlcExpand;
import ch.ethz.idsc.owl.glc.core.CheckedTrajectoryPlanner;
import ch.ethz.idsc.owl.glc.core.GlcNode;
import ch.ethz.idsc.owl.glc.core.TrajectoryPlanner;
import ch.ethz.idsc.owl.math.state.StateTime;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Norm;
import junit.framework.TestCase;

public class Se2WrapDemoTest extends TestCase {
  public void testSe2Wrap() {
    TrajectoryPlanner trajectoryPlanner = //
        CheckedTrajectoryPlanner.wrap(Se2WrapDemo.createPlanner(Se2Wrap.INSTANCE));
    trajectoryPlanner.insertRoot(new StateTime(Tensors.vector(0.1, 0, 0), RealScalar.ZERO));
    GlcExpand glcExpand = new GlcExpand(trajectoryPlanner);
    glcExpand.findAny(20);
    assertEquals(glcExpand.getExpandCount(), 20);
    glcExpand.findAny(100);
    assertTrue(glcExpand.getExpandCount() < 100);
    GlcNode glcNode = trajectoryPlanner.getBest().get();
    Scalar error = Norm._2.ofVector(glcNode.state().subtract(Tensors.vector(-0.2, 0, Math.PI * 2)));
    assertTrue(Scalars.lessThan(error, RealScalar.of(0.5)));
    // TODO JPH GLC check why failure:
    // HeuristicConsistency.check(trajectoryPlanner);
    // DebugUtils.heuristicConsistencyCheck(trajectoryPlanner);
    // DebugUtils.nodeAmountCompare(trajectoryPlanner);
  }

  public void testSe2CoveringWrap() {
    TrajectoryPlanner trajectoryPlanner = //
        CheckedTrajectoryPlanner.wrap(Se2WrapDemo.createPlanner(Se2CoveringWrap.INSTANCE));
    trajectoryPlanner.insertRoot(new StateTime(Tensors.vector(0.1, 0, 0), RealScalar.ZERO));
    GlcExpand glcExpand = new GlcExpand(trajectoryPlanner);
    glcExpand.findAny(20);
    assertEquals(glcExpand.getExpandCount(), 20);
    glcExpand.findAny(1000);
    assertTrue(glcExpand.getExpandCount() < 900);
    GlcNode glcNode = trajectoryPlanner.getBest().get();
    Scalar error = Norm._2.ofVector(glcNode.state().subtract(Tensors.vector(-0.2, 0, 0)));
    assertTrue(Scalars.lessThan(error, RealScalar.of(0.5)));
    // TODO JPH GLC check why failure:
    // HeuristicConsistency.check(trajectoryPlanner);
    // DebugUtils.heuristicConsistencyCheck(trajectoryPlanner);
    // DebugUtils.nodeAmountCompare(trajectoryPlanner);
  }
  // public void testIdentity() {
  // TrajectoryPlanner trajectoryPlanner = Se2WrapDemo.createPlanner(Se2CoveringWrap.INSTANCE);
  // GlcExpand glcExpand = new GlcExpand(trajectoryPlanner);
  // glcExpand.findAny(1000);
  // assertEquals(glcExpand.getExpandCount(), 1000);
  // glcExpand.findAny(1000);
  // assertTrue(glcExpand.getExpandCount() > 1000);
  // GlcNode glcNode = trajectoryPlanner.getBest().get();
  // Scalar error = Norm._2.ofVector(glcNode.state().subtract(Tensors.vector(-0.2, 0, 0)));
  // assertTrue(Scalars.lessThan(error, RealScalar.of(0.5)));
  // DebugUtils.heuristicConsistencyCheck(trajectoryPlanner);
  // DebugUtils.nodeAmountCompare(trajectoryPlanner);
  // }
}

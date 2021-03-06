// code by jph
package ch.ethz.idsc.owl.bot.util;

import ch.ethz.idsc.owl.math.map.TranslationFamily;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.ScalarTensorFunction;

/** the term "family" conveys the meaning that the translation
 * depends on a single parameter, for instance time */
public final class SimpleTranslationFamily extends TranslationFamily {
  private final ScalarTensorFunction function;

  /** @param function maps a scalar to a vector in R^n */
  public SimpleTranslationFamily(ScalarTensorFunction function) {
    this.function = function;
  }

  @Override // from TranslationFamily
  public Tensor function_apply(Scalar scalar) {
    return function.apply(scalar);
  }
}

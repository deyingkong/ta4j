/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Ta4j Organization & respective
 * authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ta4j.core.indicators.numeric;

import org.junit.Test;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AbstractIndicatorTest;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.mocks.MockBarSeries;
import org.ta4j.core.num.Num;

import java.util.function.Function;

import static org.junit.Assert.*;
import static org.ta4j.core.TestUtils.assertNumEquals;

public class NumericIndicatorTest extends AbstractIndicatorTest<NumericIndicator, Num> {


    public NumericIndicatorTest(Function<Number, Num> numFunction) {
        super(numFunction);
    }

    @Test
    public void plus() {

        final BarSeries series = new MockBarSeries(numFunction, 1,2,3,4,5,6,7,8,9,8,7,6,5,4,3);
        final ClosePriceIndicator cp1 = new ClosePriceIndicator(series);
        final EMAIndicator ema = new EMAIndicator(cp1, 3);

        final NumericIndicator numericIndicator = new NumericIndicator(cp1);

        final NumericIndicator staticPlus = numericIndicator.plus(5);
        assertNumEquals(1 + 5, staticPlus.getValue(0));
        assertNumEquals(9 + 5, staticPlus.getValue(8));

        final NumericIndicator dynamicPlus = numericIndicator.plus(ema);
        assertNumEquals(cp1.getValue(0).plus(ema.getValue(0)), dynamicPlus.getValue(0));
        assertNumEquals(cp1.getValue(8).plus(ema.getValue(8)), dynamicPlus.getValue(8));

    }

    @Test
    public void minus() {
        fail("todo");
    }

    @Test
    public void sqrt() {
        fail("todo");
    }

}
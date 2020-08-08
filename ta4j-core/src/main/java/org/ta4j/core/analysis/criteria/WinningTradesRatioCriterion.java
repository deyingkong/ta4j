/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 Marc de Verdelhan, 2017-2020 Ta4j Organization & respective
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
package org.ta4j.core.analysis.criteria;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.num.Num;

/**
 * Calculates the percentage of trades which are profitable.
 *
 * Defined as <code># of winning trades / total # of trades</code>.
 */
public class WinningTradesRatioCriterion extends AbstractAnalysisCriterion {

    @Override
    public Num calculate(BarSeries series, Trade trade) {
        return isProfitableTrade(series, trade) ? series.numOf(1) : series.numOf(0);
    }

    private boolean isProfitableTrade(BarSeries series, Trade trade) {
        if (trade.isClosed()) {
            Num zero = series.numOf(0);
            return trade.getProfit().isGreaterThan(zero);
        }
        return false;
    }

    @Override
    public Num calculate(BarSeries series, TradingRecord tradingRecord) {
        long numberOfProfitable = tradingRecord.getTrades().stream().filter(t -> isProfitableTrade(series, t)).count();
        return series.numOf(numberOfProfitable).dividedBy(series.numOf(tradingRecord.getTradeCount()));
    }

    @Override
    public boolean betterThan(Num criterionValue1, Num criterionValue2) {
        return criterionValue1.isGreaterThan(criterionValue2);
    }
}
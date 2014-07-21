/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yahoo.ads.reporting.util;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Sales demo bar chart.
 */
public class StackedBarChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Sales stacked bar chart";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The monthly sales for the last 2 years (stacked bar chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Intent execute(Context context) {
    String[] titles = new String[] { "Filled", "Fallout", "Unsold", "Ad Blocker" };
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[] { 5000, 6000, 5000, 3000, 7000, 6000, 5678 });
    values.add(new double[] { 500, 300, 400, 200, 450, 430, 390 });
    values.add(new double[] { 300, 200, 100, 300, 40, 100, 400, 424 });
    values.add(new double[] { 30, 40, 30, 60, 9, 32, 44 });
        
    int[] colors = new int[] { Color.YELLOW, Color.RED, Color.GREEN, Color.MAGENTA};
    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
    setChartSettings(renderer, "Monthly sales in the last 2 years", "Month", "Units sold", 0.5,
        12.5, 0, 8000, Color.GRAY, Color.LTGRAY);
    renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
    renderer.getSeriesRendererAt(1).setDisplayChartValues(true);
    renderer.setXLabels(7);
    renderer.setYLabels(10);
    renderer.setXLabelsAlign(Align.LEFT);
    renderer.setYLabelsAlign(Align.LEFT);
    renderer.setPanEnabled(true, false);
    // renderer.setZoomEnabled(false);
    renderer.setZoomRate(1.1f);
    renderer.setBarSpacing(0.5f);
    return ChartFactory.getBarChartIntent(context, buildBarDataset(titles, values), renderer,
        Type.STACKED);
  }

}

package charts;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({"chartist.js","bar-chart.js"})
@StyleSheet({"chartist.min.css"})
public abstract class BarChartComponent 
extends 
AbstractJavaScriptComponent
{/****/ private static final long serialVersionUID = 2L;}
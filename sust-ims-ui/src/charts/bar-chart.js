/**
 * 
 */

var charts_BarChart	= function(){
	this.onStateChange = function() {
		var axisYLabel = this.getState().axisYLabel;
		var showNumber = this.getState().showAxisYLineNumber;
		var data	   = {labels: this.getState().labels, series: this.getState().series };
		var options = {
				high: 				this.getState().high
			   ,low:  				this.getState().low
			   ,fullWidth: 			this.getState().fullWidth
			   ,centerBars: 		this.getState().centerBars
			   ,seriesBarDistance:  this.getState().seriesBarDistance
			   ,stackBars: 			this.getState().stackBars
			   ,axisX: {offset: 	this.getState().axisXOffset}
			   ,axisY: {
				   offset: 			   this.getState().axisYOffset
				  ,labelInterpolationFnc: 
					  function (value){
					  if(showNumber === true)
					  {return value+axisYLabel;}
					  else
					  {return axisYLabel;}
				  }
			   ,scaleMinSpace: 		this.getState().scaleMinSpace
			   }	   
		};
		new Chartist.Bar('.bar-chart', data, options);	
	}
}
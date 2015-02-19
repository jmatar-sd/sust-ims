/**
 *
 * {
			  labelInterpolationFnc: function(value) {
				    return Math.round(value / data.series.reduce(sum) * 100) + '%';  
			  }
 */
var charts_PieChart		= function(){
	this.onStateChange	= function(){
		//tools
		var sum 			= function(a, b) { return a + b };
		var showPrecentage	= this.getState().showPrecentage;
		
		var	data		= {series: this.getState().series};
		var options 	= {
				labelInterpolationFnc:
				function(value) {return Math.round(value / data.series.reduce(sum) * 100) + '%';}
			   ,showLabel: this.getState().showLabel
			   ,donut:	   this.getState().donut
			   ,donutWidth:this.getState().donutWidth
			   ,startAngle:this.getState().startAngle
			   ,total:	   this.getState().total
		};
		
		if(showPrecentage === true)
		{var	PieChart	= new Chartist.Pie('.pie-chart', data, options);}
		else
		{
			var	data		= {labels: this.getState().labels, series: this.getState().series};
			var options 	= {
					labelInterpolationFnc:
					function(value) {return value}
				   ,showLabel: this.getState().showLabel
				   ,donut	 : this.getState().donut
				   ,donutWidth:this.getState().donutWidth
				   ,startAngle:this.getState().startAngle
				   ,total:	   this.getState().total
				   
			};
			var	PieChart	= new Chartist.Pie('.pie-chart', data, options);
		}
		
	}
}


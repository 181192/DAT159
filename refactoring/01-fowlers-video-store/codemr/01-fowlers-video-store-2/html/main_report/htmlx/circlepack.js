window.onload = function() {
	EQ_addComboItems("ClassMSel");
};

function paintCallBack() {
	circle.style("fill", function(d) {
		return (d.children) ? "#C6DCEC" : format_color(d);
	});

	circle.selectAll("title").text(
			function(d) {
				return d.name + " " + EQ_getSelectedMetricName(d) + " "
						+ EQ_GET_METRICVALUE(d);
			}); // + (d.children ? "" : ": " + format(d.size)); });

}

var OPTION_SKIP_DOT_COUNT = 1; // since packages names can be long, you can
								// shorten qualified names by skipping common
								// suffix

var margin = 20, diameter = 800;
var SELF_FRAME_HEIGHT = 850;

function format_color(d) {
	return EQ_GET_COLOR(d);
}

function getSmartLabel(d) {
	// if ((currentDepth + 1) >= d.depth) {

	if (OPTION_SKIP_DOT_COUNT == 0)
		return d.name;

	var str = d.name;
	var first = str.indexOf(".");

	if (OPTION_SKIP_DOT_COUNT == 1)
		return str.substring(first + 1);

	var second = str.indexOf(".", first + 1);
	if (OPTION_SKIP_DOT_COUNT == 2) {
		return str.substring(second + 1)
	}

	var third = str.indexOf(".", second + 1);
	if (OPTION_SKIP_DOT_COUNT == 3) {
		return str.substring(third + 1)
	}
	return str.substring(third + 1);
	// }
	// return "";
}

var pack = d3.layout.pack().padding(2).size(
		[ diameter - margin, diameter - margin ]).value(function(d) {
	return d.value;
}).sort(sortItems);

function sortItems(a, b) {
	var result;
	switch (1) { // bana descending daha güzel geldi...
	case 0:
		// str = 'ascending';
		result = a.value - b.value;
		break;
	case 1:
		// str = 'descending';
		result = b.value - a.value;
		break;
	default:
		// str = 'null';
		result = null;
	}
	return result;
}

var svg = d3.select("#mainchartbody").append("div").attr("class",
		"circlepackchart").append("svg").attr("width", diameter).attr("height",
		diameter).append("g").attr("transform",
		"translate(" + diameter / 2 + "," + diameter / 2 + ")");

root = EQ_GET_DATA(); // _/
{
	// _/ if (error) throw error;

	var focus = root, nodes = pack.nodes(root), view;

	var circlex = svg.selectAll("circle").data(nodes).enter().append("circle");

	var last = null;

	var circle = circlex
			.attr(
					"class",
					function(d) {
						return d.parent ? d.children ? "node"
								: "node node--leaf" : "node node--root";
					})
			.style("fill", function(d) {
				return (d.children) ? "#C6DCEC" : format_color(d);
			})
			// { return d.children ? color(d.depth) : null; })
			.style("fill-opacity", function(d) {
				return (d.children) ? "0.5" : "0.9";
			})
			.style("stroke", function(d) {
				return "#000"
			})
			.on("click", function(d) {
				if (focus !== d)
					zoom(d), d3.event.stopPropagation();
			})
			.on(
					"mousemove",
					function(d) {
						if (!(last === d)) {
							if (d.metrics)
								eQ.MetricChart
										.updateDots(EQ_convertMetricValues(d.metricvalues));
							last = d;
							if (typeof (updateSelectedElement) == "function"
									&& (d.parent)) {
								if (d.key)
									updateSelectedElement(d.parent.name, d.key);
								else
									updateSelectedElement(d.name, "");
							}
						}
					});

	circlex.append("title").text(
			function(d) {
				return d.name + " " + EQ_getSelectedMetricName(d) + " "
						+ EQ_GET_METRICVALUE(d);
			}); // + (d.children ? "" : ": " + format(d.size)); });

	var textx = svg.selectAll("text").data(nodes).enter().append("text");

	var text = textx.attr("class", "circlelabel")
	// .style("fill-opacity", function(d) { return d.parent === focus ? 1 : 0;
	// })
	.style("display", function(d) {
		return d.parent === root ? null : "none";
	}).text(function(d) {
		return getSmartLabel(d)
	});

	textx.append("title").text(
			function(d) {
				return d.name + " " + EQ_getSelectedMetricName(d) + " "
						+ EQ_GET_METRICVALUE(d);
			});

	var node = svg.selectAll("circle,text");

	d3.select("body")
	// .style("background", color(-1))
	.on("click", function() {
		zoom(root);
	});

	zoomTo([ root.x, root.y, root.r * 2 + margin ]);

	function zoom(d) {
		var focus0 = focus;
		focus = d;

		var transition = d3.transition().duration(d3.event.altKey ? 7500 : 750)
				.tween(
						"zoom",
						function(d) {
							var i = d3.interpolateZoom(view, [ focus.x,
									focus.y, focus.r * 2 + margin ]);
							return function(t) {
								zoomTo(i(t));
							};
						});

		transition.selectAll("text").filter(function(d) {
			if (!d)
				return false;
			return d.parent === focus || this.style.display === "inline";
		}).style("fill-opacity", function(d) {
			return d.parent === focus ? 1 : 0;
		}).each("start", function(d) {
			if (d.parent === focus)
				this.style.display = "inline";
		}).each("end", function(d) {
			if (d.parent !== focus)
				this.style.display = "none";
		});

		if (d.metrics)
			eQ.MetricChart.updateDots(d.metrics);

	}

};// _/

d3.select(self.frameElement).style("height", SELF_FRAME_HEIGHT + "px");

function zoomTo(v) {
	var k = diameter / v[2];
	view = v;
	node.attr("transform", function(d) {
		return "translate(" + (d.x - v[0]) * k + "," + (d.y - v[1]) * k + ")";
	});
	circle.attr("r", function(d) {
		return d.r * k;
	});
}
##CordovaGlassGesture##
---

This plugin adds gesture support from the Google Glass Touchpad to your Cordova based Application.


###Usage###

Add plugin via Node

`cordova plugin add https://github.com/aphex/cordova-glass-gesture`

Add an event listener to the document of your web application

```
document.addEventListener('swiperight', 
	function() {
		console.log('Got that Swipe Right');
	}
);
```

###Basic Events###
- tap
- longpress
- swipeup
- swipeleft 
- swiperight
- twotap
- twolongpress
- twoswipeup
- twoswipedown
- twoswipeleft
- twoswiperight
- threetap
- threelongpress


*A helpful note here a swipe to the left is backwards on the glass touchpad (from your eye towards your ear) where a swipe to the right is from your ear towards your eye.*

###Advanced Events###
Advanced events have data associated with them

- scroll
- twofingerscroll
- fingercountchanged


###Advanced Examples###

```
// Data contains properties displacement, delta and velocity
document.addEventListener("scroll", function(data) {
	console.log("Got that scroll");
	console.log(data.displacement);
	console.log(data.delta);
	console.log(data.velocity);
})
```

```
// Data contains properties displacement, delta and velocity
document.addEventListener("twofingerscroll", function(data) {
	console.log("Got that two finger scroll");
	console.log(data.displacement);
	console.log(data.delta);
	console.log(data.velocity);
})
```

```
// Data contains properties from and to
document.addEventListener("fingercountchanged", function(data) {
	console.log("Fingers changed from " + data.from + " to " + data.to);
})
```
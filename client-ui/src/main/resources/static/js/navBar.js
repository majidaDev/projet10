document.addEventListener( "DOMContentLoaded", function() {
	window.onscroll = function() { scrollFunction() };

	function scrollFunction() {
	  if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
		  document.getElementById("header").classList.add("shrink");
		  document.getElementById("imgHeader").classList.add("shrinkImg");
	  } else {
		  document.getElementById("header").classList.remove("shrink");
		  document.getElementById("imgHeader").classList.remove("shrinkImg");
	  }
	}
});
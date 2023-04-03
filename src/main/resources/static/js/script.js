/**function userAction() {
	var dStyle = document.getElementById("user-management-link").style.display;

if(dStyle === "none"){
  document.getElementById("user-arrow-down").style.display = "none";
	document.getElementById("user-arrow-up").style.display = "block";
	var elem = document.getElementById("user-arrow-up");
	elem.className = "bi bi-chevron-up ms-auto ";


document.getElementById("user-management-link").style.display = `block`;}
else{
  document.getElementById("user-arrow-up").style.display = "none";
	document.getElementById("user-arrow-down").style.display = "block";
	var elem = document.getElementById("user-arrow-down");
	elem.className = "bi bi-chevron-down ms-auto ";


document.getElementById("user-management-link").style.display = `none`;
  }
}
  function userAction1() {
	var dStyle = document.getElementById("marketing-link").style.display;

if(dStyle === "none"){
document.getElementById("marketing-arrow-down").style.display = "none";
	document.getElementById("marketing-arrow-up").style.display = "block";
	var elem = document.getElementById("marketing-arrow-up");
	elem.className = "bi bi-chevron-up ms-auto animate";

document.getElementById("marketing-link").style.display = `block`;}
else{
  document.getElementById("marketing-arrow-up").style.display = "none";
	document.getElementById("marketing-arrow-down").style.display = "block";
	var elem = document.getElementById("marketing-arrow-down");
	elem.className = "bi bi-chevron-down ms-auto ";


document.getElementById("marketing-link").style.display = `none`;
  }
}


  function userAction2() {
	var dStyle = document.getElementById("analytics-link").style.display;

if(dStyle === "none") {

	document.getElementById("analytics-arrow-down").style.display = "none";
	document.getElementById("analytics-arrow-up").style.display = "block";
	var elem = document.getElementById("analytics-arrow-up");
	elem.className = "bi bi-chevron-up ms-auto animate";


   
	document.getElementById("analytics-link").style.display = `block`;
}
   
else {
    
	document.getElementById("analytics-arrow-up").style.display = "none";
	document.getElementById("analytics-arrow-down").style.display = "block";
	var elem = document.getElementById("analytics-arrow-down");
	elem.className = "bi bi-chevron-down ms-auto ";

	document.getElementById("analytics-link").style.display = `none`;
}

  }**/

/**function diplayaddcontent(){
  

document.querySelector(".table-div").style.display = "none";
document.querySelector(".inside-search-content").style.display = "none";
document.querySelector(".inside-button-content").style.display = "none";
document.querySelector(".add-contents").style.display= "flex";
document.querySelector(".cancel-save-btn").style.display= "flex";
}

function displayaddpeople(){
  document.querySelector(".table-div").style.display = "none";
document.querySelector(".inside-search-content").style.display = "none";
document.querySelector(".inside-button-content").style.display = "none";
document.querySelector(".add-people").style.display= "block";
document.querySelector(".cancel-save-btn-addpeople").style.display= "flex";

}**/


function displaymedia() {
	var type = document.querySelector('#contenttype').value;
	var element1 = document.getElementById("mediaid");
	var element2 = document.getElementById("bannerid");
	var element3 = document.getElementById("peopleid");
	var element4 = document.getElementById("addonsid");
	element1.classList.add("linkid");
	element2.classList.remove("linkid");
	element3.classList.remove("linkid");
	element4.classList.remove("linkid");

	if (type == 'audio') {

		document.querySelector('.media').style.display = "none";

		document.querySelector('.audiomedia').style.display = "block";
		document.querySelector('#mediavideo').style.display = "none";
		document.querySelector('#mediaaudio').style.display = "block";

	}
	else {
		document.querySelector('.media').style.display = "block";
		document.querySelector('.audiomedia').style.display = "none";
		document.querySelector('#mediaaudio').style.display = "none";
		document.querySelector('#mediavideo').style.display = "block";
	}
	// document.querySelector(".media").style.display = "block";
	document.querySelector(".banner").style.display = "none";
	document.querySelector(".people").style.display = "none";
	document.querySelector(".addons").style.display = "none";
	document.querySelector('.addons-1').style.display = "none"
	document.querySelector('#tabledisplay').style.display = "none"
	// document.querySelector('.audiomedia').style.display="none";
}
function displaybanner() {
	var element1 = document.getElementById("mediaid");
	var element2 = document.getElementById("bannerid");
	var element3 = document.getElementById("peopleid");
	var element4 = document.getElementById("addonsid");
	element1.classList.remove("linkid");
	element2.classList.add("linkid");
	element3.classList.remove("linkid");
	element4.classList.remove("linkid");


	document.querySelector(".banner").style.display = "block";
	document.querySelector(".media").style.display = "none";
	document.querySelector(".people").style.display = "none";
	document.querySelector(".addons").style.display = "none";
	document.querySelector('.addons-1').style.display = "none"
	document.querySelector('.audiomedia').style.display = "none";
	document.querySelector('#tabledisplay').style.display = "none"
}
function displaypeople() {
	var element1 = document.getElementById("mediaid");
	var element2 = document.getElementById("bannerid");
	var element3 = document.getElementById("peopleid");
	var element4 = document.getElementById("addonsid");
	element1.classList.remove("linkid");
	element2.classList.remove("linkid");
	element3.classList.add("linkid");
	element4.classList.remove("linkid");
    document.querySelector("#contentpeopleTable").style.display = "flex";
	document.querySelector(".people").style.display = "flex";
	document.querySelector(".media").style.display = "none";
	document.querySelector(".banner").style.display = "none";
	document.querySelector(".addons").style.display = "none";
	document.querySelector('.addons-1').style.display = "none"
	document.querySelector('.audiomedia').style.display = "none";
	document.querySelector('#tabledisplay').style.display = "block"
}
function displaypeople1() {
	var element1 = document.getElementById("mediaid");
	var element2 = document.getElementById("bannerid");
	var element3 = document.getElementById("peopleid");
	var element4 = document.getElementById("addonsid");
	element1.classList.remove("linkid");
	element2.classList.remove("linkid");
	element3.classList.add("linkid");
	element4.classList.remove("linkid");
    
	document.querySelector(".people").style.display = "flex";
	document.querySelector(".media").style.display = "none";
	document.querySelector(".banner").style.display = "none";
	document.querySelector(".addons").style.display = "none";
	document.querySelector('.addons-1').style.display = "none"
	document.querySelector('.audiomedia').style.display = "none";
	document.querySelector('#tabledisplay').style.display = "block"
}
function displayaddons() {
	var element1 = document.getElementById("mediaid");
	var element2 = document.getElementById("bannerid");
	var element3 = document.getElementById("peopleid");
	var element4 = document.getElementById("addonsid");
	element1.classList.remove("linkid");
	element2.classList.remove("linkid");
	element3.classList.remove("linkid");
	element4.classList.add("linkid");

	document.querySelector(".addons").style.display = "block";
	document.querySelector(".banner").style.display = "none";
	document.querySelector(".people").style.display = "none";
	document.querySelector(".media").style.display = "none";
	document.querySelector('.addons-1').style.display = "none";
	document.querySelector('.audiomedia').style.display = "none";
	document.querySelector('#tabledisplay').style.display = "none"

}

function addons1() {

	document.querySelector('.addons').style.display = "none";
	document.querySelector('.addons-1').style.display = "block";
	document.querySelector('#tabledisplay').style.display = "none"

}
function previewnone() {
	var type = document.querySelector('#contenttype').value;
	if (type == 'audio') {
		document.querySelector('.media').style.display = "none";
		document.querySelector('.audiomedia').style.display = "block";
		document.querySelector('#mediavideo').style.display = "none";
		document.querySelector('#mediaaudio').style.display = "block";

	}
	else {
		document.querySelector('.media').style.display = "block";
		document.querySelector('.audiomedia').style.display = "none";
		document.querySelector('#mediaaudio').style.display = "none";
		document.querySelector('#mediavideo').style.display = "block";
	}
}




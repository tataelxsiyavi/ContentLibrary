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
function displaymanagecategory(){
    
}


function displaymedia(){
    document.querySelector(".media").style.display = "block";
    document.querySelector(".banner").style.display = "none";
    document.querySelector(".people").style.display = "none";
    document.querySelector(".addons").style.display = "none";
    document.querySelector('.addons-1').style.display="none"

}
function displaybanner(){
    document.querySelector(".banner").style.display = "block";
    document.querySelector(".media").style.display = "none";
    document.querySelector(".people").style.display = "none";
    document.querySelector(".addons").style.display = "none";
    document.querySelector('.addons-1').style.display="none"

}
function displaypeople(){
    document.querySelector(".people").style.display = "flex";
    document.querySelector(".media").style.display = "none";
    document.querySelector(".banner").style.display = "none";
    document.querySelector(".addons").style.display = "none";
    document.querySelector('.addons-1').style.display="none"

}
function displayaddons(){
    document.querySelector(".addons").style.display = "block";
    document.querySelector(".banner").style.display = "none";
    document.querySelector(".people").style.display = "none";
    document.querySelector(".media").style.display = "none";
    document.querySelector('.addons-1').style.display="none";


}

function addons1(){
    document.querySelector('.addons').style.display="none";
    document.querySelector('.addons-1').style.display="block";

}




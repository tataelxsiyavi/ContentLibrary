var file = document.querySelector('.preview_fileselect');

file.onchange = function(e) {
  var ext = this.value.match(/\.([^\.]+)$/)[1];
  switch (ext) {
    case 'mp4':
   
      alert('Allowed');
      break;
    default:
      alert('Not allowed');
      this.value = '';
  }
};
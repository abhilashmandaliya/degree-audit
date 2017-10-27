$(document).ready(function(){
  $(".btn-danger").click(function(e){
    e.preventDefault();
    $(this).text('Add');
    $(this).removeClass("btn-danger").addClass("btn-primary");
    var tr = $(this).closest("tr").remove().clone();
    $("#masterList tbody").append(tr);
  });
  $(".btn-primary").click(function(e){
    e.preventDefault();
    $(this).text('Remove');
    $(this).removeClass("btn-primary").addClass("btn-danger");
    var tr = $(this).closest("tr").remove().clone();
    $("#semesterTable tbody").append(tr);
  });
});

$(document).ready(function(){
    subdir = getURLParameter('subdir');
    $.ajax({ url: "getdir",
        data: {subdir: subdir},
        success: function(data){
            document.getElementById("file_to_list").innerHTML = data;
        }});
});

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||""
}
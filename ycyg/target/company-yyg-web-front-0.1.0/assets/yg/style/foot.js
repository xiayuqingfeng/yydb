//提示效果
$(".layerTip").bind('mouseover',function(){
    layer.tips($(this).attr('_title'), $(this), { time:2 });
});
//表单浮动提示
function validTip(msg,o,cssctl){
    if(!o.obj.is("form")){
        var objtip=o.obj.parents(".form-box").find(".Validform_checktip");
        cssctl(objtip,o.type);
        objtip.text(msg);

        var infoObj=o.obj.parents(".form-box").find(".Validform_info");
        if(o.type==2){
            infoObj.fadeOut(200);
        }else{
            if(infoObj.is(":visible")){return;}
            var left=o.obj.offset().left,top=o.obj.offset().top;

            infoObj.css({
                left:left+240,
                top:top-45
            }).show().animate({
                top:top-35
            },200);
        }

    }
}
//加鼠标效果class
function itemhover(obj){
    obj=(obj!=undefined)?obj:'.item';
    if($(obj).length>0){
        $(obj).bind('mouseover',function(){
            $(this).addClass('hover');
        }).bind('mouseout',function(){
            $(this).removeClass('hover');
        })
    }
}
//参与人数
$(function(){
    if($("#BIDNUMBER").length>0){
//        showBidNumber(logCount,1);
//        getBidNumberData();
//        setInterval('getBidNumberData()', 10000);
    }
});
function getBidNumberData(){
//    $.post('/welcome/bidCount','',function(data){
//        showBidNumber(data.count,2);
//        showGjjNumber(data.gjj);
//    },'json');
}
function showBidNumber(n,c){
    var it = $("#BIDNUMBER i");
    var len = String(n).length;
    for(var i=0;i<len;i++){
        if(c==1 && (i==3 || i==6)){
            $("#BIDNUMBER").append("<font>,</font>");
        }
        if(it.length<=i){
            $("#BIDNUMBER").append("<i><em></em></i>");
        }
        var num=String(n).charAt(i);
        var y = -parseInt(num)*29;
        var obj = $("#BIDNUMBER i").eq(i).find('em');
        obj.animate({
            top: String(y)+'px'
        }, 2000,'swing');
    }
}
function showGjjNumber(n){
    $("#GJJ").html("");
    $("#GJJ").html(n);
}
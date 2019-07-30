$(function(){
    //返回顶部
    if($('#top').length>0){
        $("#top").hide();
        $(window).scroll(function(){
            if ($(window).scrollTop()>100){
                $("#top").fadeIn();
            }else{
                $("#top").fadeOut();
            }
        });
        $("#top").click(function(){
            $('body,html').animate({scrollTop:0},500);
            return false;
        });
    }

});
//表单浮动提示
function validTip(msg,o,cssctl){
    if(!o.obj.is("form")){
        var objtip=o.obj.parents(".form-box").find(".Validform_checktip");
        cssctl(objtip,o.type);
        objtip.text(msg);
    }
}
//tab
function tabs(obj, nav, cont, cur, type){
    var c = cont;
    var obj = $(obj),
        nav = $(nav,obj),
        cont = $(cont,obj);

    nav.first().addClass(cur);
    cont.first().show();

    nav.each(function(index) {
        $(this).bind(type,function(){
            nav.removeClass(cur);
            $(this).addClass(cur);
            cont.hide();
            cont.eq(index).show();
        });
    });
}
//参与人数
$(function(){
    if($("#BIDNUMBER").length>0){
        showBidNumber(logCount,1);
        getBidNumberData();
        setInterval('getBidNumberData()', 10000);
    }
});
function getBidNumberData(){
    $.post('/buyCount.json','',function(data){
        showBidNumber(data.count,2);
    },'json');
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

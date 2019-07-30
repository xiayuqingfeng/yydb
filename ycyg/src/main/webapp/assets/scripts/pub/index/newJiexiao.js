/* *
 * 倒计时
 */

var Tday_jx = [];
var timeID_jx = [];
var timeID_h_jx = [];
var Secondms_jx = 60*1000;
var minutems_jx = 1000;

//倒计时时钟
function clock_jx(key,skin)
{
    var diff = Tday_jx[key];
    var DifferSecond   = Math.floor(diff / Secondms_jx);
    diff -= DifferSecond * Secondms_jx;
    var DifferMinute   = Math.floor(diff / minutems_jx);
    diff -= DifferMinute * minutems_jx;

    if(DifferSecond.toString().length < 2) DifferSecond = '0'+DifferSecond;
    if(DifferMinute.toString().length < 2) DifferMinute = '0'+DifferMinute;

    var DifferSecond_1 = DifferSecond.toString().substr(0,1);
    var DifferSecond_2 = DifferSecond.toString().substr(1,1);
    var DifferSecond_3 = DifferSecond.toString().substr(2,1);

    var DifferMinute_1 = DifferMinute.toString().substr(0,1);
    var DifferMinute_2 = DifferMinute.toString().substr(1,10);

    var sTime = "";
    sTime += "<span class='s1'><font>"+DifferSecond_1+"</font><font>"+DifferSecond_2+"</font>"+(DifferSecond_3?"<font class='f3' style='display:none;'>"+DifferSecond_3+"</font>":'')+"</span><b>:</b>";
    sTime += "<span><font>"+DifferMinute_1+"</font><font>"+DifferMinute_2+"</font></span><b>:</b>";
    sTime += "<span class='timeHm'>"+99+"</span>";

    var itemDjx = $('#itemDjx'+key);
    var qihao = itemDjx.attr('q');

    if(Tday_jx[key]<=0){
        //结束计时，开奖
        timeID_jx[key] = window.clearInterval(timeID_jx[key]);
        timeID_h_jx[key] = window.clearInterval(timeID_h_jx[key]);
        //加载最新揭晓
        loadNewJiexiao();
    }else{
        Tday_jx[key]=Tday_jx[key]-1000;
        document.getElementById("leftTimeJx"+key).innerHTML = sTime; //显示倒计时信息

        //分钟 三位和两位的样式转换
        if(DifferSecond.toString().length >= 3){
            itemDjx.addClass('timer_three').find('.f3').show();
            if(itemDjx.find('cite').length>0){
                itemDjx.find('cite').css('width','147px').find('.s1').css('width','50px');
            }
            if(itemDjx.find('.index-time').length>0){
                itemDjx.find('.index-time').css('font-size','16px');
            }
        }else{
            itemDjx.removeClass('timer_three').find('.f3').hide();
            if(itemDjx.find('cite').length>0){
                itemDjx.find('cite').css('width','131px').find('.s1').css('width','34px');
            }
            if(itemDjx.find('.index-time').length>0){
                itemDjx.find('.index-time').css('font-size','24px');
            }
        }
    }
}

/**
 * 倒计时入口函数
 * @param key       计时DIV的循环ID key,即 id="leftTimeJx{$key}"
 * @param diff_time 倒计时时间差
 * @param skin      倒计时样式：默认0
 */
function onload_leftTime_jx(key,diff_time,skin){
/*    $.get('/yunbuy/getLeftTime/'+key,'',function(data){
        diff_time = data;
    });*/
    skin = skin?skin:'default';
    Tday_jx[key] = parseInt(diff_time);
    timeID_jx[key] = window.setInterval(function(){clock_jx(key,skin);}, 1000);

    //毫秒单独计时
    var h = 100;
    timeID_h_jx[key] = window.setInterval(function(){
        if(h<=0) h = 100;
        h = parseInt(h)-1;
        if(h.toString().length < 1) h = '00';
        if(h.toString().length == 1) h = '0'+h;
        if(h.toString().length > 2) h = '99';

        var h_1 = h.toString().substr(0,1);
        var h_2 = h.toString().substr(1,1);

        $("#leftTimeJx"+key).find('.timeHm').html('<font>'+h_1+'</font><font>'+h_2+'</font>');
    }, 10);
}

//获取最新揭晓
function loadNewJiexiao() { 
	//清理定时器
    jQuery.post("/newJieXiaos.html", {}, function(data) {
            $("#win-list").html(data);
     });
}
//加载最新揭晓
loadNewJiexiao();
setInterval('loadNewJiexiao()', 5 * 1000);//5秒
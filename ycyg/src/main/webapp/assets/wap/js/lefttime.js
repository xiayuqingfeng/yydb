/* *
 * 倒计时
 */

var Tday = new Array();
var timeID = [];
var daysms = 24 * 60 * 60;
var hoursms = 60 * 60;
var Secondms = 60;
var minutems = 1;

//倒计时时钟
function clock(key,type,skin)
{
    var diff = Tday[key];
    var DifferDay   = Math.floor(diff / daysms);
    diff -= DifferDay * daysms;
    var DifferHour   = Math.floor(diff / hoursms);
    diff -= DifferHour * hoursms;
    var DifferSecond   = Math.floor(diff / Secondms);
    diff -= DifferSecond * Secondms;
    var DifferMinute   = Math.floor(diff / minutems);
    diff -= DifferMinute * minutems;

    var sTime = "<span class='yomi'>";
    if(skin==1){
        if(DifferDay) sTime += "<b>"+DifferDay+"天</b><h></h>";
        if(DifferHour) sTime += "<b>"+DifferHour+"</b><h>:</h>";
        if(DifferSecond) sTime += "<b>"+DifferSecond+"</b><h>:</h>";
        sTime += "<b>"+DifferMinute+"</b>";
    }else{
        if(DifferDay) sTime += "<b class='yomiday'>"+DifferDay+"</b><h class='split'>天</h>";
        if(DifferHour) sTime += "<b class='yomihour'>"+DifferHour+"</b><h class='split'>时</h>";
        if(DifferSecond) sTime += "<b class='yomimin'>"+DifferSecond+"</b><h class='split'>分</h>";
        sTime += "<b class='yomisec'>"+DifferMinute+"</b><h class='split'>秒</h>";
    }
    sTime += "</span>";

    if(Tday[key]<=0){
        //更新缓存
        $.post('welcome/clearCaches',{ ajax:1,type:'auction_cats' },function(data){ },"json");
        timeID[key] = window.clearInterval(timeID[key]);
        document.getElementById("leftTime"+key).innerHTML = (type==0)?'已开始':'已结束';
    }else{
        Tday[key]=Tday[key]-1;
        document.getElementById("leftTime"+key).innerHTML = sTime; //显示倒计时信息
    }
}

/**
 * 倒计时入口函数
 * @param key       计时DIV的循环ID key,即 id="leftTime{$key}"
 * @param diff_time  倒计时结束时间
 * @param type      倒计时类型：默认0结束计时 1即将开始
 * @param skin      倒计时样式：默认0
 */
function onload_leftTime(key,diff_time,type,skin){
    type = type?parseInt(type):1;
    skin = skin?parseInt(skin):0;
    Tday[key] = parseInt(diff_time);

    timeID[key] = window.setInterval(function(){clock(key,type,skin);}, 1000);
}

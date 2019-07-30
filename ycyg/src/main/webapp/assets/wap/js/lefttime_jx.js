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

    var sTime = "";
    sTime += "<span>"+DifferSecond+"</span><b>:</b>";
    sTime += "<span>"+DifferMinute+"</span><b>:</b>";
    sTime += "<span class='timeHm'>"+99+"</span>";

    if(Tday_jx[key]<=0){
        //结束计时，开奖
        timeID_jx[key] = window.clearInterval(timeID_jx[key]);
        timeID_h_jx[key] = window.clearInterval(timeID_h_jx[key]);

        $("#leftTimeJx"+key).parent().hide();
        $("#leftTimeJx"+key).parent().parent().find('.jx-ing').show();

        var itemDjx = $('#itemDjx'+key);
        var qihao = itemDjx.attr('q');
        $.getJSON('welcome/lottery/'+qihao ,function(result) {

            $.post('yunbuy/lottery','skin='+skin+'&id='+key,function(data){
                setTimeout(function(){
                    //itemDjx.hide();
                    itemDjx.before(data.html);
                    itemDjx.remove();
                }, 5000);
            },'json');

        });
    }else{
        Tday_jx[key]=Tday_jx[key]-1000;
        document.getElementById("leftTimeJx"+key).innerHTML = sTime; //显示倒计时信息
    }
}

/**
 * 倒计时入口函数
 * @param key       计时DIV的循环ID key,即 id="leftTimeJx{$key}"
 * @param diff_time 倒计时时间差
 * @param skin      倒计时样式：默认0
 */
function onload_leftTime_jx(key,diff_time,skin){
    $.get('yunbuy/getLeftTime/'+key,'',function(data){
        diff_time = data;
    });
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
        $("#leftTimeJx"+key).find('.timeHm').html(h)
    }, 10);
}

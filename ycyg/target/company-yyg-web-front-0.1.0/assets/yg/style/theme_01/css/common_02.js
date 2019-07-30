$(".screen-left .nav-sub").find("li:last").addClass("nav-sub-last")

//漂浮
$(function(){
    var navH2_len = $(".menu-show").length;
    //获取要定位元素距离浏览器顶部的距离
    var navH = $(window).height()/2;
    var navH2 = navH2_len > 0 ? $(".menu-show").offset().top : 100;
    //滚动条事件
    $(window).scroll(function(){
        //获取滚动条的滑动距离
        var scroH = $(this).scrollTop();
        //滚动条的滑动距离大于等于定位元素距离浏览器顶部的距离，就固定，反之就不固定
        if(scroH>=400){
            $(".bodyright").fadeIn(500);
        }else if(scroH<400){
            $(".bodyright").fadeOut(500);
        }
        if(scroH>navH2){
            $(".header").addClass("head_fx");
            $(".header").find(".screen-left").removeClass("head-fx1");
        }else{
            $(".header").removeClass("head_fx");
            $(".header").find(".screen-left").addClass("head-fx1");
        }
    });
});
$(function(){
    var navH1_len = $(".index-menu-show").length;
    //获取要定位元素距离浏览器顶部的距离
    var navH1 = navH1_len > 0 ? $(".index-menu-show").offset().top-30 : '';
    //滚动条事件
    $(window).scroll(function(){
        //获取滚动条的滑动距离
        var scroH = $(this).scrollTop();
        //滚动条的滑动距离大于等于定位元素距离浏览器顶部的距离，就固定，反之就不固定
       if(navH1_len > 0 && scroH>navH1){
            $(".index-menu").fadeIn(500);
        }else{
            $(".index-menu").fadeOut(500);
        }
    });
});


$('.bodyright li.br-6').click(function () {
    $('html,body').animate({
        scrollTop : '0px'
    }, 500);//返回顶部所用的时间 返回顶部也可调用goto()函数
});
$('.bottom-close').click(function () {
    $(".bottom-ad").fadeOut(300)
});
$('.nav-title').hover(function(){
    $(this).find(".screen-left").show()
},function(){
    $(this).find(".screen-left").hide()
});



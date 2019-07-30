/**
 * jquery pageload
 * suhafe
 */
(function($) {
    $.fn.pageload = function(options){
        var elements = $(this);
        var winHeight = $(window).height();
        var locked = false;
        var settings = {
            url : '/',            //异步加载地址
            param : '',           //post参数
            trigger : '.getMore', //触发层
            itembox : '',         //外层
            page : 1,             //默认加载的分页数
            scroll : false,       //是否滚动加载
            loader : "more_loader_spinner", //加载中样式class
            more : "点击获取更多...",
            nomore : "没有更多了！"
        };
        if (options){
            $.extend(settings, options);
        }

        function load_data(){
            if($(settings.trigger).html()==settings.nomore) return;
            $(settings.trigger).before('<div class="'+settings.loader+'"></div>');
            $(settings.trigger).hide();

            var D = 'ajaxcontent=1';
            if(settings.param) D=D+"&"+settings.param;
            $.post(settings.url+'/'+settings.page,D,function(data){
                if(data.content){
                    elements.append(data.content);
                    settings.page = settings.page+1;
                }else{
                    $(settings.trigger).html(settings.nomore);
                }
                $(settings.trigger).show();
                $('.'+settings.loader).hide();
            },'json')
        }

        //页面加载时
        $(function(){
            $(settings.trigger).html(settings.more);
            load_data();
        });

        //点击更多时
        $(settings.trigger).bind('click',function(){
            load_data();
        });

        //页面滚动时
        $(window).scroll(function () {
            var scrollTop = $(window).scrollTop();
            var offset = $(elements).offset().top + $(elements).height() - (scrollTop + winHeight);
            if(offset < settings.offset && !locked){
                locked = true;
                load_data();
            }
        });
    };
})(jQuery);
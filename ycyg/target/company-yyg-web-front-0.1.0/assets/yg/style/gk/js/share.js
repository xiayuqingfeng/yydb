$(document).ready(function(){
	var $container = $('#wall');
	
	$container.html("拼命加载中...").load(_url,function(){
        $(this).imagesLoaded(function(){
            $container.masonry({
                itemSelector: '.item',
                columnWidth: 4,
                isAnimated: true
            });

            itemhover();
        });
    });
	
	$container.infinitescroll({
		navSelector  : '#loadmore',
		nextSelector : '#loadmore a',
		itemSelector : '.item',
		loading: {
			finishedMsg: '没有更多的页面加载。',
			img: '/style/gk/images/loading.gif'
		}
    },function(newElements){

        var $newElems = $( newElements ).css({ opacity: 0 });
        $newElems.imagesLoaded(function(){
            $newElems.animate({opacity:1});
            $container.masonry( 'appended', $newElems, true );

            itemhover();
        });

    });
});
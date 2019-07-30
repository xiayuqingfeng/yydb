/** 文章分类对象 */
var ProductObject = {
	init : function() {
		this._reg_buy_num();
		this._reg_tab();
		//加载购买记录
		loadBuyRecord(1);
		//加载分享记录
		loadShareRecord(1);
		//加载分期分页
		loadPeriodPage(1);
		//商品详情页签切换
		jQuery(".m-detail-mainTab").slide( { titCell:".w-tabs-tab div",mainCell:".w-tabs-panel",titOnClassName:"w-tabs-tab-item-selected" } );
		//期数展开和收起
		$(".period_Open").click(function(){
		    if($('.yhp-wq').css('height')=='98px'){
		        $('.yhp-wq').css('height','auto');
		        $(this).children('a').html("<< 收起");
		    }else{
		        $('.yhp-wq').css('height','98px');
		        $(this).children('a').html("展开>>");
		    }
		});
	},
	/**购买数量事件*/
	_reg_buy_num:function(){
		 var need_num = $('input.w-number-input').attr('data-max');
		 $('.w-number-a').bind('click',function(){
             $('.w-number-a').removeClass('hover');
             $(this).addClass('hover')
             $('.w-number-input').val($(this).attr('data-value'));
             ProductObject.getWinPoint($(this).attr('data-value'),need_num);
         })
        $(".w-number-btn-plus").click(function(){
            var max = $(this).parent().children('input.w-number-input').attr('data-max');
            var qty = $(this).parent().children('input.w-number-input').val()*1;
            if(qty < max){
                $(this).parent().children('input.w-number-input').val(qty+1);
                ProductObject.getWinPoint(qty+1,need_num);
            }
        });
        $(".w-number-btn-minus").click(function(){
            var qty = $(this).parent().children('input.w-number-input').val()*1;
            if(qty>1){
                $(this).parent().children('input.w-number-input').val(qty-1);
                ProductObject. getWinPoint(qty-1,need_num);
            }
        });
        $(".w-number-input").blur(function(){
            var max = $(this).attr('data-max')*1;
            if($(this).val()>max){
                $(this).val(max);
            }
            ProductObject.getWinPoint($(this).val(),need_num);
        });
	},
	_reg_tab:function(){
        //选项卡鼠标滑过事件
        $('.add-tab-tt  li').hover(function(){
        	ProductObject.TabSelect(".add-tab-tt  li", ".add-tab-box .add-nr01", "dq", $(this))
        });
        $('.add-tab-tt li').eq(0).trigger("click");
	},
	/**获得机率*/
	getWinPoint:function($num,$need_num){
        layer.tips('获得机率'+($num/$need_num*100).toFixed(3)+'%', $('.w-number-input'), { time:2 });
    },
    /**我的云购记录页签*/
    TabSelect:function(tab,con,addClass,obj){
        var $_self = obj;
        var $_nav = $(tab);
        $_nav.removeClass(addClass),
                $_self.addClass(addClass);
        var $_index = $_nav.index($_self);
        var $_con = $(con);
        $_con.hide(),
        $_con.eq($_index).show();
    }
}
ProductObject.init();
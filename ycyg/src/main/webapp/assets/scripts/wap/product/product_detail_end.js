/** 文章分类对象 */
var ProductObject = {
	init : function() { 
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
	_reg_tab:function(){
        //选项卡鼠标滑过事件
        $('.add-tab-tt  li').hover(function(){
        	ProductObject.TabSelect(".add-tab-tt  li", ".add-tab-box .add-nr01", "dq", $(this))
        });
        $('.add-tab-tt li').eq(0).trigger("click");
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
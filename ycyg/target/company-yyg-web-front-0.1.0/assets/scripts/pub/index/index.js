//提示效果
$(".layerTip").bind('mouseover',function(){
    layer.tips($(this).attr('_title'), $(this), { time:2 });
});
//微信官方
$(".weixin").hover(function () {
    $(this).find("img").show();
},function () {
    $(this).find("img").hide();
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

//参与人数统计
function getBidNumberData(){

	$.ajax({
		type : "post",
		url : "/buyCount.json",
		data : "",
		cache : false,
		dataType : "text",
		//timeout : 100000,
		success : function(o) {
	        showBidNumber(o,2);
		}
	});
}
//参与人数格式
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
//添加购物车
function addToCart(id,type,obj,pre){
    obj = obj?obj:'';
    pre = pre?pre:'';
    id = parseInt(id);
    var qty = ($('#qty_'+id).length>0 && type != 'buy_buy') ? $('#qty_'+id).val() : 1;

    $.post('/cart/addCart.json',{"id":id,"buyNum":qty},function(data){ 
        if(data.isSuccess==false){ 
            //layer.alert(data.message,8);
        	alert(data.message);
        }else{
            if(type=='buy' || type=='buy_buy'){
                location.href='/cart/';
            }else{
            	loadCart();
                MoveBox(obj,id,pre);
            }
        }
    },"json");
}
//加载购物车
function loadCart(){
    $.post('/cart/loadCart.json',{},function(data){
        //$('#cart_info').html(data.html);
    	if(data.isSuccess==false || data.message.length==0){
    		$('#cart_info').html('<div class="w-miniCart-empty">您的清单中还没有任何物品</div>');
    	}else{
    		var total=0;
    		var msg='<div class="gwc-nr">';
    		msg+='<h3>最近加入的商品</h3>';
    		for(var i=0;i<data.message.length;i++){
    			var o=data.message[i];
	    		msg+='<div class="fn-clear gwc-list">';
	    		msg+='<p class="p1">';
	    		msg+='<a href="/product/detail/'+o.id+'.html"> <img src="'+o.product.logoPath+'" alt=""></a>';
	    		msg+='</p>';
	    		msg+='<p class="p2">';
	    		msg+='<a href="/product/detail/'+o.id+'.html"" class="a-title">(第'+o.product.period+'期)'+o.product.name+'</a>';
	    		
	    		msg+=' <br><span class="color01">'+o.product.singlePrice+'云购币 × '+o.buyNum+'人次</span>';
	    		msg+='<span><a href="javascript:;" onclick="delCart('+o.id+')" class="right">删除</a></span>';
	    		msg+='</p>';
	    		msg+='</div>';
	    		total+=o.product.singlePrice*o.buyNum;
    		}

    		msg+='</div>';
    		
    		msg+='<div class="gwc-gmbox"><p>共有'+data.message.length+'件商品，金额总计：<strong class="color01">'+total+'云购币</strong></p><a href="/cart" class="gwc-btn2">查看清单并结算</a></div>';

    		$('#cart_info').html(msg);
 
    	      
    	    
    	}
        
        $('#cartNum,.cartNum').html(data.message.length);
    },"json");
}
//删除购物车
function delCart(id,type){ 
    if(id){
        $.post('/cart/deleteCart.json',{ids:id},function(data){
        	//购物车明细中删除要刷新页面
        	if(type==1){
        		window.location.reload();
        	}else{
            	loadCart();
        	}
        });
        
    }
}
//加入购物车特效
function MoveBox(t,id,pre) {
    pre = pre?pre:'';
    var divTop = $(t).offset().top;
    var divLeft = $(t).offset().left;
    var cartBox = $("#cartNum").last();
    var src = $('#buy_img_'+id+pre).attr('src');
    $('body').append('<div class="ui-cart-move"><img src="'+src+'" /></div>');
    var obj = $('.ui-cart-move').last();

    obj.css({
        "position": "absolute",
        "z-index": "1000",
        "left": divLeft + "px",
        "top": divTop + "px"
    }).animate({
        "left": cartBox.offset().left + "px",
        "top": cartBox.offset().top + "px"
    },500).fadeTo(0, 0.1, function(){
        obj.remove();
    });
}
//滚动
function scrollLoading(){
    if($(".scrollLoading").length>0)
        $(".scrollLoading").scrollLoading();
}
//注册购物车事件
function reg_cart(){
	$('.load_cart').hover(function() {
	    $('#cart_info').show();
	   }, function() {
	    $('#cart_info').hide();
	});
}
//参与人数
if($("#BIDNUMBER").length>0){
    showBidNumber(logCount,1);
    getBidNumberData();
    setInterval('getBidNumberData()', 100000);
}
scrollLoading();
reg_cart();
loadCart();
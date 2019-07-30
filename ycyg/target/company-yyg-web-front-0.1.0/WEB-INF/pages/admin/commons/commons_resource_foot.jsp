<%@ page language="java" pageEncoding="UTF-8"%>
<script src="${vendorsBase}/jquery.min.js?v=${version}" type="text/javascript"></script>
<script src="${vendorsBase}/bootstrap/bootstrap.min.js?v=${version}" type="text/javascript"></script>
<script src="${vendorsBase}/bootstrap/jquery.bootstrap.min.js?v=${version}" type="text/javascript"></script>
<script src="${vendorsBase }/amazeui/js/amazeui.min.js?v=${version}" type="text/javascript"></script>
<script src="${vendorsBase }/jquery-form/jquery.form.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$(".leftsidebar_box dd").hide();
		$(".leftsidebar_box dt").click(function() {
			//全部置为原始橙色
			$(".leftsidebar_box dl").css({
				"background-color" : "#FB805A"
			})
			$(this).parent().css({
				"background-color" : "#D63707"
			});
			$(this).parent().find('dd').removeClass("menu_chioce");
			$(".leftsidebar_box dt .leftsidebar-img").removeClass("active");
			$(this).parent().find('.leftsidebar-img').addClass("active");
			$(".menu_chioce").slideUp();
			$(this).parent().find('dd').slideToggle();
			$(this).parent().find('dd').addClass("menu_chioce");
		});
		
		//第一次加载执行，展开相应的菜单
		var hash = window.location.hash;
		if (hash && hash.length > 1) {
			hash = hash.substring(1);
			var search = "a[data='" + hash + "']";
			var a = $(".leftsidebar_box dl dd " + search);
			var dl = a.parent().parent();
			var dt = dl.find("dt");
			//最顶级菜单，置为选中
			dl.css({
				"background-color" : "#D63707"
			});
			dl.find('.leftsidebar-img').addClass("active");
			dl.find('dd').addClass("menu_chioce");
			a.parent().addClass("activity");
			dl.find('dd').show();
		}
		
		$("#hl-topbar-brand-bars").click(function() {
			var sidebar = $("#hl-left-sidebar").css("display");
			if (sidebar == "block") {
				$("#hl-left-sidebar").addClass("active");
				$("#hl-admin-content").addClass("active");
			} else {
				$("#hl-left-sidebar").removeClass("active");
				$("#hl-admin-content").removeClass("active");
			}
		});
	})
</script>
<script type="text/javascript">
	function showConfirmDialog(callback, msg, title) {
		$.messager.model = {
			ok : {
				text : "确定",
				classed : 'btn-danger'
			},
			cancel : {
				text : "取消",
				classed : 'btn-default'
			}
		};
		$.messager.confirm(title || "提醒", msg || "确定?", function() {
			callback && callback();
		});
		return false;
	}

	function showAlertDialog(msg, title) {
		$.messager.model = {
			ok : {
				text : "知道了",
				classed : 'btn-primary'
			},
		};
		$.messager.alert(title || "注意", msg || "");
	}

	function showPopup(message) {
		$.messager.popup(message);
	}
</script>
<script type="text/javascript">
	var site_preview = function() {
		if ($.support.leadingWhitespace) {
			alert("不支持在IE浏览器下预览，建议使用谷歌浏览器或者360极速浏览器或者直接在微信上预览。");
			return;
		}
		var left = ($(window).width() - 450) / 2;
		window.open("/site/575", "我的微官网", "height=650,width=450,top=0,left=" + left + ",toolbar=yes,menubar=yes,scrollbars=no, resizable=no,location=no, status=no");
	}
</script>
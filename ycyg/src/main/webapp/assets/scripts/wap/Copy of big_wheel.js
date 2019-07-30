//
var BigWheelObject = {
	init : function() {
		// “我的奖品”按钮事件
		this._reg_my_prize_btn_event();
		// 检查是否弹出登录信息
		this._is_show_login_modal();
		// 抽奖按钮事件
		this._reg_prize_btn_event();
	},
	// 校验初始是否显示个人信息收集框
	_check_is_to_show_user_collect_into_modal : function() {
		if (isLogin == true) {
			$("#wz-modal-user").modal({
				closeViaDimmer : 0
			});
			$("#wz-modal-user").modal("open");
		}
	},
	// 抽奖按钮事件
	_reg_prize_btn_event : function() {
		/*
		 * //抽奖前要收集用户信息 if(isLogin ==true &&
		 * isCollectUserInfoBeforePrize==true){ }
		 */

		$('#plateBtn').click(function() {
			if (!waiting) {
				if ($.trim($("#lottery_count").html()) != 0) {
					// 还有抽奖次数
					$.ajax({
						type : 'get',
						url : "/bigwheel/" + bigWheelId + "/prize.json",
						// data:{},
						timeout : 1000 * 60,
						cache : false,
						async : true,
						beforeSend : function(req) {
							waiting = true;
						},
						success : function(data) {
							var message = data.message;
							if (data.isSuccess) {
								lottery_count = message.lastTimes;
								if (lottery_count == -1) {
									$("#lottery_count").html("无限次");
								} else {
									$("#lottery_count").html(lottery_count);
								}
								BigWheelObject._rotate_prize(message);
							} else {
								// alertModal(json.msg);
								showFormError(data.message);
							}
						},
						error : function() {
							showFormError("系统繁忙，请稍后重试！");
						},
						complete : function(req, status) {
							if (waitingTimeOut) {
								clearTimeout(waitingTimeOut);
							}
							var waitingTimeOut = setTimeout(function() {
								waiting = false;
							}, 2 * 1000);
						}
					});
				} else {
					showFormError("您的抽奖机会用完了！");
				}
			} else {
				showFormError("您抽奖频率太快了, 休息一下吧!");
			}
		});
	},
	// 脚处理抽奖结果过程
	_rotate_prize : function(prizeResultDto) {
		var $plateBtn = $('#plateBtn');
		$plateBtn.stopRotate();

		// 找到转盘刻度
		var $prize = $(".prizeId" + prizeResultDto.prizedId).eq(0);
		var index = $prize.attr("index");
		// 360/8=45 八格，每格45度
		// 请处出度数
		var dushu = 45 * index - 23;
		var isNeedCollectUserInfo = prizeResultDto.isNeedCollectUserInfo;
		$plateBtn.rotate({
			angle : 0,
			duration : 5000,
			animateTo : dushu + 1440 - 180,
			callback : function() {
				if (prizeResultDto.isPrized == true) {
					$("#btn_result").html("领奖");
				} else {
					$("#btn_result").html("继续抽奖");
				}
				$("#wz-modal-result").modal({
					closeViaDimmer : 0,
					onConfirm : function() {
						// 是否中奖
						if (prizeResultDto.isPrized == true) {
							// 是否需要收集用户信息
							if (isNeedCollectUserInfo == true) {
								$("#wz-modal-user").modal({
									closeViaDimmer : 0
								});
								$("#wz-modal-user").modal("open");
							} else {
								// 领奖方式:在线领取
								if (prizeResultDto.acceptPrizeType == 2) {
									window.location.href = prizeResultDto.prizeString;
								} else if (prizeResultDto.acceptPrizeType == 1) {
									// 领奖方式:优惠券
									$("#wz-modal-result1").modal({
										closeViaDimmer : 0
									});
									$("#wz-modal-result1-title").html("请保存优惠券, 关闭页面后无法找回");
									$("#wz-modal-result1-info").html(prizeResultDto.prizeString);
									$("#wz-modal-result1").modal("open");
								} else if (prizeResultDto.acceptPrizeType == 3) {
									// 领奖方式:自提
									$("#wz-modal-result1").modal({
										closeViaDimmer : 0
									});
									$("#wz-modal-result1-title").html("请至以下地址自提奖品");
									$("#wz-modal-result1-info").html(prizeResultDto.prizeString);
									$("#wz-modal-result1").modal("open");
								} else {
									showFormError("领奖申请已提交!");
								}
							}
						} else {
							$plateBtn.click();
						}
					}
				});
				// 显示优惠券
				$("#dialog-info-result").html(prizeResultDto.prizeName);
				$("#wz-modal-result").modal("open");
			}
		});
	},
	// “我的奖品”按钮事件
	_reg_my_prize_btn_event : function() {
		$("#btn-my-prizes").on("click", function() {
			$("#ul_my_prizes").html("");
			$("#wz-modal-my-prizes").modal("open");
			$.ajax({
				type : 'get',
				url : "/bigwheel/" + bigWheelId + "/myprize.json",
				// data:{},
				timeout : 1000 * 60,
				async : true,
				success : function(data) {
					if (data.isSuccess) {
						if (data.message && data.message.length > 0) {
							$.each(data.message, function(indes, obj) {
								$("#ul_my_prizes").append('<li><span>' + obj.prizeName + '</span></li>');
							});
						} else {
							$("#ul_my_prizes").html("没有中奖记录!");
						}
					} else {
						showFormError(json.msg);
					}
				},
				error : function() {
					showFormError("系统繁忙，请稍后重试！");
				}
			});
		});
	},
	// 检查是否弹出登录信息
	_is_show_login_modal : function() {
		if (isLogin == false) {
			$("#wz-modal-phone").modal({
				closeViaDimmer : 0
			})
			$("#wz-modal-phone").modal("open");
		}
	},
	// 异步检查提交手机号
	_reg_check_phone_and_login_btn : function() {
		$("#pf-login-btn").click(function() {
			if (check_phone($("#phone1").val()) == true) {
				// 异步请求
				$.ajax({
					type : "GET",
					cache : false,
					dataType : "json",
					url : "/bigwheel/" + bigWheelId + "/checkphone.json?tel=" + $("#phone1").val(),
					success : function(data) {
						if (data != undefined && data.isSuccess == true) {
							// 校验成功
							var checkPhoneDto = data.message;
							// 一、还剩抽奖次数
							if (checkPhoneDto.lastTimes == -1) {
								$("#lottery_count").html("不限次数")
								lottery_count = -1;
							} else {
								$("#lottery_count").html(checkPhoneDto.lastTimes);
								lottery_count = checkPhoneDto.lastTimes;
							}
							// 二、是否已完善良用户信息
							isUserInfoCommit = checkPhoneDto.isUserInfoCommit;
							$("#wz-modal-phone").modal('close');

							// 三、判断是否，弹出收集用户信息窗口，如果是：抽奖前要收集用户信息，而还没有收集信息，则弹出收集信息窗口
							if (isCollectUserInfoBeforePrize == true && isUserInfoCommit == false) {
								$("#wz-modal-user").modal({
									closeViaDimmer : 0
								});
								$("#wz-modal-user").modal("open");
							}
						} else {
							showFormError(data.message);
						}
					}
				});
			}
		});
		// 检查首页 登录 时的电话号码
		function check_phone(phone) {
			if (!phone) {
				showFormError("请填写手机号!");
				return false;
			}
			if (phone && !phone.match("^[1][0-9][0-9]{9}$")) {
				showFormError("请填写正确的手机号码!");
				return false;
			}
			return true;
		}
	}
}
BigWheelObject.init();
// 显示校验出错弹出信息
function showFormError(err) {
	$("#form_error_info").html(err);
	$("#form_error_info").show();
	if (errorTimeOut) {
		clearTimeout(errorTimeOut);
	}
	var errorTimeOut = setTimeout(function() {
		$("#form_error_info").hide();
	}, 2 * 1000);
}

$(function() {
	var $plateBtn = $('#plateBtn');
	var $result = $('#result');
	var $resultTxt = $('#resultTxt');
	var $resultBtn = $('#resultBtn');
	$resultBtn.click(function() {
		$result.hide();
	});
});
$(function() {
	if (flashTimeOut) {
		clearTimeout(flashTimeOut);
	}
	var flashTimeOut = setTimeout(function() {
		$(".wz-flash-info").hide();
	}, 2 * 1000);
});
// modal
function alertModal(message) {
	$("#dialog_info_message").html(message);
	$("#dialog_info").modal("open");
}

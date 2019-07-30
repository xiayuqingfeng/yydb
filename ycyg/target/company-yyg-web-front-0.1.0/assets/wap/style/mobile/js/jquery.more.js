(function($) {
	var target = null;
	var template = null;
	var lock = false;
	var variables = {
		'last' : 0
	};
	var settings = {
		'status' : '0',
		'amount' : '10',
		'address' : 'comments.php',
		'format' : 'json',
		'template' : '.item',
		'trigger' : '.getMore',
		'scroll' : 'false',
		'offset' : '100',
		'spinner_code' : '',
		'more' : "点击获取更多...",
		'nomore' : "没有更多了！"
	};

	var methods = {
		init : function(options) {
			return this.each(function() {
				if (options) {
					$.extend(settings, options);
				}
				template = $(this).children(settings.template).wrap('<div/>').parent();
				template.css('display', 'none');
				$(this).append('<div class="more_loader_spinner">' + settings.spinner_code + '</div>');
				$(this).children(settings.template).remove();
				target = $(this);
				$(settings.trigger).html(settings.more);

				if (settings.scroll == 'false') {
					$(settings.trigger).bind('click.more', methods.get_data);
					$(this).more('get_data');
				} else {
					if ($(this).height() <= $(this).attr('scrollHeight')) {
						target.more('get_data', settings.amount * 2);
					}
					$(this).bind('scroll.more', methods.check_scroll);
				}
			})
		},
		check_scroll : function() {
			if ((target.scrollTop() + target.height() + parseInt(settings.offset)) >= target.attr('scrollHeight') && lock == false) {
				target.more('get_data');
			}
		},
		debug : function() {
			var debug_string = '';
			$.each(variables, function(k, v) {
				debug_string += k + ' : ' + v + '\n';
			})
			alert(debug_string);
		},
		remove : function() {
			$(settings.trigger).unbind('.more');
			target.unbind('.more');
			$(settings.trigger).html(settings.nomore);
		},
		add_elements : function(data) {
			var root = target
			var counter = 0;
			if (data) {
				$(data).each(function() {
					counter++
					var t = template
					$.each(this, function(key, value) {
						if (t.find('.' + key).length > 0) {
							t.find('.' + key).html(value);
						} else {
							t.find(settings.template).html(value);
						}
					})
					if (settings.scroll == 'true') {
						root.children('.more_loader_spinner').before(t.html());
					} else {
						root.children('.more_loader_spinner').before(t.html());
						// $(settings.trigger).before(t.html())
					}

					root.children(settings.template + ':last').attr('id', 'more_element_' + ((variables.last++) + 1))
				})
			} else
				methods.remove()
			target.children('.more_loader_spinner').css('display', 'none');
			if (counter < settings.amount)
				methods.remove()

		},
		get_data : function() {
			// alert('getting data')
			var ile;
			lock = true;
			target.children(".more_loader_spinner").css('display', 'block');
			$(settings.trigger).css('display', 'none');
			if (typeof (arguments[0]) == 'number')
				ile = arguments[0];
			else {
				ile = settings.amount;
			}
			var YYGpage = 1;
			if (variables.last == 0) {
				YYGpage = 1;
			} else {
				YYGpage =  variables.last/ile+1;
			}
			$.post(settings.address + "/" + YYGpage, {
				last : variables.last,
				amount : ile,
				status : settings.status
			}, function(data) {
				$(settings.trigger).css('display', 'block')
				methods.add_elements(data);
				lock = false;
			}, settings.format)

		}
	};
	$.fn.more = function(method) {
		if (methods[method])
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		else if (typeof method == 'object' || !method)
			return methods.init.apply(this, arguments);
		else
			$.error('Method ' + method + ' does not exist!');

	}
})(jQuery)
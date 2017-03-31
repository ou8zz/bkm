/**
Themes script to handle the theme 
**/
var Themes = function() {

    // Handle Theme Settings
    var handleTheme = function() {

        var lastSelectedLayout = '';

        var setLayout = function() {

        	var layoutOption = "fluid";						// fluid全屏 boxed中央居中
            var sidebarOption = "fixed";					// 左侧菜单边栏固定
            var headerOption = "fixed";						// 顶部固定
            var footerOption = "default";					// footer是否固定，默认不固定
            var sidebarPosOption = "left";					// 菜单浮动left
            var headerTopDropdownStyle = "default";			// 设置顶部下拉样式和主题风格是否一样 dark and default
            var sidebarStyleOption = "light";				// 设置菜单选中样式
            var sidebarMenuOption = "";
            $.cookie('layout-style-option', 'default');		// 设置边角rounded
            
            if (sidebarOption == "fixed" && headerOption == "default") {
                alert('Default Header with Fixed Sidebar option is not supported. Proceed with Fixed Header with Fixed Sidebar.');
                sidebarOption = 'fixed';
                headerOption = 'fixed';
            }

            if (layoutOption === "boxed") {
                $("body").addClass("page-boxed");

                // set header
                $('.page-header > .page-header-inner').addClass("container");
                var cont = $('body > .clearfix').after('<div class="container"></div>');

                // set content
                $('.page-container').appendTo('body > .container');

                // set footer
                if (footerOption === 'fixed') {
                    $('.page-footer').html('<div class="container">' + $('.page-footer').html() + '</div>');
                } else {
                    $('.page-footer').appendTo('body > .container');
                }
            }

            if (lastSelectedLayout != layoutOption) {
                //layout changed, run responsive handler: 
                Metronic.runResizeHandlers();
            }
            lastSelectedLayout = layoutOption;

            //header
            if (headerOption === 'fixed') {
                $("body").addClass("page-header-fixed");
                $(".page-header").removeClass("navbar-static-top").addClass("navbar-fixed-top");
            } else {
                $("body").removeClass("page-header-fixed");
                $(".page-header").removeClass("navbar-fixed-top").addClass("navbar-static-top");
            }

            //sidebar
            if ($('body').hasClass('page-full-width') === false) {
                if (sidebarOption === 'fixed') {
                    $("body").addClass("page-sidebar-fixed");
                    $("page-sidebar-menu").addClass("page-sidebar-menu-fixed");
                    $("page-sidebar-menu").removeClass("page-sidebar-menu-default");
                    Layout.initFixedSidebarHoverEffect();
                } else {
                    $("body").removeClass("page-sidebar-fixed");
                    $("page-sidebar-menu").addClass("page-sidebar-menu-default");
                    $("page-sidebar-menu").removeClass("page-sidebar-menu-fixed");
                    $('.page-sidebar-menu').unbind('mouseenter').unbind('mouseleave');
                }
            }

            // top dropdown style
            if (headerTopDropdownStyle === 'dark') {
                $(".top-menu > .navbar-nav > li.dropdown").addClass("dropdown-dark");
            } else {
                $(".top-menu > .navbar-nav > li.dropdown").removeClass("dropdown-dark");
            }

            //footer 
            if (footerOption === 'fixed') {
                $("body").addClass("page-footer-fixed");
            } else {
                $("body").removeClass("page-footer-fixed");
            }

            //sidebar style
            if (sidebarStyleOption === 'light') {
                $(".page-sidebar-menu").addClass("page-sidebar-menu-light");
            } else {
                $(".page-sidebar-menu").removeClass("page-sidebar-menu-light");
            }

            //sidebar menu 
            if (sidebarMenuOption === 'hover') {
                if (sidebarOption == 'fixed') {
                    alert("Hover Sidebar Menu is not compatible with Fixed Sidebar Mode. Select Default Sidebar Mode Instead.");
                } else {
                    $(".page-sidebar-menu").addClass("page-sidebar-menu-hover-submenu");
                }
            } else {
                $(".page-sidebar-menu").removeClass("page-sidebar-menu-hover-submenu");
            }

            //sidebar position
            if (sidebarPosOption === 'right') {
                $("body").addClass("page-sidebar-reversed");
                $('#frontend-link').tooltip('destroy').tooltip({
                    placement: 'left'
                });
            } else {
                $("body").removeClass("page-sidebar-reversed");
                $('#frontend-link').tooltip('destroy').tooltip({
                    placement: 'right'
                });
            }

            Layout.fixContentHeight(); // fix content height            
            Layout.initFixedSidebar(); // reinitialize fixed sidebar
        };

       
        setLayout();
//        $('.layout-option, .page-header-option, .page-header-top-dropdown-style-option, .sidebar-option, .page-footer-option, .sidebar-pos-option, .sidebar-style-option, .sidebar-menu-option', panel).change(setLayout);
    };

    // handle theme style
    var setThemeStyle = function(style) {
        var file = (style === 'rounded' ? 'components-rounded' : 'components');

        $('#style_components').attr("href", Metronic.getGlobalCssPath() + file + ".css");

        if ($.cookie) {
            $.cookie('layout-style-option', style);
        }
    };

    // handle theme colors
    var setColor = function(color) {
        var color_ = color;
        var th_path = Layout.getLayoutCssPath() + 'themes/' + color_ + ".css";
        $.cookie('layout-style-color', color);
        $('#style_color').attr("href", th_path);
        if (color == 'light2') {
            $('.page-logo img').attr('src', Layout.getLayoutImgPath() + 'logo-invert.png');
        } else {
            $('.page-logo img').attr('src', Layout.getLayoutImgPath() + 'logo.png');
        }
    };
    
    return {
    	setColor:setColor,
        //main function to initiate the theme
        init: function() {
			$.cookie.defaults = {
				// 是否以严格模式读取和设置cookie，默认true
				// 严格模式将编码键值对再设置，非严格模式将不进行编码
				// 严格模式将解码键值对再读取，非严格模式将不进行解码
				isStrict : true,
				// 在无域名的时候，必须设置为空才能在本地写入
				domain : "",
				// 默认cookie有效期416天（单位秒）
				expires : 3600,
				// 默认cookie存储路径
				path : "/bkm",
				// 是否加密cookie
				secure : false
			};
        	
            // handles style customer tool
            handleTheme();

            // set layout style from cookie
            if ($.cookie && $.cookie('layout-style-option') === 'rounded') {
                setThemeStyle($.cookie('layout-style-option'));
            }
            
         // set layout style from cookie
            if ($.cookie && $.cookie('layout-style-color')) {
                var color_ = $.cookie('layout-style-color');
                var th_path = Layout.getLayoutCssPath() + 'themes/' + color_ + ".css";
                $('#style_color').attr("href", th_path);
                if (color_ == 'light2') {
                    $('.page-logo img').attr('src', Layout.getLayoutImgPath() + 'logo-invert.png');
                } else {
                    $('.page-logo img').attr('src', Layout.getLayoutImgPath() + 'logo.png');
                }
            }
            
//            $("body").addClass("page-sidebar-fixed");
//            $("page-sidebar-menu").addClass("page-sidebar-menu-fixed");
//            $("page-sidebar-menu").removeClass("page-sidebar-menu-default");
//            Layout.initFixedSidebarHoverEffect();
            
            // 设置颜色绑定事件
            $("#header_themes_bar").find(".dropdown-themes").find("ul").find("li").click(function(d) {
            	var style = $(this).attr("data-style");
            	setColor(style);
            });
        }
    };

}();
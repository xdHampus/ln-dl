let dleSearchDelay=!1,dleSearchValue="",idDomSearch="#story";if($("#ajax_search").length&&(idDomSearch="#ajax_search"),"function"==typeof FastSearch){}function formNavigation(e){return e=e<0?0:e,$("#from_page").val(e),$("#fullsearch").submit(),!1}function dleSearch(){$(idDomSearch).attr("autocomplete","off"),$(idDomSearch).blur((function(){$("#searchsuggestions").fadeOut()})),$(idDomSearch).keyup((function(){let e=$(this).val();0===e.length?$("#searchsuggestions").fadeOut():dleSearchValue!==e&&e.length>=dleSearchConfig.minChar&&e.length<=dleSearchConfig.maxChar&&(clearInterval(dleSearchDelay),dleSearchDelay=setInterval((function(){dleSearchInterval(e)}),200))}))}function dleSearchInterval(e){clearInterval(dleSearchDelay),$("#searchsuggestions").remove(),$("body").append("<div id='searchsuggestions' style='display:none'></div>"),$.post(dle_root+"engine/lazydev/dle_search/ajax.php",{story:e,dle_hash:dle_login_hash,thisUrl:dleSearchConfig.page},(function(e){"return"===(e=$.parseJSON(e)).content||e.error||$("#searchsuggestions").html(e.content).fadeIn().css({position:"absolute",top:0,left:0}).position({my:"left top",at:"left bottom",of:idDomSearch,collision:"fit flip"})})),dleSearchValue=e}$((function(){$(idDomSearch).attr("maxlength",dleSearchConfig.maxChar).attr("minlength",dleSearchConfig.minChar),$("body").on("submit","form",(function(e){$(this).find(idDomSearch).length>0&&(!$(idDomSearch).val().trim()||$(idDomSearch).val().trim().length<dleSearchConfig.minChar||$(idDomSearch).val().trim().length>dleSearchConfig.maxChar)&&e.preventDefault()})),$("body").on("click","#dosearch",(function(){formNavigation(-1)})),$("body").on("click","#searchsuggestions a",(function(e){e.preventDefault();let t=void 0===$(this).find("a").context?$(this)[0].href:$(this).find("a").context.href;/(\d+)\-(\w+)\.html/i.test(t)||/(\d+)\/(\d+)\/(\d+)\/(\w+)\.html/i.test(t)?$.post(dle_root+"engine/lazydev/dle_search/ajax.php",{news:t,dle_hash:dle_login_hash},(function(e){window.location.href=t})):window.location.href=t})),"search"==dleSearchPage&&$("body").on("click","#dle-content a:not([onclick])",(function(e){e.preventDefault();let t=void 0===$(this).find("a").context?$(this)[0].href:$(this).find("a").context.href;/(\d+)\-(\w+)\.html/i.test(t)||/(\d+)\/(\d+)\/(\d+)\/(\w+)\.html/i.test(t)?$.post(dle_root+"engine/lazydev/dle_search/ajax.php",{news:t,dle_hash:dle_login_hash},(function(e){window.location.href=t})):window.location.href=t}))})),0===dleSearchConfig.ajax&&dleSearch(),1===dleSearchConfig.url&&$("body").on("submit","form",(function(e){if($(this).find(idDomSearch).length>0){e.preventDefault();let t=$(this).find(idDomSearch).val().trim();t&&$.post(dle_root+"engine/lazydev/dle_search/ajax.php",{story:t,gu:1,dle_hash:dle_login_hash},(function(e){window.location.href="/search/"+e}))}}));
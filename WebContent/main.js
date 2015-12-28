function doMagic(){
	$("#wishboxContainer").animate({
		'margin-top' : '0px'
	},750);
	var retData = getData($("#wishbox").val());
	var allResults = [];
	$(retData).each(function(i,val){
	    $.each(val,function(k,v){
	         if(k === "events"){
	        	 $.each(v, function(index, item) {
	        		 //console.log(item.title);
	        		 var toDisplay = {};
	        		 toDisplay.title = item.title;
	        		 toDisplay.desc = item.description;
	        		 toDisplay.price = item.ticketInfo.minPrice;
	        		 toDisplay.business = "Stubhub";
	        		 toDisplay.url = item.eventInfoUrl;
	        		 toDisplay.logo = "stadium.jpg";
	        		 allResults.push(toDisplay);
	        	 });
	         } else if(k === "hits"){
	        	 var hitsArr = v.hits;
	        	 console.log(hitsArr);
	        	 for(var i=0;i<hitsArr.length;i++){
        			if(i>=5){
        				break;
        			}
        			var toDisplay = {};
        			var id = hitsArr[i]._source.id;
        			var merchantInfo = getMerchantInfo(id);
	        		 toDisplay.title = hitsArr[i]._source.data.menu[0].children[0].name;
	        		 toDisplay.desc = hitsArr[i]._source.data.menu[0].children[0].description;
	        		 toDisplay.price = hitsArr[i]._source.data.menu[0].children[0].price;
	        		 toDisplay.business = hitsArr[i]._source.name;
	        		 toDisplay.url = merchantInfo._source.summary.url.complete;
	        		 toDisplay.logo = merchantInfo._source.summary.merchant_logo;
	        		 allResults.push(toDisplay);
	        	 }
	         }   
	    });
	});
	//console.log(allResults);
	$("#all-rests").empty();
	var d = 200;
	for(var i=0;i<allResults.length;i++){
		if(i>=5){
			break;
		}
		var toDisplay = allResults[i];
		var business = toDisplay.business;
		var factor = d / 3 * 2;
		$("#all-rests").append(
							$('<li>').delay(d = d + factor).hide().fadeIn(1500).append(
									$('<div>').attr('class',"post-container").append(
											$('<div>').attr('class',"post-thumb").append(
													$('<img>').attr('id','logo').attr('src',toDisplay.logo))).append(
															$('<div>').attr('class',"post-content").append(
																	$('<div>').attr('class',"title-business").append(
																			$('<h3>').attr('id','title').attr('class','post-title').text(toDisplay.title)).append(
																			$('<a>').attr('id','business').attr('class','nobubble').attr('target',"_blank").attr('href',toDisplay.url).text(toDisplay.business))).append(
																					$('<p>').attr('id','desc').text(toDisplay.desc)).append(
																							$('<p>').attr('id','price').text("Price: $"+toDisplay.price)))));
	}
	var filledList = $("#all-rests").html();
	localStorage.setItem('filledList', filledList);
}

/*function doMagic(){
	$("#wishboxContainer").animate({
		'margin-top' : '0px'
	},750);
	var query = $("#wishbox").val()
	var retData = getData(query);
	var allResults = [];
	$(retData).each(function(i,val){
	    $.each(val,function(k,v){
	         if(k === "events"){
	        	 $.each(v, function(index, item) {
	        		 //console.log(item.title);
	        		 var toDisplay = {};
	        		 toDisplay.title = item.title;
	        		 toDisplay.desc = item.description;
	        		 toDisplay.price = item.ticketInfo.minPrice;
	        		 toDisplay.business = "Stubhub";
	        		 toDisplay.url = item.eventInfoUrl;
	        		 toDisplay.logo = "stadium.jpg";
	        		 allResults.push(toDisplay);
	        	 });
	         } else if(k === "hits"){
	        	 var hitsArr = v.hits;
	        	 console.log(hitsArr);
	        	 for(var i=0;i<hitsArr.length;i++){
        			if(i>=5){
        				break;
        			}
        			var toDisplay = {};
        			var id = hitsArr[i]._source.id;
        			var merchantInfo = getMerchantInfo(id);
	        		 toDisplay.title = hitsArr[i]._source.data.menu[0].children[0].name;
	        		 toDisplay.desc = hitsArr[i]._source.data.menu[0].children[0].description;
	        		 toDisplay.price = hitsArr[i]._source.data.menu[0].children[0].price;
	        		 toDisplay.business = hitsArr[i]._source.name;
	        		 toDisplay.url = merchantInfo._source.summary.url.complete;
	        		 toDisplay.logo = merchantInfo._source.summary.merchant_logo;
	        		 allResults.push(toDisplay);
	        		 var toDisplay = {};
	        		 var id = hitsArr[i]._source.id;
	        		 var merchantInfo = getMerchantInfo(id);
	        		 var title = hitsArr[i]._source.data.menu[i].children[i].name;
	        		 console.log(hitsArr[i]._source.data.menu[i]);
	        		 console.log(title);
	        		 if(title.indexOf(query) > -1){
	        			 toDisplay.title = title;
		        		 toDisplay.desc = hitsArr[i]._source.data.menu[i].children[i].description;
		        		 toDisplay.price = hitsArr[i]._source.data.menu[i].children[i].price;
		        		 toDisplay.business = hitsArr[i]._source.name;
		        		 toDisplay.url = merchantInfo._source.summary.url.complete;
		        		 toDisplay.logo = merchantInfo._source.summary.merchant_logo;
		        		 allResults.push(toDisplay);
	        		 }
	        	 }
	         }   
	    });
	});
	//console.log(allResults);
	$("#all-rests").empty();
	var d = 200;
	for(var i=0;i<allResults.length;i++){
		if(i>=5){
			break;
		}
		var toDisplay = allResults[i];
		var business = toDisplay.business;
		var factor = d / 3 * 2;
		$("#all-rests").append(
							$('<li>').delay(d = d + factor).hide().fadeIn(1500).append(
									$('<div>').attr('class',"post-container").append(
											$('<div>').attr('class',"post-thumb").append(
													$('<img>').attr('id','logo').attr('src',toDisplay.logo))).append(
															$('<div>').attr('class',"post-content").append(
																	$('<div>').attr('class',"title-business").append(
																			$('<h3>').attr('id','title').attr('class','post-title').text(toDisplay.title)).append(
																			$('<a>').attr('id','business').attr('class','nobubble').attr('target',"_blank").attr('href',toDisplay.url).text(toDisplay.business))).append(
																					$('<p>').attr('id','desc').text(toDisplay.desc)).append(
																							$('<p>').attr('id','price').text("Price: $"+toDisplay.price)))));
	}
	var filledList = $("#all-rests").html();
	localStorage.setItem('filledList', filledList);
}*/

function getMerchantInfo(merchantId){
	var resp = null;
	$.ajax({
        type: 'GET',
        url: 'http://localhost:9200/delivery/merchants/' + merchantId,
        async : false,
        success: function(data){
        	resp = data;
        },
         failure : function(emsg) {
             alert("Error:::" + emsg);
         }
     });
	return resp;
}

function getData(query){
	var resp = null;
	if(query.indexOf("ticket") > -1 || query.indexOf("badger") > -1){
		$.ajax({
	        type: 'GET',
	        url: 'https://api.stubhub.com/search/catalog/events/v2?q=' + query,
	        headers: {'Authorization': 'Bearer 8Ts_tm5pQH24UW6Ar87cSaWW2Iwa'},
	        async : false,
	        success: function(data){
	        	resp = data;
	        },
	         failure : function(emsg) {
	             alert("Error:::" + emsg);
	         }
	     });
	} else {
		var jsonReq = {query: {
		        query_string: {
		            query:query,
		            fields: ["data.menu.children.name"]
		        }
		    }
		};
		$.ajax({
	        type: 'POST',
	        url: 'http://localhost:9200/_search',
	        data: JSON.stringify(jsonReq),
	        async : false,
	        success: function(data){
	        	resp = data;
	        },
	         failure : function(emsg) {
	             alert("Error:::" + emsg);
	         }
	     });
	}
		
	return resp;
}

function showConfirmation(object){
	var title = $(object).find("#title").text();
	var business = $(object).find("#business").text();
	var price = $(object).find("#price").text().split(":")[1];
	var logoUrl = $(object).find("#logo").attr('src');
	localStorage.setItem('title', title);
	localStorage.setItem('business',business);
	localStorage.setItem('price', price);
	localStorage.setItem('logoUrl', logoUrl);
	window.location = "confirmation.html";
}
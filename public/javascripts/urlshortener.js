$( document ).ready(function() {
    console.log( "ready!" );

    var URI_PREFIX = "http://localhost:9000/";
    var URL_SHORTENER = "shorturl";

    // ajax call to generate short URL endpoint
    function generateShortURL( url ){
        $.ajax({
            url: URI_PREFIX + URL_SHORTENER,
            data: JSON.stringify({ "original" : url }),
            type: "POST",
            dataType: "json",
            contentType: "application/json"
        })
        .done( function( url ){
            if( url ) {
                console.log("Successful response: " + url);
                $("#shortUrl").append( $("<h4></h4>").text("Your shorted URL: "))
                .append($("<br />"))
                .append( $("<p></p>")
                .append($("<a></a>").attr("href", url.shortURL).text(url.shortURL)));
            }
        })
        .fail( function(){
            console.log("No short URL generated");
            $("#shortUrl").append( $("<h4></h4>").text("Sorry an error has occurred"));
        })
        .always( function(){
            console.log("Request done");
        });
    }

    $("#submit").click( function() {

        $("#shortUrl").empty();

        //get url from the input field
        var url = $("#url").val();

        generateShortURL( url );
    });

});
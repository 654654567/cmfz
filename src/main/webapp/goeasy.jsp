<%@page pageEncoding="UTF-8" contentType="text/html; utf-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="${app}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script>
        var goEasy = new GoEasy({
            appkey: "BC-ad807c30743c43c69b5f98598d90354c"
        });
        goEasy.subscribe({
            channel: "lts",
            onMessage: function (message) {//alert("Channel:" + message.channel + " content:" + message.content);
                var p = $("<p>" + message.content + "</p>")
                $("#asd").append(p)
            }
        });

        function lts() {
            var text = $("#tex").val();
            $("#tex").val("")
            $.ajax({
                url: "${app}/goeasy/lts",
                type: "POST",
                datatype: "JSON",
                data: {"context": text}
            })
        }

    </script>
</head>
<body>
<div style="margin: 0 auto; width: 100% " id="asd">
    <input type="text" id="tex" name="context">
    <button onclick="lts()">发送</button>
</div>

</body>
</html>
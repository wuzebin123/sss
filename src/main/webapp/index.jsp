<%@page contentType="text/html; utf-8" isELIgnored="false" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
    <script>
        $(function () {
            $("#bt").click(
                function () {
                    var code = $("#in").val();
                    $.ajax({
                        url: "${pageContext.request.contextPath}/poem/find",
                        data: {"s": code},
                        type: "post",
                        dataType: "JSON",
                        success: function (data) {
                            $("#di").empty();
                            $.each(data, function (i, c) {
                                var name = $("<h1>").html(c.name);
                                var author = $("<h2>").html(c.author);
                                var type = $("<h3>").html(c.type);
                                var content = $("<h2>").html(c.content);
                                var authordes = $("<h4>").html(c.authordes);
                                $("#di").append(name).append(author).append(type).append(content).append(authordes);
                            })
                        }
                    })
                }
            );
        })
    </script>
</head>
<body>
<center><input type="text" id="in">
    <input type="button" value="aaa" id="bt">
</center>
<div id="di" style="text-align: center">
</div>
</body>
</html>
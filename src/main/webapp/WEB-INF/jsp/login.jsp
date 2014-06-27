<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@include file="./inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache,must-revalidate">
<meta http-equiv="expires" content="0">
<title>Login ...</title>
<style type="text/css">
input {
	width: 200px;
	htight: 20px;
}

.formContent {
	text-align: center;
	margin: 0px auto;
}
</style>
<script type="text/javascript">
    var jcrop_api;
    var cutImg = {
        source : "",
        dest : "",
        top : 0,
        left : 0,
        width : 0,
        height : 0
    };

    $(function() {
        initJcrop();
    });

    function initJcrop() {
        $("#target").Jcrop({
            bgColor : "black",
            bgOpacity : .7,
            aspectRatio : 16 / 9,
            onSelect : updateCoords,
            onChange : updateCoords,
            onRelease : releaseCoords
        }, function() {
            jcrop_api = this;
        });
    }

    function releaseCoords() {
        cutImg.top = 0;
        cutImg.left = 0;
        cutImg.width = 0;
        cutImg.height = 0;
    }

    function updateCoords(c) {
        cutImg.top = c.y;
        cutImg.left = c.x;
        cutImg.width = c.w;
        cutImg.height = c.h;
    };

    function checkCoords() {
        if (cutImg.width > 0 && cutImg.source.length > 0) {
            $.post("cutimg?t=" + new Date().getTime(), cutImg, function(data) {
                if (data.success) {
                    var newSrc = data.msg + "?t=" + new Date().getTime();
                    cutImg.source = data.msg;
                    jcrop_api.destroy();
                    $("#target").attr("src", newSrc);
                    initJcrop();
                } else {
                    alert(data.msg);
                }
            }, "json");
            return;
        } else {
            alert("请选择需要截取的区域.");
        }
    };

    function ajaxFileUpload() {
        $("#loading").ajaxStart(function() {
            $(this).show();
        }).ajaxComplete(function() {
            $(this).hide();
        });
        /* 
         $.ajaxFileUpload({
         url : "upload/image",
         secureuri : false,
         fileElementId : "fileToUpload",
         dataType : "JSON",
         success : function(data, status) {
         if (data.success == true) {
         cutImg.source = data.msg;
         if (jcrop_api != undefined && jcrop_api != null) {
         jcrop_api.destroy();
         }
         $("#target").attr("src", data.msg + "?t=" + new Date().getTime());
         initJcrop();
         } else {
         alert(data.msg);
         }
         },
         error : function(data, status, e) {
         alert(e);
         }
         });
         */
        $.ajaxFileUpload({
            url : "upload/file",
            secureuri : false,
            fileElementId : "fileToUpload",
            dataType : "JSON",
            success : function(data, status) {
                if (data.success == true) {
                    $("#result").html(data.msg);
                } else {
                    alert(data.msg);
                }
            },
            error : function(data, status, e) {
                alert(e);
            }
        });

        return false;

    }

    var area = {
        id : 48,
        name : "哈哈哈哈哈---2",
        descrip : "desc",
        background : "xxxxxxx",
        createTime : null,
        x : 0,
        y : 0,
        width : 0,
        height : 0,
        other : null,
        snbid : null,
        objLst : [ {
            name : "pc001",
            type : "pc",
            descrip : "this is pc111",
            x : 0,
            y : 0,
            width : 0,
            height : 0,
            snbid : null,
            font : null,
            fontSize : null,
            color : null,
            bgcolor : null,
            other : null
        }, {
            name : "pc002",
            type : "pc2",
            descrip : "this is pc2222",
            x : 0,
            y : 0,
            width : 0,
            height : 0,
            snbid : null,
            font : null,
            fontSize : null,
            color : null,
            bgcolor : null,
            other : null
        } ]
    };

    $.post("area/update", {
        data : JSON.stringify(area)
    }, function(msg) {
        if (msg.success) {
            alert("更新成功");
        } else {
            alert(msg.msg);
        }
    }, "JSON");
</script>
</head>
<body>
	<img id="loading" src="resources/img/loading.gif"
		style="display: none;">
	<form name="form" method="POST" enctype="multipart/form-data">
		<table cellpadding="1" cellspacing="1" class="tableForm">
			<thead>
				<tr>
					<th>Please select a file and click Upload button</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input id="fileToUpload" type="file" size="45"
						name="fileToUpload" class="input"></td>
				</tr>
				<tr>
					<td id="result"></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td><button class="button" id="buttonUpload"
							onclick="return ajaxFileUpload();">Upload</button></td>
				</tr>
			</tfoot>
		</table>
	</form>

	<fieldset>
		<img id="target" alt="" src="">

	</fieldset>

	<input type="button" class="btn" value="Crop Image"
		onclick="checkCoords();" />

</body>
</html>
<%@include file="./inc/footer.jsp"%>
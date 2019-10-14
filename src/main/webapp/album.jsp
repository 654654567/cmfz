<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>

<script>
    $(function () {
        $("#album").jqGrid({
            styleUI: "Bootstrap",
            url: "${app}/album/queryAll",
            datatype: "json",
            colNames: ["标题", "分数", "作者", "播音", "章节数", "专辑简介", "状态", "上传时间", "插图"],
            autowidth: true,
            pager: "#page",
            page: 1,
            rowNum: 5,
            height: "60%",
            viewrecords: true,
            rowList: [5, 7, 8],
            editurl: "${app}/album/edit",
            cellEdit: false,
            toolbar: [true, "top"],
            multiselect: true,
            colModel: [
                {
                    name: "title", align: "center", editable: true
                },
                {
                    name: "score", editable: true, align: "center"
                },
                {
                    name: "author", editable: true, align: "center"
                },
                {
                    name: "broadcast", editable: true, align: "center"
                },
                {
                    name: "chapter_count", align: "center"
                },
                {
                    name: "context", editable: true, align: "center"
                },
                {
                    name: "state", editable: true, align: "center", edittype: "select", editoptions: {
                        value: "否:否;是:是"
                    }
                },
                {
                    name: "create_time", align: "center"
                },
                {
                    name: "url",
                    editable: true, edittype: "file", align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img style='height: 80px;width: 100px' src='${app}/img/" + cellvalue + "' />"
                    }
                }
            ],
            subGrid: true,
            subGridRowExpanded: function (subgrid_id, albumId) {
                addSunGrid(subgrid_id, albumId)
            }

        }).jqGrid("navGrid", "#page",
            {
                search: false
            },
            {
                closeAfterEdit: true,
                beforeShowForm: function (data) {
                    data.find("#url").attr("disabled", true)
                }
            },
            {
                closeAfterAdd: true,
                afterSubmit: function (data) {
                    var text = data.responseText;
                    $.ajaxFileUpload({
                        url: "${app}/album/upload",
                        fileElementId: "url",
                        data: {albumId: text},
                        success: function (data) {
                            $("#album").trigger("reloadGrid")
                        }

                    })
                    return data
                }
            },
            {}
        )
    })

    function addSunGrid(sungrid_id, albumId) {
        var tableId = sungrid_id + "table";
        var divId = sungrid_id + "div";
        $("#" + sungrid_id).html(
            "<table id='" + tableId + "'></table><div id='" + divId + "'></div>"
        );
        $("#" + tableId).jqGrid({
            styleUI: "Bootstrap",
            url: "${app}/chapter/queryAll?albumId=" + albumId,
            datatype: "json",
            colNames: ["标题", "大小", "时长", "上传时间", "音频", "操作"],
            autowidth: true,
            pager: "#" + divId,
            page: 1,
            rowNum: 5,
            height: "60%",
            viewrecords: true,
            rowList: [5, 7, 8],
            editurl: "${app}/chapter/edit?albumId=" + albumId,
            cellEdit: false,
            toolbar: [true, "top"],
            multiselect: true,
            colModel: [
                {
                    name: "title", align: "center", editable: true
                },
                {
                    name: "size", align: "center"
                },
                {
                    name: "time", align: "center"
                },
                {
                    name: "create_time", align: "center"
                },
                {
                    name: "mp3_name", align: "center", editable: true, edittype: "file",
                },
                {
                    name: "mp3_name", align: "center",
                    formatter: function (cellValue, options, rowObject) {
                        return "<a onclick=\"playAudio('" + cellValue + "')\" href='#'><span class='glyphicon glyphicon-play-circle'></span></a>" + "                       " +
                            "<a onclick=\"downloadAudio('" + cellValue + "')\" href='#'><span class='glyphicon glyphicon-download'></span></a>"
                    }
                }
            ],
        }).jqGrid("navGrid", "#" + divId,
            {
                search: false
            },
            {
                closeAfterEdit: true,
                beforeShowForm: function (data) {
                    data.find("#mp3_name").attr("disabled", true)
                }
            },
            {
                closeAfterAdd: true,
                afterSubmit: function (data) {
                    var text = data.responseText;
                    $.ajaxFileUpload({
                        url: "${app}/chapter/upload?albumId=" + albumId,
                        fileElementId: "mp3_name",
                        data: {chapterId: text},
                        success: function (data) {
                            $("#" + tableId).trigger("reloadGrid")
                            $("#album").trigger("reloadGrid")
                        }
                    })
                    return data
                }
            },
            {
                afterSubmit: function () {
                    $("#" + tableId).trigger("reloadGrid");
                    $("#album").trigger("reloadGrid");
                    return "adf";
                }
            }
        )
    }

    function playAudio(d) {
        $("#dialogId").modal("show");
        $("#audioId").attr("src", "${app}/mp3/" + d);
    }

    function downloadAudio(a) {
        location.href = "${app}/chapter/download?audioName=" + a;
    }


</script>


<div id="dialogId" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <audio id="audioId" controls src=""></audio>
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="panel panel-default">
    <div class="panel-heading"><h1>专辑与章节管理</h1></div>
</div>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#"><strong>专辑于章节信息</strong></a></li>
</ul>


<table id="album"></table>
<div id="page"></div>